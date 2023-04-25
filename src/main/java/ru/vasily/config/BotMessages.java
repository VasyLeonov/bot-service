package ru.vasily.config;

import ru.vasily.dto.weather.WeatherDto;

import java.util.Map;

public class BotMessages {

    public static final String HI_MESSAGE = "Привет ";

    public static final String REG_MESSAGE = "Ты успешно подписался!";

    public static final String HAS_REG_MESSAGE = "Ты уже подписан";

    public static final String ERROR_MESSAGE_WEATHER = "В данный момент не удалось" +
            " получить данные о погоде попробуйте позже";

    public static final String HELP_MESSAGE_WEATHER = "Введите адрес после " +
            "команды /weather \n Например: /weather Москва, улица Новый Арбат";

    public static final String HELP_MESSAGE = """
            Пока что, я поддерживаю комманды: \s
             /start начать общение,
             /news получить новости,
             /weather узнать погоду,
             /register подписаться на сообщения, \s
             /help помощь \s""";

    public static String weatherMessage(WeatherDto weather) {
        Map<Integer, String> precipitationMap = initPrecipitationMap();
        int temp = weather.getFact().getTemp();
        int windSpeed = weather.getFact().getWindSpeed();
        int humidity = weather.getFact().getHumidity();
        int pressureMm = weather.getFact().getPressureMm();
        String season = weather.getFact().getSeason();
        String precipitation = precipitationMap.get(weather.getFact().getPrecipitation());

        return "Сечас в вашем городе: " + temp + " °C  \n" +
                "Осадки: " + precipitation + "\n" +
                "Скороть ветра:  " + windSpeed + " М/С \n" +
                "Влажность воздуха: " + humidity + " % \n" +
                "Давление: " + pressureMm + " мм рт. ст. \n" +
                "Время года: " +  season;
    }

    private static Map<Integer, String> initPrecipitationMap() {
        return Map.of(0, "без осадков",
                1, "дождь",
                2, "дождь со снегом",
                3, "снег",
                4, "град");
    }
}
