# Literate Scala Programming

**sbt-scaliterate** generates Scala source code from a *programming book* written in **Markdown**.

## Example Project

```
$ tree
.
├── project
│   └── scaliterate.sbt
└── src.scala.md

1 directory, 2 files
```

Add **sbt-scaliterate** as an sbt plugin to `project/scaliterate.sbt`:

```scala
addSbtPlugin("com.github.wookietreiber" % "sbt-scaliterate" % "0.1.0")
```

You need to have a **single** Markdown file, your *programming book*. The default name of this file is simply called `src.scala.md`. It resides directly in your projects base directory.

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

## Configuration

The default name of the *programming book* Markdown source can be set via:

```
scaliterateSource := baseDirectory.value / "src.scala.md"
```

**Note:** There is no support yet to convert the Markdown source to other formats *directly from within sbt*. Personally, I recommend using [pandoc](http://pandoc.org/) as an external tool for its excellent TeX and e-book outputs, e.g. for a high quality PDF:

```
pandoc                       \
  --standalone               \
  --table-of-contents        \
  --number-sections          \
  --latex-engine=xelatex     \
  -V documentclass=report    \
  -V linkcolor=blue          \
  -V geometry='left=24.1mm'  \
  -V geometry='right=24.1mm' \
  -V geometry='bottom=4.5cm' \
  -V fontsize=10pt           \
  -V papersize=a4paper       \
  -o src.scala.pdf           \
  src.scala.md
```
