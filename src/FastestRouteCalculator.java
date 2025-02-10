import java.util.*;

/**
 * Finds the shortest route between two points based on given roads.
 */
public class FastestRouteCalculator {
    private Map<String, List<Road>> roadMap;

    /**
     * Constructs a new FastestRouteCalculator with a given list of roads.
     * @param roads the list of roads to be used in the calculation
     */
    public FastestRouteCalculator(List<Road> roads) {
        roadMap = new HashMap<>();
        for (Road road : roads) {
            if (!roadMap.containsKey(road.getStartPoint())) {
                roadMap.put(road.getStartPoint(), new ArrayList<>());
            }
            roadMap.get(road.getStartPoint()).add(road);

            if (!roadMap.containsKey(road.getEndPoint())) {
                roadMap.put(road.getEndPoint(), new ArrayList<>());
            }
            roadMap.get(road.getEndPoint()).add(road);
        }

    }

    /**
     * Sorts a list of routes based on the distance and road ID.
     *
     * @param routes the list of routes to sort
     */
    public static void sortRoutes(List<Route> routes) {
        Collections.sort(routes, (route1, route2) -> {
            int distanceCompare = Integer.compare(route1.getDistance(), route2.getDistance());
            if (distanceCompare != 0) {
                return distanceCompare;
            }
            return Integer.compare(route1.getRoad().getID(), route2.getRoad().getID());
        });
    }

    /**
     * Finds the fastest route between the start and end points.
     *
     * @param start the starting point of the route
     * @param end   the ending point of the route
     * @param args  additional arguments that may be used for output
     * @return the list of roads constituting the fastest route
     */
    public List<Road> findFastestRoute(String start, String end, String[] args, boolean isBarelyMap) {
        Map<String, Route> map = new HashMap<>();
        List<Route> routes = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Map<String, Integer> shortestDistances = new HashMap<>();

        for (Road road : roadMap.get(start)) {
            String neighbor = road.getStartPoint().equals(start) ? road.getEndPoint() : road.getStartPoint();
            Route route = new Route(road.getLength(), road, start, neighbor);
            routes.add(route);
            shortestDistances.put(neighbor, road.getLength());
        }

        // Sort the routes based on distance and ID
        sortRoutes(routes);

        while (!routes.isEmpty()) {
            Route currentNode = routes.remove(0);
            String currentPoint = currentNode.getToWhere();

            if (visited.contains(currentPoint)) {
                continue;
            }

            map.put(currentPoint, currentNode);
            visited.add(currentPoint);

            if (currentPoint.equals(end)) {
                break;
            }

            for (Road road : roadMap.get(currentPoint)) {
                String neighbor = road.getStartPoint().equals(currentPoint) ? road.getEndPoint() : road.getStartPoint();

                if (!visited.contains(neighbor)) {
                    int newDistance = currentNode.getDistance() + road.getLength();

                    // Check if a shorter route to the neighbor already exists
                    if (!shortestDistances.containsKey(neighbor) || newDistance < shortestDistances.get(neighbor)) {
                        Route newNode = new Route(newDistance, road, currentPoint, neighbor);
                        routes.add(newNode);
                        shortestDistances.put(neighbor, newDistance);
                    }
                }
            }

            // Re-sort the routes after adding new nodes
            sortRoutes(routes);
        }

        // Reconstruct the path
        List<Road> fastestRoute = new ArrayList<>();
        Route node = map.get(end);

        if (node != null) {
            fastestRoute.add(node.getRoad());
            while (node != null && !node.getFromWhere().equals(start)) {
                node = map.get(node.getFromWhere());
                if (node != null) {
                    fastestRoute.add(node.getRoad());
                }
            }
        }

        Collections.reverse(fastestRoute);
        fastestRoutePrinter(args, start, end, fastestRoute, isBarelyMap);
        return fastestRoute;
    }

    /**
     * Calculates the total distance of a found route.
     *
     * @param fastestRoute the route for which to calculate the distance
     * @return the total distance of the route
     */
    public int distanceCalculator(List<Road> fastestRoute) {
        return fastestRoute.stream().mapToInt(Road::getLength).sum();
    }

    /**
     * Prints the fastest route to output file.
     *
     * @param args          the arguments specifying the input and output file
     * @param start         the starting point of the route
     * @param end           the ending point of the route
     * @param fastestRoute  the fastest route to print
     */
    public void fastestRoutePrinter(String[] args, String start, String end, List<Road> fastestRoute, boolean isBarelyMap) {
        FileOutput.writeToFile(args[1], "Fastest Route from " + start + " to " + end + (isBarelyMap ? " on Barely Connected Map (" : " (") + distanceCalculator(fastestRoute) + " KM):", true, true);
        for (Road road : fastestRoute) {
            FileOutput.writeToFile(args[1], road.toString(), true, true);
        }
    }
    /**
     * Analyzes the map and prints the analysis to output file.
     * It calculates the total distance of the original map, the barely connected map,
     * and the fastest route in the barely connected map. It also calculates and prints
     * the ratios of distances for analysis.
     *
     * @param roads                  the list of all roads in the original map
     * @param fastestBarelyMapRoute  the fastest route in the barely connected map
     * @param barelyConnectedMap     the barely connected map
     * @param fastestRoute           the fastest route in the original map
     * @param args                   the arguments specifying the output file
     */
    public void mapAnalysis(List<Road> roads,List<Road> fastestBarelyMapRoute,List<Road> barelyConnectedMap,List<Road> fastestRoute,String[] args){
        // Calculate the total distance for the original map
        int totalDistanceOriginalMap = 0;
        for (Road road : roads) {
            totalDistanceOriginalMap += road.getLength();
        }
        // Calculate total distance for the fastest route in Barely Connected Map
        int totalDistanceBarelyConnected = 0;
        for (Road road : fastestBarelyMapRoute) {
            totalDistanceBarelyConnected += road.getLength();
        }
        // Calculate the total distance for the barely connected map
        int totalDistanceBarelyConnectedMap = barelyConnectedMap.stream().mapToInt(Road::getLength).sum();
        // Calculate the ratios
        double ratioOfFastestRoutes = (double) totalDistanceBarelyConnected / distanceCalculator(fastestRoute);
        double ratioOfConstructionMaterial = (double) totalDistanceBarelyConnectedMap / totalDistanceOriginalMap;
        // Print the analysis to OutpuFile
        FileOutput.writeToFile(args[1],"Analysis:",true,true);
        String content = String.format("Ratio of Construction Material Usage Between Barely Connected and Original Map: %.2f\n", ratioOfConstructionMaterial) +
                String.format("Ratio of Fastest Route Between Barely Connected and Original Map: %.2f", ratioOfFastestRoutes);
        FileOutput.writeToFile(args[1],content,true,false);
    }

}
