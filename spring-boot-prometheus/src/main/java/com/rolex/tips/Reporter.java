package com.rolex.tips;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.PushGateway;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2022
 */
public class Reporter {
    public void read() throws IOException {
        String url = "http://localhost:1234/api/v1/query_range?query=cpu_counter&start=2022-12-01T00:00:00.000Z&end=2022-12-02T00:00:00.000Z";
        HttpGet get = new HttpGet(url);
        CloseableHttpClient client = HttpClients.custom().build();
        CloseableHttpResponse httpResponse = client.execute(get);
        /*
         {
           "status" : "success",
           "data" : {
              "resultType" : "matrix",
              "result" : [
                 {
                    "metric" : {
                       "__name__" : "up",
                       "job" : "prometheus",
                       "instance" : "localhost:9090"
                    },
                    "values" : [
                       [ 1435781430.781, "1" ],
                       [ 1435781445.781, "1" ],
                       [ 1435781460.781, "1" ]
                    ]
                 },
                 {
                    "metric" : {
                       "__name__" : "up",
                       "job" : "node",
                       "instance" : "localhost:9091"
                    },
                    "values" : [
                   [ 1435781430.781, "0" ],
                       [ 1435781445.781, "0" ],
                       [ 1435781460.781, "1" ]
                    ]
                 }
              ]
           }
        }
         */
    }

    @Autowired
    Counter counter;
    @Autowired
    Gauge gauge;
    @Autowired
    PushGateway gateway;

    public void report() throws IOException, InterruptedException {
        gauge.inc(0);
        gauge.inc(20);
        gateway.push(gauge, "cpu-gauge-job");
        Thread.sleep(5000);
    }

}
