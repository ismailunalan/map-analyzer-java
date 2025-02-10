/**
 * Represents a route between two points using a specific road.
 */
public class Route {
    private int distance;
    private Road road;
    private String fromWhere;
    private String toWhere;

    /**
     * Constructs a new Route instance.
     *
     * @param distance  The distance of the route.
     * @param road      The road associated with the route.
     * @param fromWhere The starting point of the route.
     * @param toWhere   The ending point of the route.
     */
    public Route(int distance, Road road, String fromWhere, String toWhere) {
        this.distance = distance;
        this.road = road;
        this.fromWhere = fromWhere;
        this.toWhere = toWhere;
    }

    /**
     * Gets the distance of the route.
     *
     * @return The distance of the route.
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Gets the road associated with the route.
     *
     * @return The road associated with the route.
     */
    public Road getRoad() {
        return road;
    }

    /**
     * Gets the starting point of the route.
     *
     * @return The starting point of the route.
     */
    public String getFromWhere() {
        return fromWhere;
    }

    /**
     * Gets the ending point of the route.
     *
     * @return The ending point of the route.
     */
    public String getToWhere() {
        return toWhere;
    }

}
