package UI;

import Data.*;
import UI.Controller.QuestionGraphFormController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage mainStage;
    private static Stage questionGraphStage;

    private static QuestionGraphFormController questionGraphFormController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/mainForm.fxml"));
        primaryStage.setTitle("Экспертная система: Выбор книги");
        Scene scene = new Scene(root, 600, 405);
        scene.getStylesheets().add(getClass().getResource("Resources/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        mainStage = primaryStage;

        questionGraphStage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/questionsGraphForm.fxml"));
        Scene scene1 = new Scene(loader.load());

        questionGraphFormController = loader.getController();
        questionGraphStage.setTitle("Дерево правил");
        questionGraphStage.setScene(scene1);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static Stage getQuestionGraphStage() {
        return questionGraphStage;
    }

    public static QuestionGraphFormController getQuestionGraphFormController() {
        return questionGraphFormController;
    }

    public static void main(String[] args) {
       //DataManager.test();
       launch(args);
    }
}
