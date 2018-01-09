package UI.Controller;

import Data.Attribute;
import Data.DataManager;
import Data.Rule;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditRuleFormController implements Initializable{
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
    private boolean isAdd;

    public void setCurrentTreeItemSelection(TreeItem<Rule> currentTreeItemSelection, boolean isAdd) {
        this.currentTreeItemSelection = currentTreeItemSelection;

        Rule it = currentTreeItemSelection.getValue();

        tagField.setText(           it.getTag());
        attributeComboBox.setValue( it.getAttribute().getText());
        valueComboBox.setValue(     it.getValue());
        operationComboBox.setValue( it.getOperation());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listProperty = new SimpleListProperty<>();

        attributeComboBox.itemsProperty().bind(listProperty);
        attributeComboBox.setTooltip(new Tooltip());
        attributeComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) ->
                        attributeComboBox.getTooltip().setText(newValue.toString())
        );
        listProperty.set(FXCollections.observableArrayList(DataManager.getAttributes()));
    }

    public void attributePlusOnAction(ActionEvent actionEvent) {
        // Create the custom dialog.
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

    public void valuePlusOnAction(ActionEvent actionEvent) {
    }

    public void cancelButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void saveButtonOnAction(ActionEvent actionEvent) {

        String tag = tagField.getText();
        String atr = (String) attributeComboBox.getValue();
        Rule.Operation op = (Rule.Operation) operationComboBox.getValue();
        Object value = valueComboBox.getValue();

        if (isAdd) { //если добавляем элемент

        } else {     //если изменяем текущий

        }

        currentTreeItemSelection.getParent().getChildren()
                .removeIf(ruleTreeItem -> ruleTreeItem.equals(currentTreeItemSelection));
    }
}
