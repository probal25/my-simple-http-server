package ws.probal.http.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.probal.http.constants.ASCIICharConstants;
import ws.probal.http.exception.HttpParsingException;
import ws.probal.http.model.HttpRequest;
import ws.probal.http.model.enums.HttpMethodEnum;
import ws.probal.http.model.enums.HttpStatusCode;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    public HttpRequest parseHttpRequest(InputStream in) throws HttpParsingException {

        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.US_ASCII);
        HttpRequest httpRequest = new HttpRequest();
        try {
            parseRequestLine(reader, httpRequest);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        parseHeaders(reader, httpRequest);
        parseBody(reader, httpRequest);

        return httpRequest;
    }

    private void parseRequestLine(InputStreamReader reader, HttpRequest httpRequest) throws IOException, HttpParsingException {
        StringBuilder processingDataBuffer = new StringBuilder();

        boolean methodParsed = false;
        boolean requestTargetParsed = false;

        int _byte;
        while ((_byte = reader.read()) >= 0) {
            if (_byte == ASCIICharConstants.CR) {
                _byte = reader.read();
                if (_byte == ASCIICharConstants.LF) {
                    LOGGER.debug("Request Line VERSION to process : {}", processingDataBuffer);
                    if (!methodParsed || !requestTargetParsed) {
                        throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                    }
                    return;
                }
            }
            if (_byte == ASCIICharConstants.SP) {
                if (!methodParsed) {
                    LOGGER.debug("Request Line METHOD to process : {}", processingDataBuffer);
                    methodParsed = true;
                    httpRequest.setMethod(processingDataBuffer.toString());
                } else if (!requestTargetParsed) {
                    LOGGER.debug("Request Line REQUEST TARGET to process : {}", processingDataBuffer);
                    requestTargetParsed = true;
                } else {
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }
                processingDataBuffer.delete(0, processingDataBuffer.length());
            } else {
                processingDataBuffer.append((char) _byte);
                if (!methodParsed) {
                    if (processingDataBuffer.length() > HttpMethodEnum.MAX_LENGTH) {
                        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);
                    }
                }
            }
        }
    }

    private void parseHeaders(InputStreamReader reader, HttpRequest httpRequest) {

    }

    private void parseBody(InputStreamReader reader, HttpRequest httpRequest) {

    }
}
