import java.net.URI;

public interface FileHandler {
    /*
    Download and save a file, return local URI
     */
    URI downloadFile(String fileURL);

    boolean deleteFile(URI fileURI);
}
