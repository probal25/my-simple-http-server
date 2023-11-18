package ws.probal.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {

    private Socket socket;
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            int inputByte;

            while ((inputByte = inputStream.read()) >= 0) {
                System.out.print((char) inputByte);
            }

            String html = "<html><head><title>My server</title></head><body><h1>This server is awesome..</h1></body></html>";
            final String CRLF = "\n\r";

            String response = "HTTP/1.0 200 OK" + CRLF +    // status line : HTTP_VERSION RESPONSE_CODE RESPONSE_MESSAGE
                    "Content-Length: " + html.getBytes().length + CRLF +                                      // HEADER
                    CRLF +
                    html +
                    CRLF + CRLF;

            outputStream.write(response.getBytes());
            // pleaseSleep(5000);
            LOGGER.info("Connection processing finished . .");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                }
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private void pleaseSleep(int time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
