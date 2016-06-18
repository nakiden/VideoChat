import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class FullDesctopWindow extends JFrame {

    private JPanel contentPane;
    public BufferedImage currentImage;

    public FullDesctopWindow(final User user) {
        super("Skype - Contacts");
        currentImage = user.getBufferedImage();
        final int width = currentImage.getWidth() / 2;
        final int height = currentImage.getHeight() / 2;
        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLayout(new FlowLayout());

        contentPane  = new JPanel(){

            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                Image dimg = user.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                g.drawImage(dimg, 0, 0, this);
            }

        };
        setContentPane(contentPane);

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

                while(true) {
                    currentImage = user.getBufferedImage();
                    contentPane.update(getGraphics());

                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();
    }
}
