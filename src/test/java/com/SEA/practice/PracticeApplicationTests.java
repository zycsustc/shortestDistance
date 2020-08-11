package com.SEA.practice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PracticeApplicationTests {

	private List<Vertex> nodes;
	private List<Edge> edges;

	ShortestPathService shortestPathService = new ShortestPathService();

	private void addLane(String laneId, String sourceLoc, String destLoc, int cost) {
		Edge lane = new Edge(laneId, shortestPathService.getVertexById(sourceLoc, nodes),
				shortestPathService.getVertexById(destLoc, nodes), cost );
		edges.add(lane);
	}

	private LinkedList<Vertex> getLinkedVertexByStops(ArrayList<String> stops){
		LinkedList<Vertex> path = new LinkedList<>();
		for(String stop: stops){
			path.add(shortestPathService.getVertexById(stop, nodes));
		}return path;
	}

	@BeforeEach
	void setUp() {
		nodes = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();

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

		addLane("Edge_0", "A", "B", 5);
		addLane("Edge_1", "B", "C", 4);
		addLane("Edge_2", "C", "D", 8);
		addLane("Edge_3", "D", "C", 8);
		addLane("Edge_4", "D", "E", 6);
		addLane("Edge_5", "A", "D", 5);
		addLane("Edge_6", "C", "E", 2);
		addLane("Edge_7", "E", "B", 3);
		addLane("Edge_8", "A", "E", 7);
	}

	@Test
	void shouldReturnPathDistanceExactlyAtoBtoC() {
		Graph graph = new Graph(nodes, edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

		ArrayList<String> stops = new ArrayList<>();
		stops.add("A");
		stops.add("B");
		stops.add("C");
		LinkedList<Vertex> path = getLinkedVertexByStops(stops);

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
		LinkedList<Vertex> path = getLinkedVertexByStops(stops);

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
		LinkedList<Vertex> path = getLinkedVertexByStops(stops);

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
		LinkedList<Vertex> path = getLinkedVertexByStops(stops);

		dijkstra.execute(path.getFirst());

		assertEquals(dijkstra.getExactPath(path), "ROUTE FIND");
		assertEquals(dijkstra.getDistanceByPath(path), 22);
	}

    @Test
	void shouldReturnRouteNotFoundMessageExactlyAtoEtoD() {
		Graph graph = new Graph(nodes, edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

		ArrayList<String> stops = new ArrayList<>();
		stops.add("A");
		stops.add("E");
		stops.add("D");
		LinkedList<Vertex> path = getLinkedVertexByStops(stops);

		dijkstra.execute(path.getFirst());
		assertEquals(dijkstra.getExactPath(path), "NO SUCH ROUTE");
	}
}
