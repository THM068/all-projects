import com.crm.server.AppServer
import com.crm.server.routes._
import zio._
import zio.logging.{ConsoleLoggerConfig, consoleLogger}

object Main extends ZIOAppDefault {
  override val run: Task[Unit] = for {
    _ <- JokeTickerBroadCaster.scheduleNotification.fork
    _ <- ZIO.serviceWithZIO[AppServer](_.runServer())
      .provide(
        AppServer.layer,
        HomeRoute.layer,
        AssetRoutes.layer,
        ExampleRoutes.layer,
        JokeWsRoute.layer,
        NotFoundRoute.layer,
        LoginRoute.layer,
        ZLayer.Debug.mermaid
      )
  } yield ()
}