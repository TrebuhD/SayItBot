import org.telegram.telegrambots.api.methods.send.SendVoice;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import java.io.File;

class SayItCommand extends BotCommand {
    private static final String TAG = "SAYITCOMMAND";

    SayItCommand() {
        super("say", "get speech from text");
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        StringBuilder builder = new StringBuilder();
        for (String s : strings) {
            builder.append(s);
            builder.append(" ");
        }
        String message = builder.toString();

        SendVoice voice = new SendVoice()
                .setChatId(chat.getId().toString());
        FileDownloader speechDownloader = new FileDownloader(message);
        try {
            speechDownloader.downloadAudioFile();
            File speechFile = speechDownloader.getSpeechFile();
            voice.setNewVoice(speechFile);
            absSender.sendVoice(voice);
            speechFile.delete();
        } catch (TelegramApiException e) {
            BotLogger.error(TAG, e);
        }
    }
}
