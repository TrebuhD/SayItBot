import org.apache.commons.io.FileUtils;
import util.Constants;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

class FileDownloader implements FileHandler {
    private final String message;
    private File speechFile;

    FileDownloader(String message) {
        this.message = message;

        System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 5.1; rv:19.0) Gecko/20100101 Firefox/19.0");
    }

    public boolean downloadAudioFile() {
        speechFile = new File(generateFilename().concat(".mp3"));
        try {
            speechFile.createNewFile();
            URL apiUrl = new URL(Constants.SPEECH_API_URL_PL.replace("<MESSAGE>", URLEncoder.encode(message, "UTF-8")));
            FileUtils.copyURLToFile(apiUrl, speechFile);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateFilename() {
        return UUID.randomUUID().toString();
    }

    public boolean deleteFile() {
        try {
            speechFile.delete();
            return true;
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        }
    }

    File getSpeechFile() {
        return speechFile;
    }
}
