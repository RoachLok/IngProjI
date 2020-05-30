package pkg2ingproyi.Util;

import javafx.application.Platform;
import org.controlsfx.control.Notifications;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkDELETERequester extends Thread {

    private final String strUrl, objectId;
    private final ConnectionChecker conChecker;

    public NetworkDELETERequester(String strUrl, String objectId, ConnectionChecker conChecker) {
        this.strUrl     = strUrl;
        this.objectId   = objectId;
        this.conChecker = conChecker;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(strUrl + '/' + objectId);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("DELETE");
            urlConnection.connect();

            //Check if connection was stabilised correctly.
            if (urlConnection.getResponseCode() != 200)
                conChecker.onDataFail();
            else {
                Platform.runLater(() -> Notifications.create().title("Element Deleted").text("El elemento con ID: '" + objectId + "' fue eliminado de la BD.").showConfirm());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
