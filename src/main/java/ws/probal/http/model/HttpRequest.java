package ws.probal.http.model;

import ws.probal.http.exception.BadHttpVersionException;
import ws.probal.http.exception.HttpParsingException;
import ws.probal.http.model.enums.HttpMethodEnum;
import ws.probal.http.model.enums.HttpStatusCode;
import ws.probal.http.model.enums.HttpVersion;

public class HttpRequest extends HttpMessage {

    private HttpMethodEnum method;
    private String requestTarget;
    private String originalHttpVersion; // from request
    private HttpVersion bestCompatibleHttpVersion;

    public HttpRequest() {
    }

    public HttpMethodEnum getMethod() {
        return method;
    }

    public String getRequestTarget() {
        return requestTarget;
    }

    public String getOriginalHttpVersion() {
        return originalHttpVersion;
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

    public void setRequestTarget(String requestTarget) throws HttpParsingException {
        if (requestTarget == null || requestTarget.isEmpty())
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_500_INTERNAL_SERVER_ERROR);
        this.requestTarget = requestTarget;
    }

    public void setHttpVersion(String originalHttpVersion) throws BadHttpVersionException, HttpParsingException {
        this.originalHttpVersion = originalHttpVersion;
        this.bestCompatibleHttpVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);
        if (this.bestCompatibleHttpVersion == null)
            throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_505_HTTP_VERSION_NOT_SUPPORTED);
    }
}
