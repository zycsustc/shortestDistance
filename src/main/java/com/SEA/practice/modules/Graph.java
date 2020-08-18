package com.SEA.practice.modules;

import java.util.LinkedList;
import java.util.List;

public class Graph {
    private final List<Vertex> vertexes;
    private final List<Edge> edges;

    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public Vertex getVertexById(String id) {
        for (Vertex node : vertexes) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return null;
    }

    public LinkedList<Vertex> getLinkedVertexByStops(List<String> stops) {
        LinkedList<Vertex> path = new LinkedList<>();
        for(String stop: stops){
            path.add(getVertexById(stop));
        }return path;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
