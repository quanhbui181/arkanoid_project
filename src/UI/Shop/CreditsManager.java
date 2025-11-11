package UI.Shop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CreditsManager {

  //object creditManager duy nhat
  private static CreditsManager instance;
  private final IntegerProperty credits = new SimpleIntegerProperty(0);
  private static final String CREDITS_FILE = "credits.txt";

  private CreditsManager() {
    loadCredits();
  } // k cho phep khoi tao obj ben ngoai

  public static CreditsManager getInstance(){
    if(instance == null){
      instance = new CreditsManager();
    }
    return instance;
  }

  // Load credits from file
  private void loadCredits(){
    File file = new File(CREDITS_FILE);
    if(!file.exists()){
      credits.set(0); // If file not found, set credits to 0(default)
      saveCredits();
      return;
    }

    try(BufferedReader reader = new BufferedReader(new FileReader(file))) { //Doc file
      String line =  reader.readLine(); // Doc dong dau tien
      if(line != null && !line.isEmpty()){
        credits.set(Integer.parseInt(line)); // Chuyen string -> int roi set vao credits
      }
    } catch (IOException e){
      e.printStackTrace();
    }
  }

  private void saveCredits(){
    try(BufferedWriter writer = new BufferedWriter(new FileWriter(CREDITS_FILE))){
      writer.write(String.valueOf(getCredits()));
    } catch (IOException e){
      e.printStackTrace(); // In ra toan bo ttin chi tiet ve loi e
    }
  }

  // add credits
  public void addCredits(int amount){
    int newCredits = getCredits() + amount;
    credits.set(newCredits);
    saveCredits();
  }

  //spend credits
  public boolean spendCredits(int amount){ //Only spend if enough credits
    if(getCredits() >= amount){
      int newCredits = getCredits() - amount;
      credits.set(newCredits);
      saveCredits();
      return true;
    }
    return false; // khong du credit
  }


  public int getCredits(){
    return credits.get();
  }

  public void setCredits(int amount){
    credits.set(amount);
    saveCredits();
  }

  public IntegerProperty getCreditsProperty(){
    return credits;
  }

}
