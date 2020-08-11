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

		LinkedList<Vertex> path = new LinkedList<>();
		path.add(shortestPathService.getVertexById("A", nodes));
		path.add(shortestPathService.getVertexById("B", nodes));
		path.add(shortestPathService.getVertexById("C", nodes));

		dijkstra.execute(path.getFirst());

		assertEquals(dijkstra.getExactPath(path), "ROUTE FIND");
		assertEquals(dijkstra.getDistanceByPath(path), 9);
	}
}
