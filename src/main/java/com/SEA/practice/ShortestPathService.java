package com.SEA.practice;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ShortestPathService {

    private List<Vertex> nodes;
    private List<Edge> edges;
    private Util util = new Util();

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

        System.out.println("Test Data ready!");
    }

    public LinkedList<Vertex> getShortestPath(Vertex source, Vertex target, DijkstraAlgorithm dijkstraAlgorithm){
        dijkstraAlgorithm.execute(source);
        if(source.equals(target)){
            return dijkstraAlgorithm.getShortestPathSameStartAndEnd(source, dijkstraAlgorithm);
        } else {
            return dijkstraAlgorithm.getShortestPathDifferentStartAndEnd(target);
        }
    }

    public ArrayList<LinkedList<Vertex>> getPathsWithConditionOnStops(Vertex source, Vertex target, DijkstraAlgorithm dijkstraAlgorithm,
                                                                        String condition, int number){
        dijkstraAlgorithm.execute(source);
        if(source.equals(target)){
            return dijkstraAlgorithm.getPathsByConditionOnStopsSameStartAndEnd(source, dijkstraAlgorithm, condition, number);
        } else {
            return null;
        }
    }

}
