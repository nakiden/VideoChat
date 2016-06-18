import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.peer.MouseInfoPeer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main extends JFrame implements ActionListener {


    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        new SetNameWindow().setVisible(true);
    }

    public static  ContactsWindow cw;
    public static  ChatWindow chw;
    public static  Messages msg;
    public static  Users users;
    private boolean connected = false;
    public static WebSocketClient mWebSocketClient;
    private JPanel contentPane;

    JButton saveBtn = new JButton("Save");
    JButton sendBtn = new JButton("Send");
    JButton changeBtn = new JButton("Change");
    JButton clearBtn = new JButton("Clear");
    JTextField text = new JTextField("");

    Main(){
        super("Skype - JAVA");
        setSize(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        this.setBackground(Color.cyan);
    }

    public void init_main(){
        msg = new Messages();
        users = new Users();
        cw = new ContactsWindow();
        chw = new ChatWindow();
        cw.setVisible(true);
        chw.setVisible(true);
        runSocket();
        contentPane = new GraphicsBackground(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
        setContentPane(contentPane);
        saveBtn.addActionListener(this);
        sendBtn.addActionListener(this);
        clearBtn.addActionListener(this);
        add(sendBtn);
        add(clearBtn);
        add(saveBtn);
        text.setColumns(20);
        text.setVisible(true);
        showGUI(false);
    }

    public void showGUI(boolean show){
        sendBtn.setVisible(show);
        changeBtn.setVisible(show);
        saveBtn.setVisible(show);
    }

    private void runSocket(){
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URI uri = new URI("ws://10.0.0.109:8080");

                    mWebSocketClient = new WebSocketClient(uri) {
                        @Override
                        public void onOpen(ServerHandshake serverHandshake) {
                            connected = true;
                            mWebSocketClient.send(SocketMaster.returnObjectToSend("", "new user"));
                            runMyBackgroundSender();
                            System.out.println("Opened");
                        }

                        @Override
                        public void onMessage(String s){
                            SocketMaster.parseJSON(s);
                            contentPane.update(getGraphics());
                            save();
                        }

                        @Override
                        public void onClose(int i, String s, boolean b) {
                            System.out.println("Closed " + s);
                            System.out.println("Im trying");
                            connected = false;
                        }

                        @Override
                        public void onError(Exception e) {
                            System.out.println("Error " + e.getMessage());
                        }
                    };
                    mWebSocketClient.connect();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();
    }

    private void runMyBackgroundSender() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

                while (connected) {

                    try {
                        mWebSocketClient.send(SocketMaster.returnObjectToSend("", "update user"));
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();
    }

    public void save() throws IOException {
        showGUI(false);
        BufferedImage bImg = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D cg = bImg.createGraphics();
        this.paintAll(cg);
        ImageIO.write(bImg, "png", new File(Settings.IMAGE_PATH));
        showGUI(false);
        WPChanger.User32.INSTANCE.SystemParametersInfo(0x0014, 0, Settings.IMAGE_PATH, 1);
    }

    public static byte[] convertImgToByte(BufferedImage img) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(img, "png", byteArrayOutputStream);
        return null;
    }

    public static BufferedImage convertByteToImg(byte[] bytes) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(bytes));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("Send")){
            mWebSocketClient.send(SocketMaster.returnObjectToSend(text.getText(), "message"));
        }

        if (e.getActionCommand().equals("Save")){
            save();
        }

        if (e.getActionCommand().equals("Clear")){
            Graphics g = getGraphics();
            g.setColor(Color.black);
            g.clearRect(0, 0, 800, 600);
        }
    }
}
