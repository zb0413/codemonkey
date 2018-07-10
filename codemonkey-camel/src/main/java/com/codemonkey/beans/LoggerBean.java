package com.codemonkey.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("loggerBean")
public class LoggerBean implements Processor {

    private static final Logger log = LoggerFactory.getLogger(LoggerBean.class);

    public void process(Exchange msg) {
        log.trace("Processing msg {}", msg);
        String record = msg.getIn().getBody(String.class);
        log.info("Processing record {}", record);
        // Do something useful with this record.
    }
}