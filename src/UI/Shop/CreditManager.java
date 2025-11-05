package UI.Shop;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class CreditManager {

  private static final String CREDIT_SAVE_FILE = "game_data.properties";
  private static final Path SAVE_PATH = Paths.get(System.getProperty("user.home"), CREDIT_SAVE_FILE);

  private Properties loadProperties() {
    Properties props = new Properties();
    if (Files.exists(SAVE_PATH)) {
      try (InputStream in = Files.newInputStream(SAVE_PATH)) {
        props.load(in);
      } catch (IOException e) {
        System.err.println("Lỗi khi tải Properties: " + e.getMessage());
      }
    }
    return props;
  }

  private void saveProperties(Properties props) {
    try (OutputStream out = Files.newOutputStream(SAVE_PATH)) {
      props.store(out, "Game save Data");
    } catch (IOException e) {
      System.err.println("Lỗi khi lưu Properties: " + e.getMessage());
    }
  }


  //Luu credits
  public void saveCredits(long credits){
    Properties props = loadProperties();
    props.setProperty("credits", String.valueOf(credits));
    saveProperties(props);
    System.out.println("Credits saved!: " + credits);
  }

  public long loadCredits(){
    Properties props = loadProperties();
    String creditStr = props.getProperty("Credits","0");
    System.out.println("Credits loaded!: " + creditStr);
    return Long.parseLong(creditStr);
  }
}
