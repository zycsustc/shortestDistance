package com.SEA.practice.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DijkstraAlgorithmTest {

    private Graph graph;
    private DijkstraAlgorithm dijkstraAlgorithm;

    @BeforeEach
    void setUp() {
        ArrayList<Vertex> vertexes = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();

        Vertex vertexA = new Vertex("A");
        Vertex vertexB = new Vertex("B");
        Vertex vertexC = new Vertex("C");
        Vertex vertexD = new Vertex("D");
        Vertex vertexE = new Vertex("E");

        vertexes.add(vertexA);
        vertexes.add(vertexB);
        vertexes.add(vertexC);
        vertexes.add(vertexD);
        vertexes.add(vertexE);

        edges.add(new Edge("Edge_0", vertexA, vertexB, 5));
        edges.add(new Edge("Edge_1", vertexB, vertexC, 4));
        edges.add(new Edge("Edge_2", vertexC, vertexD, 8));
        edges.add(new Edge("Edge_3", vertexD, vertexC, 8));
        edges.add(new Edge("Edge_4", vertexD, vertexE, 6));
        edges.add(new Edge("Edge_5", vertexA, vertexD, 5));
        edges.add(new Edge("Edge_6", vertexC, vertexE, 2));
        edges.add(new Edge("Edge_7", vertexE, vertexB, 3));
        edges.add(new Edge("Edge_8", vertexA, vertexE, 7));

        graph = new Graph(vertexes, edges);
        dijkstraAlgorithm = new DijkstraAlgorithm(graph);
    }

    @Test
    void shouldReturnPathDistanceExactlyAtoBtoC() {
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("B");
        stops.add("C");
        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(stops);

        dijkstra.execute(path.getFirst());

        assertEquals(dijkstra.getExactPath(path), "ROUTE FIND");
        assertEquals(dijkstra.getDistanceByPath(path), 9);
    }

    @Test
    void shouldReturnPathDistanceExactlyAtoD() {
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("D");
        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(stops);

        dijkstra.execute(path.getFirst());

        assertEquals(dijkstra.getExactPath(path), "ROUTE FIND");
        assertEquals(dijkstra.getDistanceByPath(path), 5);
    }

    @Test
    void shouldReturnPathDistanceExactlyAtoDtoC() {
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("D");
        stops.add("C");
        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(stops);

        dijkstra.execute(path.getFirst());

        assertEquals(dijkstra.getExactPath(path), "ROUTE FIND");
        assertEquals(dijkstra.getDistanceByPath(path), 13);
    }

    @Test
    void shouldReturnPathDistanceExactlyAtoEtoBtoCtoD() {
        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("E");
        stops.add("B");
        stops.add("C");
        stops.add("D");
        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(stops);

        dijkstraAlgorithm.execute(path.getFirst());

        assertEquals(dijkstraAlgorithm.getExactPath(path), "ROUTE FIND");
        assertEquals(dijkstraAlgorithm.getDistanceByPath(path), 22);
    }

    @Test
    void shouldReturnNoSuchRouteMessageExactlyAtoEtoD() {
        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("E");
        stops.add("D");
        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(stops);

        dijkstraAlgorithm.execute(path.getFirst());
        assertEquals(dijkstraAlgorithm.getExactPath(path), "NO SUCH ROUTE");
    }

    @Test
    void shouldReturnNumberOfRoutesFromCtoCWithMaximumThreeStops(){
        Vertex source = graph.getVertexInGraphById("C");

        ArrayList<String> paths = dijkstraAlgorithm.getPathsByConditionOnStopsSameStartAndEnd(source, dijkstraAlgorithm,
                "Max", 3);

        assertEquals(2, paths.size());
    }

    @Test
    void shouldReturnShortestDistanceFromAtoC() {
        Vertex source = graph.getVertexInGraphById("A");
        Vertex target = graph.getVertexInGraphById("C");

        dijkstraAlgorithm.execute(source);

        LinkedList<Vertex> path = dijkstraAlgorithm.getShortestPathDifferentStartAndEnd(target);
        int distance = dijkstraAlgorithm.getDistanceByPath(path);

        assertEquals(distance, 9);
    }

    @Test
    void shouldReturnShortestDistanceFromBtoB() {
        Vertex source = graph.getVertexInGraphById("B");

        LinkedList<Vertex> path = dijkstraAlgorithm.getShortestPathSameStartAndEnd(source, dijkstraAlgorithm);
        int distance = dijkstraAlgorithm.getDistanceByPath(path);

        assertEquals(9, distance);
    }

    @Test
    void shouldFindAllPathsFromAtoCWithFourStops() {
        Vertex source = graph.getVertexInGraphById("A");
        Vertex target = graph.getVertexInGraphById("C");

        dijkstraAlgorithm.getAllPathsWithExactStops(source, target, 4);

        assertEquals(3, dijkstraAlgorithm.resultPaths.size());
    }

    @Test
    void shouldFindAllPathsFromCtoCWithMaxDistance30() {
        Vertex source = graph.getVertexInGraphById("C");
        Vertex target = graph.getVertexInGraphById("C");

        dijkstraAlgorithm.getAllPathsWithMaxDistance(source, target, 30);

        assertEquals(7, dijkstraAlgorithm.resultPaths.size());
    }
}

