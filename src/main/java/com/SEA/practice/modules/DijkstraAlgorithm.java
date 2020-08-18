package com.SEA.practice.modules;

import java.util.*;

public class DijkstraAlgorithm {

    private final Graph graph;
    private Set<Vertex> settledVertices;
    private Set<Vertex> unSettledVertices;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Integer> distance;
    private final LinkedList<Vertex> visitedList = new LinkedList<>();
    public ArrayList<String> resultPaths = new ArrayList<>();

    public DijkstraAlgorithm(Graph graph) {
        this.graph = graph;
    }

    public void execute(Vertex source) {
        settledVertices = new HashSet<>();
        unSettledVertices = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0);
        unSettledVertices.add(source);
        while (unSettledVertices.size() > 0) {
            Vertex vertex = getMinimum(unSettledVertices);
            settledVertices.add(vertex);
            unSettledVertices.remove(vertex);
            findMinimalDistances(vertex);
        }
    }

    private void findMinimalDistances(Vertex vertex) {
        List<Vertex> adjacentVertices = getNeighbors(vertex);
        for (Vertex target : adjacentVertices) {
            if (getShortestDistance(target) > getShortestDistance(vertex)
                    + getDistance(vertex, target)) {
                distance.put(target, getShortestDistance(vertex)
                        + getDistance(vertex, target));
                predecessors.put(target, vertex);
                unSettledVertices.add(target);
            }
        }

    }

    private int getDistance(Vertex vertex, Vertex target) {
        for (Edge edge : graph.getEdges()) {
            if (edge.getSource().equals(vertex)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        return Integer.MAX_VALUE;
    }

    public List<Vertex> getNeighbors(Vertex vertex) {
        List<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : graph.getEdges()) {
            if (edge.getSource().equals(vertex)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    public List<Vertex> getAdjacentVertices(Vertex vertex) {
        List<Vertex> adjacentVertices = new ArrayList<>();
        for (Edge edge : graph.getEdges()) {
            if (edge.getSource().equals(vertex)) {
                adjacentVertices.add(edge.getDestination());
            }
        }
        return adjacentVertices;
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
        return settledVertices.contains(vertex);
    }

    private int getShortestDistance(Vertex destination) {
        Integer d = distance.get(destination);
        return Objects.requireNonNullElse(d, Integer.MAX_VALUE);
    }

    public LinkedList<Vertex> getShortestPathDifferentStartAndEnd(Vertex target) {
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

    public ArrayList<LinkedList<Vertex>> getPathsSameStartAndEnd(Vertex source, DijkstraAlgorithm dijkstraAlgorithm) {
        ArrayList<LinkedList<Vertex>> paths = new ArrayList<>();
        List<Vertex> adjacentVertices = getAdjacentVertices(source);
        for (Vertex newStart : adjacentVertices) {
            dijkstraAlgorithm.execute(newStart);
            if (dijkstraAlgorithm.getShortestPathDifferentStartAndEnd(source) != null) {
                LinkedList<Vertex> wholePath = dijkstraAlgorithm.getShortestPathDifferentStartAndEnd(source);
                wholePath.add(0, source);
                paths.add(wholePath);
            }
        }
        return paths;
    }

    public LinkedList<Vertex> getShortestPathSameStartAndEnd(Vertex source, DijkstraAlgorithm dijkstraAlgorithm) {
        int minDistance = Integer.MAX_VALUE;
        LinkedList<Vertex> shortestPath = new LinkedList<>();
        ArrayList<LinkedList<Vertex>> paths = getPathsSameStartAndEnd(source, dijkstraAlgorithm);
        if (!(paths.size() > 0)) {
            return null;
        }
        for (LinkedList<Vertex> path : paths) {
            int distance = getDistanceByPath(path);
            minDistance = Math.min(distance, minDistance);
            shortestPath = minDistance == distance ? path : shortestPath;
        }
        return shortestPath;
    }

    public ArrayList<String> getPathsByConditionOnStopsSameStartAndEnd(
            Vertex source, DijkstraAlgorithm dijkstraAlgorithm, String Condition, int number) {
        if (Condition.equals("Equal")) {
            dijkstraAlgorithm.getAllPathsWithExactStops(source, source, number);
            return dijkstraAlgorithm.resultPaths;
        } else {
            ArrayList<LinkedList<Vertex>> paths = getPathsSameStartAndEnd(source, dijkstraAlgorithm);
            ArrayList<String> result = new ArrayList<>();
            for (LinkedList<Vertex> path : paths) {
                if (path.size() <= number + 1) {
                    result.add(path.toString());
                }
            }
            return result;
        }
    }

    public String getExactPath(LinkedList<Vertex> exactPath) {
        for (Vertex vertex : exactPath) {
            if (vertex.equals(exactPath.getLast())) {
                break;
            } else if (getDistance(vertex, exactPath.get(exactPath.indexOf(vertex) + 1)) == Integer.MAX_VALUE) {
                return "NO SUCH ROUTE";
            }
        }
        return "ROUTE FIND";
    }

    public int getDistanceByPath(LinkedList<Vertex> path) {
        int distance = 0;
        int vertexIndex = 0;
        Vertex nextVertex;
        if (path.size() < 2) {
            return distance;
        }
        for (Vertex vertex : path) {
            nextVertex = path.get(vertexIndex + 1);
            distance += getDistance(vertex, nextVertex);
            vertexIndex += 1;
            if (vertexIndex == path.size() - 1) {
                return distance;
            }
        }
        return distance;
    }

    public void getAllPathsWithExactStops(Vertex startVertex, Vertex endVertex, int stops) {
        visitedList.add(startVertex);
        for (Edge edge : graph.getEdges()) {
            if (edge.getSource().equals(startVertex)) {
                if (edge.getDestination().equals(endVertex) && visitedList.size() == stops) {
                    resultPaths.add(visitedList.toString().substring(0, visitedList.toString().lastIndexOf("]")) + ", " + endVertex + "]");
                    continue;
                }
                if (visitedList.size() <= stops) {
                    getAllPathsWithExactStops(edge.getDestination(), endVertex, stops);
                }
            }
        }
        visitedList.remove(startVertex);
    }

    public void getAllPathsWithMaxDistance(Vertex startVertex, Vertex endVertex, int maxDistance) {
        visitedList.add(startVertex);
        int distance = getDistanceByPath(visitedList);
        if (distance < maxDistance) {
            for (Edge edge : graph.getEdges()) {
                if (edge.getSource().equals(startVertex)) {
                    if (edge.getDestination().equals(endVertex) && distance + edge.getWeight() < maxDistance) {
                        resultPaths.add(visitedList.toString().substring(0, visitedList.toString().lastIndexOf("]")) + ", " + endVertex + "]");
                    }
                    getAllPathsWithMaxDistance(edge.getDestination(), endVertex, maxDistance);
                }
            }
        }
        visitedList.remove(visitedList.size() - 1);
    }
}
