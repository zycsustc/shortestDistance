package com.SEA.practice;

import com.SEA.practice.common.Util;
import com.SEA.practice.modules.DijkstraAlgorithm;
import com.SEA.practice.modules.Edge;
import com.SEA.practice.modules.Graph;
import com.SEA.practice.modules.Vertex;
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
	private final Util util = new Util();

	private void addLane(String laneId, String sourceLoc, String destLoc, int cost) {
		Edge lane = new Edge(laneId, util.getVertexById(sourceLoc, nodes),
				util.getVertexById(destLoc, nodes), cost );
		edges.add(lane);
	}

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
		LinkedList<Vertex> path = util.getLinkedVertexByStops(stops, nodes);

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
		LinkedList<Vertex> path = util.getLinkedVertexByStops(stops, nodes);

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
		LinkedList<Vertex> path = util.getLinkedVertexByStops(stops, nodes);

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
		LinkedList<Vertex> path = util.getLinkedVertexByStops(stops, nodes);

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
		LinkedList<Vertex> path = util.getLinkedVertexByStops(stops, nodes);

		dijkstra.execute(path.getFirst());
		assertEquals(dijkstra.getExactPath(path), "NO SUCH ROUTE");
	}

	@Test
	void shouldReturnNumberOfRoutesFromCtoCWithMaximumThreeStops(){
		Graph graph = new Graph(nodes, edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

		Vertex source = util.getVertexById("C", nodes);

		ArrayList<String> paths = dijkstra.getPathsByConditionOnStopsSameStartAndEnd(source, dijkstra,
				"Max", 3);

		assertEquals(2, paths.size());
	}

	@Test
	void shouldReturnShortestDistanceFromAtoC() {
		Graph graph = new Graph(nodes, edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

		Vertex source = util.getVertexById("A", nodes);
		Vertex target = util.getVertexById("C", nodes);

		dijkstra.execute(source);

		LinkedList<Vertex> path = dijkstra.getShortestPathDifferentStartAndEnd(target);
		int distance = dijkstra.getDistanceByPath(path);

		assertEquals(distance, 9);
	}

	@Test
	void shouldReturnShortestDistanceFromBtoB() {
		Graph graph = new Graph(nodes, edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

		Vertex source = util.getVertexById("B", nodes);

		LinkedList<Vertex> path = dijkstra.getShortestPathSameStartAndEnd(source, dijkstra);
		int distance = dijkstra.getDistanceByPath(path);

		assertEquals(9, distance);
	}

	@Test
	void shouldFindAllPathsFromAtoCWithFourStops() {
		Graph graph = new Graph(nodes, edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

		Vertex source = util.getVertexById("A", nodes);
		Vertex target = util.getVertexById("C", nodes);

		dijkstra.getAllPathsWithExactStops(source, target, 4);

		assertEquals(3, dijkstra.resultPaths.size());
	}

	@Test
	void shouldFindAllPathsFromCtoCWithMaxDistance30() {
		Graph graph = new Graph(nodes, edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

		Vertex source = util.getVertexById("C", nodes);
		Vertex target = util.getVertexById("C", nodes);

		dijkstra.getAllPathsWithMaxDistance(source, target, 30);

		assertEquals(7, dijkstra.resultSet.size());
	}

	@Test
	void shouldTest() {
		Graph graph = new Graph(nodes, edges);
		DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(graph);

		Vertex source = util.getVertexById("C", nodes);
		Vertex target = util.getVertexById("C", nodes);

		dijkstra.execute(source);
		dijkstra.getAllPathsWithExactStops(source, target, 4);
		System.out.println(dijkstra.resultPaths);
	}
}
