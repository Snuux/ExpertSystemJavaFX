package UI.Controller;

import Data.Attribute;
import Data.DataManager;
import Data.Rule;
import UI.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class MainFormController implements Initializable {

    @FXML
    public Label questionTitle;

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
    VBox mainBox;

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
        questionTitle.setText("Добро пожаловать в экспертную систему!");
        questionLabel.setText("Пожалуйста загрузите базу знаний из \"Файл\"->\"Загрузить базу знаний из папки\".\n" +
                "Или используйте базу знаний по умолчанию (для тем: \"Выбор книги\" и \"Выбор языка программирования\").");

        init();
    }

    private void init() {
        if (DataManager.getTree() != null) {
            //Получаем объект, у которого меньше всего вопросов в ветке
            currentNode = Data.DataManager.getPreferObject(DataManager.getTree());

            //Получаем вопрос из ветки у объекта и задаём его
            processNextQuestion();
        }
    }

    private void processNextQuestion() {
        questionTitle.setText("Вопрос:");
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

            //Если объект остался один, то сразу выводим его
            if (DataManager.getIsUsedCountforObjects() == 1) {
                objectSelected(currentNode);
                return;
            }
            //Если такой объект не найден, то останавливаем работу
            // и говорим, что в системе нет соответствующего объекта
            else if (currentNode == null) {
                noObjectSelected();
                return;
            }
        }

        //Получив вопрос, показываем его
        if (!currentRule.getTag().equals("EMPTY")) {
            showQuestion(currentRule);
        } else {
            noObjectSelected();
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

        if (node.getValue().getAttribute().getValue() == null)
            alert.setContentText(node.getValue().getValue().toString());
        else
            alert.setContentText(node.getValue().getAttribute().getValue().toString());

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("/UI/Resources/dialogStyle.css").toExternalForm());

        dialogPane.getStyleClass().add("myDialog");

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

    /**
     * Метод, завершающий работу ЭС если объект остался один
     */
    private void noObjectSelectedLastOne(TreeItem<Rule> node) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Результат");
        alert.setHeaderText("К сожалению экспертная система на смогла выбрать объект!");
        alert.setContentText("Ближайший подходящий объект: " + node.getValue());
        alert.showAndWait().ifPresent(resetConsumer);
    }

    //Объект, обрабатывающий сброс и начало новой сессии вопросов-ответов
    private Consumer resetConsumer = rs -> {
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
        alert.setHeaderText("Эксперт по подбору книг, языков программирования и т.д.");
        alert.setContentText("Данная программа - лучший друг в вопросе выбора книги или языка программирования кому бы то ни было. " +
                "Выбираете вы их для себя или для кого-то, просто так или в подарок - это не важно. " +
                "Наша система поможет решить любую вашу задачу!");
        alert.showAndWait();
    }

    public void loadMenuOnAction(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Загрузить базу знаний");
        File defaultDirectory = new File(System.getProperty("user.dir"));
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(Main.getMainStage());

        if (selectedDirectory.isDirectory()) {
            DataManager.load(selectedDirectory.getPath());

            init();

            Main.getQuestionGraphFormController().updateDialog();
            Main.getQuestionGraphFormController().updateTree();
        } else {
            alertErrorDirectory();
        }
    }

    public void saveMenuOnAction(ActionEvent actionEvent) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Сохранить базу знаний");
        File defaultDirectory = new File(System.getProperty("user.dir"));
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(Main.getMainStage());

        if (selectedDirectory.isDirectory()) {
            DataManager.save(selectedDirectory.getPath());
        } else {
            alertErrorDirectory();
        }
    }

    private void alertErrorDirectory() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка загрузки базы знаний");
        alert.setHeaderText("Внимание возможно неверно выбрана папка");
        alert.setContentText("Попробуйте выбрать другую папку...");
        alert.showAndWait();
    }

    public void loadDefaultBooksMenuOnAction(ActionEvent actionEvent) {
        DataManager.defaultBaseBooks();

        init();

        Main.getQuestionGraphFormController().updateDialog();
        Main.getQuestionGraphFormController().updateTree();
    }

    public void loadDefaultLanguagesMenuOnAction(ActionEvent actionEvent) {
        DataManager.defaultBaseLanguages();

        init();

        Main.getQuestionGraphFormController().updateDialog();
        Main.getQuestionGraphFormController().updateTree();
    }
}
