package org.drama2k.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.drama2k.functions.MapProcessor;
import org.drama2k.model.Employee;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;


public class FileTransferRouteBuilder extends RouteBuilder {

    public void configure() throws JAXBException {

        //define XML Data Format
        JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
        JAXBContext con = JAXBContext.newInstance(Employee.class);
        xmlDataFormat.setContext(con);

        //define JSON Data Format
        JacksonDataFormat jsonDataFormat = new JacksonDataFormat(Employee.class);

        from("file:src/data?noop=true")
                .unmarshal(xmlDataFormat)
                    .log("unmarshaling done!")
                .process(new MapProcessor())
                    .log("mapping done!")
                .marshal(jsonDataFormat)
                    .log("marshalling done!")
            .to("file:target/messages/mapped?fileName=output.json")
                .log("json message delivered");

    }

}
