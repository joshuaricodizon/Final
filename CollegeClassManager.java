package Final;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * The CollegeClassManager class provides functionality for managing college course progressions
 * and recommending next classes based on the dependencies between courses in different subjects.
 * It utilizes directed graphs for each subject (CIS, MAT, ENG) to represent the course dependencies.
 *
 * @author Joshua Dizon
 * @since December 2023
 */
public class CollegeClassManager {
    private static final String CIS = "CIS";
    private static final String MAT = "MAT";
    private static final String ENG = "ENG";

    private Graph cisGraph;
    private Graph matGraph;
    private Graph engGraph;

    /**
     * Constructs a CollegeClassManager with the specified graphs for different subjects.
     *
     * @param cisGraph The graph representing course dependencies for the CIS subject.
     * @param matGraph The graph representing course dependencies for the MAT subject.
     * @param engGraph The graph representing course dependencies for the ENG subject.
     */
    public CollegeClassManager(Graph cisGraph, Graph matGraph, Graph engGraph) {
        this.cisGraph = cisGraph;
        this.matGraph = matGraph;
        this.engGraph = engGraph;
    }

    /**
     * Recommends the next classes to take based on the current class and the specified course type.
     *
     * @param currentClass The current class for which recommendations are sought.
     * @param courseType   The type of course (CIS, MAT, ENG) for which recommendations are sought.
     * @return A list of recommended classes to take after the current class.
     */
    public List<String> recommendNextClass(String currentClass, String courseType) {
        List<String> recommendations = new ArrayList<>();
        Set<String> visited = new HashSet<>();

        switch (courseType.toUpperCase()) {
            case CIS:
                dfs(currentClass, visited, recommendations, cisGraph);
                break;
            case MAT:
                dfs(currentClass, visited, recommendations, matGraph);
                break;
            case ENG:
                dfs(currentClass, visited, recommendations, engGraph);
                break;
            default:
                System.out.println("Invalid course type.");
        }

        return recommendations;
    }
    
