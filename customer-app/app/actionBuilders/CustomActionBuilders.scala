package actionBuilders

import play.api.mvc.{ActionBuilder, AnyContent, BodyParser, Request, Result}

import scala.concurrent.{ExecutionContext, Future}

object Interceptor extends ActionBuilder[Request, AnyContent] {

  override def parser: BodyParser[AnyContent] = ???

  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = block(request)

  override protected def executionContext: ExecutionContext = ???
}