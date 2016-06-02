import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Printscreen {

    public static BufferedImage makePrintscreen() throws AWTException {
        Robot robot = new Robot();

        Dimension doom = Toolkit.getDefaultToolkit().getScreenSize();

        Rectangle rect = new Rectangle(doom);

        BufferedImage bfimg = robot.createScreenCapture(rect);
        String wallpaper_file = "c://wallpaper.jpg";

        try {
            if (ImageIO.write(bfimg, "png", new File("./printscreen.png")))
            {
                System.out.println("-- saved");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bfimg;
    }
}
