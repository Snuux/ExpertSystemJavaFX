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
        //Получаем объект, у которого меньше всего вопросов в ветке
        currentNode = Data.DataManager.getPreferObject(DataManager.getTree());

        //Получаем вопрос из ветки у объекта и задаём его
        processNextQuestion();
    }

    private void processNextQuestion() {
        //Получение вопроса для текущего объекта
        Rule currentRule = (Rule) DataManager.getQuestion(currentNode).getValue();

<<<<<<< HEAD
        //Если объект имеет атрибут -> вывести объект .
        //Если объект не имеет атрибут -> продолжить .
        //Если объект не имеет атрибут и правило сработало -> сменить объект .
        //Если ни один объект не имеет атрибут и все правила сработали -> вывести нет объекта .

        Rule rule = (Rule) currentNode.getValue();

        if (rule.isUsed()) {
            if (rule.getAttribute().isTrue()) {
                objectSelected(currentNode);
                return;
            } else if (!rule.getAttribute().isTrue()) {
                if (DataManager.checkAllObjectsUsed(DataManager.getTree())) { //all objects not true
                    noObjectSelected();
                    return;
                } else {
                    currentNode = Data.DataManager.getPreferObject(DataManager.getTree());
                    currentRule = (Rule) DataManager.getQuestion(currentNode).getValue();
                }
            }
        }


        //true magic begins
        /*Rule rule = (Rule) currentNode.getValue();
        if (rule.getAttribute().isEntered() || (!rule.getAttribute().isEntered() && rule.getAttribute().getValue() == null)) {
            if (DataManager.checkAllObjectsExistsDontKnow(DataManager.getTree())) {
=======
        //Выбор ветки графа (объекта, который будем рассматривать
        Rule rule = (Rule) currentNode.getValue();
        if (rule.getAttribute().hasValue()) {
            objectSelected(currentNode);
            return;
        } else {
            //Ищем новый объект с минимальным деревом вопросов
            currentNode = DataManager.getPreferObject(DataManager.getTree());
            //Если такой объект не найден, то останавливаем работу
            // и говорим, что в системе нет соответствующего объекта
            if (currentNode == null) {
>>>>>>> test
                noObjectSelected();
                return;
            }
        }

<<<<<<< HEAD
        } else if (rule.getAttribute().isEntered() && rule.getAttribute().getValue() != null) {
            objectSelected(currentNode);
            return;
        }*/


        showQuestion(currentRule);
=======
        //Получив вопрос, показываем его
        if (!currentRule.getTag().equals("EMPTY")) {
            showQuestion(currentRule);
        } else {
            noObjectSelected();
            return;
        }
>>>>>>> test
    }

    private void showQuestion(Rule rule) {
        //Получаем текст вопроса из атрибута
        questionLabel.setText(rule.getAttribute().getText());

        //Удаляем все кнопки из контейнера с кнопками
        buttonsBox.getChildren().clear();
<<<<<<< HEAD
=======

        //В зависимости от типа вопроса добавляем разные виды кнопок:
>>>>>>> test
        if (rule.getAttribute().getType() == Attribute.Type.QUESTION) {
            if (rule.getAttribute().getValueType() == Attribute.ValueType.TRUE_FALSE ||
                    rule.getAttribute().getValueType() == Attribute.ValueType.TRUE_FALSE_DONT_KNOW) {
                Button button1 = new Button("Да");
                button1.setOnAction(e -> {
                    rule.getAttribute().setEntered(true);
                    rule.getAttribute().setValue(1);
                    processNextQuestion();
                });
                buttonsBox.getChildren().add(button1);

                Button button2 = new Button("Нет");
                button2.setOnAction(e -> {
<<<<<<< HEAD
                    rule.getAttribute().setEntered(true);
=======
                    rule.setUsed(true);
>>>>>>> test
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
    public void handleExitMenuItem(ActionEvent actionEvent) { //Выход из приложения по нажатию кнопки закрыть
        System.exit(0);
    }

    @FXML
    public void handleShowAllObjectsGraph(ActionEvent actionEvent) { //Отображение графа правил
        Main.getQuestionGraphStage().show();
    }

    /**
     * Метод, завершающий работу ЭС при нахождении нужного объекта
     * Показывает Alert с информацией о результате работы системы
     *
     * @param node узел, в котором содержится отработавшее правило и найденный объект
     */
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

    /**
     * Метод, завершающий работу ЭС при отсутствии нужного объекта
     * Показывает Alert с информацией о результате работы системы
     */
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
