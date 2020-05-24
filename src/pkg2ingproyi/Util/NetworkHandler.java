package pkg2ingproyi.Util;

import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

//Launches a thread for data listening.
public class NetworkHandler extends Thread {
    
    private final String strUrl;
    private final OnDataReceivedListener listener;

    public NetworkHandler(String strUrl, OnDataReceivedListener listener) {
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
            if (urlConnection.getResponseCode() != 200) {
                this.listener.onDataFail();
            } else {
                //Proceed only if correct connection with url.

                //Read URL and pass to String.
                Scanner sc = new Scanner(url.openStream());
                StringBuilder rawData = new StringBuilder();
                while (sc.hasNext())
                    rawData.append(sc.nextLine());

                //Pass data to listener.
                this.listener.dataReceived(rawData.toString());
            }

        } catch (IOException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}