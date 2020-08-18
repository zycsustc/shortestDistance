package com.SEA.practice;

import com.SEA.practice.common.EdgeUtil;
import com.SEA.practice.common.GraphUtil;
import com.SEA.practice.modules.DijkstraAlgorithm;
import com.SEA.practice.modules.Edge;
import com.SEA.practice.modules.Graph;
import com.SEA.practice.modules.Vertex;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ShortestPathService {

    private final ArrayList<Vertex> nodes;
    private final ArrayList<Edge> edges;
    private final DijkstraAlgorithm dijkstraAlgorithm;
    private final GraphUtil graphUtil = new GraphUtil();
    private final EdgeUtil edgeUtil = new EdgeUtil();

    public ShortestPathService() {
        Graph graph;
        {
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
        dijkstraAlgorithm = new DijkstraAlgorithm(graph);

        System.out.println("Test Data ready!");
    }

    public LinkedList<Vertex> getShortestPath(String source, String target){
        Vertex sourceNode = graphUtil.getVertexById(source, nodes);
        Vertex targetNode = graphUtil.getVertexById(target, nodes);
        dijkstraAlgorithm.execute(sourceNode);

        if(source.equals(target)){
            return dijkstraAlgorithm.getShortestPathSameStartAndEnd(sourceNode, dijkstraAlgorithm);
        } else {
            return dijkstraAlgorithm.getShortestPathDifferentStartAndEnd(targetNode);
        }
    }

    public String getDistanceOfExactPath(List<String> stops){
        LinkedList<Vertex> path = graphUtil.getLinkedVertexByStops(stops, nodes);
        dijkstraAlgorithm.execute(path.getFirst());
        String result = dijkstraAlgorithm.getExactPath(path);
        return result.equals("ROUTE FIND") ? String.valueOf(dijkstraAlgorithm.getDistanceByPath(path)) : result;
    }

    public ArrayList<String> getPathsWithConditionOnStops(
            String source, String target, String condition, int number) {
        Vertex sourceNode = graphUtil.getVertexById(source, nodes);
        Vertex targetNode = graphUtil.getVertexById(target, nodes);

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
        Vertex sourceNode = graphUtil.getVertexById(source, nodes);
        Vertex targetNode = graphUtil.getVertexById(target, nodes);

        dijkstraAlgorithm.resultPaths = new ArrayList<>();
        dijkstraAlgorithm.getAllPathsWithMaxDistance(sourceNode, targetNode, maxDistance);
        return dijkstraAlgorithm.resultPaths;
    }

}
