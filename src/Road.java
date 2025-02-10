/**
 * Represents a road with a start point, end point, length, and an identifier.
 */
public class Road {
    private String startPoint;
    private String endPoint;
    private int length;
    private int ID;

    /**
     * Constructs a new Road instance.
     *
     * @param startPoint The starting point of the road.
     * @param endPoint   The ending point of the road.
     * @param length     The length of the road.
     * @param ID         The unique identifier for the road.
     */
    public Road(String startPoint, String endPoint, int length, int ID) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.length = length;
        this.ID = ID;
    }

    /**
     * Gets the starting point of the road.
     *
     * @return The starting point of the road.
     */
    public String getStartPoint() {
        return startPoint;
    }

    /**
     * Gets the ending point of the road.
     *
     * @return The ending point of the road.
     */
    public String getEndPoint() {
        return endPoint;
    }

    /**
     * Gets the length of the road.
     *
     * @return The length of the road.
     */
    public int getLength() {
        return length;
    }

    /**
     * Gets the unique identifier of the road.
     *
     * @return The unique identifier of the road.
     */
    public int getID() {
        return ID;
    }

    /**
     * Returns a string representation of the road, formatted as start point, end point, length, and ID.
     *
     * @return A string representation of the road.
     */
    @Override
    public String toString() {
        return startPoint + "\t" + endPoint + "\t" + length + "\t" + ID;
    }
}
