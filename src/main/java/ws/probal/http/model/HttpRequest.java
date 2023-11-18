package ws.probal.http.model;

import ws.probal.http.exception.HttpParsingException;
import ws.probal.http.model.enums.HttpMethodEnum;
import ws.probal.http.model.enums.HttpStatusCode;

public class HttpRequest extends HttpMessage {

    private HttpMethodEnum method;
    private String requestTarget;
    private String httpVersion;

    public HttpRequest() {
    }

    public HttpMethodEnum getMethod() {
        return method;
    }

    public void setMethod(String methodName) throws HttpParsingException {
        for (HttpMethodEnum httpMethodEnum : HttpMethodEnum.values()) {
            if (methodName.equals(httpMethodEnum.name())) {
                this.method = HttpMethodEnum.valueOf(methodName);
                return;
            }
        }
        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);

    }
}
