# CheatCodez

To import in Eclipse: File -> Import -> Gradle -> Gradle Project
To import to Intellij IDEA: File -> Open -> build.gradle
To import to NetBeans: File -> Open Project...


## Running tests
To run the tests use gradlew tests:test

## Main Game

### Constants
    Game constants are largely set in the Constants.java file for easy access and modification

### Graphs and Pathfinding
    The main game has path finding implemented using an A* path algorithm. 
        All entities are capable of using the pathfinding algorithm. 
        This requires entities to be given a start node and an end node.
    
    The graph is loaded from the graph.txt file in the assets folder and can be both loaded rendered by calling the appropriate functions (loadGraph and renderGraph respectively) in the MapLoader class. 
        The graph being rendered is just for debugging and isn't necessary for its function
    
    It is currently necessary for firetrucks to be set a start position using a node position
        This can be easily obtained by using the HashMap<String, Coord> of coords which is populated by the loadGraph function. 
    
    Setting a goal is simple as EntityName.(targetCoord)
        targetCoord should be retrieved as shown below using the HashMap

```Java

    Map<String, Coord> coords = new HashMap<>();
    MapGraph mapGraph = new MapGraph();

    // Load the mapGraph and nodes into coords from file
    MapLoader.loadGraph(coords,mapGraph, "graph.txt");

    Firetruck ft =  new Firetruck(mapGraph, coords.get("B"), 1, game.manager);
    ft.setGoal(coords.get("C"))

```
## Testing

To run all tests use ```gradlew tests:test``` in a terminal. This will run all tests that are in ```tests/src```

### Creating Tests

To create new tests use JUnit structure. If you require the LibGDX environment then you must include ```@RunWith(GameTestRunner.class)``` at the top of the test as shown below.

```
// Include libGdx environment
@RunWith(GameTestRunner.class)
public class FiretruckTestNew {
```

If your test doesn't require the environment then do not include this line

