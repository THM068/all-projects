package com.blog.learning.verticles;

import com.blog.learning.MainVerticle;
import com.blog.learning.controllers.ServiceEndpoint;
import com.blog.learning.verticles.utility.RouterUtility;
import io.micronaut.context.BeanContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestApiVerticle extends AbstractVerticle {
  private Logger logger = LoggerFactory.getLogger(RestApiVerticle.class);

  public static String VERTICLE_NAME = "com.blog.learning.verticles.RestApiVerticle";
  @Override
  public void start(Promise<Void> startPromise) {
    Router mainRouter = RouterUtility.create(
      BeanContext.run().streamOfType(ServiceEndpoint.class), vertx, "/api/*"
    );
    RouterUtility.handleSpecificErrorCodes(mainRouter);

    vertx.createHttpServer().requestHandler(mainRouter).listen(7777)
      .onSuccess(id -> {
        logger.info("HTTP server started on http://localhost:7777");

      })
      .onFailure(throwable -> startPromise.fail(throwable.getMessage()));
    startPromise.complete();
  }

}
