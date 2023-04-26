package ru.vasily.service.geo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.vasily.config.BotConfig;
import ru.vasily.dto.geo.LocationDto;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoClientService {

    private final BotConfig config;

    public LocationDto requestApiGeo(String address) throws IOException {
        log.info("GeoClientService: requestToApiGeo {}", address);

        String uri = config.getGeoUrl() + config.getGeoKey() +"&format=json&geocode=" +
                URLEncoder.encode(address, StandardCharsets.UTF_8);
        log.info("GeoClientService: uri {}", uri);

        JSONObject response = read(uri);
        JSONObject location = response.getJSONObject("response");
        location = location.getJSONObject("GeoObjectCollection");
        location = location.getJSONArray("featureMember").getJSONObject(0);
        location = location.getJSONObject("GeoObject");
        location = location.getJSONObject("Point");
        String[] point = location.getString("pos").split(" ");

        final double lng = Double.parseDouble(point[0]);
        final double lat = Double.parseDouble(point[1]);

        return  new LocationDto(lat, lng);
    }

    private String readAll(final Reader rd) throws IOException {
        final StringBuilder builder = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            builder.append((char) cp);
        }
        return builder.toString();
    }

    private JSONObject read(final String url) throws IOException, JSONException {
        final InputStream stream = new URL(url).openStream();
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(stream,
                    StandardCharsets.UTF_8));
            final String jsonText = readAll(reader);
            return new JSONObject(jsonText);
        } finally {
            stream.close();
        }
    }
}
