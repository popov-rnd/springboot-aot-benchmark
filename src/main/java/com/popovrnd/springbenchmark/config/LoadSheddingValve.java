package com.popovrnd.springbenchmark.config;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.SemaphoreValve;

import java.io.IOException;

public class LoadSheddingValve extends SemaphoreValve {

    @Override
    public void permitDenied(Request request, Response response) throws IOException {

        // Do NOT call super.permitDenied()
        response.setStatus(highConcurrencyStatus);
        response.setContentLength(0);
    }
}
