
package com.example.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainController {

    @FXML
    private TabPane tab_pane;
    @FXML
    private BorderPane cal_border;

    @FXML
    private GridPane calc_board;


    @FXML
    private Tab calc_tab;

    @FXML
    private TextField calc_text;

    @FXML
    private Button divide;

    @FXML
    private Button equals;
    @FXML
    private TextField resultText;
    @FXML
    private Tab graph_tab;

    @FXML
    private Button minus;

    @FXML
    private Button mult;
    @FXML
    private Button clear;
    @FXML
    private Button del;
    @FXML
    private Button plus;

    @FXML
    void tabCheck(MouseEvent event) {
        System.out.println("tabCheck");
        if (graph_tab.isSelected()) {

            tab_pane.getStyleClass().add("graphing-style");
            tab_pane.getStyleClass().remove("calc-tab-style");
        } else if (calc_tab.isSelected()) {

            tab_pane.getStyleClass().add("calc-tab-style");
            tab_pane.getStyleClass().remove("graphing-style");
        }

    }

    @FXML
    void clearAction(ActionEvent event) {
        resultText.setText("");
        calc_text.setText("");
    }
    @FXML
    void deleteAction(ActionEvent event) {
        if (!calc_text.getText().equals(""))
        calc_text.setText(calc_text.getText().substring(0,calc_text.getText().length()-1));
    }

    @FXML
    void equalsAction(ActionEvent event) {
        System.out.println("equalsAction");
        BigDecimal result = handleOperations(calc_text.getText());
        resultText.setText("" + result);

    }

    private BigDecimal handleOperations(String input) {

        if (input.equals("")) {

            return BigDecimal.valueOf(0.0);
        }
        Pattern terms = Pattern.compile("\\d+");
        Matcher termMat = terms.matcher(input);
        Pattern operations = Pattern.compile("[*/+-]");
        Matcher operMat = operations.matcher(input);


        LinkedList<String> expr = new LinkedList<>();
        while (termMat.find()) {
            expr.add(termMat.group());
            if (termMat.end() > input.length() - 1)
                break;
            operMat.find();
            expr.add(operMat.group());
        }


        BigDecimal result = new BigDecimal(0d);
        BigDecimal term1 = new BigDecimal(0d);
        BigDecimal term2 = new BigDecimal(0d);
        String symb = "*";
        int pointer = 0;
        String[] symbols = {"*", "/", "+", "-"};
        System.out.println(expr);
        for (int i = 0; i < symbols.length; i++) {
            symb = symbols[i];
            while (expr.contains(symb)) {
                for (int j = 0; j < expr.size(); j++) {
                    if (expr.contains(symb))
                    switch (symb) {
                        case "*" -> {
                            pointer = expr.indexOf("*")-1;
                            term1 = term1.valueOf(Double.parseDouble(expr.remove(expr.indexOf("*") - 1)));
                            term2 = term2.valueOf(Double.parseDouble(expr.remove(expr.indexOf("*") + 1)));

                            result = term1.multiply(term2, MathContext.DECIMAL128);
                            expr.set(pointer, String.valueOf(result));
                        }
                        case "/" -> {
                            pointer = expr.indexOf("/")-1;
                            term1 = term1.valueOf(Double.parseDouble(expr.remove(expr.indexOf("/") - 1)));
                            term2 = term2.valueOf(Double.parseDouble(expr.remove(expr.indexOf("/") + 1)));

                            result = term1.divide(term2, 10, RoundingMode.HALF_UP);
                            expr.set(pointer, String.valueOf(result));
                        }
                        case "+" -> {
                            pointer = expr.indexOf("+")-1;
                            term1 = term1.valueOf(Double.parseDouble(expr.remove(expr.indexOf("+") - 1)));
                            term2 = term2.valueOf(Double.parseDouble(expr.remove(expr.indexOf("+") + 1)));


                            result = term1.add(term2,MathContext.DECIMAL128);
                            expr.set(pointer, String.valueOf(result));
                        }
                        case "-" -> {
                            pointer = expr.indexOf("-")-1;
                            term1 = term1.valueOf(Double.parseDouble(expr.remove(expr.indexOf("-") - 1)));
                            term2 = term2.valueOf(Double.parseDouble(expr.remove(expr.indexOf("-") + 1)));

                            result = term1.subtract(term2, MathContext.DECIMAL128);
                            expr.set(pointer, String.valueOf(result));
                        }

                    }

                    System.out.println(expr);
                }
            }
        }
        System.out.println(expr);


        return result;
    }


    @FXML
    void operationAction(MouseEvent event) {
        System.out.println("operationAction");
        Button b = (Button) event.getSource();

        calc_text.setText(calc_text.getText() + b.getText());
    }

}

