package org.tracker.raidtracker.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class RbTrackerTgBot extends TelegramLongPollingBot {

    private final String botToken = "8174001378:AAE_o7sjCEseUySyJCngu8GxYOXfo2TJ6BA"; //
    private final String botUsername = "dudewhereismycar_bot";

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            String messageText = message.getText();
            String chatId = String.valueOf(update.getMessage().getChatId());
            String userId = update.getMessage().getFrom().getId().toString();
            if ("/start".equals(messageText)) {
                System.out.println("User ID: " + userId);
                System.out.println("Chat ID: " + chatId);
                if (userId.equals(chatId)) {
                    sendMessage(userId, "Welcome! Your notifications are now set up. Your id: " + userId + ". Enter it in application to enable notifications.");
                } else {
                    sendMessage(userId, "Welcome! Your notifications are now set up. Your id: " + chatId + ". Enter it in application to enable notifications.");
                }
            }
        }
    }

    public void sendMessage(String userId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(userId);
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
