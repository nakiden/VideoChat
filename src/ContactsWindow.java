import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;


public class ContactsWindow extends JFrame{

    public ContactsWindow() {
        super("Skype - Contacts");
        setSize(Settings.WINDOW_WIDTH / 4, Settings.WINDOW_HEIGHT / 2);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
    }

    public void printGUI(){
        final ArrayList<User> users = Main.users.getUsers();

        for (int i = 0; i < users.size(); i++) {
            Image dimg = users.get(i).getBufferedImage().getScaledInstance(Settings.SMALL_WINDOW_WIDTH
                    , Settings.SMALL_WINDOW_HEIGHT, Image.SCALE_SMOOTH);
            JLabel picLabel = new JLabel(new ImageIcon(dimg));
            picLabel.setVisible(true);
            picLabel.setSize(Settings.SMALL_WINDOW_WIDTH, Settings.SMALL_WINDOW_HEIGHT);
            final int finalI = i;
            picLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    new FullDesctopWindow(users.get(finalI)).setVisible(true);
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            picLabel.setToolTipText(users.get(i).name);
            picLabel.setLocation(0, (i) * 75);
            add(picLabel);
            update(getGraphics());
        }
    }
}
