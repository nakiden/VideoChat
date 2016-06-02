import javax.swing.*;
import java.awt.*;


public class GraphicsBackground extends JPanel {
    Graphics2D g2;

    public GraphicsBackground(int width, int height){
        super();
        setBackground(Settings.BACKGROUND_COLOR);
        setSize(width, height);
    }


    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g2 = (Graphics2D)g;
        g2.setColor(Settings.TEXT_COLOR);

        for (int i = 0; i < Main.msg.getMessages().size(); i++) {

            if (i == 0) {
                g2.drawString(Main.msg.getMessages().get(i), 0, 20);
            } else {
                g2.drawString(Main.msg.getMessages().get(i), 0, 20 + (i * Settings.SPACE_BETWEEN_ROWS));
            }
        }
    }
}