    /**
     * Depth-First Search (DFS) algorithm for traversing the graph and generating recommendations.
     * This method is used by the CollegeClassManager to recommend next classes based on course dependencies.
     * It explores the graph starting from the current class and adds recommendations to the provided list.
     *
     * @param currentClass   The current class being explored.
     * @param visited        A set of visited classes to avoid revisiting the same node.
     * @param recommendations The list to which recommended classes are added.
     * @param graph          The graph representing course dependencies.
     */
    private void dfs(String currentClass, Set<String> visited, List<String> recommendations, Graph graph) {
        // Mark the current class as visited to avoid revisiting during traversal
        visited.add(currentClass);

        // Get the list of neighbors (dependent classes) for the current class
        List<String> neighbors = graph.getNeighbors(currentClass);

        // If the current class has no dependencies, mark it as "COURSE COMPLETION" and return
        if (neighbors.isEmpty()) {
            recommendations.add("COURSE COMPLETION");
            return;
        }

        // Iterate through the neighbors and recursively call dfs for unvisited classes
        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                recommendations.add(neighbor);
                dfs(neighbor, visited, recommendations, graph);
            }
        }
    }


    public static void main(String[] args) {
        Graph cisGraph = new Graph();
        Graph matGraph = new Graph();
        Graph engGraph = new Graph();
        CollegeClassManager classManager = new CollegeClassManager(cisGraph, matGraph, engGraph);

        // Adding CIS dependencies
        cisGraph.addEdge("CIS254", "CIS255");
        cisGraph.addEdge("CIS256", "CIS305");
        cisGraph.addEdge("CIS150", "CIS254");
        cisGraph.addEdge("CIS132", "CIS150");
        cisGraph.addEdge("CIS305", "CIS380");
        cisGraph.addEdge("CIS100", "CIS132");
        cisGraph.addEdge("CIS255", "CIS256");

        // Adding MAT dependencies
        matGraph.addEdge("MAT101", "MAT102");
        matGraph.addEdge("MAT102", "MAT201");
        matGraph.addEdge("MAT201", "MAT202");
        matGraph.addEdge("MAT202", "MAT301");

        // Adding ENG dependencies
        engGraph.addEdge("ENG101", "ENG202");
        engGraph.addEdge("ENG202", "ENG303");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu options with flipped order
            displayHeader("main");
            System.out.println("\nMenu Options:");
            System.out.println("0 - View all classes");
            System.out.println("1 - View recommended ENG class");
            System.out.println("2 - View recommended CIS class");
            System.out.println("3 - View recommended MAT class");
            System.out.println("00 - Exit program");

            // Get user input for menu option
            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            // Show header
            displayHeader("main");
            switch (choice) {
                case "0":
                    // Display all classes with dependencies
                    System.out.println("*************************");
                    System.out.println("ENG Course Progression: ");
                    displayAllClasses(engGraph);
                    System.out.println("*************************");
                    System.out.println("CIS Course Progression: ");
                    displayAllClasses(cisGraph);
                    System.out.println("*************************");
                    System.out.println("MAT Course Progression: ");
                    displayAllClasses(matGraph);
                    System.out.println("*************************");
                    break;
                case "1":
                    // Get user input for ENG classes
                    System.out.print("Enter your current ENG class: ");
                    String currentENGClass = scanner.nextLine();
                    List<String> engRecommendations = classManager.recommendNextClass(currentENGClass, ENG);
                    displayHeader("recommend");
                    System.out.println("Recommended ENG Classes to take after " + currentENGClass + ": " + engRecommendations);
                    break;
                case "2":
                    // Get user input for CIS classes
                    System.out.print("Enter your current CIS class: ");
                    String currentCISClass = scanner.nextLine();
                    List<String> cisRecommendations = classManager.recommendNextClass(currentCISClass, CIS);
                    displayHeader("recommend");
                    System.out.println("Recommended CIS Classes to take after " + currentCISClass + ": " + cisRecommendations);
                    break;
                case "3":
                    // Get user input for MAT classes
                    System.out.print("Enter your current MAT class: ");
                    String currentMATClass = scanner.nextLine();
                    List<String> matRecommendations = classManager.recommendNextClass(currentMATClass, MAT);
                    displayHeader("recommend");
                    System.out.println("Recommended MAT Classes to take after " + currentMATClass + ": " + matRecommendations);
                    break;
                case "00":
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    /**
     * Displays all classes and their dependencies for a given subject represented by the provided graph.
     * The method generates a formatted output, printing each class and its dependencies in ascending order.
     *
     * @param graph The graph representing course dependencies for a specific subject.
     */
    private static void displayAllClasses(Graph graph) {
        // Create a set to store all nodes (classes and their dependencies)
        Set<String> allNodes = new HashSet<>();

        // Populate the set with nodes from the graph's adjacency list
        for (Map.Entry<String, List<String>> entry : graph.adjacencyList.entrySet()) {
            allNodes.add(entry.getKey());
            allNodes.addAll(entry.getValue());
        }

        // Convert the set to a list and sort it in ascending order
        List<String> allClasses = new ArrayList<>(allNodes);
        Collections.sort(allClasses);

        // Display each class and its dependencies in a formatted manner
        for (String currentClass : allClasses) {
            List<String> neighbors = graph.getNeighbors(currentClass);
            System.out.print(currentClass + " -> ");
            if (!neighbors.isEmpty()) {
                System.out.print(neighbors.get(0));
                for (int i = 1; i < neighbors.size(); i++) {
                    System.out.print(", " + neighbors.get(i));
                }
            }
            System.out.println();
        }
    }

    /**
     * Displays a header for the console application based on the specified type.
     * The header includes a decorative banner with the application name.
     *
     * @param headerType The type of header to display ("main" or "exit").
     */
    private static void displayHeader(String headerType) {
        if ("main".equals(headerType)) {
            System.out.println("*****************************************");
            System.out.println("*     College Course Road Map Helper    *");
            System.out.println("*****************************************");
        } else if ("exit".equals(headerType)) {
            System.out.println("*****************************************");
            System.out.println("*     Closing Course Road Map Helper    *");
            System.out.println("*****************************************");
        } else {
            // Handle other cases or do nothing for unspecified header types
        }
    }
}
