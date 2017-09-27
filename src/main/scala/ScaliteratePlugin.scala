package scaliterate

import scala.sys.process._
import sbt._
import Keys._

object ScaliteratePlugin extends AutoPlugin {

  override def requires = plugins.JvmPlugin
  override def trigger = allRequirements

  object autoImport {
    lazy val scaliterate = taskKey[Seq[File]]("Generates Scala sources from Markdown files.")
    lazy val scaliterateSource = settingKey[File]("Input file for scaliterate.")
    lazy val scaliteratePandocPDF = taskKey[File]("Generates the programming book (PDF) with pandoc.")
    lazy val scaliteratePandocPDFOptions = settingKey[Seq[String]]("Pandoc options to use when generating the programming book.")

    lazy val baseScaliterateSettings: Seq[Def.Setting[_]] = Seq (
      /* markdown source */
      scaliterateSource := baseDirectory.value / (name.value + ".md"),

      /* main task to generate Scala code from markdown code blocks */
      scaliterate := {
        val log = streams.value.log

        val file = scaliterateSource.value

        log.debug(s"""[scaliterate] using input file $file""")

        val output = (sourceManaged in Compile).value / (name.value + ".scala")
        IO.write(output, Scaliterate(file))

        log.debug(s"""[scaliterate] generated output file $output""")

        Seq(output)
      },

      /* watch markdown */
      watchSources += baseDirectory.value / "demo" / "examples.txt",
      watchSources in Defaults.ConfigGlobal += scaliterateSource.value,

      /* append to other source generators */
      sourceGenerators in Compile += scaliterate.taskValue,

      /* use pandoc to generate programming book */
      scaliteratePandocPDF := {
        val log = streams.value.log

        val pdf = target.value / (name.value + ".pdf")

        val cmd: List[String] = List("pandoc") :::
          scaliteratePandocPDFOptions.value.toList :::
          List("-o", pdf.toString, scaliterateSource.value.toString)

        log.debug(s"""[scaliterate] ${cmd.mkString(" ")}""")

        cmd ! log

        log.debug(s"""[scaliterate] wrote $pdf""")

        pdf
      },

      /* sensible, default pandoc options */
      scaliteratePandocPDFOptions := Seq (
        "--standalone",
        "--table-of-contents",
        "--number-sections",
        "--latex-engine=xelatex",
        "-V", "documentclass=report",
        "-V", "linkcolor=blue",
        "-V", "geometry=left=24.1mm",
        "-V", "geometry=right=24.1mm",
        "-V", "geometry=bottom=4.5cm",
        "-V", "fontsize=10pt",
        "-V", "papersize=a4paper"
      )
    )
  }

  import autoImport._

  override def projectSettings = inConfig(Compile)(baseScaliterateSettings)

}
