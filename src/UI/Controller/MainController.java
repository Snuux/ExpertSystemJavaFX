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

    TreeItem currentNode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentNode = Data.DataManager.getPreferObject(DataManager.getTree());

        processNextQuestion();
    }

    private void processNextQuestion() {
        //magic begins
        int kk = 0;
        if (currentNode == null)
            kk = 1;

        Rule currentRule = (Rule) DataManager.getQuestion(currentNode).getValue();

        if (currentRule.isUsed()) {
            currentNode = Data.DataManager.getPreferObject(DataManager.getTree());
            currentRule = (Rule) DataManager.getQuestion(currentNode).getValue();
        }

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
                    System.out.println("lol");
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
}
