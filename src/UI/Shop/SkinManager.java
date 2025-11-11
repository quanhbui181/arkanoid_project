package UI.Shop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SkinManager {

  private static SkinManager instance;
  private static final String SKINS_FILE = "skins.txt";


  //Paddle + ball skins list
  private final List<SkinData> paddleSkins = new ArrayList<>();
  private final List<SkinData> ballSkins = new ArrayList<>();

  //Skin dang dung
  private int selectedPaddleId = 1;
  private int selectedBallId = 1;


  private SkinManager() {
    initSkins();
    loadSkins();
  }

  public static SkinManager getInstance() {
    if (instance == null) {
      instance = new SkinManager();
    }
    return instance;
  }

  //Khoi tao mac dinh
  private void initSkins() {
    //Paddle skins
    paddleSkins.add(new SkinData(1, "/images/shopImages/paddleSkins/defaultPaddle.png", 0, true));
    paddleSkins.add(new SkinData(2, "/images/shopImages/paddleSkins/redPaddle.png", 500, false));
    paddleSkins.add(new SkinData(3, "/images/shopImages/paddleSkins/bluePaddle.png", 1500, false));
    paddleSkins.add(new SkinData(4, "/images/shopImages/paddleSkins/purplePaddle.png", 2000, false));
    paddleSkins.add(new SkinData(5, "/images/shopImages/paddleSkins/greenPaddle.png", 2500, false));
    paddleSkins.add(new SkinData(6, "/images/shopImages/paddleSkins/pinkPaddle.png", 3000, false));
    paddleSkins.add(new SkinData(7, "/images/shopImages/paddleSkins/orangePaddle.png", 3500, false));
    //.... them cac skin khac

    //Ball skins
    ballSkins.add(new SkinData(1, "/images/shopImages/ballSkins/defaultBall.png", 0, true));
    ballSkins.add(new SkinData(2, "/images/shopImages/ballSkins/ballSkin.png", 500, false));
    ballSkins.add(new SkinData(3, "/images/shopImages/ballSkins/ballSkin2.png", 1500, false));
    ballSkins.add(new SkinData(4, "/images/shopImages/ballSkins/ballSkin3.png", 2000, false));
    ballSkins.add(new SkinData(5, "/images/shopImages/ballSkins/ballSkin4.png", 2500, false));
    ballSkins.add(new SkinData(6, "/images/shopImages/ballSkins/ballSkin5.png", 3000, false));
    ballSkins.add(new SkinData(7, "/images/shopImages/ballSkins/ballSkin6.png", 3500, false));
    ballSkins.add(new SkinData(8, "/images/shopImages/ballSkins/ballSkin7.png", 4000, false));
    ballSkins.add(new SkinData(9, "/images/shopImages/ballSkins/ballSkin8.png", 4500, false));
    //.... them cac skin khac
  }

  // ---Getters---
  public List<SkinData> getPaddleSkins() {
    return paddleSkins;
  }

  public List<SkinData> getBallSkins() {
    return ballSkins;
  }

  public SkinData getSelectedPaddle() {
    return getSkinById(paddleSkins, selectedPaddleId);
  }

  public SkinData getSelectedBall() {
    return getSkinById(ballSkins, selectedBallId);
  }

  private SkinData getSkinById(List<SkinData> skins, int id) {
    for (SkinData skin : skins) {
      if (skin.getId() == id) {
        return skin;
      }
    }
    return null;
  }


  // ---- MUA SKIN ----
  public boolean buyPaddle(int id) {
    SkinData skin = getSkinById(paddleSkins, id);
    if (skin != null) {
      skin.setOwned(true); // danh dau da so huu
      saveSkins();
      return true;
    }
    return false;
  }

  public boolean buyBall(int id) {
    SkinData skin = getSkinById(ballSkins, id);
    if (skin != null) {
      skin.setOwned(true); // danh dau da so huu
      saveSkins();
      return true;
    }
    return false;
  }


  // ---- EQUIP SKIN ----
  public void selectPaddle(int id) {
    SkinData skin = getSkinById(paddleSkins, id);
    if (skin != null && skin.isOwned()) {
      selectedPaddleId = id;
      saveSkins();
    }
  }

  public void selectBall(int id) {
    SkinData skin = getSkinById(ballSkins, id);
    if (skin != null && skin.isOwned()) {
      selectedBallId = id;
      saveSkins();
    }
  }

//Load Skins + save skins from file

  private void loadSkins() {
    File file = new File(SKINS_FILE);
    if (!file.exists()) {
      saveSkins();
      return;
    }
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("selectedPaddle:")) {
          selectedPaddleId = Integer.parseInt(line.split(":")[1]);
        } else if (line.startsWith("selectedBall:")) {
          selectedBallId = Integer.parseInt(line.split(":")[1]);
        } else if (line.startsWith("ownedPaddles:")) {
          setOwnedSkinsFromLine(line, paddleSkins);
        } else if (line.startsWith("ownedBalls:")) {
          setOwnedSkinsFromLine(line, ballSkins);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void saveSkins() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(SKINS_FILE))) {
      writer.write("selectedPaddle:" + selectedPaddleId);
      writer.newLine();
      writer.write("selectedBall:" + selectedBallId);
      writer.newLine();

      writer.write("ownedPaddles:" + getOwnedIds(paddleSkins));
      writer.newLine();
      writer.write("ownedBalls:" + getOwnedIds(ballSkins));
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //Method that setOwnedSkins from each line of file
  private void setOwnedSkinsFromLine(String line, List<SkinData> skins) {
    String[] parts = line.split(":", 2);
    if (parts.length > 1) {
      String[] ids = parts[1].split(",");
      for (String idStr : ids) {
        try {
          int id = Integer.parseInt(idStr);
          SkinData skin = getSkinById(skins, id);
          if (skin != null) {
            skin.setOwned(true);
          }
        } catch (NumberFormatException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private String getOwnedIds(List<SkinData> skins) {
    StringBuilder ids = new StringBuilder();
    for (SkinData skin : skins) {
      if (skin.isOwned()) {
        if (ids.length() > 0) {
          ids.append(",");
        }
        ids.append(skin.getId());
      }
    }
    return ids.toString();
  }
}



