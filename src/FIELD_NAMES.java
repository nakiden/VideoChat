public class FIELD_NAMES {
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_IMAGE = "USER_IMAGE";
    public static final String MESSAGE = "MESSAGE";

    public static enum EventsTo {
        NEW_USER
        ,UPDATE_USER
        ,MESSAGE
    }

    public static enum EventsFrom {
        DEFAULT
        ,NEW_USER
        ,UPDATE_USER
        ,MESSAGE
    }
}
