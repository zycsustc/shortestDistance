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
        List<Vertex> adjacentNodes = new ArrayList<Vertex>();
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
        LinkedList<Vertex> path = new LinkedList<Vertex>();
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

    public LinkedList<Vertex> getShortestPathSameStartAndEnd(Vertex source, DijkstraAlgorithm dijkstraAlgorithm){
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        List<Vertex> adjacentNodes = getAdjacentNodes(source);
        int minDistance = Integer.MAX_VALUE;
        for(Vertex newStart: adjacentNodes){
            dijkstraAlgorithm.execute(newStart);
            if(dijkstraAlgorithm.getShortestPathDifferentStartAndEnd(source)!=null){
                LinkedList<Vertex> wholePath = dijkstraAlgorithm.getShortestPathDifferentStartAndEnd(source);
                wholePath.add(0, source);
                int distance = getDistanceByPath(wholePath);
                minDistance = Math.min(distance, minDistance);
                path = minDistance==distance ? wholePath : path;
            }
        }
        return path;
    }

    public LinkedList<Vertex> getShortestPath(Vertex source, Vertex target, DijkstraAlgorithm dijkstraAlgorithm){
        if(source.equals(target)){
            return dijkstraAlgorithm.getShortestPathSameStartAndEnd(source, dijkstraAlgorithm);
        } else {
            return dijkstraAlgorithm.getShortestPathDifferentStartAndEnd(target);
        }
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
        Vertex nextNode;
        for (Vertex node: path) {
            nextNode = path.get(path.indexOf(node)+1);
            distance += getDistance(node, nextNode);
            if(nextNode.equals(path.getLast())){
                return distance;
            }
        }
        return distance;
    }
}
