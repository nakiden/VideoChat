import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class FullDesctopWindow extends JFrame {

    private JPanel contentPane;
    public BufferedImage currentimage;

    public FullDesctopWindow(final User user) {
        super("Skype - Contacts");
        BufferedImage img = user.getBufferedImage();
        final int width = img.getWidth() / 2;
        final int height = img.getHeight() / 2;
        setSize(width, height);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        currentimage = img;
        contentPane  = new JPanel(){
            Graphics2D g2;

            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                g2 = (Graphics2D)g;
                g2.setColor(Color.BLACK);

                Image dimg = user.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                g2.drawImage(dimg, 0, 0, this);
            }

        };
        setContentPane(contentPane);

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    updateBackground(user.getBufferedImage());
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

    public void updateBackground(BufferedImage img){
        currentimage = img;
        contentPane.update(getGraphics());
    }
}
