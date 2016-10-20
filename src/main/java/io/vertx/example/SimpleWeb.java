package io.vertx.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class SimpleWeb extends AbstractVerticle {

  @Override
  public void start(Future<Void> startFuture) throws Exception {
    Router router = Router.router(vertx);

    router.get("/").handler(this::handleGet);

    vertx.createHttpServer().requestHandler(router::accept).listen(8080, ar -> {
      if (ar.succeeded()) {
        startFuture.complete();
      } else {
        startFuture.fail(ar.cause());
      }
    });
  }

  private void handleGet(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    String welcome = routingContext.request().getParam("welcome");
    response.end("Reply: " + welcome);
  }
}