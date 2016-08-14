lazy val check = taskKey[Unit]("check")

name := "hello"

scalaVersion := "2.11.8"

check <<= (sourceManaged in Compile) map { (dir) =>
  val f = dir / "hello.scala"
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
