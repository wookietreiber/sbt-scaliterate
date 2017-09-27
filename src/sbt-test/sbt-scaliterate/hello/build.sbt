lazy val check = taskKey[Unit]("check")

scalaVersion := "2.12.3"

check := {
  val f = (sourceManaged in Compile).value / (name.value + ".scala")
  val lines = scala.io.Source.fromFile(f).getLines.toList
  if (!(lines sameElements
          """|package hello
             |
             |object Hello extends App {
             |  println("Hello, world!")
             |}
             |""".stripMargin.split("\n").toList))
    sys.error("unexpected output: \n" + lines.mkString("\n"))
  ()
}
