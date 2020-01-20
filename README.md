# CheatCodez

To import in Eclipse: File -> Import -> Gradle -> Gradle Project

To import to Intellij IDEA: File -> Open -> build.gradle

To import to NetBeans: File -> Open Project...


## Running tests
To run the tests use gradlew tests:test

## Main Game

### Asset Manager
    All textures and tmx files are loaded in by an AssetManager in kroyGame.java
    The AssetManager can then be used to retrieve any loaded files without having to reload the actual file.
    It is set up to already load all textures in the assets folder

Retrieval:
```Java
        Texture tex = manager.get(TextureName, Texture.class);
        TiledMap map = manager.get(TiledMap filename, TiledMap.class);
```

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

### Creating Tests

To create new tests use JUnit structure. If you require the LibGDX environment then you must include ```@RunWith(GameTestRunner.class)``` at the top of the test as shown below.

```
// Include libGdx environment
@RunWith(GameTestRunner.class)
public class FiretruckTestNew {
```

If your test doesn't require the environment then do not include this line. You don't have to run all the tests at the same time. You can run them individually (running through IDE is easiest way, make sure working directory is set to android to eleviate any errors surrounding assets)

**BOX2D PHYSICS DO NOT WORK IN THE TESTS.**
Running anything from the Box2D library will not work in the test. E.g. body.applyForceToCenter(vector, true); this will have no effect. 

### Running Tests

To run all tests use ```gradlew tests:test``` in a terminal. This will run all tests that are in ```tests/src```





