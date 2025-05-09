package streams

import zio.stream.{ZPipeline, ZSink, ZStream}
import zio.{ExitCode, Scope, ZIO, ZIOAppArgs, ZIOAppDefault}

import scala.util.matching.Regex

object ProcessFile extends ZIOAppDefault {
  val post1: String = "hello-word.md"
  val post1_content: Array[Byte] =
    """---
      |title: "Hello World"
      |tags: []
      |---
      |======
      |
      |## Generic Heading
      |
      |Even pretend blog posts need a #generic intro.
      |""".stripMargin.getBytes

  val post2: String = "scala-3-extensions.md"
  val post2_content: Array[Byte] =
    """---
      |title: "Scala 3 for You and Me"
      |tags: []
      |---
      |======
      |
      |## Cool Heading
      |
      |This is a post about #Scala and their re-work of #implicits via thing like #extensions.
      |""".stripMargin.getBytes

  val post3: String = "zio-streams.md"
  val post3_content: Array[Byte] =
    """---
      |title: "ZIO Streams: An Introduction"
      |tags: []
      |---
      |======
      |
      |## Some Heading
      |
      |This is a post about #Scala and #ZIO #ZStreams!
  """.stripMargin.getBytes

  val fileMap: Map[String, Array[Byte]] = Map(
    post1 -> post1_content,
    post2 -> post2_content,
    post3 -> post3_content,
  )
  val hashFilter: String => Boolean = str =>
    str.startsWith("#") &&
    str.count(_ == '#') == 1 &&
    str.length > 2

  val punctRegex: Regex = """\p{Punct}""".r

  val lowerCase: ZPipeline[Any, Nothing, String, String] =
    ZPipeline.map[String, String](_.toLowerCase)

  val writeFile: String => ZSink[Any, Throwable, Byte, Byte, Long] =
    ZSink.fromFileName(_)
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = for {
     _ <- ZIO.foreachDiscard(fileMap) { kv =>
       Console.println(s"  Generating file ${kv._1}")
       ZStream.fromIterable(kv._2)
         .via(
           ZPipeline.utf8Decode >>>
           lowerCase >>>
           ZPipeline.utf8Encode
         )
         .run(writeFile(s"src/main/resources/${kv._1}"))

     }
  } yield()
}
