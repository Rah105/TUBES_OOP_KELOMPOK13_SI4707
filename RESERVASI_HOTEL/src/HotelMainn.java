
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class HotelMainn extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        showLogin();  // Tampilkan layar login saat pertama kali dijalankan
    }

    public static void showLogin() {
        try {
            Parent root = FXMLLoader.load(HotelMainn.class.getResource("Login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Login - Reservasi Hotel");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showHotel() {
        try {
            Parent root = FXMLLoader.load(HotelMainn.class.getResource("Hotel.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Hotel - Reservasi Hotel");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}