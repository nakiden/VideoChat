import org.java_websocket.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SocketMaster {

    public static String returnObjectToSend(String id, String event){
        JSONObject obj = new JSONObject();
        String destination = "server";
        String evt = event;

        try {
            obj.put("id", id);
            obj.put("event", evt);
            obj.put("data", returnJsonData(event.replace(" ", "_"), id));
        } catch (JSONException jex){
            //Log.i("~mylog~JSON", jex.toString());
        }
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
                    data.put(FIELD_NAMES.USER_IMAGE, Base64.encodeBytes(Main.convertImgToByte(Printscreen.makePrintscreen())));
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
        }
        return null;
    }


    private static void routeMsg(String id, String event, JSONObject array){
        event = event.replace(" ", "_");
        FIELD_NAMES.EventsFrom cmd = FIELD_NAMES.EventsFrom.DEFAULT;
        try {
            cmd = FIELD_NAMES.EventsFrom.valueOf(event.toUpperCase());
        } catch (Exception e){}
        try {
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
        } catch (Exception jex){}
    }

    public static String parseJSON(String json){
        String event;
        String id;
        try {
            JSONTokener tokener = new JSONTokener(json);

            JSONObject jsonObj = new JSONObject(tokener);
            id = jsonObj.get("id").toString();
            event = jsonObj.get("event").toString();
            System.out.println("HIER -> " + 2);
            JSONObject ja = jsonObj.getJSONObject("data");
            routeMsg(id, event, ja);
        } catch (JSONException jex){
            System.out.println("ERROR PARSING DATA " + jex);
        }
        return "";
    }

    private static void onNewUser(JSONObject array){

        try {
            String username = array.getString(FIELD_NAMES.USER_NAME);
            String imgStr = array.getString(FIELD_NAMES.USER_IMAGE);
            BufferedImage img = Main.convertByteToImg(Base64.decode(imgStr));

            if (ImageIO.write(img, "png", new File("./new.png"))) {
                System.out.println("-- saved");
            }
            System.out.println("~~~~~~~~~~1~~~~~~~~~~~");
            User user = new User(username, img);
            System.out.println("~~~~~~~~~~2~~~~~~~~~~~");
            Main.users.addUser(user);
            System.out.println("~~~~~~~~~~3~~~~~~~~~~~");
            Main.cw.printGUI();
        } catch (Exception ex) {
            System.out.println("HIER -> " + ex);
        }
    }

    private static void onUpdateUser(JSONObject array){

        try {
            String username = array.getString(FIELD_NAMES.USER_NAME);
            String imgStr = array.getString(FIELD_NAMES.USER_IMAGE);
            BufferedImage img = Main.convertByteToImg(Base64.decode(imgStr));

            if (ImageIO.write(img, "png", new File("./new.png"))) {
                System.out.println("-- saved");
            }

            if (Main.users.updateUserByName(username, img) == null){
                User user = new User(username, img);
                Main.users.addUser(user);
            }
            Main.cw.printGUI();
        } catch (Exception ex) {
            System.out.println("HIER -> " + ex);
        }
    }

    private static void onMessage(JSONObject array){
        String msg = null;
        try {
            msg = "MSG FROM:" + array.getString(FIELD_NAMES.USER_NAME) + " : " + array.getString(FIELD_NAMES.MESSAGE);
            Main.msg.addMessage(msg);

        } catch (JSONException e) {
            System.out.println("HIER -> " + e);
        }
    }

}
