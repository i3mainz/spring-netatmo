package org.springframework.social.netatmo;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.netatmo.api.Netatmo;
import org.springframework.social.netatmo.api.WeatherStationMeasurement;
import org.springframework.social.netatmo.connect.NetatmoServiceProvider;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        TestConfig.class }, loader = AnnotationConfigContextLoader.class)
public class NetatmoIntegrationTests {

    private Netatmo clientNetatmo;

    @Value("${appId}")
    private String appId;
    @Value("${appSecret}")
    private String appSecret;
    @Value("${user.name}@${user.domain}")
    private String username;
    @Value("${password}")
    private String password;

    @Before
    public void setup() {
        NetatmoServiceProvider provider = new NetatmoServiceProvider(appId,
                appSecret);
        OAuth2Operations oauth = provider.getOAuthOperations();
        AccessGrant clientGrant = oauth.exchangeCredentialsForAccess(username,
                password, null);
        clientNetatmo = provider.getApi(clientGrant.getAccessToken());
    }

    @Test
    public void publicWeatherDataTest() {
        List<WeatherStationMeasurement> test = clientNetatmo.weatherOperations()
                .getPublicWeather(49.999234, 8.223434, 50.0034234, 8.235423,
                        null, null);

        test.stream().forEach(this::print);

    }

    private void print(WeatherStationMeasurement station) {
        System.out.println(station.getId());
        System.out.println(station.getLocation().toText());
        station.getMeasurements().stream()
                .map(m -> m.getProperty() + ": " + m.getValue() + " "
                        + m.getUom() + " um " + m.getDate())
                .forEach(System.out::println);
    }

}
