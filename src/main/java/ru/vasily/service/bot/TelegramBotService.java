package ru.vasily.service.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vasily.config.BotConfig;
import ru.vasily.dto.geo.LocationDto;
import ru.vasily.dto.weather.WeatherDto;
import ru.vasily.model.ChatUser;
import ru.vasily.repository.UserRepository;
import ru.vasily.service.geo.GeoClientService;
import ru.vasily.service.weather.WeatherClientService;

import java.io.IOException;
import java.util.Optional;

import static ru.vasily.config.BotCommands.*;
import static ru.vasily.config.BotMessages.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBotService extends TelegramLongPollingBot {

    private final BotConfig botConfig;

    private final UserRepository userRepository;

    private final WeatherClientService weatherService;

    private final GeoClientService geoService;

    @Override
    public void onUpdateReceived(Update update) {
        log.info("TelegramBotService: onUpdateReceived {}", update);

        Long chatId = update.getMessage().getChatId();
        String textCommand = update.getMessage().getText();
        String firstName = update.getMessage().getChat().getFirstName();

        if (update.hasMessage() && update.getMessage().hasText()) {

            if (textCommand.startsWith(WEATHER) && !textCommand.endsWith(WEATHER)) {
                sendWeatherMessage(chatId, textCommand);
            }

            switch (textCommand) {
                case START -> {
                    sendMessage(chatId, HI_MESSAGE.concat(firstName));
                    setBotCommands();
                }
                case REGISTER -> registerChatUser(chatId, update);
                case HELP -> sendMessage(chatId, HELP_MESSAGE);
                case WEATHER -> sendMessage(chatId, HELP_MESSAGE_WEATHER);
                case GET_NEWS -> sendMessage(chatId, NOT_SUPPORTED);
                default -> sendMessage(chatId, "");
            }
        }
    }

    private void sendWeatherMessage(Long chatId, String textMessage) {
        log.info("TelegramBotService: sendWeather {}", textMessage);
        WeatherDto weather = null;
        String address = textMessage.substring(textMessage.indexOf(" ")).trim();
        try {
            LocationDto location = geoService.requestApiGeo(address);
            weather = location != null ? weatherService.requestApiWeather(String.valueOf(location.getLat()),
                    String.valueOf(location.getLng())) : null;

        } catch (IOException e) {
            log.error(e.getMessage());
        }

        if (weather != null) {
            weather.setAddress(address);
            sendMessage(chatId, weatherMessage(weather));
        } else {
            sendMessage(chatId, ERROR_MESSAGE_WEATHER);
        }
    }

    private void sendMessage(Long chatId, String message) {
        log.info("TelegramBotService: sendMessage {}", message);
        SendMessage sendMessage = new SendMessage(String.valueOf(chatId), message);
        executeMessage(sendMessage);
    }

    private void registerChatUser(Long chatId, Update update) {
        Optional<ChatUser> user = userRepository.findById(chatId);
        if (user.isEmpty()) {
            log.info("TelegramBotService: registerUser {}", update);
            userRepository.save(new ChatUser(chatId,
                    update.getMessage().getChat().getFirstName(),
                    update.getMessage().getChat().getLastName()));
            sendMessage(chatId, REG_MESSAGE);

        } else {
            sendMessage(chatId, HAS_REG_MESSAGE);
        }
    }

    private void executeMessage(SendMessage message) {
        log.info("TelegramBotService: executeMessage {}", message);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("TelegramBotService: executeMessage {}", message);
        }
    }

    private void setBotCommands() {
        try {
            this.execute(new SetMyCommands(COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("TelegramBotService: setBotCommands {}", e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken () {
        return botConfig.getBotToken();
    }
}
