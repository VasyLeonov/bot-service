package ru.vasily.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
public class BotConfig {

    @Value("${bot.botToken}")
    private String botToken;

    @Value("${bot.botName}")
    private String botName;

    @Value("${yandex-api.apiUrl}")
    private String weatherUrl;

    @Value("${yandex-api.apiKey}")
    private String apiKey;

    @Value("${yandex-api.apiLang}")
    private String apiLang;

    @Value("${geo-api.geoUrl}")
    private String geoUrl;

    @Value("${geo-api.geoKey}")
    private String geoKey;
}
