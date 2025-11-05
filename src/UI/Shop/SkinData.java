package UI.Shop;

public class SkinData {
  private final int id;
  private final String imagePath;
  private final long price;
  private boolean isOwned;

  public SkinData(int id, String imagePath, long price, boolean isOwned) {
    this.id = id;
    this.imagePath = imagePath;
    this.price = price;
    this.isOwned = isOwned;
  }

  public int getId() {
    return id;
  }

  public String getImagePath() {
    return imagePath;
  }

  public long getPrice() {
    return price;
  }

  public boolean isOwned() {
    return isOwned;
  }

  public void setOwned(boolean owned) {
    isOwned = owned;
  }
}
