package pkg2ingproyi.SupervisorView;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class SupervisorGraph implements Initializable {

    @FXML
    private LineChart<Number, Number> grafvelo;
    @FXML
    private LineChart<Number, Number> grafpres;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        grafvelo.setAnimated(true);
        grafpres.setAnimated(true);

        String pathToLogs = "";

        Random rng = new Random(); //Temporal for mock-up.
        switch (rng.nextInt(6)) {
            case 0:
                pathToLogs = "src/resources/log_S0001A.json";
                break;
            case 1:
                pathToLogs = "src/resources/log_S0002A.json";
                break;
            case 2:
                pathToLogs = "src/resources/log_S0003A.json";
                break;

            case 3:
                pathToLogs = "src/resources/log_S0004A.json";
                break;

            case 4:
                pathToLogs = "src/resources/log_S0005A.json";
                break;
            case 5:
                pathToLogs = "src/resources/log_S0006A.json";
                break;
        }

        try (Reader reader = new FileReader(pathToLogs)) {
            JSONParser parser = new JSONParser();
            JSONArray medicionesList = (JSONArray) parser.parse(reader);
            java.util.List<Medicion> mediciones = new ArrayList<>();


            medicionesList.forEach(msg -> {
                Medicion medicion = parseMedicion((JSONObject) msg);
                if (medicion.getTipo().equals("v")) {
                    mediciones.add(medicion);
                }
            });

            XYChart.Series series = new XYChart.Series();

            for (int i = 0; i < mediciones.size(); i++)
                series.getData().add(
                        new XYChart.Data(mediciones.get(i).getTimestamp(),
                                mediciones.get(i).getValor()));

            grafvelo.getData().add(series);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        try (Reader reader2 = new FileReader(pathToLogs)) {
            JSONParser parser2 = new JSONParser();
            JSONArray medicionesList2 = (JSONArray) parser2.parse(reader2);
            java.util.List<Medicion> mediciones2 = new ArrayList<>();

            medicionesList2.forEach(msg -> {
                Medicion medicion = parseMedicion((JSONObject) msg);
                if (medicion.getTipo().equals("p")) {
                    mediciones2.add(medicion);
                }
            });

            XYChart.Series presion = new XYChart.Series();

            for (int i = 0; i < mediciones2.size(); i++)
                presion.getData().add(
                        new XYChart.Data(mediciones2.get(i).getTimestamp(),
                                mediciones2.get(i).getValor()));

            grafpres.getData().add(presion);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
    }

    private static Medicion parseMedicion(JSONObject medida) {
        Object valor = medida.get("valor");
        String timestamp = (String) medida.get("timestamp");
        String servicio = (String) medida.get("service_id");
        String type = (String) medida.get("type");
        return new Medicion(type, servicio, Double.valueOf(valor.toString()), timestamp);
    }
}


 	
