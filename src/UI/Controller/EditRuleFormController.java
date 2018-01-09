package UI.Controller;

import Data.Attribute;
import Data.DataManager;
import Data.Rule;
import UI.Main;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditRuleFormController implements Initializable{
    @FXML
    AnchorPane anchorPane;
    @FXML
    ComboBox attributeComboBox;
    @FXML
    ComboBox valueComboBox;
    @FXML
    ComboBox operationComboBox;
    @FXML
    TextField tagField;
    @FXML
    Button saveButton;
    @FXML
    Button cancelButton;

    private ListProperty<Attribute> listProperty;
    private TreeItem<Rule> currentTreeItemSelection;
    private EditType type;
    private QuestionGraphFormController controller;

    /**
     * Инициализация окна
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Настройка связывания комбобоксов с уже имеющимися объектами
        listProperty = new SimpleListProperty<>();

        attributeComboBox.itemsProperty().bind(listProperty);
        attributeComboBox.setTooltip(new Tooltip());
        attributeComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) -> attributeComboBox.getTooltip().setText(newValue.toString())
        );
        listProperty.set(FXCollections.observableArrayList(DataManager.getAttributes()));

        operationComboBox.getItems().addAll(Rule.Operation.AND, Rule.Operation.OR, Rule.Operation.NO_OP);
        //operationComboBox.getItems().add(Rule.Operation.XOR); - раскомментировать при добавлении соответствующего правила
    }

    /**
     * Функция, устанавливающая текущий обрабатываемый объект и тип операции: добавление или изменение
     * @param node - текущий узел
     * @param editType - тип операции
     * @param controller - контроллер
     */
    public void setCurrentTreeItemSelection(TreeItem<Rule> node, EditType editType, QuestionGraphFormController controller) {
        //Устанавливаем текущий объект и тип операции
        currentTreeItemSelection = node;
        type = editType;
        this.controller = controller;

        //Если тип операции - изменение, то
        //получаем правило из объекта и выводим его в интерфейс
        if (type == EditType.CHANGE) {
            Rule currentRule = currentTreeItemSelection.getValue();
            tagField.setText(currentRule.getTag());
            attributeComboBox.setValue(currentRule.getAttribute().getText());
            valueComboBox.setValue(currentRule.getValue());
            operationComboBox.setValue(currentRule.getOperation());
        }
    }

    /**
     * Функция для добавления нового атрибута
     * @param actionEvent - событие, передаваемое по нажатию по кнопке
     */
    public void attributePlusOnAction(ActionEvent actionEvent) {
        //Создаём модальное диалоговое окно
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Добавление атрибута");
        dialog.setHeaderText("Добавьте атрибут и выберите тип");

        ButtonType addButtonType = new ButtonType("Добавить", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField attributeName = new TextField();
        attributeName.setPromptText("...");
        ComboBox attributeType = new ComboBox();
        attributeType.getItems().addAll("O", "Q","A","R","T");
        attributeType.setValue("Q");

        grid.add(new Label("Имя атрибута:"), 0, 0);
        grid.add(attributeName, 1, 0);
        grid.add(new Label("Тип атрибута:"), 0, 1);
        grid.add(attributeType, 1, 1);

        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> attributeName.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new Pair<>(attributeName.getText(), (String) attributeType.getValue());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(attributeParameters -> {
            if (result.get() != null) {
                Pair<String, String> p = result.get();
                DataManager.getAttributes().add(new Attribute(p.getKey(), null, null).setType(p.getValue()));

                listProperty.set(FXCollections.observableArrayList(DataManager.getAttributes()));
                attributeComboBox.setValue(attributeComboBox.getItems().get(attributeComboBox.getItems().size()-1)); //hack
            }
        });
    }

    /**
     * Функция для добавления нового значения атрибута
     * @param actionEvent - событие, передаваемое по нажатию по кнопке
     */
    public void valuePlusOnAction(ActionEvent actionEvent) {
        //TODO Доработать функционал кнопки добавления новых значений
    }

    /**
     * Функция закрытия окна правки правила
     * @param actionEvent - событие, передаваемое по нажатию по кнопке
     */
    public void cancelButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Функция сохранения внесённых правок
     * @param actionEvent - событие, передаваемое по нажатию по кнопке
     */
    public void saveButtonOnAction(ActionEvent actionEvent) {
        //Получаем значения введённые пользователем в интерфейс
        String tag = tagField.getText();
        String attribute = (String) attributeComboBox.getValue();
        String operation = operationComboBox.getValue().toString();
        Object value = valueComboBox.getValue();

        //Если выбрана операция добавления, то в текущий узел добавляем новое правило
        if (type == EditType.ADD) {
            //TODO добавление нового правила в текущий узел
        } //Если выбрана операция изменения, то изменяем текущий узел
        else if (type == EditType.CHANGE) {
            Rule currentRule = currentTreeItemSelection.getValue();
            currentRule.setTag(tag);
            //currentRule.setAttribute(attribute); //TODO Доработать добавление атрибута
            //Сейчас из интерфейса мы получаем строку
            // необходимо по описанию находить атрибут в списке атрибутов и использовать его
            // если такового нет, то добавлять новый атрибут в DataManager.attributes
            currentRule.setOperation(operation);
            currentRule.setValue(value);
        }
        controller.updateTree();
        cancelButtonOnAction(actionEvent);
    }

    public enum EditType {
        ADD,
        CHANGE
    }
}
