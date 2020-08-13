package com.SEA.practice;

import java.util.*;

public class DijkstraAlgorithm {

    private final List<Vertex> nodes;
    private final List<Edge> edges;
    private Vertex source;
    private Set<Vertex> settledNodes;
    private Set<Vertex> unSettledNodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Integer> distance;
    private LinkedList<Vertex> visitedList = new LinkedList<>();
    public Set<String> resultSet = new HashSet<String>();
    public ArrayList<LinkedList<Vertex>> resultPaths = new ArrayList<>();
    private final Set<Edge> loopList = new HashSet<Edge>();


    public DijkstraAlgorithm(Graph graph) {
        this.nodes = new ArrayList<Vertex>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
    }

    public void execute(Vertex source) {
        this.source = source;
        settledNodes = new HashSet<Vertex>();
        unSettledNodes = new HashSet<Vertex>();
        distance = new HashMap<Vertex, Integer>();
        predecessors = new HashMap<Vertex, Vertex>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Vertex node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbors(node);
        for (Vertex target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        return Integer.MAX_VALUE;
    }

    public List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<Vertex>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    public List<Vertex> getAdjacentNodes(Vertex node) {
        List<Vertex> adjacentNodes = new ArrayList<>();
        for (Edge edge: edges) {
            if(edge.getSource().equals(node)){
                adjacentNodes.add(edge.getDestination());
            }
        }
        return adjacentNodes;
    }

    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;
        for (Vertex vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(Vertex destination) {
        Integer d = distance.get(destination);
        return Objects.requireNonNullElse(d, Integer.MAX_VALUE);
    }

    public LinkedList<Vertex> getShortestPathDifferentStartAndEnd(Vertex target){
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex step = target;
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }

    public ArrayList<LinkedList<Vertex>> getPathsSameStartAndEnd(Vertex source, DijkstraAlgorithm dijkstraAlgorithm){
        ArrayList<LinkedList<Vertex>> paths = new ArrayList<>();
        List<Vertex> adjacentNodes = getAdjacentNodes(source);
        for(Vertex newStart: adjacentNodes){
            dijkstraAlgorithm.execute(newStart);
            if(dijkstraAlgorithm.getShortestPathDifferentStartAndEnd(source)!=null){
                LinkedList<Vertex> wholePath = dijkstraAlgorithm.getShortestPathDifferentStartAndEnd(source);
                wholePath.add(0, source);
                paths.add(wholePath);
            }
        }
        return paths;
    }

    public LinkedList<Vertex> getShortestPathSameStartAndEnd(Vertex source, DijkstraAlgorithm dijkstraAlgorithm){
        int minDistance = Integer.MAX_VALUE;
        LinkedList<Vertex> shortestPath = new LinkedList<>();
        ArrayList<LinkedList<Vertex>> paths = getPathsSameStartAndEnd(source, dijkstraAlgorithm);
        if(!(paths.size()>0)){
            return null;
        }
        for(LinkedList<Vertex> path: paths){
                int distance = getDistanceByPath(path);
                minDistance = Math.min(distance, minDistance);
                shortestPath = minDistance==distance ? path : shortestPath;
        }
        return shortestPath;
    }

    public ArrayList<LinkedList<Vertex>> getPathsByConditionOnStopsSameStartAndEnd(
            Vertex source, DijkstraAlgorithm dijkstraAlgorithm, String Condition, int number) {
        ArrayList<LinkedList<Vertex>> paths = getPathsSameStartAndEnd(source, dijkstraAlgorithm);
        ArrayList<LinkedList<Vertex>> result = new ArrayList<>();
        for(LinkedList<Vertex> path: paths){
            switch (Condition){
                case "Equal":
                    if(path.size()==number){
                        result.add(path);
                    }
                case "Max":
                    if(path.size()<=number+1){
                        result.add(path);
                    }
            }
        }
        return result;
    }

    public String getExactPath(LinkedList<Vertex> exactPath){
        for(Vertex node: exactPath){
            if(node.equals(exactPath.getLast())){
                break;
            } else if(getDistance(node, exactPath.get(exactPath.indexOf(node) + 1)) == Integer.MAX_VALUE){
                return "NO SUCH ROUTE";
            }
        }
        return "ROUTE FIND";
    }

    public int getDistanceByPath(LinkedList<Vertex> path) {
        int distance = 0;
        int nodeIndex = 0;
        Vertex nextNode;
        if(path.size()<2){
            return distance;
        }
        for (Vertex node: path) {
            nextNode = path.get(nodeIndex+1);
            distance += getDistance(node, nextNode);
            nodeIndex += 1;
            if(nodeIndex==path.size()-1){
                return distance;
            }
        }
        return distance;
    }

    public void getAllPathsWithExactStops(Vertex startNode, Vertex endNode, int stops) {
        visitedList.add(startNode);
        for (Edge edge : edges) {
            if (edge.getSource().equals(startNode)) {
                if (edge.getDestination().equals(endNode) && visitedList.size() == stops) {
                    resultSet.add(visitedList.toString().substring(0, visitedList.toString().lastIndexOf("]")) + "," + endNode + "]");
                    continue;
                }
                if (visitedList.size()<=stops) {
                    getAllPathsWithExactStops(edge.getDestination(), endNode, stops);
                } else {
                    loopList.add(edge);
                }
            }
        }
        visitedList.remove(startNode);
    }

    public void getAllPathsWithMaxDistance(Vertex startNode, Vertex endNode, int maxDistance) {
        visitedList.add(startNode);
        int distance = getDistanceByPath(visitedList);
        if(distance<maxDistance){
            for (Edge edge : edges) {
                if (edge.getSource().equals(startNode)) {
                    if (edge.getDestination().equals(endNode) && distance+edge.getWeight()<maxDistance) {
                        resultSet.add(visitedList.toString().substring(0, visitedList.toString().lastIndexOf("]")) + ", " + endNode + "]");
                    }
                    getAllPathsWithMaxDistance(edge.getDestination(), endNode, maxDistance);
                }
            }
        }
        visitedList.remove(visitedList.size()-1);
    }
}
