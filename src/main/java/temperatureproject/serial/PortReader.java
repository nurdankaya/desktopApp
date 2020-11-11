/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject.serial;

import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author NURDAN
 */
class PortReader implements SerialPortEventListener {

    private final SerialPort serialPort;
    private String temperature;
    private String humidity;

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public PortReader(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {

        System.out.println("started");
        if (event.getEventValue() > 0) {
            try {

                byte[] buffer;
                buffer = serialPort.readBytes(19);

                //Convert bytes into String
                String dataStream = new String(buffer);

                if (dataStream.contains(":")) {
                    String[] parts = null;
                    parts = dataStream.split(":");   //  #00.00#00.00#     -->format
                    if (parts.length >= 3) {
                        temperature = parts[1]; // temperature
                        humidity = parts[2]; // humidity
                    }
                }
                System.out.println("alınan : " + dataStream);

            } catch (SerialPortException ex) {
                System.out.println("Error in receiving string from COM-port: " + ex);
            }
        }
    }
}
