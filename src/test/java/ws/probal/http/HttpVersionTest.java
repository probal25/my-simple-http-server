package ws.probal.http;

import org.junit.jupiter.api.Test;
import ws.probal.http.exception.BadHttpVersionException;
import ws.probal.http.model.enums.HttpVersion;

import static org.junit.jupiter.api.Assertions.*;

public class HttpVersionTest {

    @Test
    void getBestCompatibleVersionExactMatch() {
        HttpVersion version = null;
        try {
            version = HttpVersion.getBestCompatibleVersion("HTTP/1.1");
        } catch (BadHttpVersionException e) {
            fail();
        }
        assertNotNull(version);
        assertEquals(HttpVersion.HTTP_1_1, version);
    }

    @Test
    void getBestCompatibleVersionBadFormat() {
        HttpVersion version = null;
        try {
            version = HttpVersion.getBestCompatibleVersion("HP/1.1");
            fail();
        } catch (BadHttpVersionException e) {

        }
    }


}
