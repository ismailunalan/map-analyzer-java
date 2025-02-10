import java.util.*;
/**
 * The MapAnalyzer class is responsible for executing the program which involves reading road data,
 * creating maps, and calculating fastest routes.
 */
public class MapAnalyzer {
    /**
     * The main method reads road data from a file, calculates the fastest route
     * on the original map, creates a barely connected map,
     * and calculates the fastest route on the barely connected map.
     *
     * @param args Command line arguments where args[0] is the input file and args[1] is the output file.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        // Initialize file output
        FileOutput.writeToFile(args[1],"",false,false);
        // List of roads
        List<Road> roads = new ArrayList<>();
        // Variables to store start and end points
        String start = "";
        String end = "";

        // Read file input
        String[] lines = FileInput.readFile(args[0], true, true);
        // Process the first line to get start and end points
        if (lines.length > 0) {
            String[] startEnd = lines[0].split("\t");
            start = startEnd[0];
            end = startEnd[1];
        }
        // Process the remaining lines to create Road objects and add roads list
        for (int i = 1; i < lines.length; i++) {
            String[] roadInfo = lines[i].split("\t");
            if (roadInfo.length != 4) {
                continue;
            }
            String point1 = roadInfo[0];
            String point2 = roadInfo[1];
            int length = Integer.parseInt(roadInfo[2]);
            int roadId = Integer.parseInt(roadInfo[3]);
            roads.add(new Road(point1, point2, length, roadId));
        }

        // Calculate the fastest route for the original map
        FastestRouteCalculator fastestRouteCalculator = new FastestRouteCalculator(roads);
        List<Road> fastestRoute = fastestRouteCalculator.findFastestRoute(start, end, args,false);

        // Calculate the barely connected map
        BarelyConnectedMapCreator barelyConnectedMapCreator = new BarelyConnectedMapCreator(roads);
        List<Road> barelyConnectedMap = barelyConnectedMapCreator.createBarelyConnectedMap(args);

        // Calculate the fastest route for the barely connected map
        FastestRouteCalculator fastestBarelyMapRouteCalculator = new FastestRouteCalculator(barelyConnectedMap);
        List<Road> fastestBarelyMapRoute = fastestBarelyMapRouteCalculator.findFastestRoute(start, end, args,true);

        // Print map analysis
        fastestRouteCalculator.mapAnalysis(roads,fastestBarelyMapRoute,barelyConnectedMap,fastestRoute,args);
    }
}
