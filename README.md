# College Course Road Map Helper

The **CollegeClassManager** package provides a Java implementation for managing college course progressions and recommending next classes based on the dependencies between courses in different subjects. It utilizes directed graphs for each subject (CIS, MAT, ENG) to represent the course dependencies.

## Author
- **Joshua Dizon**
- **Since:** December 2023

## Class Structure
The main class in this package is **CollegeClassManager**, which includes the following methods:

### `public class CollegeClassManager`
#### Constructor
- `public CollegeClassManager(Graph cisGraph, Graph matGraph, Graph engGraph)`: Constructs a CollegeClassManager with graphs representing course dependencies for CIS, MAT, and ENG subjects.

#### Recommendation Method
- `public List<String> recommendNextClass(String currentClass, String courseType)`: Recommends the next classes to take based on the current class and the specified course type (CIS, MAT, ENG).

#### DFS Algorithm
- `private void dfs(String currentClass, Set<String> visited, List<String> recommendations, Graph graph)`: Depth-First Search (DFS) algorithm for traversing the graph and generating recommendations.

#### Main Method
- `public static void main(String[] args)`: Includes a simple interactive console interface to interact with the CollegeClassManager, displaying recommendations and course progressions.

#### Display Methods
- `private static void displayAllClasses(Graph graph)`: Displays all classes and their dependencies for a given subject represented by the provided graph.
- `private static void displayHeader(String headerType)`: Displays a header for the console application based on the specified type.

## Example Usage
```java
Graph cisGraph = new Graph();
Graph matGraph = new Graph();
Graph engGraph = new Graph();
CollegeClassManager classManager = new CollegeClassManager(cisGraph, matGraph, engGraph);

// Adding CIS dependencies
cisGraph.addEdge("CIS254", "CIS255");
// ... (similarly add dependencies for MAT and ENG)

// Get user input for recommendations
List<String> engRecommendations = classManager.recommendNextClass("ENG101", "ENG");
```

## Graph Class
The package assumes the existence of a **Graph** class to represent the course dependencies. This class should have methods like `addEdge` and `getNeighbors`.

```java
public class Graph {
    // Implementation details for Graph class
}
```

## How to Run
1. Compile the Java files in the package.
2. Run the compiled main class (`CollegeClassManager`) to interact with the program through the console.

## Dependencies
- Java (JDK 8 or higher)