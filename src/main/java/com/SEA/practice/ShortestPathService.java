package com.SEA.practice;

import com.SEA.practice.common.Util;
import com.SEA.practice.modules.DijkstraAlgorithm;
import com.SEA.practice.modules.Edge;
import com.SEA.practice.modules.Graph;
import com.SEA.practice.modules.Vertex;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ShortestPathService {

    private final List<Vertex> nodes;
    private final List<Edge> edges;
    private Graph graph;
    private final DijkstraAlgorithm dijkstraAlgorithm;
    private final Util util = new Util();

    private void addLane(String laneId, String sourceLoc, String destLoc, int cost) {
        Edge lane = new Edge(laneId, util.getVertexById(sourceLoc, nodes), util.getVertexById(destLoc, nodes), cost );
        edges.add(lane);
    }

    public ShortestPathService() {
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

        graph = new Graph(nodes, edges);
        dijkstraAlgorithm = new DijkstraAlgorithm(graph);

        System.out.println("Test Data ready!");
    }

    public LinkedList<Vertex> getShortestPath(String source, String target){
        Vertex sourceNode = util.getVertexById(source, nodes);
        Vertex targetNode = util.getVertexById(target, nodes);
        dijkstraAlgorithm.execute(sourceNode);

        if(source.equals(target)){
            return dijkstraAlgorithm.getShortestPathSameStartAndEnd(sourceNode, dijkstraAlgorithm);
        } else {
            return dijkstraAlgorithm.getShortestPathDifferentStartAndEnd(targetNode);
        }
    }

    public String getDistanceOfExactPath(List<String> stops){
        LinkedList<Vertex> path = util.getLinkedVertexByStops(stops, nodes);
        dijkstraAlgorithm.execute(path.getFirst());
        String result = dijkstraAlgorithm.getExactPath(path);
        return result.equals("ROUTE FIND") ? String.valueOf(dijkstraAlgorithm.getDistanceByPath(path)) : result;
    }

    public ArrayList<String> getPathsWithConditionOnStops(
            String source, String target, String condition, int number) {
        Vertex sourceNode = util.getVertexById(source, nodes);
        Vertex targetNode = util.getVertexById(target, nodes);

        dijkstraAlgorithm.execute(sourceNode);
        dijkstraAlgorithm.resultPaths = new ArrayList<>();

        if(source.equals(target)){
            return dijkstraAlgorithm.getPathsByConditionOnStopsSameStartAndEnd(sourceNode, dijkstraAlgorithm, condition, number);
        } else {
            // need to implement paths with maximum number of stops when source and target are different
            if(condition.equals("Equal")){
                dijkstraAlgorithm.getAllPathsWithExactStops(sourceNode, targetNode, number);
                return dijkstraAlgorithm.resultPaths;
            } else {
                ArrayList<String> result = new ArrayList<>();
                result.add("Didn't implement max stops with different start and end city yet, sorry!");
                return result;
            }
        }
    }

    public ArrayList<String> getPathsWithMaxDistance(String source, String target, int maxDistance) {
        Vertex sourceNode = util.getVertexById(source, nodes);
        Vertex targetNode = util.getVertexById(target, nodes);

        dijkstraAlgorithm.resultPaths = new ArrayList<>();
        dijkstraAlgorithm.getAllPathsWithMaxDistance(sourceNode, targetNode, maxDistance);
        return dijkstraAlgorithm.resultPaths;
    }

}
