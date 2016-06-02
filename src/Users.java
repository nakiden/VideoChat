import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Users {

    private ArrayList<User> users;

    public ArrayList<User> addUser(User user){
        users.add(user);
        return users;
    }

    public Users(String myname, BufferedImage img){
        users = new ArrayList<User>();
        users.add(new User(myname, img));
    }

    public Users(){
        users = new ArrayList<User>();
    }

    public ArrayList<User> deleteUserByName(String name){

        for (int i = 0; i < users.size(); i++){
            User obj = users.get(i);

            if (obj.name.matches(name)){
                users.remove(obj);
            }
        }

        return users;
    }

    public ArrayList<User> updateUserByName(String name, BufferedImage img){

        for (User obj : users) {

            if (obj.name.matches(name)) {
                obj.setBufferedImage(img);
                return users;
            }
        }
        return null;
    }

    public ArrayList<User> getUsers(){
        return users;
    }
}
