package UI.Controller;

import Data.Attribute;
import Data.DataManager;
import Data.Item;
import UI.UIUtilities;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class QuestionGraphController implements Initializable {
    @FXML
    public Button closeGraphButton;
    @FXML
    TreeView treeView;
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

    TreeItem currentTreeItemSelection;
    ListProperty<Attribute> listProperty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        treeView.setRoot(Data.DataManager.getTree());
        currentTreeItemSelection = Data.DataManager.getTree();
        createContextMenu(Data.DataManager.getTree());

        listProperty = new SimpleListProperty<>();

        attributeComboBox.itemsProperty().bind(listProperty);
        attributeComboBox.setTooltip(new Tooltip());
        attributeComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) ->
                        attributeComboBox.getTooltip().setText(newValue.toString())
        );
        listProperty.set(FXCollections.observableArrayList(DataManager.getAttributes()));

        //Настройка ComboBox со значениями
        valueComboBox.setTooltip(new Tooltip());
        valueComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) ->
                    valueComboBox.getTooltip().setText(newValue.toString())
                );

        UIUtilities.updateTooltipBehavior(0, 999999,0,true);
    }

    private void createContextMenu(TreeItem iRoot){
        MenuItem entry1 = new MenuItem("Добавить правило");
        MenuItem entry2 = new MenuItem("Исправить правило");
        MenuItem entry3 = new MenuItem("Удалить правило");

        entry1.setOnAction(ae -> {
            //System.out.println(((Item)getTreeViewSelection().getValue()).getTag());
        });

        treeView.setContextMenu(new ContextMenu(entry1, entry2, entry3));
    }

    public void onTreeViewClickedHandle(MouseEvent mouseEvent) {
        if (currentTreeItemSelection == null)
            return;

        TreeItem selectedItem = (TreeItem)treeView.getSelectionModel().getSelectedItem();

        if (selectedItem == null)
            return;

        if (!currentTreeItemSelection.equals(selectedItem)) {
            currentTreeItemSelection = selectedItem;
            Item it = (Item)selectedItem.getValue();

            tagField.setText(it.getTag());
            attributeComboBox.setValue(it.getAttribute().getText());
            valueComboBox.setValue(it.getValue());
            operationComboBox.setValue(it.getOperation());
        }
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

    public void handleCloseGraphButton(ActionEvent actionEvent) {
        Stage stage = (Stage) closeGraphButton.getScene().getWindow();
        stage.hide();
    }
}
