package com.blog.learning;

import com.blog.learning.verticles.RestApiVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
  private Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(RestApiVerticle.VERTICLE_NAME)
      .onSuccess(id -> {
        logger.info("application with id" + id + "deployed successfully");
        startPromise.complete();
      })
      .onFailure(throwable -> {
        logger.error(throwable.getMessage());
        startPromise.fail(throwable);
      });
  }

}
