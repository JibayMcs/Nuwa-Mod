package api.contentpack.json.conditions;

public class Check {
    public Type type;
    public Object value;

    public Type getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public enum Type {
        IS_EMPTY,
        CONTAIN,
        EQUAL,
        NOT_EQUAL
    }
}