package io.vertx.example.tests;

import io.vertx.core.Vertx;
import io.vertx.example.SimpleWebVerticle;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class SimpleWebVerticleTest {

	private Vertx vertx;

	@Before
	public void setup(TestContext context) {
		vertx = Vertx.vertx();
		vertx.deployVerticle(SimpleWebVerticle.class.getName(),
				context.asyncAssertSuccess());
	}

	@After
	public void tearDown(TestContext context) {
		vertx.close(context.asyncAssertSuccess());
	}

	@Test
	public void helloWorldTest(TestContext context) {
		final Async async = context.async();
		vertx.createHttpClient().getNow(8080, "localhost", "/?welcome=fabric8io", response -> {
			response.handler(body -> {
				context.assertTrue(body.toString().contains("Reply: fabric8io"));
				async.complete();
			});
		});
	}
}