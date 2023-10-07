import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TSPGenerator {
    public static List<City> generateRandomCities(int numCities, double xMin, double xMax, double yMin, double yMax) {
        List<City> cities = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < numCities; i++) {
            double x = xMin + (xMax - xMin) * random.nextDouble();
            double y = yMin + (yMax - yMin) * random.nextDouble();
            cities.add(new City(x, y, i));
        }

        return cities;
    }
}