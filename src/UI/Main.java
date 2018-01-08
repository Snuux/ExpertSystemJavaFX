package UI;

import Data.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage mainStage;
    private static Stage questionGraphStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/main.fxml"));
        primaryStage.setTitle("Экспертная система: Выбор книги");
        primaryStage.setScene(new Scene(root, 300, 325));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        mainStage = primaryStage;

        questionGraphStage = new Stage();
        Scene scene1 = new Scene(FXMLLoader.load(getClass().getResource("FXML/questionGraph.fxml")));
        questionGraphStage.setTitle("Дерево правил");
        questionGraphStage.setScene(scene1);
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static Stage getQuestionGraphStage() {
        return questionGraphStage;
    }

    public static void main(String[] args) {
       DataManager.test();
       launch(args);
    }
}
