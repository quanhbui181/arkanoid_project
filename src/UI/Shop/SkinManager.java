package UI.Shop;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class SkinManager {

  private static final String SKIN_SAVE_FILE = "game_data.properties";
  private static final Path SAVE_PATH = Paths.get(System.getProperty("user.home"), SKIN_SAVE_FILE);

  private static final String EQUIPPED_PADDLE_KEY = "equippedPaddle";
  private static final String EQUIPPED_BALL_KEY = "equippedBall";
  private static final String OWNED_SKINS_KEY = "ownedSkins"; //Luu dsach id

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

  //QUAN LY SKIN

  public void saveEquippedSkins(int paddleId, int ballId) {
    Properties props = loadProperties();
    props.setProperty(EQUIPPED_PADDLE_KEY, String.valueOf(paddleId));
    props.setProperty(EQUIPPED_BALL_KEY, String.valueOf(ballId));
    saveProperties(props);
    System.out.println("Equipped skins saved!: Paddle " + paddleId + ", Ball " + ballId);
  }

  public int[] loadEquippedSkins() {
    Properties props = loadProperties();
    //ID mac dinh
    int paddleId = Integer.parseInt(props.getProperty(EQUIPPED_PADDLE_KEY, "1"));
    int ballId = Integer.parseInt(props.getProperty(EQUIPPED_BALL_KEY, "10"));
    System.out.println("Equipped skins loaded!: Paddle " + paddleId + ", Ball " + ballId);

    return new int[]{paddleId, ballId};
  }

  public List<Integer> loadOwnedSkins() {
    Properties props = loadProperties();

    //Mac dinh so huu skin 1 va 11
    String ownedStr = props.getProperty(OWNED_SKINS_KEY, "1,10");

    List<Integer> ownedIds = new ArrayList<>();
    if (ownedStr.isEmpty())
      return ownedIds; // neu ko co skin nao ca

    // Tach chuoi va chuyen sang Integer de them vao danh sach
    Arrays.stream(ownedStr.split(",")).map(Integer::parseInt).forEach(ownedIds::add);
    return ownedIds;
  }


  public void saveOwnedSkins(List<Integer> ownedSkinIds) {
    Properties props = loadProperties();

    //Chuyen List<Integer> sang chuoi "1,2,3,11,12..."
    String ownedStr = ownedSkinIds.stream().map(String::valueOf).collect(Collectors.joining(","));
    props.setProperty(OWNED_SKINS_KEY, ownedStr);
    saveProperties(props);
    System.out.println("Owned skins saved!: " + ownedStr);
  }

  public void unlockSkin(int skinId){
    List<Integer> ownedSkins = loadOwnedSkins();
    if(!ownedSkins.contains(skinId)){
      ownedSkins.add(skinId);
      saveOwnedSkins(ownedSkins);
      System.out.println("Skin unlocked: " + skinId);
    }
  }
}

