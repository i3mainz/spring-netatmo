package org.springframework.social.netatmo.api.impl;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.springframework.social.netatmo.api.Measurement;
import org.springframework.social.netatmo.api.PublicWeatherParameter;
import org.springframework.social.netatmo.api.UOM;
import org.springframework.social.netatmo.api.WeatherOperations;
import org.springframework.social.netatmo.api.WeatherStationMeasurement;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class WeatherTemplate extends AbstractTemplate
        implements WeatherOperations {

    private final GeometryFactory geometryFactory = new GeometryFactory();

    public WeatherTemplate(NetatmoTemplate netatmo, boolean authorized) {
        super(netatmo, authorized);
    }

    @Override
    public List<WeatherStationMeasurement> getPublicWeather(
            PublicWeatherParameter parameter) {
        return getPublicWeather(parameter.getLatSW(), parameter.getLonSW(),
                parameter.getLatNE(), parameter.getLonNE(),
                parameter.getRequiredData(), parameter.getFilter());
    }

    @Override
    public List<WeatherStationMeasurement> getPublicWeather(double latSW,
            double lonSW, double latNE, double lonNE,
            List<String> required_data, Boolean filter) {

        MultiValueMap<String, String> queryVariables = new LinkedMultiValueMap<>();
        queryVariables.set("lat_ne", String.valueOf(latNE));
        queryVariables.set("lon_ne", String.valueOf(lonNE));
        queryVariables.set("lat_sw", String.valueOf(latSW));
        queryVariables.set("lon_sw", String.valueOf(lonSW));
        if (required_data != null && !required_data.isEmpty()) {
            queryVariables.set("required_data",
                    required_data.stream().collect(joining(", ")));
        }
        if (filter != null) {
            queryVariables.set("filter", String.valueOf(filter));
        }

        return this.netatmo.fetch("getpublicdata", this::mapEntry,
                queryVariables);
    }

    @SuppressWarnings("unchecked")
    private WeatherStationMeasurement mapEntry(Map<?, ?> entry) {
        Map<?, ?> pos = (Map<?, ?>) entry.get("place");
        String idPrefix = entry.get("_id").toString();
        List<Double> lage = (List<Double>) pos.get("location");
        Number altitude = (Number) pos.get("altitude");
        String timezone = (String) pos.get("timezone");
        Point location = geometryFactory.createPoint(new Coordinate(lage.get(0),
                lage.get(1), altitude.doubleValue()));
        Collection<Map<?, ?>> measures = ((Map<?, Map<?, ?>>) entry
                .get("measures")).values();
        List<Measurement> measurements = Stream
                .concat(measures.stream()
                        .filter(e -> e.containsKey("res")).flatMap(
                                m -> parseResMeasurement(m, timezone).stream()),
                        measures.stream()
                                .filter(e -> e.containsKey("rain_timeutc"))
                                .flatMap(m -> parseRainMeasurement(
                                        (Map<String, Number>) m, timezone)
                                                .stream()))
                .collect(toList());

        return new WeatherStationMeasurement(idPrefix, location, measurements);
    }

    private Collection<Measurement> parseRainMeasurement(Map<String, Number> m,
            String timezone) {
        ZonedDateTime date = ZonedDateTime.ofInstant(
                Instant.ofEpochSecond(m.get("rain_timeutc").longValue()),
                ZoneId.of(timezone));
        return Stream.of(
                new Measurement(date, "rain_60min", "mm/h",
                        m.get("rain_60min")),
                new Measurement(date, "rain_24h", "mm/h", m.get("rain_24h")),
                new Measurement(date, "rain_live", "mm/h", m.get("rain_live")))
                .collect(toList());
    }

    @SuppressWarnings("unchecked")
    private Collection<Measurement> parseResMeasurement(Map<?, ?> m,
            String timezone) {
        Map<?, ?> res = (Map<?, ?>) m.get("res");
        List<String> type = (List<String>) m.get("type");
        ZonedDateTime date = ZonedDateTime.ofInstant(
                Instant.ofEpochSecond(new Long(res.entrySet().stream()
                        .findFirst().get().getKey().toString())),
                ZoneId.of(timezone));
        List<Number> results = (List<Number>) res.entrySet().stream()
                .findFirst().get().getValue();
        AtomicInteger index = new AtomicInteger();
        return results.stream().map(v -> {
            String typeStr = type.get(index.getAndIncrement());
            String uom = UOM.valueOf(typeStr.toUpperCase()).getUOM();
            return new Measurement(date, typeStr, uom, v.doubleValue());
        }).collect(toList());
    }
}
