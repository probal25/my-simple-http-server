package ws.probal.http.model.enums;

public enum HttpMethodEnum {
    GET, HEAD;
    public static final int MAX_LENGTH;

    static {
        int tempMaxLength = -1;
        for (HttpMethodEnum method : HttpMethodEnum.values()) {
            if (method.name().length() > tempMaxLength) {
                tempMaxLength = method.name().length();
            }
        }
        MAX_LENGTH = tempMaxLength;
    }
}
