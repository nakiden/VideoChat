import java.awt.*;
import java.awt.image.BufferedImage;


public class User {

    public String name;
    private BufferedImage desctop;

    public User(String name, BufferedImage img){
        this.desctop = img;
        this.name = name;
    }

    public BufferedImage setBufferedImage(BufferedImage img){
        this.desctop = img;
        return this.desctop;
    }

    public BufferedImage getBufferedImage(){
        return this.desctop;
    }

    public Image getSizebleImage(int width, int height){
        return this.desctop.getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }
}
