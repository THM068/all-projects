package com.blog.learning.repository.request;

import com.blog.learning.controllers.handlers.Context;
import io.vertx.core.Vertx;

public abstract class BaseRequest {
  private final Context context;
  protected final Vertx vertx;

  public BaseRequest(Vertx vertx, Context context) {
    this.vertx = vertx;
    this.context =  context;
  }

  public Vertx getVertx() {
    return vertx;
  }

  public abstract String path();

  public Context getContext() {
    return context;
  }
}
