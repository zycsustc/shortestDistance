package com.SEA.practice;

import com.SEA.practice.common.EdgeUtil;
import com.SEA.practice.common.GraphUtil;
import com.SEA.practice.modules.DijkstraAlgorithm;
import com.SEA.practice.modules.Edge;
import com.SEA.practice.modules.Graph;
import com.SEA.practice.modules.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DijkstraAlgorithmTest {

    private ArrayList<Vertex> nodes;
    private ArrayList<Edge> edges;
    private Graph graph;
    private final GraphUtil graphUtil = new GraphUtil();
    private final EdgeUtil edgeUtil = new EdgeUtil();

    @BeforeEach
    void setUp() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();

        ArrayList<String> vertexs = new ArrayList<>();
        vertexs.add("A");
        vertexs.add("B");
        vertexs.add("C");
        vertexs.add("D");
        vertexs.add("E");

        for (String id : vertexs) {
            Vertex location = new Vertex(id);
            nodes.add(location);
        }

        edges.add(edgeUtil.generateEdge("Edge_0", "A", "B", 5, nodes));
        edges.add(edgeUtil.generateEdge("Edge_1", "B", "C", 4, nodes));
        edges.add(edgeUtil.generateEdge("Edge_2", "C", "D", 8, nodes));
        edges.add(edgeUtil.generateEdge("Edge_3", "D", "C", 8, nodes));
        edges.add(edgeUtil.generateEdge("Edge_4", "D", "E", 6, nodes));
        edges.add(edgeUtil.generateEdge("Edge_5", "A", "D", 5, nodes));
        edges.add(edgeUtil.generateEdge("Edge_6", "C", "E", 2, nodes));
        edges.add(edgeUtil.generateEdge("Edge_7", "E", "B", 3, nodes));
        edges.add(edgeUtil.generateEdge("Edge_8", "A", "E", 7, nodes));

        graph = new Graph(nodes, edges);
    }

    @Test
    void shouldReturnPathDistanceExactlyAtoBtoC() {
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("B");
        stops.add("C");
        LinkedList<Vertex> path = graphUtil.getLinkedVertexByStops(stops, nodes);

        dijkstra.execute(path.getFirst());

        assertEquals(dijkstra.getExactPath(path), "ROUTE FIND");
        assertEquals(dijkstra.getDistanceByPath(path), 9);
    }

    @Test
    void shouldReturnPathDistanceExactlyAtoD() {
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("D");
        LinkedList<Vertex> path = graphUtil.getLinkedVertexByStops(stops, nodes);

        dijkstra.execute(path.getFirst());

        assertEquals(dijkstra.getExactPath(path), "ROUTE FIND");
        assertEquals(dijkstra.getDistanceByPath(path), 5);
    }

    @Test
    void shouldReturnPathDistanceExactlyAtoDtoC() {
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("D");
        stops.add("C");
        LinkedList<Vertex> path = graphUtil.getLinkedVertexByStops(stops, nodes);

        dijkstra.execute(path.getFirst());

        assertEquals(dijkstra.getExactPath(path), "ROUTE FIND");
        assertEquals(dijkstra.getDistanceByPath(path), 13);
    }

    @Test
    void shouldReturnPathDistanceExactlyAtoEtoBtoCtoD() {
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("E");
        stops.add("B");
        stops.add("C");
        stops.add("D");
        LinkedList<Vertex> path = graphUtil.getLinkedVertexByStops(stops, nodes);

        dijkstra.execute(path.getFirst());

        assertEquals(dijkstra.getExactPath(path), "ROUTE FIND");
        assertEquals(dijkstra.getDistanceByPath(path), 22);
    }

    @Test
    void shouldReturnNoSuchRouteMessageExactlyAtoEtoD() {
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("E");
        stops.add("D");
        LinkedList<Vertex> path = graphUtil.getLinkedVertexByStops(stops, nodes);

        dijkstra.execute(path.getFirst());
        assertEquals(dijkstra.getExactPath(path), "NO SUCH ROUTE");
    }

    @Test
    void shouldReturnNumberOfRoutesFromCtoCWithMaximumThreeStops(){
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        Vertex source = graph.getVertexById("C");

        ArrayList<String> paths = dijkstra.getPathsByConditionOnStopsSameStartAndEnd(source, dijkstra,
                "Max", 3);

        assertEquals(2, paths.size());
    }

    @Test
    void shouldReturnShortestDistanceFromAtoC() {
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        Vertex source = graph.getVertexById("A");
        Vertex target = graph.getVertexById("C");

        dijkstra.execute(source);

        LinkedList<Vertex> path = dijkstra.getShortestPathDifferentStartAndEnd(target);
        int distance = dijkstra.getDistanceByPath(path);

        assertEquals(distance, 9);
    }

    @Test
    void shouldReturnShortestDistanceFromBtoB() {
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        Vertex source = graph.getVertexById("B");

        LinkedList<Vertex> path = dijkstra.getShortestPathSameStartAndEnd(source, dijkstra);
        int distance = dijkstra.getDistanceByPath(path);

        assertEquals(9, distance);
    }

    @Test
    void shouldFindAllPathsFromAtoCWithFourStops() {
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        Vertex source = graph.getVertexById("A");
        Vertex target = graph.getVertexById("C");

        dijkstra.getAllPathsWithExactStops(source, target, 4);

        assertEquals(3, dijkstra.resultPaths.size());
    }

    @Test
    void shouldFindAllPathsFromCtoCWithMaxDistance30() {
        Graph graph = new Graph(nodes, edges);
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

        Vertex source = graph.getVertexById("C");
        Vertex target = graph.getVertexById("C");

        dijkstra.getAllPathsWithMaxDistance(source, target, 30);

        assertEquals(7, dijkstra.resultPaths.size());
    }
}

