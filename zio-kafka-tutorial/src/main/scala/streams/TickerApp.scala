package streams
import zio.Console._
import zio._
import zio.stream.ZStream

import java.io.IOException
object TickerApp extends ZIOAppDefault {

  val program: ZStream[Any, Nothing, String] = ZStream.repeat("100.0")
  def run = program.tap(printLine(_)).runDrain

}
