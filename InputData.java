package publicTransportRouting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputData {

    Map<String, List<Pair<Station, Integer>>> adjacencyList;
    Map<String, Station> quickAccess_staionByName;
    Map<Station, Station> traceRoute;

    public InputData(Map<String, List<Pair<Station, Integer>>> adjacencyList, Map<Station, Station> traceRoute, Map<String, Station> quickAccess_staionByName) {
        this.adjacencyList = adjacencyList;
        this.quickAccess_staionByName = quickAccess_staionByName;
        this.traceRoute = traceRoute;
    }

    public void processInput(boolean automaticInput, List<String> data) throws IOException {
        if (automaticInput) {
            processAutomaticInput(data);
        } else {
            processManualInput();
        }
    }

    public void processManualInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int numberOfEdges = Integer.parseInt(reader.readLine());

        while (numberOfEdges-- > 0) {
            reader = new BufferedReader(new InputStreamReader(System.in));
            String[] input = reader.readLine().replaceAll("-|>|:", " ").trim().split("\\s+");

            quickAccess_staionByName.putIfAbsent(input[0], new Station(input[0]));
            quickAccess_staionByName.putIfAbsent(input[1], new Station(input[1]));
            adjacencyList.putIfAbsent(input[0], new ArrayList<>());
            adjacencyList.get(input[0]).add(new Pair<>(quickAccess_staionByName.get(input[1]), Integer.parseInt(input[2])));
        }
        reader.close();
    }

    public void processAutomaticInput(List<String> data) {

        for (String s : data) {
            String[] input = s.replaceAll("-|>|:", " ").trim().split("\\s+");
            quickAccess_staionByName.putIfAbsent(input[0], new Station(input[0]));
            quickAccess_staionByName.putIfAbsent(input[1], new Station(input[1]));
            adjacencyList.putIfAbsent(input[0], new ArrayList<>());
            adjacencyList.get(input[0]).add(new Pair<>(quickAccess_staionByName.get(input[1]), Integer.parseInt(input[2])));
        }
    }
}
