description := "generates Scala source code from a programming book"

homepage := Some(url("https://github.com/wookietreiber/sbt-scaliterate"))

startYear := Some(2016)

licenses := Seq(("MIT", url("https://github.com/wookietreiber/sbt-scaliterate/blob/master/LICENSE")))

scmInfo := Some (
  ScmInfo (
    browseUrl     = url("https://github.com/wookietreiber/sbt-scaliterate"),
    connection    = "scm:git:git://github.com/wookietreiber/sbt-scaliterate.git",
    devConnection = Some("scm:git:https://github.com/wookietreiber/sbt-scaliterate.git")
  )
)

pomExtra := (
  <developers>
    <developer>
      <id>wookietreiber</id>
      <name>Christian Krause</name>
      <url>https://github.com/wookietreiber</url>
    </developer>
  </developers>
)
