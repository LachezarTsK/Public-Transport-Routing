package publicTransportRouting;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class StartApplication {

    /*
    If the program is started directly from this class, and and input is manual, 
    the variable 'automaticInput' has to be set to 'false'.
    
    In the JUnit Test File, 'automaticInput' is set to 'true'.    
    
    Manual Input is in the form "SOURCE -> DESTINATION: INTEGER".
    Automatic Input is in the form of List<String> where each list element in the form "SOURCE -> DESTINATION: INTEGER".
    
    The program extracts from the string the necessary data.  
     */
    public static void main(String[] args) throws IOException {

        StartApplication routeSearch = new StartApplication();

        boolean automaticInput = false;
        routeSearch.inputData.processInput(automaticInput, null);

        System.out.println(routeSearch.routeQuery.route("A -> B"));
        System.out.println(routeSearch.routeQuery.route("A -> E"));

        System.out.println(routeSearch.nearbyQuery.nearby("A, 130"));
        System.out.println(routeSearch.nearbyQuery.nearby("C, 240"));
    }

    Map<String, List<Pair<Station, Integer>>> adjacencyList;
    Map<String, Station> quickAccess_staionByName;
    Map<Station, Station> traceRoute;
    public RouteQuery routeQuery;
    NearbyQuery nearbyQuery;
    InputData inputData;

    public StartApplication() {
        adjacencyList = new HashMap<>();
        quickAccess_staionByName = new HashMap<>();
        inputData = new InputData(adjacencyList, traceRoute, quickAccess_staionByName);
        routeQuery = new RouteQuery(adjacencyList, traceRoute, quickAccess_staionByName);
        nearbyQuery = new NearbyQuery(adjacencyList, quickAccess_staionByName);
    }
}
