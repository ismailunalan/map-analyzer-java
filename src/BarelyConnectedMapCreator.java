import java.util.*;
/**
 * A map creator class that takes a list of roads and constructs a barely connected map.
 */
public class BarelyConnectedMapCreator {
    private List<Road> roads;
    private Map<String, Set<String>> connectedPoints;
    /**
     * Constructs a new BarelyConnectedMapCreatoe with the given list of roads.
     *
     * @param roads List of roads to be used for creating the map.
     */
    public BarelyConnectedMapCreator(List<Road> roads) {
        this.roads = new ArrayList<>(roads);
        this.connectedPoints = new HashMap<>();
    }
    /**
     * Creates a barely connected map .
     *
     * @param args Command line arguments.
     * @return List of roads forming the barely connected map.
     */
    public List<Road> createBarelyConnectedMap(String[] args) {
        // Sort roads by length, then by ID
        roads.sort(Comparator.comparingInt(Road::getLength).thenComparingInt(Road::getID));

        List<Road> barelyConnectedMap = new ArrayList<>();
        for (Road road : roads) {
            if (!connectedPoints.containsKey(road.getStartPoint())) {
                connectedPoints.put(road.getStartPoint(), new HashSet<>());
            }
            if (!connectedPoints.get(road.getStartPoint()).contains(road.getEndPoint())) {
                barelyConnectedMap.add(road);
                addConnection(road.getStartPoint(), road.getEndPoint());
            }
        }
        barelyConnectedMapPrinter(args,barelyConnectedMap);
        return barelyConnectedMap;
    }
    /**
     * Adds a connection between two points and updates the connected points map.
     *
     * @param point1 Start point of the road.
     * @param point2 End point of the road.
     */
    private void addConnection(String point1, String point2) {
        if (!connectedPoints.containsKey(point1)) {
            connectedPoints.put(point1, new HashSet<>());
        }
        if (!connectedPoints.containsKey(point2)) {
            connectedPoints.put(point2, new HashSet<>());
        }
        // Connect the two points
        connectedPoints.get(point1).add(point2);
        connectedPoints.get(point2).add(point1);

        // Merge the sets of connected points
        Set<String> mergedSet = new HashSet<>(connectedPoints.get(point1));
        mergedSet.addAll(connectedPoints.get(point2));

        for (String point : mergedSet) {
            connectedPoints.put(point, mergedSet);
        }
    }
    /**
     * Prints the barely connected map to a file.
     *
     * @param args Command line arguments.
     * @param barelyConnectedMap List of roads forming the barely connected map.
     */
    public void barelyConnectedMapPrinter(String[] args, List<Road> barelyConnectedMap){
        FileOutput.writeToFile(args[1],"Roads of Barely Connected Map is:",true,true);
        for (Road road : barelyConnectedMap) {
            FileOutput.writeToFile(args[1],road.toString(),true,true);
        }
    }
}
