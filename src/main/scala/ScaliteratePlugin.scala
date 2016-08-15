package scaliterate

import sbt._
import Keys._

object ScaliteratePlugin extends AutoPlugin {

  override def requires = plugins.JvmPlugin
  override def trigger = allRequirements

  object autoImport {
    lazy val scaliterate = taskKey[Seq[File]]("Generates Scala sources from Markdown files.")
    lazy val scaliterateSource = settingKey[File]("Input file for scaliterate.")

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
      watchSources in Defaults.ConfigGlobal += scaliterateSource.value,

      /* append to other source generators */
      sourceGenerators in Compile += scaliterate.taskValue
    )
  }

  import autoImport._

  override def projectSettings = inConfig(Compile)(baseScaliterateSettings)

}
