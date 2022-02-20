package publicTransportRouting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class JUnitTestStart {

    StartApplication routeSearch;
    List<String> input = new ArrayList<>(List.of(
            "A -> B: 240",
            "A -> C: 70",
            "A -> D: 120",
            "C -> B: 60",
            "D -> E: 480",
            "C -> E: 240",
            "B -> E: 210",
            "E -> A: 300",
            "A -> Z: 800"
    ));

    public JUnitTestStart() throws IOException {
        routeSearch = new StartApplication();
        boolean automaticInput = true;
        routeSearch.inputData.processInput(automaticInput, input);
    }

    @Test
    public void ruoteQuery_01() throws IOException {
        assertEquals("A -> C -> B: 130", routeSearch.routeQuery.route("A -> B"));
    }

    @Test
    public void ruoteQuery_02() throws IOException {
        assertEquals("A -> C -> E: 310", routeSearch.routeQuery.route("A -> E"));
    }

    @Test
    public void ruoteQuery_03() throws IOException {
        assertEquals("C -> C: 0", routeSearch.routeQuery.route("C -> C"));
    }

    @Test
    public void ruoteQuery_04() throws IOException {
        assertEquals("C -> E: 240", routeSearch.routeQuery.route("C -> E"));
    }

    @Test
    public void ruoteQuery_05() throws IOException {
        assertEquals("E -> A -> C -> B: 430", routeSearch.routeQuery.route("E -> B"));
    }

    @Test
    public void ruoteQuery_06() throws IOException {
        assertEquals("Error: No route from Z to A!", routeSearch.routeQuery.route("Z -> A"));
    }

    //X is not in the database.
    @Test
    public void ruoteQuery_07() throws IOException {
        assertEquals("Error: No route from A to X!", routeSearch.routeQuery.route("A -> X"));
    }

    @Test
    public void ruoteQuery_08() throws IOException {
        assertEquals("D -> E -> A -> C -> B: 910", routeSearch.routeQuery.route("D -> B"));
    }

    @Test
    public void nearbyQuery_01() throws IOException {
        assertEquals("C: 70, D: 120, B: 130", routeSearch.nearbyQuery.nearby("A, 130"));
    }

    @Test
    public void nearbyQuery_02() throws IOException {
        assertEquals("C: 70, D: 120, B: 130", routeSearch.nearbyQuery.nearby("A, 250"));
    }

    @Test
    public void nearbyQuery_03() throws IOException {
        assertEquals("C: 70, D: 120, B: 130, E: 310, Z: 800", routeSearch.nearbyQuery.nearby("A, 800"));
    }

    @Test
    public void nearbyQuery_04() throws IOException {
        assertEquals("B: 60", routeSearch.nearbyQuery.nearby("C, 80"));
    }

    @Test
    public void nearbyQuery_05() throws IOException {
        assertEquals("B: 60, E: 240", routeSearch.nearbyQuery.nearby("C, 240"));
    }

    @Test
    public void nearbyQuery_06() throws IOException {
        assertEquals("E: 210, A: 510", routeSearch.nearbyQuery.nearby("B, 510"));
    }

    @Test
    public void nearbyQuery_07() throws IOException {
        assertEquals("There are no stations in range 10!", routeSearch.nearbyQuery.nearby("E, 10"));
    }

    //X is not in the database.
    @Test
    public void nearbyQuery_08() throws IOException {
        assertEquals("Invalid Query!", routeSearch.nearbyQuery.nearby("X, 10"));
    }
}
