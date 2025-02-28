import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.HomeView;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Scene scene = new HomeView();
        stage.setTitle("Recipez");
        stage.setScene(scene);
        stage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
