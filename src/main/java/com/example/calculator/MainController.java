
package com.example.calculator;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.Tab;
        import javafx.scene.control.TabPane;
        import javafx.scene.control.TextField;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.layout.BorderPane;
        import javafx.scene.layout.GridPane;
        import javafx.stage.Stage;

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
    private Button plus;

    @FXML
    void tabCheck(MouseEvent event) {
        System.out.println("tabCheck");
        if (graph_tab.isSelected()) {

            tab_pane.getStyleClass().add("graphing-style");
            tab_pane.getStyleClass().remove("calc-tab-style");
        }
        else if (calc_tab.isSelected()) {

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
    void equalsAction(ActionEvent event) {
        System.out.println("equalsAction");
        Double result = handleOperations(calc_text.getText());
        resultText.setText("" + result);

    }

    private Double handleOperations(String input) {

            if (input.equals("")) {

                return 0.0;
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


            Double result = 0d;
            Double term1 = 0d;
            Double term2 = 0d;
            String symb = "*";
            String[] symbols = {"*", "/", "+", "-"};
            System.out.println(expr);
            for (int i = 0; i < symbols.length; i++) {
                symb = symbols[i];
                while (expr.contains(symb))
                    for (int j = 0; j < expr.size(); j++) {
                        if (expr.contains(symb)) {
                            switch (symb) {

                                case "*" -> {

                                    term1 = Double.parseDouble(expr.remove(expr.indexOf("*") - 1));
                                    term2 = Double.parseDouble(expr.remove(expr.indexOf("*") + 1));
                                    expr.remove(expr.indexOf("*"));


                                    result = term1 * term2;
                                }
                                case "/" -> {
                                    term1 = Double.parseDouble(expr.remove(expr.indexOf("/") - 1));
                                    term2 = Double.parseDouble(expr.remove(expr.indexOf("/") + 1));
                                    expr.remove(expr.indexOf("/"));


                                    result = term1 / term2;
                                }
                                case "+" -> {

                                    term1 = Double.parseDouble(expr.remove(expr.indexOf("+") - 1));
                                    term2 = Double.parseDouble(expr.remove(expr.indexOf("+") + 1));
                                    expr.remove(expr.indexOf("+"));


                                    result = term1 + term2;
                                }
                                case "-" -> {
                                    term1 = Double.parseDouble(expr.remove(expr.indexOf("-") - 1));
                                    term2 = Double.parseDouble(expr.remove(expr.indexOf("-") + 1));
                                    expr.remove(expr.indexOf("-"));

                                    result = term1 - term2;
                                }

                            }
                            expr.addFirst(String.valueOf(result));
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
        Button b = (Button)event.getSource();

        calc_text.setText(calc_text.getText() + b.getText());
    }

}

