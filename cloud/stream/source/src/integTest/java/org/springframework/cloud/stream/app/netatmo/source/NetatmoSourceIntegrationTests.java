/**
 * 
 */
package org.springframework.cloud.stream.app.netatmo.source;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.stream.annotation.Bindings;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.social.netatmo.api.WeatherStationMeasurement;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Some nice integration tests UBA processor
 * 
 * @author Nikolai Bock
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetatmoSourceIntegrationTests.UBAProcessorApplication.class, webEnvironment=WebEnvironment.RANDOM_PORT)
public abstract class NetatmoSourceIntegrationTests {

    @Autowired
    @Bindings(NetatmoSourceConfiguration.class)
    protected Source source;

    @Autowired
    protected MessageCollector messageCollector;

    @SpringBootTest
    public static class TestUBAStandardRequest
            extends NetatmoSourceIntegrationTests {

        @Test
        public void testInsert() throws JsonProcessingException, InterruptedException {
//            BlockingQueue<Message<?>> messages = messageCollector
//                    .forChannel(source.output());
            Message<?> received = messageCollector.forChannel(this.source.output()).poll(10, TimeUnit.SECONDS);
            System.out.println(((WeatherStationMeasurement) received.getPayload()).getLocation().toText());
//            messages.stream().map(Message::getPayload)
//                    .forEach(System.out::println);
        }
    }

    @SpringBootApplication
    public static class UBAProcessorApplication {

    }
}
