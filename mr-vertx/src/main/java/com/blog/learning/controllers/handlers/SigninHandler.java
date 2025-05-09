package com.blog.learning.controllers.handlers;

import com.blog.learning.beans.BeanPropertiesConfig;
import com.blog.learning.repository.SigninRepository;
import com.blog.learning.repository.request.SigninInitRequest;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Singleton;

@Singleton
public class SigninHandler {
  private final SigninRepository signinRepository;
  private final BeanPropertiesConfig beanPropertiesConfig;

  public SigninHandler(SigninRepository signinRepository, BeanPropertiesConfig beanPropertiesConfig) {
    this.signinRepository = signinRepository;
    this.beanPropertiesConfig = beanPropertiesConfig;
  }

  public void init(RoutingContext routingContext) {
    Context context = routingContext.get(ProviderTerritoryHandler.PTP_CONTEXT);
    String baseUrl = beanPropertiesConfig.getUrl();
    SigninInitRequest signinInitRequest = new SigninInitRequest(routingContext.vertx(), context, baseUrl);

    signinInitRequest.request().send()
      .onSuccess(result -> {
        var statusCode = result.statusCode();

        if(statusCode == 200) {
          routingContext.response()
            .setStatusCode(result.statusCode())
            .end(result.body().encode());
        }
        routingContext.response().setStatusCode(statusCode).end();


      })
      .onFailure(throwable -> {
        routingContext.fail(throwable);
      });

  }
}
