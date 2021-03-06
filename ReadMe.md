# Scala Bootcamp

This repository contains information on the [Evolution Gaming](https://eng.evolutiongaming.com/) Scala Bootcamp.

## Schedule

The [2020 Q3-Q4 bootcamp](https://scala-bootcamp.evolutiongaming.com/) has the following tentative schedule:

| 2020-* | Topics                                                                                                                          | Responsible                                                                   | Materials                                                                                                                                                                                                                                                                                                                                  |
|--------|---------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 09-22  | **Introduction** and **basic [Scala](https://www.scala-lang.org/) Syntax** (types, functions, parametric polymorphism)          | [@jurisk](https://github.com/jurisk)                                          | [Basics](src/main/scala/com/evolutiongaming/bootcamp/basics/Basics.scala) ([t](src/test/scala/com/evolutiongaming/bootcamp/basics/BasicsSpec.scala))                                                                                                                                                                                       |
| 09-24  | **Classes & Traits** and some **Control Structures**: `if`-`else`, pattern matching                                             | [@jurisk](https://github.com/jurisk)                                          | [Classes & Traits](src/main/scala/com/evolutiongaming/bootcamp/basics/ClassesAndTraits.scala) ([t](src/test/scala/com/evolutiongaming/bootcamp/basics/ClassesAndTraitsSpec.scala))                                                                                                                                                         |
| 09-29  | **Control Structures**: recursion, `map`, `flatMap`, `filter`, `for`-comprehensions                                             | [@jurisk](https://github.com/jurisk)                                          | [Control Structures](src/main/scala/com/evolutiongaming/bootcamp/basics/ControlStructures.scala) ([t](src/test/scala/com/evolutiongaming/bootcamp/basics/ControlStructuresSpec.scala))                                                                                                                                                     |
| 10-01  | **Data Structures** ([im]mutable, `Array`, `List`, `Map`, tuples)                                                               | [@jurisk](https://github.com/jurisk)                                          | [Data Structures](src/main/scala/com/evolutiongaming/bootcamp/basics/DataStructures.scala) ([t](src/test/scala/com/evolutiongaming/bootcamp/basics/DataStructuresSpec.scala))                                                                                                                                                              |
| 10-06  | **Functions** ([im]pure, total/partial) & **Algebraic Data Types** - role in functional design, using them                      | [@apavlovics](https://github.com/apavlovics)                                  | [Functions](src/main/scala/com/evolutiongaming/bootcamp/functions/Functions.scala) ([t](src/test/scala/com/evolutiongaming/bootcamp/functions/FunctionsSpec.scala)) , [ADTs](src/main/scala/com/evolutiongaming/bootcamp/adt/AlgebraicDataTypes.scala) ([t](src/test/scala/com/evolutiongaming/bootcamp/adt/AlgebraicDataTypesSpec.scala)) |
| 10-08  | **Implicits** & **Type Classes** - defining them in Scala, Higher Kinded Types                                                  | [@migesok](https://github.com/migesok)                                        | [Implicits](src/main/scala/com/evolutiongaming/bootcamp/typeclass/Implicits.scala) ([t](src/test/scala/com/evolutiongaming/bootcamp/typeclass/ImplicitsSpec.scala))                                                                                                                                                                        |
| 10-13  | **Error Handling** - `Option`, `Either`, `Try`, `Validated`, encoding errors as ADTs                                            | [@apavlovics](https://github.com/apavlovics)                                  | [Error Handling](src/main/scala/com/evolutiongaming/bootcamp/error_handling/ErrorHandling.scala) ([t](src/test/scala/com/evolutiongaming/bootcamp/error_handling/ErrorHandlingSpec.scala))                                                                                                                                                 |
| 10-15  | **Processing JSON** using [Circe](https://circe.github.io/circe/), writing custom coders and decoders                           | [@arixmkii](https://github.com/arixmkii)                                      | [JSON](src/main/scala/com/evolutiongaming/bootcamp/json/CirceExercises.scala) ([t](src/test/scala/com/evolutiongaming/bootcamp/json/CirceExercisesSpec.scala), [hw](src/test/scala/com/evolutiongaming/bootcamp/json/HomeworkSpec.scala))                                                                                                  |
| 10-20  | **Unit Testing** - Benefits, testing pyramid, [ScalaTest](https://www.scalatest.org/), structuring code to be testable          | [@rtar](https://github.com/rtar)                                              | [Testing](src/test/scala/com/evolutiongaming/bootcamp/testing2)                                                                                                                                                                                                                                                                            |
| 10-23  | **[Cats](https://typelevel.org/cats/)** - `cats-core` introduction, Monad Transformers                                          | [@Nbooo](https://github.com/Nbooo)                                            | [Cats](https://github.com/evolution-gaming/scala-bootcamp/tree/master/src/main/scala/com/evolutiongaming/bootcamp/cats/v2) ([t](https://github.com/evolution-gaming/scala-bootcamp/tree/master/src/test/scala/com/evolutiongaming/bootcamp/cats/v2))                                                                                       |
| 10-27  | **Questions & Answers** - covering gaps, design & coding practice                                                               | [@jurisk](https://github.com/jurisk)                                          |                                                                                                                                                                                                                                                                                                                                            |
| 10-29  | **Asynchronous Programming** - JVM threads, perils of critical sections using `synchronized`, `Atomic*`                         | [@migesok](https://github.com/migesok)                                        | [Presentation](presentations/2020-q1-q2/Asynchronous%20programming.pdf) ([examples](src/main/scala/com/evolutiongaming/bootcamp/async/async.scala)) ([hw](src/main/scala/com/evolutiongaming/bootcamp/async/AsyncHomework.scala))                                                                                                          |
| 11-03  | **Effects** - [Cats Effect](https://typelevel.org/cats-effect/) IO                                                              | [@jurisk](https://github.com/jurisk) & [@fr3akX](https://github.com/fr3akX)   | [Effects](src/main/scala/com/evolutiongaming/bootcamp/effects) ([t](src/test/scala/com/evolutiongaming/bootcamp/effects/EffectsSpec.scala))                                                                                                                                                                                                |
| 11-05  | **Effects** - continued, **[Shared State in FP](https://typelevel.org/cats-effect/concurrency/basics.html)**                    | [@fr3akX](https://github.com/fr3akX) & [@Kvitral](https://github.com/Kvitral) |                                                                                                                                                                                                                                                                                                                                            |
| 11-10  | **Shared State in FP** - continued                                                                                              | [@Kvitral](https://github.com/Kvitral)                                        |                                                                                                                                                                                                                                                                                                                                            |
| 11-12  | **HTTP REST** with [http4s](https://http4s.org/)                                                                                | [@apavlovics](https://github.com/apavlovics)                                  | [HTTP](src/main/scala/com/evolutiongaming/bootcamp/http/Http.scala) ([t](src/test/scala/com/evolutiongaming/bootcamp/http/HttpSpec.scala))                                                                                                                                                                                                 |
| 11-17  | **WebSocket** with [http4s](https://http4s.org/)                                                                                | [@apavlovics](https://github.com/apavlovics)                                  | [WebSocket](src/main/scala/com/evolutiongaming/bootcamp/http/WebSocket.scala)                                                                                                                                                                                                                                                              |
| 11-19  | **Containers** - [Docker](https://www.docker.com/) and [Kubernetes](https://kubernetes.io/)                                     | [@arixmkii](https://github.com/arixmkii)                                      |                                                                                                                                                                                                                                                                                                                                            |
| 11-24  | **[Akka](https://akka.io/)** - Actors and other Akka features                                                                   | [@migesok](https://github.com/migesok)                                        | [Lecture](src/main/scala/com/evolutiongaming/bootcamp/akka/actors/Lecture.md) ([examples](src/main/scala/com/evolutiongaming/bootcamp/akka/actors)) ([hw](src/main/scala/com/evolutiongaming/bootcamp/akka/actors/Homework.md))                                                                                                            |
| 11-26  | **Structure of Scala Applications** - [SBT](https://www.scala-sbt.org/), single- vs multi-module projects, "package-by-feature" | [@rtar](https://github.com/rtar)                                              |                                                                                                                                                                                                                                                                                                                                            |
| 12-01  | **Working with Databases** from Scala: JDBC, [Slick](http://scala-slick.org/), [Doobie](https://tpolecat.github.io/doobie/)     | [@mr-git](https://github.com/mr-git)                                          | [Databases](src/main/scala/com/evolutiongaming/bootcamp/db/DoobieExercises.scala)                                                                                                                                                                                                                                                          |
| 12-03  | **Event Sourcing using Akka Persistence** and CQRS                                                                              | [@mikla](https://github.com/mikla)                                            |                                                                                                                                                                                                                                                                                                                                            |
| 12-08  | **Development Practices & Processes** - Agile, Code Reviews, Testing, CI/CD                                                     | [@jurisk](https://github.com/jurisk)                                          |                                                                                                                                                                                                                                                                                                                                            |
| 12-10  | **Work on Course Projects** and Q&A sessions - joint or with mentors                                                            | N/A                                                                           |                                                                                                                                                                                                                                                                                                                                            |
| 12-15  | **Work on Course Projects** and Q&A sessions - joint or with mentors                                                            | N/A                                                                           |                                                                                                                                                                                                                                                                                                                                            |
| 12-17  | **Presentation of Course Projects** to mentors & each other                                                                     | [@jurisk](https://github.com/jurisk)                                          |                                                                                                                                                                                                                                                                                                                                            |
| 12-22  | **Presentation of Course Projects** to mentors & each other - continued                                                         | [@jurisk](https://github.com/jurisk)                                          |                                                                                                                                                                                                                                                                                                                                            |
| 12-29  | **Graduation** - Discussion of course results, free form Q&A session                                                            | [@jurisk](https://github.com/jurisk)                                          |                                                                                                                                                                                                                                                                                                                                            |

The schedule will be adjusted according to learning progress.

## Preparation for the bootcamp

### Prerequisites

Please install recent versions of the following before the first lecture:
- [intelliJ IDEA Community Edition](https://www.jetbrains.com/idea/download/)
- [Scala plug-in](https://www.jetbrains.com/help/idea/discover-intellij-idea-for-scala.html) for IntelliJ IDEA
- OpenJDK, e.g. [AdoptOpenJDK](https://adoptopenjdk.net/), [Oracle OpenJDK](https://jdk.java.net/) or [OpenJDK using homebrew](https://formulae.brew.sh/formula/openjdk) (for MacOS)
- [Scala](https://www.scala-lang.org/download/)
- [SBT](https://www.scala-sbt.org/download.html)
- [Git](https://git-scm.com/downloads)

Alternatives that are also expected to work:
- [Visual Studio Code with Metals](https://marketplace.visualstudio.com/items?itemName=scalameta.metals) as an IDE

### Prepare the project

- Check out the [Scala Bootcamp](https://github.com/evolution-gaming/scala-bootcamp) project
- Run tests from the command line using `sbt test`
- Open the project in IntelliJ IDEA and run tests there (right-click on `scala-bootcamp` project in the left panel and click `Run ScalaTests in ‘scala…’`)

The tests will fail (for now), this is normal and expected.

### Troubleshooting

In case of issues:
- Read [Getting Started with Scala](https://docs.scala-lang.org/getting-started/index.html)
- Read [Discover IntelliJ IDEA for Scala](https://www.jetbrains.com/help/idea/discover-intellij-idea-for-scala.html)
- Ask in the Bootcamp chat

## Learning resources

All resources are listed in no particular order.

### Books

- [Essential Scala](https://underscore.io/books/essential-scala/) (free)
- [Scala with Cats 2](https://www.scalawithcats.com/) (free)
- [Functional Programming for Mortals with Scalaz](https://leanpub.com/fpmortals) (free+)
- [Functional Programming for Mortals with Cats](https://leanpub.com/fpmortals-cats) ($15+)
- [Scala from Scratch: Exploration](https://leanpub.com/scala-from-scratch-exploration) ($15+)
- [Functional Programming in Scala](https://www.manning.com/books/functional-programming-in-scala#toc) ($25+)
- [Practical FP in Scala: A hands-on approach](https://leanpub.com/pfp-scala) ($30+)
- [Programming in Scala](https://booksites.artima.com/programming_in_scala_3ed) ($30+)
- [Zionomicon](https://www.zionomicon.com/) ($70)

### Other

- [Tour of Scala](https://docs.scala-lang.org/tour/tour-of-scala.html) & [Scala Book](https://docs.scala-lang.org/overviews/scala-book/introduction.html) from [scala-lang.org](https://www.scala-lang.org/)
- [Scala Exercises](https://www.scala-exercises.org/) 
- [Coursera Scala Specialization](https://www.coursera.org/specializations/scala)

### Non-Scala

- [Learn Git Branching](https://learngitbranching.js.org/)

## Status

* The [2020 Q1-Q2 bootcamp](https://evolution-gaming.timepad.ru/event/1106949/) has concluded
* The [2020 Q3-Q4 bootcamp](https://scala-bootcamp.evolutiongaming.com/) is ongoing
