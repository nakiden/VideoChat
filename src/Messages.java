import java.util.ArrayList;

public class Messages {
    private ArrayList<String> messages;

    public Messages(){
        messages = new ArrayList<String>();
    }

    public void clearMessages(){
        messages.removeAll(messages);
    }

    public ArrayList<String> addMessage(String str){
        messages.add(str);
        return messages;
    }

    public ArrayList<String> getMessages(){
        return messages;
    }
}
