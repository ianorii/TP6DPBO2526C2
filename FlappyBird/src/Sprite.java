import java.awt.*;

public class Sprite {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;

    // Constructor
    public Sprite(int posX, int posY, int width, int height, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    // Setter & getter posisi X
    public void setPosX(int posX) {this.posX = posX;}
    public int getPosX() {return posX;}

    // Setter & getter posisi Y
    public void setPosY(int posY) {this.posY = posY;}
    public int getPosY() {return posY;}

    // Setter & getter lebar
    public void setWidth(int width) {this.width = width;}
    public int getWidth() {return width;}

    // Setter & getter tinggi
    public void setHeight(int height) {this.height = height;}
    public int getHeight() {return height;}

    // Setter & getter gambar
    public void setImage(Image image) {this.image = image;}
    public Image getImage() {return image;}
}
