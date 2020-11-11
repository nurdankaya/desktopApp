/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject.serial;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;



/**
 *
 * @author NURDAN
 */
/**
 * Send '1' to esp32 and receive temperature and humidity infos
 */
public class SerialCommunication {

    SerialPort serialPort;
    String temperature = null;
    String humidity = null;
    PortReader portReader;

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public SerialCommunication() throws SerialPortException, InterruptedException {
        String[] portNames = null;
        portNames = SerialPortList.getPortNames();
        for (String string : portNames) {
            System.out.println(string);
        }

        if (portNames.length == 0) {
            System.out.println("There are no serial-ports");
        } else {
            serialPort = new SerialPort("COM4");  //Define Port. My port handing ESP32 was COM4
            try {
                serialPort.openPort();

                serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);

            } catch (Exception e) {
                System.out.println("There are an error on writing string to port т: " + e);
            }

        }
    }

    public void portSender() throws SerialPortException {
        portReader = new PortReader(serialPort);
        serialPort.addEventListener(portReader);
        temperature = null;
        humidity = null;
        char x = '1';
        System.out.println("11111111111");
        serialPort.writeByte((byte) x);

        while (temperature == null || humidity == null) { // sıcaklık ve nem değerlerinin gelmesi için 

            temperature = portReader.getTemperature();
            humidity = portReader.getHumidity();

        }
        System.out.println("111111111113");
        serialPort.removeEventListener();
        System.out.println("111111141111");
        System.out.println("xxxx" + temperature + "xxxx" + humidity);
    }

}
