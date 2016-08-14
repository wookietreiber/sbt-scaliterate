package scaliterate

import sbt.{ File, IO }

/** Extracts all Scala code blocks out of markdown. */
object Scaliterate {

  val codeBlockStart = """^\s*```scala\s*$"""

  val codeBlockEnd = """^\s*```\s*$"""

  def apply(file: File) = {
    var include = false
    var buf = collection.mutable.ListBuffer[String]()

    val it = IO.readLines(file).iterator

    while (it.hasNext) {
      val line = it.next

      if (include) {
        if (line matches codeBlockEnd) {
          buf += ""
          include = false
        } else
          buf += line
      } else if (line matches codeBlockStart)
        include = true
    }

    buf.mkString("\n")
  }
}
