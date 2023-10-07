import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CityVisualization extends Application {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int CITY_RADIUS = 5;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("City Visualization");

        Pane root = new Pane();

        // Replace this with your list of cities
        City[] cities = TSPGenerator.generateRandomCities(10, 20, WINDOW_WIDTH - 20, 20, WINDOW_HEIGHT - 20).toArray(new City[0]);

        for (City city : cities) {
            Circle circle = new Circle(city.x, city.y, CITY_RADIUS);
            root.getChildren().add(circle);
        }

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    // Replace this with your own method to generate cities


}
