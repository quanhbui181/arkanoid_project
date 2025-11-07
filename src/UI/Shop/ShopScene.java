package UI.Shop;

import UI.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javax.imageio.plugins.tiff.TIFFImageReadParam;

public class ShopScene {
  @FXML
  private ScrollPane paddleScrollPane;
  @FXML
  private ScrollPane ballScrollPane;

  @FXML
  private ToggleButton paddleButton;
  @FXML
  private ToggleButton ballButton;
  @FXML
  private ToggleGroup shopButtons;
  @FXML
  private Button exitButton;

  @FXML
  private TilePane paddleSkinContain;
  @FXML
  private TilePane ballSkinContain;

  @FXML
  private Label shopCreditsLabel;

  private List<SkinData> paddleSkin = new ArrayList<>();
  private List<SkinData> ballSkins = new ArrayList<>();

  private LongProperty credits;
  private CreditManager creditManager;
  private SkinManager skinManager;

  private int currentlyEquippedPaddleId;
  private int currentlyEquippedBallId;
  private List<Integer> ownedSkinIds = new ArrayList<>();

  public void initData(LongProperty creditsProp, CreditManager cm, SkinManager sm){
    this.credits = creditsProp;
    this.creditManager = cm;
    this.skinManager = sm;


    // Tai thong tin skin so huu va equip
    int[] equippedSkins = skinManager.loadEquippedSkins();
    this.currentlyEquippedPaddleId = equippedSkins[0];
    this.currentlyEquippedBallId = equippedSkins[1];

    // Lay danh sach skin so huu
    this.ownedSkinIds = skinManager.loadOwnedSkins();

    // Thiet lap danh sach skin
    setupSkinLists();

    // Load skin len UI
    loadSkinsToUI();

    //Binding de tu cap nhat label credits
    shopCreditsLabel.textProperty().bind(Bindings.concat("$",this.credits.asString()));

    //tao listener
    shopButtons.selectedToggleProperty().addListener((obs,oldToggle,newToggle) -> {

      //neu newtoggle la null (chon lai nut dang chon) thi quay ve oldtoggle
      if(newToggle == null){
        if(oldToggle != null){
          oldToggle.setSelected(true);
        }
        return;
      }

      //Lay nut vua dc chon
      ToggleButton selectedButton = (ToggleButton) newToggle;

      //Kiem tra xem do la nut nao => hien thi scrollpane tuong ung
      if(selectedButton == paddleButton){
        paddleScrollPane.setVisible(true);
        ballScrollPane.setVisible(false);
      }
      else if(selectedButton == ballButton){
        paddleScrollPane.setVisible(false);
        ballScrollPane.setVisible(true);
      }
    });

    paddleScrollPane.setVisible(true);
    ballScrollPane.setVisible(false);

    exitButton.setOnAction(e -> {
      SceneManager.getInstance().switchTo("Menu");
    });

  }

  private void setupSkinLists(){
    //Thiet lap danh sach cac skin
    //paddle skins
    paddleSkin.add(new SkinData(1,"/images/shopImages/paddleSkins/defaultPaddle.png",0,ownedSkinIds.contains(1)));
    paddleSkin.add(new SkinData(2,"/images/shopImages/paddleSkins/redPaddle.png",500,ownedSkinIds.contains(2)));
    paddleSkin.add(new SkinData(3, "/images/shopImages/paddleSkins/bluePaddle.png",1500,ownedSkinIds.contains(3)));
    paddleSkin.add(new SkinData(4, "/images/shopImages/paddleSkins/purplePaddle.png",2000,ownedSkinIds.contains(4)));
    paddleSkin.add(new SkinData(5, "/images/shopImages/paddleSkins/greenPaddle.png",2500,ownedSkinIds.contains(5)));
    paddleSkin.add(new SkinData(6, "/images/shopImages/paddleSkins/pinkPaddle.png",3000,ownedSkinIds.contains(6)));
    paddleSkin.add(new SkinData(7, "/images/shopImages/paddleSkins/orangePaddle.png",3500,ownedSkinIds.contains(7)));
    //... them cac skin khac

    //ball skins

  }

  private void loadSkinsToUI(){
    // xoa cac skin cu truoc khi load lai
    paddleSkinContain.getChildren().clear();
    ballSkinContain.getChildren().clear();

    //load paddle skins
    for(SkinData skin: paddleSkin){
      //PASS ID SKIN PADDLE HIEN TAI
      paddleSkinContain.getChildren().add(createSkinItemNode(skin, this.currentlyEquippedPaddleId));
    }

    // load ball skins
  }

  private Node createSkinItemNode(SkinData skinData, int currentlyEquippedId){
    try{
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/Shop/Items.fxml"));
      AnchorPane itemNode = loader.load();

      //Lay controller de set data
      itemController controller = loader.getController();

      boolean isEquipped = (skinData.getId() == currentlyEquippedId);

      controller.setData(skinData, this.credits.get(), isEquipped,
          (boughtSkin) -> {
        handlePurchase(boughtSkin); },
          (equippedSkin) -> {
        handleEquip(equippedSkin); }
      );

      // Luu controller vao userData de sau nay cap nhat giao dien
      itemNode.setUserData(controller);

      return itemNode;
    } catch (IOException e){
      e.printStackTrace();
      return null;
    }
  }

  private void handlePurchase(SkinData skinToBuy){
    long currentCredits = credits.get(); // Lay credits tu property

    if(currentCredits >= skinToBuy.getPrice() && !skinToBuy.isOwned()) {
      // Tru credits
      credits.set(currentCredits - skinToBuy.getPrice()); // Tu dong cap nhat label vi da bind

      skinToBuy.setOwned(true);
      skinManager.unlockSkin(skinToBuy.getId());

      loadSkinsToUI();
    }
  }

  private void handleEquip(SkinData skinToEquip){ // dung chung cho paddle va ball
    if(skinToEquip.getId() < 10) { //paddle
      this.currentlyEquippedPaddleId = skinToEquip.getId();
    } else {
      this.currentlyEquippedBallId = skinToEquip.getId();
    }

    //Luu trang thai equip
    skinManager.saveEquippedSkins(currentlyEquippedPaddleId, currentlyEquippedBallId);

      //Cap nhat giao dien
      loadSkinsToUI();
    }


  }

