package UI.Shop;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.Constants;

public class itemController {

  @FXML
  private Button buyButton;

  @FXML
  private Button equipButton;

  @FXML
  private Label priceLabel;

  @FXML
  private ImageView skinImageView;

  private SkinData skinData;
  private Consumer<SkinData> buyAction;
  private Consumer<SkinData> equipAction;

  public void setData(SkinData skin, long credits, boolean isEquipped, Consumer<SkinData> onBuy, Consumer<SkinData> onEquip) {
    this.skinData = skin;
    this.buyAction = onBuy;
    this.equipAction = onEquip;

    //Set anh:
    Image skinImage = null;
    try(InputStream stream = getClass().getResourceAsStream(skin.getImagePath())) {
      if(stream != null){
        skinImage = new Image(stream);
      }
      else{
        System.err.println("Ko thay anh: " + skin.getImagePath());
      }
    } catch (IOException e) {
      System.err.println("Loi khi doc file anh: " + e.getMessage());
    }

    skinImageView.setImage(skinImage);

    //Set price
    priceLabel.setText(Constants.CURRENCY + skin.getPrice());

    //logic nut buy
    updateButtonState(credits, isEquipped);

    buyButton.setOnAction(e -> {
      buyAction.accept(this.skinData);
    });

    equipButton.setOnAction(e -> {
      equipAction.accept(this.skinData);
    });
  }

  public void updateButtonState(long credits, boolean isEquipped) {

    // ĐÃ SỞ HỮU
    if (skinData.isOwned()) {
      buyButton.setText("Owned");
      buyButton.setDisable(true);

      if(isEquipped){
        //Equipping
        equipButton.setText("Equipped");
        equipButton.setDisable(true);
      }
      else { // not equipping
        equipButton.setText("Equip");
        equipButton.setDisable(false);
      }
    }
    // CHƯA SỞ HỮU
    else{
      buyButton.setText("Buy");
      buyButton.setDisable(true);
      equipButton.setDisable(true);

      if(credits >= skinData.getPrice()){
        buyButton.setDisable(false);
        equipButton.setDisable(false);
      }
    }

  }
}
