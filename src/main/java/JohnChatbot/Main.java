package JohnChatbot;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for JohnChatbot using FXML.
 */
public class Main extends Application {

    private final JohnChatbot johnChatbot = new JohnChatbot();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            fxmlLoader.<MainWindow>getController().setJohnChatbot(johnChatbot); //inject the JohnChatbot instance
            stage.show();
            stage.setTitle("John Chatbot");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
