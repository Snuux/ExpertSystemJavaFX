package UI.Controller;

import Data.Attribute;
import Data.DataManager;
import Data.Rule;
import UI.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private MenuBar menuBar;

    @FXML
    private VBox questionBox;

    @FXML
    private VBox buttonsBox;

    @FXML
    public MenuItem showAllObjectsGraph;

    @FXML
    public Label questionLabel;

    @FXML
    public void handleAboutAction(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Информация о проргамме и авторах");
        alert.setHeaderText("Очень крутая экспертная система!");
        alert.setContentText("Сделана на коленке, но тем не менее очень круто!\nDeveloped by Vadim Rychkov & Andrei Kornienkov");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                System.out.println("Pressed OK.");
            }
        });
    }

    private static TreeItem currentNode;

    public static TreeItem getCurrentNode() {
        return currentNode;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentNode = Data.DataManager.getPreferObject(DataManager.getTree());

        processNextQuestion();
    }

    private void processNextQuestion() {
        //Получение вопроса для текущего объекта
        Rule currentRule = (Rule) DataManager.getQuestion(currentNode).getValue();

//        if (currentRule.getAttribute().hasValue()) {
//            objectSelected(DataManager.getQuestion(currentNode));
//        }

        //Выбор ветки графа (объекта, который будем рассматривать
        Rule rule = (Rule) currentNode.getValue();
        if (rule.getAttribute().hasValue()) {
            objectSelected(currentNode);
            return;
        } else {
            currentNode = DataManager.getPreferObject(DataManager.getTree());
            if (currentNode == null) {
                noObjectSelected();
                return;
            }
        }

        //Получив вопрос, показываем его
        showQuestion(currentRule);
    }

    private void showQuestion(Rule rule) {
        questionLabel.setText(rule.getAttribute().getText());

        buttonsBox.getChildren().clear();

        if (rule.getAttribute().getType() == Attribute.Type.QUESTION) {
            if (rule.getAttribute().getValueType() == Attribute.ValueType.TRUE_FALSE || rule.getAttribute().getValueType() == Attribute.ValueType.TRUE_FALSE_DONT_KNOW) {

                Button button1 = new Button("Да");
                button1.setOnAction(e -> {
                    rule.setUsed(true);
                    rule.getAttribute().setValue(1);
                    processNextQuestion();
                });
                buttonsBox.getChildren().add(button1);

                Button button2 = new Button("Нет");
                button2.setOnAction(e -> {
                    rule.setUsed(true);
                    rule.getAttribute().setValue(0);
                    processNextQuestion();
                });
                buttonsBox.getChildren().add(button2);

                if (rule.getAttribute().getValueType() == Attribute.ValueType.TRUE_FALSE_DONT_KNOW) {
                    Button button3 = new Button("Не знаю");
                    button3.setOnAction(e -> {
                        rule.setUsed(true);
                        rule.getAttribute().setValue(-1);
                        processNextQuestion();
                    });
                    buttonsBox.getChildren().add(button3);
                }

            } else if (rule.getAttribute().getValueType() == Attribute.ValueType.STRING) {

                for (Object i : rule.getAttribute().getVariantAnswers()) {
                    String str = (String) i;
                    Button button = new Button(str);
                    button.setOnAction(e -> {
                        rule.setUsed(true);
                        rule.getAttribute().setValue(str);

                    });
                    buttonsBox.getChildren().add(button);
                }

            } else if (rule.getAttribute().getValueType() == Attribute.ValueType.NUMBER) {
                //TODO
            }
        }
    }

    @FXML
    public void handleExitMenuItem(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void handleShowAllObjectsGraph(ActionEvent actionEvent) {
        Main.getQuestionGraphStage().show();
    }

    private void objectSelected(TreeItem node) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результат");
        alert.setHeaderText("Экспертная система нашла нужный объект");
        alert.setContentText(node.toString());
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                //TODO Reset all Attributes and rules isUsed, values and etc
            }
        });
    }

    private void noObjectSelected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результат");
        alert.setHeaderText("К сожалению экспертная система на смогла выбрать объект!");
        alert.setContentText("Попробуйте заного...");
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.OK) {
                //TODO Reset all Attributes and rules isUsed, values and etc
            }
        });
    }
}
