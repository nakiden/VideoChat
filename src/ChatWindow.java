import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ChatWindow extends JFrame{

    JButton okBtn = new JButton("SEND");
    JTextField text = new JTextField("");

    public ChatWindow() {
        super("Skype - Contacts");
        setSize(Settings.WINDOW_WIDTH / 2, Settings.WINDOW_HEIGHT / 6);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        // printGUI();
        okBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Main.mWebSocketClient.send(SocketMaster.returnObjectToSend(text.getText(), "message"));
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
        add(okBtn);
        text.setColumns(20);
        text.setVisible(true);
        add(text);
    }
}
