import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Printscreen {

    public static BufferedImage makePrintscreen() throws AWTException, IOException {
        Robot robot = new Robot();

        Dimension doom = Toolkit.getDefaultToolkit().getScreenSize();

        Rectangle rect = new Rectangle(doom);

        BufferedImage bfimg = robot.createScreenCapture(rect);

        String wallpaper_file = "c://wallpaper.jpg";

        ImageIO.write(bfimg, "png", new File("./printscreen.png"));
        return bfimg;
    }
}
