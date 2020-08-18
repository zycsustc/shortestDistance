package com.SEA.practice;

import com.SEA.practice.modules.DijkstraAlgorithm;
import com.SEA.practice.modules.Edge;
import com.SEA.practice.modules.Graph;
import com.SEA.practice.modules.Vertex;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ShortestPathService {

    private final Graph graph;
    private final DijkstraAlgorithm dijkstraAlgorithm;

    public ShortestPathService() {
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

        System.out.println("Test Data ready!");
    }

    public LinkedList<Vertex> getShortestPath(String sourceId, String targetId) {
        Vertex sourceVertex = graph.getVertexInGraphById(sourceId);
        Vertex targetVertex = graph.getVertexInGraphById(targetId);
        dijkstraAlgorithm.execute(sourceVertex);

        if (sourceId.equals(targetId)) {
            return dijkstraAlgorithm.getShortestPathSameStartAndEnd(sourceVertex, dijkstraAlgorithm);
        } else {
            return dijkstraAlgorithm.getShortestPathDifferentStartAndEnd(targetVertex);
        }
    }

    public String getDistanceOfExactPath(ArrayList<String> stops) {
        LinkedList<Vertex> path = graph.getLinkedVertexByStopsInGraph(stops);
        dijkstraAlgorithm.execute(path.getFirst());

        String result = dijkstraAlgorithm.getExactPath(path);
        return result.equals("ROUTE FIND") ? String.valueOf(dijkstraAlgorithm.getDistanceByPath(path)) : result;
    }

    public ArrayList<String> getPathsWithConditionOnStops(
            String sourceId, String targetId, String condition, int number) {
        Vertex sourceNode = graph.getVertexInGraphById(sourceId);
        Vertex targetNode = graph.getVertexInGraphById(targetId);

        dijkstraAlgorithm.execute(sourceNode);
        dijkstraAlgorithm.resultPaths = new ArrayList<>();

        if (sourceId.equals(targetId)) {
            return dijkstraAlgorithm.getPathsByConditionOnStopsSameStartAndEnd(sourceNode, dijkstraAlgorithm, condition, number);
        } else {
            // need to implement paths with maximum number of stops when source and target are different
            if (condition.equals("Equal")) {
                dijkstraAlgorithm.getAllPathsWithExactStops(sourceNode, targetNode, number);
                return dijkstraAlgorithm.resultPaths;
            } else {
                ArrayList<String> result = new ArrayList<>();
                result.add("Didn't implement max stops with different start and end city yet, sorry!");
                return result;
            }
        }
    }

    public ArrayList<String> getPathsWithMaxDistance(String sourceId, String targetId, int maxDistance) {
        Vertex sourceNode = graph.getVertexInGraphById(sourceId);
        Vertex targetNode = graph.getVertexInGraphById(targetId);

        dijkstraAlgorithm.resultPaths = new ArrayList<>();
        dijkstraAlgorithm.getAllPathsWithMaxDistance(sourceNode, targetNode, maxDistance);
        return dijkstraAlgorithm.resultPaths;
    }

}
