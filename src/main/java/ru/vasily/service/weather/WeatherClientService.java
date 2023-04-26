package ru.vasily.service.weather;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;
import ru.vasily.config.BotConfig;
import ru.vasily.dto.weather.WeatherDto;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherClientService {

    private final BotConfig config;

    public WeatherDto requestApiWeather(String lat, String lon) throws IOException {
        log.info("WeatherClientService: requestWeather {}", lat + " " + lon);

        CloseableHttpClient client = HttpClients.createDefault();
        String uri = config.getWeatherUrl().concat("lat=" + lat + "&lon=" +
                lon + "&lang=" + config.getApiLang() + "&extra=true");

        ObjectMapper mapper = new ObjectMapper();
        HttpGet request = new HttpGet(uri);
        request.setHeader("X-Yandex-API-Key", config.getApiKey());

        WeatherDto weather = client.execute(request, httpResponse ->
                mapper.readValue(httpResponse.getEntity().getContent(), WeatherDto.class));
        log.info("WeatherClientService: WeatherDto {}", weather);
        return weather;
    }
}
