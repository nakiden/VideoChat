import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main extends JFrame   {

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

    public int oldX = -1;
    public int oldY = -1;

    JButton saveBtn = new JButton("Save");
    JButton sendBtn = new JButton("Clear");
    JButton changeBtn = new JButton("Change");
    JButton okBtn = new JButton("SEND");
    JTextField text = new JTextField("");
    private JPanel contentPane;

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

        changeBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

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
        saveBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                save();
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
        sendBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Graphics g = getGraphics();
                g.setColor(Color.black);
                g.clearRect(0, 0, 800, 600);
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
        okBtn.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mWebSocketClient.send(SocketMaster.returnObjectToSend(text.getText(), "message"));
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
        add(sendBtn);
        add(changeBtn);
        add(saveBtn);
        text.setColumns(20);
        text.setVisible(true);
        //  add(text);
        //  add(okBtn);
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
                URI uri;

                try {
                   // uri = new URI("ws://192.168.0.117:8080");
                    uri = new URI("ws://10.0.0.109:8080");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    return;
                }

                mWebSocketClient = new WebSocketClient(uri) {
                    @Override
                    public void onOpen(ServerHandshake serverHandshake) {
                        connected = true;
                        mWebSocketClient.send(SocketMaster.returnObjectToSend("", "new user"));
                        runMyBackgroundSender();
                        System.out.println("Opened");
                    }

                    @Override
                    public void onMessage(String s) {
                        SocketMaster.parseJSON(s);
                        print();
                        System.out.println("msg = " + s);
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
            }
        });
        th.start();
    }

    private void runMyBackgroundSender(){
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

                while (connected) {

                    try {
                        mWebSocketClient.send(SocketMaster.returnObjectToSend("", "update user"));
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();
    }

    public void print(){
        contentPane.update(getGraphics());
        save();
    }

    public void save()
    {
        showGUI(false);
        Container dPanel = this;
        BufferedImage bImg = new BufferedImage(dPanel.getWidth(), dPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        //users.getUsers().get(0).setBufferedImage(bImg);
        Graphics2D cg = bImg.createGraphics();
        dPanel.paintAll(cg);

        try {

            if (ImageIO.write(bImg, "png", new File(Settings.IMAGE_PATH))) {
                System.out.println("-- saved");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        showGUI(false);
        WPChanger.User32.INSTANCE.SystemParametersInfo(0x0014, 0, Settings.IMAGE_PATH, 1);
    }

    public static byte[] convertImgToByte(BufferedImage img){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            ImageIO.write(img, "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage convertByteToImg(byte[] bytes){

        try {
            return ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
