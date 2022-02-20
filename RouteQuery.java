package publicTransportRouting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class RouteQuery {

    Map<String, List<Pair<Station, Integer>>> adjacencyList;
    Map<String, Station> quickAccess_staionByName;
    Map<Station, Station> traceRoute;

    public RouteQuery(Map<String, List<Pair<Station, Integer>>> adjacencyList, Map<Station, Station> traceRoute, Map<String, Station> quickAccess_staionByName) {
        this.adjacencyList = adjacencyList;
        this.quickAccess_staionByName = quickAccess_staionByName;
        this.traceRoute = traceRoute;
    }

    public String route(String query) {

        String[] stations = query.replaceAll("-|>", " ").trim().split("\\s+");
        Station source = quickAccess_staionByName.get(stations[0]);
        Station destination = quickAccess_staionByName.get(stations[1]);

        if (source == null || destination == null) {
            return "Error: No route from " + stations[0] + " to " + stations[1] + "!";
        }

        if (source.name.equals(destination.name)) {
            return source.name + " -> " + destination.name + ": 0";
        }

        resetDistanceFromStart();
        traceRoute = new HashMap<>();
        source.distanceFromStart = 0;

        PriorityQueue<Station> queue = new PriorityQueue<>();
        queue.add(source);

        while (!queue.isEmpty()) {
            Station current = queue.poll();
            if (!adjacencyList.containsKey(current.name)) {
                continue;
            }

            List<Pair<Station, Integer>> neighbours = adjacencyList.get(current.name);
            for (Pair<Station, Integer> pair : neighbours) {

                int newDistanceFromStart = current.distanceFromStart + pair.value;
                if (pair.key.distanceFromStart > newDistanceFromStart) {
                    queue.add(pair.key);
                    traceRoute.put(pair.key, current);
                    pair.key.distanceFromStart = newDistanceFromStart;
                }
            }
        }

        List<String> path = traceBackRoute(source, destination);
        return displayRouteQuery(path, source, destination);
    }

    public List<String> traceBackRoute(Station source, Station destination) {
        if (traceRoute.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> path = new ArrayList<>();
        Station current = destination;
        path.add(current.name);

        while (current != source) {
            current = traceRoute.get(current);
            path.add(current.name);
        }
        return path;
    }

    public String displayRouteQuery(List<String> path, Station source, Station destination) {
        if (path.isEmpty()) {
            return "Error: No route from " + source.name + " to " + destination.name + "!";
        }
        StringBuilder sb = new StringBuilder();
        int index = path.size() - 1;
        for (int i = index; i >= 0; i--) {
            sb.append(path.get(i));
            if (i > 0) {
                sb.append(" -> ");
            } else {
                sb.append(": ");
            }
        }
        sb.append(destination.distanceFromStart);
        return sb.toString();
    }

    public void resetDistanceFromStart() {
        for (String name : quickAccess_staionByName.keySet()) {
            quickAccess_staionByName.get(name).distanceFromStart = Integer.MAX_VALUE;
        }
    }
}
