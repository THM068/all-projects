package streams

import zio._

object ChunkApp extends ZIOAppDefault {

  val chuck: Chunk[Int] = Chunk.fromArray(Array(1,2,3,4,5,6))
  val collectChunk = Chunk("Hello ZIO", 1.5, "Hello ZIO NIO", 2.0, "Some string", 2.5)

  val collect = collectChunk.collect { case string: String => string}

  def run = Console.printLine(collect)

}
