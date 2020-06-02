package pkg2ingproyi.Util;

import javafx.application.Platform;
import org.controlsfx.control.Notifications;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

//Launches a thread for data listening.
public class NetworkGETRequester extends Thread {
    
    private final String strUrl;
    private final OnDataReceivedListener listener;

    public NetworkGETRequester(String strUrl, OnDataReceivedListener listener) {
        this.strUrl     = strUrl;
        this.listener   = listener;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Check if connection was stabilised correctly.
            if (urlConnection.getResponseCode() != 200)
                Platform.runLater(() -> Notifications.create().title("Data Receive Error").text("No se pudo conectar con la BD.").showError());
            else {
                //Proceed only if correct connection with url.

                //Read URL and pass to String.
                Scanner sc = new Scanner(url.openStream());
                StringBuilder rawData = new StringBuilder();
                while (sc.hasNext())
                    rawData.append(sc.nextLine());

                //Pass data to listener.
                this.listener.dataReceived(rawData.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}