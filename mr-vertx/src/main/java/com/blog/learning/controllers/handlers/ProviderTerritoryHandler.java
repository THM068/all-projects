package com.blog.learning.controllers.handlers;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import java.util.Objects;

public class ProviderTerritoryHandler implements Handler<RoutingContext> {
  public static String PTP_CONTEXT = "ptp-context";
  public static ProviderTerritoryHandler create() {
    return new ProviderTerritoryHandler();
  }
  @Override
  public void handle(RoutingContext ctx) {
    String provider = ctx.request().getHeader("X-SkyOTT-Provider");
    String territory = ctx.request().getHeader("X-SkyOTT-Territory");
    String proposition = ctx.request().getHeader("X-SkyOTT-Proposition");

    if(isNotNullOrEmpty(provider) && isNotNullOrEmpty(territory) && isNotNullOrEmpty(proposition)) {
      ctx.put(PTP_CONTEXT, Context.create(provider, territory, proposition));
      ctx.next();
    }else {
      ctx.response()
        .setStatusCode(422)
        .end(new JsonObject().put("message", "Missing PTP header").encodePrettily());
    }
  }

  private boolean isNotNullOrEmpty(String obj) {
    return (!Objects.isNull(obj) && !obj.isEmpty());
  }
}
