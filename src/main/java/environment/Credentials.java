package environment;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Credentials {
    private String email;
    private String password;
    private final Logger logger = Logger.getLogger(Credentials.class.getName());
    protected JsonObject config;

    protected void getConfig(String configFile) {
        Path path = Paths.get("src", "test", "resources", "configs", configFile);

        try {
            String content = new String(Files.readAllBytes(path));
            config = new Gson().fromJson(content, JsonObject.class);

        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }
}
