import org.java_websocket.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SocketMaster {

    public static String returnObjectToSend(String id, String event) throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("event", event);
        obj.put("data", returnJsonData(event.replace(" ", "_"), id));
        return  obj.toString();
    }

    private static JSONObject returnJsonData(String event, String id){
        FIELD_NAMES.EventsTo cmd = FIELD_NAMES.EventsTo.valueOf(event.toUpperCase());
        JSONObject data = new JSONObject();

        try {
            switch (cmd) {
                case UPDATE_USER:
                case NEW_USER:
                    data.put(FIELD_NAMES.USER_NAME, Settings.MY_NAME);
                    data.put(FIELD_NAMES.USER_IMAGE,
                            Base64.encodeBytes(Main.convertImgToByte(Printscreen.makePrintscreen())));
                    break;
                case MESSAGE:
                    data.put(FIELD_NAMES.MESSAGE, id);
                    data.put(FIELD_NAMES.USER_NAME, Settings.MY_NAME);
                    break;
                default:
                    break;
            }
        return data;

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void routeMsg(String event, JSONObject array) throws IOException, JSONException {
        event = event.replace(" ", "_");
        FIELD_NAMES.EventsFrom cmd = FIELD_NAMES.EventsFrom.valueOf(event.toUpperCase());

        switch (cmd) {
            case NEW_USER:
                onNewUser(array);
                break;
            case UPDATE_USER:
                onUpdateUser(array);
                break;
            case MESSAGE:
                System.out.println("HIER -> " + 2);
                onMessage(array);
                break;
            default:
                break;
        }
    }

    public static void parseJSON(String json) throws JSONException, IOException {
        JSONTokener tokener = new JSONTokener(json);
        JSONObject jsonObj = new JSONObject(tokener);
        JSONObject ja = jsonObj.getJSONObject("data");
        routeMsg(jsonObj.get("event").toString(), ja);
    }

    private static void onNewUser(JSONObject array) throws JSONException, IOException {
        String username = array.getString(FIELD_NAMES.USER_NAME);
        String imgStr = array.getString(FIELD_NAMES.USER_IMAGE);
        BufferedImage img = Main.convertByteToImg(Base64.decode(imgStr));
        ImageIO.write(img, "png", new File("./new.png"));
        User user = new User(username, img);
        Main.users.addUser(user);
        Main.cw.printGUI();
    }

    private static void onUpdateUser(JSONObject array) throws JSONException, IOException {
        String username = array.getString(FIELD_NAMES.USER_NAME);
        String imgStr = array.getString(FIELD_NAMES.USER_IMAGE);
        BufferedImage img = Main.convertByteToImg(Base64.decode(imgStr));
        ImageIO.write(img, "png", new File("./new.png"));

        if (Main.users.updateUserByName(username, img) == null){
            User user = new User(username, img);
            Main.users.addUser(user);
        }
        Main.cw.printGUI();
    }

    private static void onMessage(JSONObject array) throws JSONException {
        Main.msg.addMessage(
                "MSG FROM:" + array.getString(FIELD_NAMES.USER_NAME) + " : " + array.getString(FIELD_NAMES.MESSAGE)
        );
    }

}
