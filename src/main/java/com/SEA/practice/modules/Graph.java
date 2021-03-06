package com.SEA.practice.modules;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    private final List<Vertex> vertexes;
    private final List<Edge> edges;

    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public Vertex getVertexInGraphById(String id) {
        for (Vertex vertex : vertexes) {
            if (vertex.getId().equals(id)) {
                return vertex;
            }
        }
        return null;
    }

    public LinkedList<Vertex> getLinkedVertexByStopsInGraph(ArrayList<String> stops) {
        LinkedList<Vertex> path = new LinkedList<>();
        for (String stop : stops) {
            path.add(getVertexInGraphById(stop));
        }
        return path;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
