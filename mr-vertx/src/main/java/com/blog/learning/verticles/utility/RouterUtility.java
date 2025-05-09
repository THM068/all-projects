package com.blog.learning.verticles.utility;

import com.blog.learning.controllers.ServiceEndpoint;
import com.blog.learning.controllers.handlers.ProviderTerritoryHandler;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.StaticHandler;

import java.util.stream.Stream;

public class RouterUtility {

  public static Router create(Stream<ServiceEndpoint> serviceEndpointStream, Vertx vertx, String path) {
    final Router mainRouter = serviceEndpointStream.collect(() -> Router.router(vertx),
      (r, s) -> {
        r.route()
          .handler(LoggerHandler.create())
          .handler(BodyHandler.create())
          .handler(StaticHandler.create())
          .handler(ProviderTerritoryHandler.create());
//              .failureHandler(this::handleFailure);
        r.route(path).subRouter(s.router(vertx));
      }, (r1, r2) -> {
      });
    return mainRouter;
  }

  public static void handleSpecificErrorCodes(Router router) {
    router.errorHandler(HttpResponseStatus.FORBIDDEN.code(), RouterUtility::handleForbidden);
    router.errorHandler(HttpResponseStatus.NOT_FOUND.code(), RouterUtility::handleNotFound);
  }

  private static void handleForbidden(RoutingContext routingContext) {
    final JsonObject result = new JsonObject()
      .put("message", "You are not permitted to get this resource")
      .put("code", 401);
    routingContext.response()
      .setStatusCode(HttpResponseStatus.FORBIDDEN.code())
      .end(result.encodePrettily());
  }

  private static void handleNotFound(RoutingContext routingContext) {
    final JsonObject result = new JsonObject()
      .put("message", "Resource requested not found")
      .put("code", 404);
    routingContext.response()
      .setStatusCode(HttpResponseStatus.NOT_FOUND.code())
      .end(result.encodePrettily());
  }
}
