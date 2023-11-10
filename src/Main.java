import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List<City> cities = TSPGenerator.generateRandomCities(10, 0, 100, 0, 100); // Replace with your own list of cities

        // Iterate through the list and print each city
        for (int i = 0; i < cities.size(); i++) {
            City city = cities.get(i);
            System.out.println("City " + (city.name) + ": (" + city.x + ", " + city.y + ")");
        }
        int numCities = cities.size();
        double[][] distances = new double[numCities][numCities];

        for (int i = 0; i < numCities; i++) {
            City city1 = cities.get(i);
            for (int j = i + 1; j < numCities; j++) {
                City city2 = cities.get(j);
                double distance = calculateDistance(city1, city2);
                distances[i][j] = distance;
                distances[j][i] = distance; // Distance matrix is symmetric
            }
        }

        // Access the distance between two cities
        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                double distance = distances[i][j];
                System.out.println("Distance between City " + (i) + " and City " + (j) + ": " + distance);
            }
        }

        List<Double> generationDistances = new ArrayList<>();
        TSPGeneticAlgorithm(cities, 10, distances, generationDistances);

        // Plot the results
        plotResults(generationDistances);
    }

    public static double calculateDistance(City city1, City city2) {
        double dx = city2.x - city1.x;
        double dy = city2.y - city1.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static void TSPGeneticAlgorithm(List<City> cities, int populationSize, double[][] distances, List<Double> generationDistances) {
        // Initialize population
        List<List<City>> initialPopulation = new ArrayList<>();
        List<Integer> fitness = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            List<City> route = new ArrayList<>(cities);
            Collections.shuffle(route, random); // Shuffle the order of cities
            initialPopulation.add(route);
            int fit = 0;
            for (int j = 0; j < route.size() - 1; j++) {
                fit += distances[route.get(j).name][route.get(j + 1).name];
            }
            fitness.add(fit);
        }

        for (int generation = 0; generation < 20; generation++) {
            System.out.println("gen " + generation + " lowest distance = " + Collections.min(fitness));

            for (int i = 0; i < populationSize / 2; i++) {
                int maxVal = Collections.max(fitness);
                int maxIdx = fitness.indexOf(maxVal);
                fitness.remove(maxIdx);
                initialPopulation.remove(maxIdx);
            }

            List<List<City>> newGen = new ArrayList<>();
            for (List<City> cityList : initialPopulation) {
                Random random1 = new Random();
                Random random2 = new Random();
                int ranIdx1 = random1.nextInt(cities.size());
                int ranIdx2 = random2.nextInt(cities.size());
                newGen.add(cityList);

                List<City> mutatedRoute = new ArrayList<>(cityList);

                Collections.swap(mutatedRoute, ranIdx1, ranIdx2);
                newGen.add(mutatedRoute);
            }
            fitness.clear();
            for (int i = 0; i < newGen.size(); i++) {
                int fit = 0;
                for (int j = 0; j < newGen.get(i).size() - 1; j++) {
                    fit += distances[newGen.get(i).get(j).name][newGen.get(i).get(j + 1).name];
                }
                fitness.add(fit);
            }
            initialPopulation.clear();
            initialPopulation = newGen;

            // Store the lowest distance of the current generation
            double minDistance = Collections.min(fitness);
            generationDistances.add(minDistance);
        }
        System.out.println("final gen lowest distance = " + Collections.min(fitness));
    }

    private static void plotResults(List<Double> generationDistances) {
        // Create Chart
        XYChart chart = new XYChart(800, 600);
        chart.setTitle("TSP Genetic Algorithm");
        chart.setXAxisTitle("Generation");
        chart.setYAxisTitle("Distance");

        // Series
        chart.addSeries("Shortest Distance", null, generationDistances);

        // Show it
        new SwingWrapper<>(chart).displayChart();
    }
}
