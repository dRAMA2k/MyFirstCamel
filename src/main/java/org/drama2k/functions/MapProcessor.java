package org.drama2k.functions;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.drama2k.model.Employee;

public class MapProcessor implements Processor {

  public void process(Exchange exchange) throws Exception {
    Employee employee = exchange.getIn().getBody(Employee.class);
    employee.setEmpName("Camel Rocks");
    exchange.getIn().setBody(employee);
  }
}
