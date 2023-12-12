package Final;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Graph class represents a directed graph that stores dependencies between nodes.
 * It is used in the CollegeClassManager application to model course dependencies for different subjects.
 * Each node in the graph represents a college course, and directed edges represent prerequisites.
 * 
 * @author Joshua Dizon
 * @since December 2023
 */
class Graph {
    /**
     * A mapping of nodes to their respective lists of neighbors (adjacency list).
     * The adjacencyList represents the dependencies between courses in the graph.
     */
    Map<String, List<String>> adjacencyList;

    /**
     * Constructs an empty graph with an empty adjacency list.
     */
    public Graph() {
        adjacencyList = new HashMap<>();
    }

    /**
     * Adds a directed edge from the start node to the end node in the graph.
     * If the start node is not already in the graph, it is added with an empty list of neighbors.
     *
     * @param start The starting node of the directed edge.
     * @param end   The ending node of the directed edge.
     */
    public void addEdge(String start, String end) {
        adjacencyList.computeIfAbsent(start, k -> new ArrayList<>()).add(end);
    }

    /**
     * Retrieves the list of neighbors (dependencies) for a given node in the graph.
     * If the node is not present in the graph, an empty list is returned.
     *
     * @param node The node for which to retrieve the list of neighbors.
     * @return A list of neighbors for the specified node.
     */
    public List<String> getNeighbors(String node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }
}
