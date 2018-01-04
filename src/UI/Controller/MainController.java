package UI.Controller;

import Data.Attribute;
import UI.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Data.Item Q1 = (Data.Item) Data.DataManager.getQuestion(Data.DataManager.getTree().getChildren().get(0).getChildren().get(0).getChildren().get(0)).getValue();

        showQuestion(Q1);
    }

    public void showQuestion(Data.Item Q1) {
        questionLabel.setText(Q1.getAttribute().getText());

        buttonsBox.getChildren().clear();

        if (Q1.getAttribute().getType() == Attribute.Type.QUESTION) {
            if (Q1.getAttribute().getValueType() == Attribute.ValueType.TRUE_FALSE) {

                Button button1 = new Button("Да");
                button1.setOnAction(e -> {
                    Q1.setAsked(true);
                    Q1.getAttribute().setValue(1);
                });
                buttonsBox.getChildren().add(button1);

                Button button2 = new Button("НЕТ!!!");
                button2.setOnAction(e -> {
                    Q1.setAsked(true);
                    Q1.getAttribute().setValue(0);
                });
                buttonsBox.getChildren().add(button2);

            } else if (Q1.getAttribute().getValueType() == Attribute.ValueType.TRUE_FALSE_DONT_KNOW) {

                Button button1 = new Button("Да");
                button1.setOnAction(e -> {
                    Q1.setAsked(true);
                    Q1.getAttribute().setValue(1);
                });
                buttonsBox.getChildren().add(button1);

                Button button2 = new Button("НЕТ!!!");
                button2.setOnAction(e -> {
                    Q1.setAsked(true);
                    Q1.getAttribute().setValue(0);
                });
                buttonsBox.getChildren().add(button2);

                Button button3 = new Button("Не знаю!!!!!!");
                button3.setOnAction(e -> {
                    Q1.setAsked(true);
                    Q1.getAttribute().setValue(-1);
                });
                buttonsBox.getChildren().add(button3);

            } else if (Q1.getAttribute().getValueType() == Attribute.ValueType.STRING) {

                for (Object i : Q1.getAttribute().getVariantAnswers()) {
                    String str = (String) i;
                    Button button = new Button(str);
                    button.setOnAction(e -> {
                        Q1.setAsked(true);
                        Q1.getAttribute().setValue(str);

                    });
                    buttonsBox.getChildren().add(button);
                }

            } else if (Q1.getAttribute().getValueType() == Attribute.ValueType.NUMBER) {
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
