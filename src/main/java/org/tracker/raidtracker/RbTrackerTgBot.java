package org.tracker.raidtracker;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class RbTrackerTgBot extends TelegramLongPollingBot {

    private final String botToken = "8174001378:AAE_o7sjCEseUySyJCngu8GxYOXfo2TJ6BA"; //
    private final String botUsername = "dudewhereismycar_bot";

    @Override
    public void onUpdateReceived(Update update) {

    }

    public void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}