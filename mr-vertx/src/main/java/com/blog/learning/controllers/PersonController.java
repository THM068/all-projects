package com.blog.learning.controllers;

import com.blog.learning.beans.PersonService;
import com.blog.learning.controllers.handlers.PersonHandler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import netscape.javascript.JSObject;

@Singleton
public class PersonController implements ServiceEndpoint {

  private PersonHandler personHandler;

  @Inject
  public PersonController(PersonHandler personHandler) {
    this.personHandler = personHandler;
  }
  @Override
  public Router router(Vertx vertx) {
    Router router = Router.router(vertx);

    router.get(path("/name")).handler(personHandler::getName);
    return router;
  }



  private String path(String action) {
    return  "/person" + action;
  }
}
