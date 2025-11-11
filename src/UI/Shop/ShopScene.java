package UI.Shop;

import UI.SceneManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.TilePane;

public class ShopScene implements Initializable  {
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

  private final CreditsManager creditsManager = CreditsManager.getInstance();
  private final SkinManager skinManager = SkinManager.getInstance();


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    //Binding credits label
    shopCreditsLabel.textProperty().bind(Bindings.concat("$", creditsManager.getCreditsProperty().asString()));

    //tao listener cho toggle buttons
    shopButtons.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
      // Neu newToggle == null ( chon lai nut dang chon) -> quay lai oldToggle
      if (newToggle == null){
        if(oldToggle != null){
          oldToggle.setSelected(true);
        }
        return;
      }

      //Lay nut vua dc chon
      ToggleButton selectedButton = (ToggleButton) newToggle;

      //Kiem tra xem do la nut nao -> hien thi scrollpane tuong ung
      if(selectedButton == paddleButton){
           paddleScrollPane.setVisible(true);
           ballScrollPane.setVisible(false);
      }
      else if(selectedButton == ballButton){
           paddleScrollPane.setVisible(false);
           ballScrollPane.setVisible(true);
      }
    });

    exitButton.setOnAction(e -> {
      SceneManager.getInstance().switchTo("Menu");
    });

    loadPaddleSkins();
    loadBallSkins();
  }

  private void loadPaddleSkins(){
    //Clear
    paddleSkinContain.getChildren().clear();

    for(SkinData skin : skinManager.getPaddleSkins()){
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/Shop/Items.fxml"));
        Node itemNode = loader.load();
        ItemController controller = loader.getController();

        controller.setData(skin, true, creditsManager, skinManager, this::refreshPaddleList);

        paddleSkinContain.getChildren().add(itemNode);
      } catch(IOException e){
        e.printStackTrace();
      }
    }
  }


  private void loadBallSkins(){
    //Clear
    ballSkinContain.getChildren().clear();

    for(SkinData skin : skinManager.getBallSkins()){
      try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/Shop/Items.fxml"));
        Node itemNode = loader.load();
        ItemController controller = loader.getController();

        controller.setData(skin, false, creditsManager, skinManager, this::refreshBallList);

        ballSkinContain.getChildren().add(itemNode);
      } catch(IOException e){
        e.printStackTrace();
      }
    }
  }

  private void refreshPaddleList(){
    loadPaddleSkins();
  }

  private void refreshBallList(){
    loadBallSkins();
  }

}
