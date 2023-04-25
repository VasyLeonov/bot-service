package ru.vasily.config;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import ru.vasily.dto.weather.WeatherDto;

import java.util.List;
import java.util.Map;

public class BotCommands {

    public static final String START = "/start";

    public static final String REGISTER = "/register";

    public static final String HELP = "/help";

    public static final String WEATHER = "/weather";

    public static final String GET_NEWS = "/news";

    public static final List<BotCommand> COMMANDS  = List.of(
            new BotCommand(START, "СТАРТ"),
            new BotCommand(WEATHER, "ПОГОДА СЕГОДНЯ"),
            new BotCommand(REGISTER, "ПОЛУЧАТЬ СООБЩЕНИЯ"),
            new BotCommand(HELP, "ПОМОЩЬ"),
            new BotCommand(GET_NEWS, "НОВОСТИ")
    );

}
