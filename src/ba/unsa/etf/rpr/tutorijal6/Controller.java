package ba.unsa.etf.rpr.tutorijal6;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextField firstName;
    public TextField lastName;
    public TextField idNumber;
    public TextField ssn;
    public DatePicker birthDate;
    public ComboBox birthplace;
    public TextField address;
    public TextField phone;
    public TextField email;
    public ComboBox department;
    public ComboBox yearOfStudy;
    public ComboBox degree;
    public ToggleGroup selfFinancing;
    public ToggleGroup war;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String datePattern = "dd.MM.yyyy";
        birthDate.setPromptText(datePattern);
        birthDate.setConverter(new StringConverter<>() {
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);

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

    private static boolean isValidName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        if (name.length() > 20) {
            return false;
        }

        return name.matches("^\\p{L}+$");
    }

    private static boolean isValidIDNumber(String ID) {
        return ID.matches("^\\d{5}$");
    }

    private static boolean isValidSSN(String SSN) {
        if (!SSN.matches("^\\d{13}$")) {
            return false;
        }

        int L = 11 - (7 * (getDigit(SSN, 1) + getDigit(SSN, 7)) +
                6 * (getDigit(SSN, 2) + getDigit(SSN, 8)) +
                5 * (getDigit(SSN, 3) + getDigit(SSN, 9)) +
                4 * (getDigit(SSN, 4) + getDigit(SSN, 10)) +
                3 * (getDigit(SSN, 5) + getDigit(SSN, 11)) +
                2 * (getDigit(SSN, 6) + getDigit(SSN, 12))) % 10;
        return L >= 10 && getDigit(SSN, 13) == 0 || L == getDigit(SSN, 13);
    }

    private boolean isValidBirthDate(String birthDate) {
        return birthDate.replaceAll("\\.", "").equals(
                ssn.getText(0, 6));
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty())
            return true;

        String pattern =
                "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\\\.[a-zA-Z0-9-]+)*$";

        return email.matches(pattern);
    }

    private static int getDigit(String number, int digitNumber) {
        return number.charAt(digitNumber - 1) - '0';
    }
}
