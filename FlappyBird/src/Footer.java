import java.awt.*;

public class Footer {
    private int posX;
    private int posY;
    private int width;
    private int height;
    private Image image;
    private int velocityX;

    // Constructor
    public Footer(int posX, int posY, int width, int height, int velocityX, Image image) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.velocityX = velocityX;
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

    // Setter & getter kecepatan horizontal
    public void setVelocityX(int velocityX) {this.velocityX = velocityX;}
    public int getVelocityX() {return velocityX;}
}
