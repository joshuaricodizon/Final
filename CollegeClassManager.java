package Final;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

public class CollegeClassManager {
    private static final String CIS = "CIS";
    private static final String MAT = "MAT";
    private static final String ENG = "ENG";

    private Graph cisGraph;
    private Graph matGraph;
    private Graph engGraph;

    public CollegeClassManager(Graph cisGraph, Graph matGraph, Graph engGraph) {
        this.cisGraph = cisGraph;
        this.matGraph = matGraph;
        this.engGraph = engGraph;
    }

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

    private void dfs(String currentClass, Set<String> visited, List<String> recommendations, Graph graph) {
        visited.add(currentClass);

        List<String> neighbors = graph.getNeighbors(currentClass);

        if (neighbors.isEmpty()) {
            recommendations.add("COURSE COMPLETION");
            return;
        }

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

        // Get user input for CIS classes
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the current CIS class: ");
        String currentCISClass = scanner.nextLine();
        List<String> cisRecommendations = classManager.recommendNextClass(currentCISClass, CIS);

        // Get user input for MAT classes
        System.out.print("Enter the current MAT class: ");
        String currentMATClass = scanner.nextLine();
        List<String> matRecommendations = classManager.recommendNextClass(currentMATClass, MAT);

        // Get user input for ENG classes
        System.out.print("Enter the current ENG class: ");
        String currentENGClass = scanner.nextLine();
        List<String> engRecommendations = classManager.recommendNextClass(currentENGClass, ENG);

        // Print recommendations
        System.out.println("\nRecommended CIS Classes to take after " + currentCISClass + ": " + cisRecommendations);
        System.out.println("Recommended MAT Classes to take after " + currentMATClass + ": " + matRecommendations);
        System.out.println("Recommended ENG Classes to take after " + currentENGClass + ": " + engRecommendations);
 
    }
}