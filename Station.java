package publicTransportRouting;

public class Station implements Comparable<Station> {

    String name;
    int distanceFromStart;
    boolean visited;//applied in nearby query to avoid duplicates in the results. 

    public Station(String name) {
        this.name = name;
        this.distanceFromStart = Integer.MAX_VALUE;
    }

    @Override
    public int compareTo(Station other) {
        return this.distanceFromStart - other.distanceFromStart;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
