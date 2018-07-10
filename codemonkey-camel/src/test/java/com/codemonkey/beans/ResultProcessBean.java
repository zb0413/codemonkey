package com.codemonkey.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ResultProcessBean implements Processor {

    public void process(Exchange msg) {
        String name = msg.getIn().getBody(String.class);
        msg.getOut().setBody("hello " + name);
    }
}
