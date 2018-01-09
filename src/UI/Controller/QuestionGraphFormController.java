package UI.Controller;

import Data.Attribute;
import Data.Rule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionGraphFormController implements Initializable {
    @FXML
    public Button closeGraphButton;
    @FXML
    TreeView<Rule> treeView;
    @FXML
    ComboBox<String> attributeComboBox;
    @FXML
    ComboBox valueComboBox;
    @FXML
    ComboBox<Rule.Operation> operationComboBox;
    @FXML
    TextField tagField;
    @FXML
    Button saveButton;

    private TreeItem<Rule> currentTreeItemSelection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateDialog();
        //UIUtilities.updateTooltipBehavior(0, 999999, 0, true);
    }

    public void updateDialog() {
        treeView.setRoot(Data.DataManager.getTree());
        currentTreeItemSelection = Data.DataManager.getTree();
        createContextMenu(Data.DataManager.getTree());


        //Настройка ComboBox со значениями
        valueComboBox.setTooltip(new Tooltip());
        valueComboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) ->
                        valueComboBox.getTooltip().setText(newValue.toString())
        );
    }

    public void updateTree() {
        treeView.refresh();
    }


    public TreeItem<Rule> getCurrentTreeItemSelection() {
        return currentTreeItemSelection;
    }

    private void createContextMenu(TreeItem<Rule> iRoot) {
        MenuItem entry1 = new MenuItem("Добавить правило");
        MenuItem entry2 = new MenuItem("Исправить правило");
        MenuItem entry3 = new MenuItem("Удалить правило");

        entry1.setOnAction(ae -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UI/FXML/editRuleForm.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();

                ((EditRuleFormController) fxmlLoader.getController()).setCurrentTreeItemSelection(currentTreeItemSelection, EditRuleFormController.EditType.ADD, this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        entry2.setOnAction(ae -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/editRuleForm.fxml"));
                Parent root1 = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();

                ((EditRuleFormController) fxmlLoader.getController()).setCurrentTreeItemSelection(currentTreeItemSelection, EditRuleFormController.EditType.CHANGE, this);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        entry3.setOnAction(ae -> {
            if (currentTreeItemSelection.getValue().getAttribute().getType() != Attribute.Type.ROOT) {
                currentTreeItemSelection.getParent()
                                        .getChildren()
                                        .removeIf(ruleTreeItem -> ruleTreeItem.equals(currentTreeItemSelection));
            }
        });

        treeView.setContextMenu(new ContextMenu(entry1, entry2, entry3));
    }

    public void onTreeViewClickedHandle(MouseEvent mouseEvent) {
        if (currentTreeItemSelection == null)
            return;

        TreeItem<Rule> selectedItem = treeView.getSelectionModel().getSelectedItem();

        if (selectedItem == null)
            return;

        if (!currentTreeItemSelection.equals(selectedItem)) {
            currentTreeItemSelection = selectedItem;
            Rule selectedItemRule = selectedItem.getValue();

            if (selectedItemRule != null) {
                tagField.setText(selectedItemRule.getTag());
                attributeComboBox.setValue(selectedItemRule.getAttribute().getText());
                operationComboBox.setValue(selectedItemRule.getOperation());

                if (selectedItemRule.getValue() != null)
                    valueComboBox.setValue(selectedItemRule.getValue());
            }
        }
    }


    public void valuePlusOnAction(ActionEvent actionEvent) {

    }

    public void handleCloseGraphButton(ActionEvent actionEvent) {
        Stage stage = (Stage) closeGraphButton.getScene().getWindow();
        stage.hide();
    }
}
