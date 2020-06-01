package pkg2ingproyi.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NetworkPOSTRequester extends Thread {
    private final String strUrl, jsonString;
    private final ConnectionChecker conChecker;

    public NetworkPOSTRequester(String strUrl, String jsonString, ConnectionChecker conChecker) {
        this.strUrl     = strUrl;
        this.jsonString = jsonString;
        this.conChecker = conChecker;
    }

    @Override
    public void run() {
        try {
            URL url = new URL (strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

            urlConnection.setRequestMethod  ( "POST"                                    );
            urlConnection.setRequestProperty( "Content-Type", "application/json; utf-8" );
            urlConnection.setRequestProperty( "Accept", "application/json"              );
            urlConnection.setDoOutput       ( true                                      );

            byte[] input = jsonString.getBytes(StandardCharsets.UTF_8);
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(input, 0, input.length);
            outputStream.close();
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
