package pkg2ingproyi.Util;

import javafx.application.Platform;
import org.controlsfx.control.Notifications;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NetworkPUTRequester extends Thread {
    private final String strUrl, objectId, changesJSON;
    private final ConnectionChecker conChecker;

    public NetworkPUTRequester(String strUrl, String objectId, String changesJSON, ConnectionChecker conChecker) {
        this.strUrl      = strUrl;
        this.objectId    = objectId;
        this.changesJSON = changesJSON;
        this.conChecker  = conChecker;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(strUrl + '/' + objectId);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod  ( "PUT"                                     );
            urlConnection.setRequestProperty( "Content-Type", "application/json; utf-8" );
            urlConnection.setRequestProperty( "Accept", "application/json"              );
            urlConnection.setDoOutput       ( true                                      );

            byte[] input = changesJSON.getBytes(StandardCharsets.UTF_8);
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(input, 0, input.length);
            outputStream.close();

            //Check if modification was correct.
            if (urlConnection.getResponseCode() != 200)
                conChecker.onDataFail();
            else {
                Platform.runLater(() -> Notifications.create().title("Reserve Accepted").text("El servicio con ID: '" + objectId + "' fue modificado correctamente.").showConfirm());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
