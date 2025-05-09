package com.blog.learning.controllers;

import com.blog.learning.controllers.handlers.ProviderTerritoryHandler;
import com.blog.learning.controllers.handlers.SigninHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import jakarta.inject.Singleton;

@Singleton
public class SigninController implements ServiceEndpoint{

  private SigninHandler signinHandler;

  public SigninController(SigninHandler signinHandler) {
    this.signinHandler = signinHandler;
  }

  @Override
  public Router router(Vertx vertx) {
    Router router = Router.router(vertx);
    router.post("/signin/init").handler(signinHandler::init);
    return router;
  }
}
