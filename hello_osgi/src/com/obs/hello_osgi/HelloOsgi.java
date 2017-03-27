package com.obs.hello_osgi;


import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloOsgi {

    private static final Logger s_logger = LoggerFactory.getLogger(HelloOsgi.class);

    private static final String APP_ID = "org.eclipse.kura.example.hello_osgi";

    protected void activate(ComponentContext componentContext) {

        s_logger.info("Bundle " + APP_ID + " has started!");

        s_logger.debug(APP_ID + ": This is a debug message.");

    }

    protected void deactivate(ComponentContext componentContext) {

        s_logger.info("Bundle " + APP_ID + " has stopped!");

    }

}
