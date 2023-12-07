package Final;

import java.util.List;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class CollegeClassManager {
    private Map<String, List<String>> graph;

    public CollegeClassManager() {
        graph = new HashMap<>();
    }

    public void addEdge(String start, String end) {
        if (!graph.containsKey(start)) {
            graph.put(start, new ArrayList<>());
        }
        graph.get(start).add(end);
    }

    public List<String> recommendNextClass(String currentClass) {
        if (!graph.containsKey(currentClass)) {
            System.out.println("No recommendations available for this class.");
            return new ArrayList<>();
        }

        List<String> recommendations = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        stack.push(currentClass);
        Map<String, Boolean> visited = new HashMap<>();

        while (!stack.isEmpty()) {
            String course = stack.pop();
            visited.put(course, true);
            if (graph.containsKey(course)) {
                for (String neighbor : graph.get(course)) {
                    if (!visited.containsKey(neighbor) || !visited.get(neighbor)) {
                        stack.push(neighbor);
                        recommendations.add(neighbor);
                    }
                }
            }
        }

        return recommendations;
    }

    public static void main(String[] args) {
        CollegeClassManager classManager = new CollegeClassManager();

        // Adding dependencies
        classManager.addEdge("CIS254", "CIS255");
        classManager.addEdge("CIS256", "CIS305");
        classManager.addEdge("CIS150", "CIS254");
        classManager.addEdge("CIS132", "CIS150");
        classManager.addEdge("CIS305", "CIS380");
        classManager.addEdge("CIS100", "CIS132");
        classManager.addEdge("CIS255", "CIS256");

        // Get user input for the current class
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the current class: ");
        String currentClass = scanner.nextLine();

        // Example usage
        List<String> recommendations = classManager.recommendNextClass(currentClass);

        System.out.println("Recommended Class Path for " + currentClass + ": " + recommendations);
    }
}