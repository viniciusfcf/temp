package basa;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Component
public class MySpringBootRouter extends RouteBuilder {

    @Override
    public void configure() {

        restConfiguration().bindingMode(RestBindingMode.auto).port(8080).component("servlet").host("localhost");

        rest("ordens")
            .post().to("direct:cadastrarOrdem");


        from("direct:cadastrarOrdem")
            .to("xj:identity?transformDirection=XML2JSON")
            .to("log:aa")
            // .setBody(constant("ok"))
            .to("kafka:ordens")
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"));

        // from("timer:hello?period={{timer.period}}").routeId("hello")
        //     .transform().method("myBean", "saySomething")
        //     .filter(simple("${body} contains 'foo'"))
        //         .to("log:foo")
        //     .end()
        //     .to("stream:out");
    }

}
