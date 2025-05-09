package streams
import zio._
import zio.stream._
import zio.json._
object ZioStreams extends ZIOAppDefault {

  val aStream: ZStream[Any, Nothing, Int] = ZStream.fromIterable(1 to 100)
  val intStream: ZStream[Any, Nothing, Int] = ZStream(1,2,3,4,5,6,7,8)
  val stringStream: ZStream[Any, Nothing, String] = aStream.map(_.toString)

  //Sink: destination for your stream
  val sum: ZSink[Any, Nothing, Int, Nothing, Int] = ZSink.sum[Int]
  val take5: ZSink[Any, Nothing, Int, Int, Chunk[Int]] = ZSink.take(5)

  val take5Map: ZSink[Any, Nothing, Int, Int, Chunk[String]] = take5.map(chunk => chunk.map(_.toString))
  val take5LeftOvers: ZSink[Any, Nothing, Int, Nothing, (Chunk[String], Chunk[Int])] = take5Map.collectLeftover
  val take5Ignore: ZSink[Any, Nothing,Int, Nothing,Chunk[Int]] = take5.ignoreLeftover

  //contramap
  val take5String: ZSink[Any, Nothing, Int, Int, Chunk[Int]] = take5.contramap(_.toInt)

  val zio: ZIO[Any, Nothing, Int] = intStream.run(sum)

  val businessLogic: ZPipeline[Any, Nothing, String, Int] = ZPipeline.map(_.toInt)

  val filterLogic: ZPipeline[Any, Nothing, Int, Int] = ZPipeline.filter( _ % 2 == 0 )

  val appLogic: ZPipeline[Any, Nothing, String, Int] = businessLogic >>> filterLogic

  val zio_v2: ZIO[Any, Nothing, Int] = stringStream.via(businessLogic).run(sum)

  val zio_v3: ZIO[Any, Nothing, Int] = stringStream.via(appLogic).run(sum)


//  def run = ZStream.repeat(2).map(_ * 2).take(20).tap(Console.printLine(_)).runDrain.debug

  def run = intStream.run(take5String).debug
 // def run = zio_v3.debug
}
