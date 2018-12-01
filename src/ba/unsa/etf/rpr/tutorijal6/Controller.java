package ba.unsa.etf.rpr.tutorijal6;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private DatePicker birthdate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String datePattern = "dd.MM.yyyy";
        birthdate.setPromptText(datePattern);
        birthdate.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);

            @Override
            public String toString(LocalDate date) {
                return date == null ? "" : dateTimeFormatter.format(date);
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateTimeFormatter);
                } else {
                    return null;
                }
            }
        });
    }
}
