package com.arun.inventoryservice.route;


import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class InventoryRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        // Configure REST to use the servlet component
        restConfiguration()
            .component("servlet") // Use the servlet component
            .bindingMode("json") // Enable JSON binding
            .contextPath("/camel"); // Set the context path

        // Define the REST endpoint
        rest("/inventory") // Base path for the REST endpoint
            .post("/process") // Handle POST requests
            .to("direct:processOrder");

        // Process the order
        from("direct:processOrder")
            .routeId("InventoryRoute")
            .marshal().json()
            .log("Received order details: ${body}")
            .to("file:inventory-logs?fileName=order-details-${date:now:yyyyMMdd-HHmmss}.txt&fileExist=Append") // Log to file
            .log("Order details logged to file")
 
            .to("http://localhost:8082/shipping/generate?bridgeEndpoint=true")
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
            .setHeader(Exchange.CONTENT_TYPE, constant("text/plain"))
            .setBody(constant("Inventory Shipped")); // Call Shipping Service
    }
}