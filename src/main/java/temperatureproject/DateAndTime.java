/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package temperatureproject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author NURDAN
 */
public class DateAndTime {

    String takeDate() {
        LocalDate localDate = LocalDate.now();//For reference
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String formattedString = localDate.format(formatter);
        return formattedString;
    }

    String taketime() {
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH.mm.ss");
        LocalTime today = LocalTime.now();
        String timeString = today.format(formatter1);
        return timeString;
    }
}
