import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SetNameWindow extends JFrame{

    JButton okBtn = new JButton("SET NAME");
    JTextField text = new JTextField("");

    public SetNameWindow() {
        super("Skype - SET YOUR NAME");
        setSize(Settings.WINDOW_WIDTH / 2, Settings.WINDOW_HEIGHT / 6);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        // printGUI();
        okBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (text.getText().length() > 2) {
                    Settings.MY_NAME = text.getText();
                    setVisible(false);
                    Main main = new Main();
                    main.setVisible(true);
                    main.init_main();
                } else {
                    text.setText("WRONG_NAME");
                }
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
