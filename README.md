# Literate Scala Programming

**sbt-scaliterate** generates Scala source code from a *programming book* written in **Markdown**.

## Example Project

Suppose a project called **hello** with the following layout:

```
$ tree hello
hello/
├── hello.md
└── project
    └── scaliterate.sbt

1 directory, 2 files
```

First of all, **sbt-scaliterate** is added to the build as a plugin in `project/scaliterate.sbt`:

```scala
addSbtPlugin("com.github.wookietreiber" % "sbt-scaliterate" % "0.2.0")
```

Furthermore, you need to have a **single** Markdown file, your *programming book*. The default name of this file is your projects name with the `.md` suffix, in our case the project name is `hello`, thus our Markdown file is name `hello.md`. It resides directly in your projects base directory. Its content is:

    % Hello World
    
    # A Beginners Program
    
    Saying **Hello, world!** is very common when learning a new language.
    
    Here is how to do it in [Scala](http://scala-lang.org/):
    
    ```scala
    package hello
    
    object Hello extends App {
      println("Hello, world!")
    }
    ```

The fenced Scala code blocks are automatically extracted from this Markdown file and compiled with sbt's `compile` task. Thus, to compile the project, simply type:

```
sbt compile
```

To generate the programming book with [pandoc][] type:

```
sbt scaliteratePandocPDF
```

## Configuration

The default name of the *programming book* Markdown source can be set via:

```scala
scaliterateSource := baseDirectory.value / "some-other-name.md"
```

To modify the options used by [pandoc][], e.g. to change the default fonts:

```scala
scaliteratePandocPDFOptions in Compile ++= Seq (
  "-V", "mainfont=Droid Serif",
  "-V", "sansfont=Droid Sans",
  "-V", "monofont=Droid Sans Mono Slashed"
)
```

**Note:** Spaces, as with the example above in the font name, do not need to be escaped, however, the `-V` and its argument need to be separated (`ProcessBuilder` semantics ...)!

[pandoc]: http://pandoc.org/
