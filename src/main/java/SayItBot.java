import org.telegram.telegrambots.api.methods.send.SendVoice;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import util.Constants;
import util.Secrets;

import java.io.File;

class SayItBot extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendVoice voice = new SendVoice()
                    .setChatId(update.getMessage().getChatId())
                    .setReplyToMessageId(update.getMessage().getMessageId());
            FileDownloader speechDownloader = new FileDownloader(update.getMessage().getText());
            try {
                speechDownloader.downloadAudioFile();
                File speechFile = speechDownloader.getSpeechFile();
                voice.setNewVoice(speechFile);
                sendVoice(voice);
                speechFile.delete();
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        return Constants.BOT_USERNAME;
    }

    public String getBotToken() {
        return Secrets.TELEGRAM_TOKEN;
    }
}
