package org.drama2k.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.drama2k.model.Employee;
import org.drama2k.functions.MapProcessor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class RESTServiceRouteBuilder extends RouteBuilder {
  @Override
  public void configure() throws Exception {


    // configure to use restlet on localhost with the given port
    // and enable auto binding mode
    restConfiguration().component("restlet").host("localhost").port(7199).bindingMode(RestBindingMode.auto);
    rest("/company")
        .post("/employee").to("direct:postEmployee")
        .get("/employee").to("direct:getEmployee")
        .get("/connection").to("direct:connection");

    from("direct:getEmployee")
        .transform().constant("Dummy");

    from("direct:connection")
        .transform().constant("Connection made!");

    //define XML Data Format
    JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
    JAXBContext con = JAXBContext.newInstance(Employee.class);
    xmlDataFormat.setContext(con);

    //define JSON Data Format
    JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);

    from("direct:postEmployee")
        .unmarshal(xmlDataFormat)
        .process(new MapProcessor())
        .marshal(jsonDataFormat);
  }
}

