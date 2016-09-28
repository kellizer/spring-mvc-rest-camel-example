package example;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class SampleCamelRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:invoke-rest-route")
                .setBody(simple("Hello ${body}"));
    }
}
