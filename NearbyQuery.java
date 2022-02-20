package publicTransportRouting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class NearbyQuery {

    Map<String, List<Pair<Station, Integer>>> adjacencyList;
    Map<String, Station> quickAccess_staionByName;

    public NearbyQuery(Map<String, List<Pair<Station, Integer>>> adjacencyList, Map<String, Station> quickAccess_staionByName) {
        this.adjacencyList = adjacencyList;
        this.quickAccess_staionByName = quickAccess_staionByName;
    }

    public String nearby(String query) {
        String[] data = query.replaceAll(",", " ").trim().split("\\s+");
        Station source = quickAccess_staionByName.get(data[0]);
        int maximumTravelTime = Integer.parseInt(data[1]);

        if (source == null || maximumTravelTime < 0) {
            return "Invalid Query!";
        }

        resetDistanceFromStart();
        resetVisited();

        source.distanceFromStart = 0;
        PriorityQueue<Station> queue = new PriorityQueue<>();
        queue.add(source);

        List<Station> listNearbyStations = new ArrayList<>();

        while (!queue.isEmpty()) {
            Station current = queue.poll();
            if (!adjacencyList.containsKey(current.name)) {
                continue;
            }

            List<Pair<Station, Integer>> neighbours = adjacencyList.get(current.name);
            for (Pair<Station, Integer> pair : neighbours) {

                int newDistanceFromStart = current.distanceFromStart + pair.value;
                if (newDistanceFromStart <= maximumTravelTime && newDistanceFromStart < pair.key.distanceFromStart) {
                    queue.add(pair.key);
                    pair.key.distanceFromStart = newDistanceFromStart;

                    if (pair.key.visited == false) {
                        listNearbyStations.add(pair.key);
                        pair.key.visited = true;
                    }
                }
            }
        }

        Collections.sort(listNearbyStations);
        return displayNearbyQuery(listNearbyStations, maximumTravelTime);
    }

    public String displayNearbyQuery(List<Station> listNearbyStations, int maximumTravelTime) {
        if (listNearbyStations.isEmpty()) {
            return "There are no stations in range " + maximumTravelTime + "!";
        }

        StringBuilder sb = new StringBuilder();
        int size = listNearbyStations.size();

        for (int i = 0; i < size; i++) {
            sb.append(listNearbyStations.get(i).name).append(": ").append(listNearbyStations.get(i).distanceFromStart);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public void resetDistanceFromStart() {
        for (String name : quickAccess_staionByName.keySet()) {
            quickAccess_staionByName.get(name).distanceFromStart = Integer.MAX_VALUE;
        }
    }

    public void resetVisited() {
        for (String name : quickAccess_staionByName.keySet()) {
            quickAccess_staionByName.get(name).visited = false;
        }
    }
}
