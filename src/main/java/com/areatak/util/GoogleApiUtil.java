package com.areatak.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Throwables;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleServiceScopes;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alirezaghias on 5/14/2017 AD.
 */
public class GoogleApiUtil {
    /** Application name. */
    private static final String APPLICATION_NAME =
            "Utadoc.com";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
            System.getProperty("user.home"), ".credentials/gmail-sabtshod");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
            JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/gmail-java-quickstart
     */
    private static final List<String> SCOPES =
            Arrays.asList(PeopleServiceScopes.CONTACTS_READONLY);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
                GoogleApiUtil.class.getResourceAsStream("/sabtshod_google.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        .setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(
                flow, new CustomLocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Gmail client service.
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public static PeopleService getPeopleService() throws IOException {
        Credential credential = authorize();
        return new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    public static class CustomLocalServerReceiver implements VerificationCodeReceiver {
        private static final String CALLBACK_PATH = "/Callback";
        private Server server;
        String code;
        String error;
        final Lock lock;
        final Condition gotAuthorizationResponse;
        private int port;
        private final String host;

        public CustomLocalServerReceiver() {
            this("localhost", -1);
        }

        CustomLocalServerReceiver(String host, int port) {
            this.lock = new ReentrantLock();
            this.gotAuthorizationResponse = this.lock.newCondition();
            this.host = host;
            this.port = port;
        }

        public String getRedirectUri() throws IOException {
            if(this.port == -1) {
                this.port = getUnusedPort();
            }

            this.server = new Server(this.port);
            Connector[] e = this.server.getConnectors();
            int len$ = e.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Connector c = e[i$];
                c.setHost(this.host);
            }

            this.server.addHandler(new CustomLocalServerReceiver.CallbackHandler());

            try {
                this.server.start();
            } catch (Exception var5) {
                Throwables.propagateIfPossible(var5);
                throw new IOException(var5);
            }

            return "http://" + this.host + ":" + this.port + "/Callback";
        }

        public String waitForCode() throws IOException {
            this.lock.lock();

            String var1;
            try {
                while(this.code == null && this.error == null) {
                    this.gotAuthorizationResponse.awaitUninterruptibly();
                }

                if(this.error != null) {
                    throw new IOException("User authorization failed (" + this.error + ")");
                }

                var1 = this.code;
            } finally {
                this.lock.unlock();
            }

            return var1;
        }

        public void stop() throws IOException {
            if(this.server != null) {
                try {
                    this.server.stop();
                } catch (Exception var2) {
                    Throwables.propagateIfPossible(var2);
                    throw new IOException(var2);
                }

                this.server = null;
            }

        }

        public String getHost() {
            return this.host;
        }

        public int getPort() {
            return this.port;
        }

        private static int getUnusedPort() throws IOException {
            Socket s = new Socket();
            s.bind((SocketAddress)null);

            int var1;
            try {
                var1 = s.getLocalPort();
            } finally {
                s.close();
            }

            return var1;
        }

        class CallbackHandler extends AbstractHandler {
            CallbackHandler() {
            }

            public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException {
                if("/Callback".equals(target)) {
                    this.writeLandingHtml(response);
                    response.flushBuffer();
                    ((Request)request).setHandled(true);
                    CustomLocalServerReceiver.this.lock.lock();

                    try {
                        CustomLocalServerReceiver.this.error = request.getParameter("error");
                        CustomLocalServerReceiver.this.code = request.getParameter("code");
                        CustomLocalServerReceiver.this.gotAuthorizationResponse.signal();
                    } finally {
                        CustomLocalServerReceiver.this.lock.unlock();
                    }

                }
            }

            private void writeLandingHtml(HttpServletResponse response) throws IOException {
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("text/html");
                PrintWriter doc = response.getWriter();
                doc.println("<html>");
                doc.println("<head><title>utadoc.com</title><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head>");
                doc.println("<body>");
                doc.println("شما می‌توانید این پنجره را ببندید");
                doc.println("</body>");
                doc.println("<script>");
                doc.println("window.close();");
                doc.println("</script>");
                doc.println("</HTML>");
                doc.flush();
            }
        }

        public static final class Builder {
            private String host = "utadoc.com";
            private int port = -1;

            public Builder() {
            }

            public CustomLocalServerReceiver build() {
                return new CustomLocalServerReceiver(this.host, this.port);
            }

            public String getHost() {
                return this.host;
            }

            public CustomLocalServerReceiver.Builder setHost(String host) {
                this.host = host;
                return this;
            }

            public int getPort() {
                return this.port;
            }

            public CustomLocalServerReceiver.Builder setPort(int port) {
                this.port = port;
                return this;
            }
        }
    }
}


