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
import java.util.function.Consumer;

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
                noObjectSelected();
                return;
            }
        }

        //Получив вопрос, показываем его
        if (!currentRule.getTag().equals("EMPTY")) {
            showQuestion(currentRule);
        } else {
            noObjectSelected();
            return;
        }
    }

    private void showQuestion(Rule rule) {
        //Получаем текст вопроса из атрибута
        questionLabel.setText(rule.getAttribute().getText());

        //Удаляем все кнопки из контейнера с кнопками
        buttonsBox.getChildren().clear();

        //В зависимости от типа вопроса добавляем разные виды кнопок:
        if (rule.getAttribute().getType() == Attribute.Type.QUESTION) {
            if (rule.getAttribute().getValueType() == Attribute.ValueType.TRUE_FALSE ||
                    rule.getAttribute().getValueType() == Attribute.ValueType.TRUE_FALSE_DONT_KNOW) {
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
     * @param node узел, в котором содержится отработавшее правило и найденный объект
     */
    private void objectSelected(TreeItem<Rule> node) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результат");
        alert.setHeaderText("Экспертная система нашла нужный объект");
        alert.setContentText(node.getValue().getAttribute().getValue().toString());
        alert.showAndWait().ifPresent(resetConsumer);
    }

    /**
     * Метод, завершающий работу ЭС при отсутствии нужного объекта
     * Показывает Alert с информацией о результате работы системы
     */
    private void noObjectSelected() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результат");
        alert.setHeaderText("К сожалению экспертная система на смогла выбрать объект!");
        alert.setContentText("Попробуйте заново...");
        alert.showAndWait().ifPresent(resetConsumer);
    }

    //Объект, обрабатывающий сброс и начало новой сессии вопросов-ответов
    Consumer resetConsumer = rs -> {
        if (rs == ButtonType.OK) {
            DataManager.reset();
            currentNode = Data.DataManager.getPreferObject(DataManager.getTree());
            processNextQuestion();
        }
    };

    /**
     * Выводит информацию о приложении
     * @param actionEvent
     */
    public void handleAboutAppAction(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setHeaderText("Эксперт по подбору книг");
        alert.setContentText("Данная программа - лучший друг в вопросе выбора книги кому бы то ни было. Выбираете вы её для себя или для кого-то, просто так или в подарок - это не важно. Наша система поможет решить любую вашу задачу!");
        alert.showAndWait();
    }
}
