package example;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SampleRestController {

    @Autowired
    ProducerTemplate producerTemplate;

    @RequestMapping(value = "/echo", method = RequestMethod.POST)
    public ResponseEntity<String> search(@RequestBody String echo) {
        Exchange exchange = new DefaultExchange(producerTemplate.getCamelContext());
        exchange.getIn().setBody(echo);
        producerTemplate.send("direct:invoke-rest-route", exchange);
        //noinspection ThrowableResultOfMethodCallIgnored
        if (exchange.getException() != null)
            return handleException(exchange.getException());
        return new ResponseEntity<>(String.valueOf(exchange.getIn().getBody()), HttpStatus.OK);
    }

    /**
     * Exception on the route - lets handle what the error was.
     *
     * @return the error response
     */
    private ResponseEntity<String> handleException(Exception failureReason) {
        //todo - handle your exceptions accordinly
        return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
    }

}
