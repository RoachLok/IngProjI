package pkg2ingproyi.SupervisorView;

import eu.hansolo.medusa.Gauge;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.ResourceBundle;


public class SupervisorInfoPanesController implements Initializable, Runnable {
    @FXML
    public Label pressure1, pressure2, pressure3, pressure4;
    @FXML
    public Label tempLabel, avgSpeedLabel;
    @FXML
    public Gauge speedGauge, fuelGauge;

    private Thread thread;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        speedGauge  .setUnit( "Km / h"  );
        fuelGauge   .setUnit( "L"       );

        fuelGauge.setAnimated(true);

        thread = new Thread(this);
        thread.start();
    }

    private Label updatePressure(Label pressureLabel) {
        Random rng = new Random();
        int status = rng.nextInt(21);

        if          (status < 1) {

            pressureLabel.setText( "LOW"    );
            pressureLabel.setTextFill(Color.DARKRED);
        } else if   (status < 2) {

            pressureLabel.setText( "HIGH"   );
            pressureLabel.setTextFill(Color.YELLOW);
        } else {

            pressureLabel.setText( "OK"     );
            pressureLabel.setTextFill(Color.GREEN);
        }

        return pressureLabel;
    }

    private Label updateTemp(Label tempLabel) {
        Random rng = new Random();
        double nextDouble   = rng.nextDouble();
        double temp         = Double.parseDouble(tempLabel.getText().substring(0, 2));

        if (temp < 22.0)
            temp += nextDouble;
        else
            temp -= nextDouble;

        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);

        tempLabel.setText(decimalFormat.format(temp) + " º C");

        return tempLabel;
    }

    private void updateSpeedGauge(int speed) {
        Random rng = new Random();
        int speedVariation = rng.nextInt(6);

        if (speed < 79)
            speed += speedVariation;
        else if (speed > 85)
            if (speedVariation % 2 == 0)
                speed += speedVariation;
            else
                speed -= speedVariation;
        else
            speed -= speedVariation;

        speedGauge.setValue(speed);
    }

    private void updateFuelGauge(int currentCapacity) {
        Random rng = new Random();
        int vary = rng.nextInt(6);
        if (vary > 4)
            fuelGauge.setValue(currentCapacity - 0.25);
    }

    @Override
    public void run() {
        while (thread != null) {
            Platform.runLater(new Runnable(){
                @Override
                public void run() {
                    pressure1       = updatePressure    ( pressure1     );
                    pressure2       = updatePressure    ( pressure2     );
                    pressure3       = updatePressure    ( pressure3     );
                    pressure4       = updatePressure    ( pressure4     );
                    tempLabel       = updateTemp        ( tempLabel     );

                    updateSpeedGauge((int) speedGauge   .getValue() );
                    updateFuelGauge ((int) fuelGauge    .getValue() );
                }
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
