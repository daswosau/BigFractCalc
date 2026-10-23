package com.example.damnbignumb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML private TextField display;
    @FXML private Label decimalLabel;

    private BigFraction a = new BigFraction();
    private char operation = 0;
    // A következő számjegy új számot kezd (művelet, egyenlőség vagy függvény után)
    private boolean replaceOnDigit = true;
    // A művelet után még nem kezdtünk el jobb oldali operandust bevinni
    private boolean awaitingOperand = true;

    @FXML
    private void initialize() {
        show("0");
    }

    private BigFraction current() {
        return BigFraction.parse(display.getText());
    }

    private void show(String text) {
        display.setText(text);
        try {
            decimalLabel.setText("Tizedes: " + BigFraction.parse(text).toDecimalString(60));
        } catch (RuntimeException e) {
            decimalLabel.setText("");
        }
    }

    private void error() {
        a = new BigFraction();
        operation = 0;
        replaceOnDigit = true;
        awaitingOperand = true;
        display.setText("Hiba");
        decimalLabel.setText("");
    }

    private void calculate() {
        BigFraction b = current();
        BigFraction c;
        switch (operation) {
            case '+':
                c = BigFractions.add(a, b);
                break;
            case '-':
                c = BigFractions.subtract(a, b);
                break;
            case '*':
                c = BigFractions.multiply(a, b);
                break;
            case '/':
                c = BigFractions.divide(a, b);
                break;
            default:
                c = b;
        }
        show(c.toString());
    }

    private void handleOperator(char op) {
        try {
            if (operation != 0 && !awaitingOperand) {
                calculate();
            }
            a = current();
            operation = op;
            replaceOnDigit = true;
            awaitingOperand = true;
        } catch (RuntimeException e) {
            error();
        }
    }

    private void enter(String text) {
        if (replaceOnDigit) {
            show(text);
        } else {
            show(display.getText() + text);
        }
        replaceOnDigit = false;
        awaitingOperand = false;
    }

    @FXML
    private void handleDigitClick(ActionEvent actionEvent) {
        String digit = ((Button) actionEvent.getSource()).getText();
        if (!replaceOnDigit && display.getText().equals("0")) {
            show(digit);
            return;
        }
        enter(digit);
    }

    @FXML
    private void handleDotClick(ActionEvent actionEvent) {
        if (replaceOnDigit) {
            enter("0.");
        } else if (!display.getText().contains(".") && !display.getText().contains("/")) {
            enter(".");
        }
    }

    @FXML
    private void handleSlashClick(ActionEvent actionEvent) {
        if (replaceOnDigit) {
            enter("0/");
        } else if (!display.getText().contains(".") && !display.getText().contains("/")) {
            enter("/");
        }
    }

    @FXML
    private void handleSignClick(ActionEvent actionEvent) {
        String t = display.getText();
        if (t.startsWith("(") && t.endsWith(")")) {
            t = t.substring(1, t.length() - 1);
        }
        show(t.startsWith("-") ? t.substring(1) : "-" + t);
        awaitingOperand = false;
    }

    @FXML
    private void handleReciprocalClick(ActionEvent actionEvent) {
        try {
            show(BigFractions.reciprocal(current()).toString());
            replaceOnDigit = true;
            awaitingOperand = false;
        } catch (RuntimeException e) {
            error();
        }
    }

    @FXML
    private void handleSimplifyClick(ActionEvent actionEvent) {
        try {
            show(BigFractions.simplify(current()).toString());
            replaceOnDigit = true;
            awaitingOperand = false;
        } catch (RuntimeException e) {
            error();
        }
    }

    @FXML
    private void handleAddClick(ActionEvent actionEvent) {
        handleOperator('+');
    }

    @FXML
    private void handleSubtractClick(ActionEvent actionEvent) {
        handleOperator('-');
    }

    @FXML
    private void handleMultiplyClick(ActionEvent actionEvent) {
        handleOperator('*');
    }

    @FXML
    private void handleDivideClick(ActionEvent actionEvent) {
        handleOperator('/');
    }

    @FXML
    private void handleEqualsClick(ActionEvent actionEvent) {
        try {
            if (operation != 0 && !awaitingOperand) {
                calculate();
            }
            operation = 0;
            replaceOnDigit = true;
            awaitingOperand = true;
        } catch (RuntimeException e) {
            error();
        }
    }

    @FXML
    private void handleClearClick(ActionEvent actionEvent) {
        a = new BigFraction();
        operation = 0;
        replaceOnDigit = true;
        awaitingOperand = true;
        show("0");
    }
}
