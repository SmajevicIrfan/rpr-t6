package ba.unsa.etf.rpr.tutorijal6;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    public ComboBox<String> birthplace;
    public TextField address;
    public TextField phone;
    public TextField email;
    public ComboBox<String> department;
    public ComboBox<String> yearOfStudy;
    public ComboBox<String> degree;
    public ToggleGroup selfFinancing;
    public ToggleGroup war;

    private static final String datePattern = "dd.MM.yyyy";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(datePattern);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        birthDate.setPromptText(datePattern);
        birthDate.setConverter(new StringConverter<>() {
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

        firstName.textProperty().addListener(nameValidator(firstName));
        lastName.textProperty().addListener(nameValidator(lastName));

        idNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isValidIDNumber(newValue)) {
                idNumber.getStyleClass().removeAll("invalidField");
                idNumber.getStyleClass().add("validField");
            } else {
                idNumber.getStyleClass().removeAll("validField");
                idNumber.getStyleClass().add("invalidField");
            }
        });

        ssn.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isValidSSN(newValue)) {
                ssn.getStyleClass().removeAll("invalidField");
                ssn.getStyleClass().add("validField");
            } else {
                ssn.getStyleClass().removeAll("validField");
                ssn.getStyleClass().add("invalidField");
            }
        });

        birthDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (isValidBirthDate(newValue.format(dateTimeFormatter))) {
                birthDate.getStyleClass().removeAll("invalidField");
                birthDate.getStyleClass().add("validField");
            } else {
                birthDate.getStyleClass().removeAll("validField");
                birthDate.getStyleClass().add("invalidField");
            }
        });

        birthplace.valueProperty().addListener(nonEmptyValidator(birthplace));

        email.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isValidEmail(newValue)) {
                email.getStyleClass().removeAll("invalidField");
                email.getStyleClass().add("validField");
            } else {
                email.getStyleClass().removeAll("validField");
                email.getStyleClass().add("invalidField");
            }
        });

        department.valueProperty().addListener(nonEmptyValidator(department));
        yearOfStudy.valueProperty().addListener(nonEmptyValidator(yearOfStudy));
        degree.valueProperty().addListener(nonEmptyValidator(degree));
    }

    private static ChangeListener<String> nameValidator(TextField field) {
        return ((observable, oldValue, newValue) -> {
            if (isValidName(field.getText())) {
                field.getStyleClass().removeAll("invalidField");
                field.getStyleClass().add("validField");
            } else {
                field.getStyleClass().removeAll("validField");
                field.getStyleClass().add("invalidField");
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
        String birthSSN = birthDate.replaceAll("\\.", "");
        System.out.println(birthSSN.substring(0, 4) + birthSSN.substring(5, 7));
        return (birthSSN.substring(0, 4) + birthSSN.substring(5, 8)).equals(
                ssn.getText(0, 7));
    }

    private static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty())
            return false;

        String pattern =
                "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)+$";

        return email.matches(pattern);
    }

    private static ChangeListener<String> nonEmptyValidator(Control control) {
        return ((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.trim().isEmpty()) {
                control.getStyleClass().removeAll("invalidField");
                control.getStyleClass().add("validField");
            } else {
                control.getStyleClass().removeAll("validField");
                control.getStyleClass().add("invalidField");
            }
        });
    }

    private static int getDigit(String number, int digitNumber) {
        return number.charAt(digitNumber - 1) - '0';
    }
}
