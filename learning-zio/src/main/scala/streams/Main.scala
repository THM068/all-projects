package streams

import zio.stream.{ZPipeline, ZSink, ZStream}
import zio.{Console, ZIO, ZIOAppDefault}

object Main extends ZIOAppDefault{
  lazy val stream: ZStream[Any, Nothing, Int] = ZStream.fromIterable(List(1, 2, 3, 4, 5)).map(a => a * 2)
  val helloStream: ZStream[Any, Nothing, Unit] =
    ZStream.fromZIO(Console.printLine("hello").orDie) ++
    ZStream.fromZIO(Console.printLine("from").orDie) ++ ZStream.fromZIO(Console.printLine("a").orDie) ++
    ZStream.fromZIO(Console.printLine("stream").orDie)

  lazy val ones: ZStream[Any, Nothing, Int] = ZStream.repeat(1)

  val streamOfNums = ZStream(1, 2, 3, 4, 5)
  val pipeLine: ZPipeline[Any, Nothing, Int, Int] = ZPipeline.map(_ * 2)
  val pipeLine2: ZPipeline[Any, Nothing, Int, Int] = ZPipeline.map(_ + 2)
  val sink: ZSink[Any, Nothing, Int, Int, Unit] = ZSink.foreach(Console.printLine(_).orDie)


  override val run = streamOfNums.via(pipeLine).via(pipeLine2).run(sink)
}
