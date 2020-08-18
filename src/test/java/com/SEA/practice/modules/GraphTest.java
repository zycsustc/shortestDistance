package com.SEA.practice.modules;

import com.SEA.practice.common.EdgeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    private ArrayList<Vertex> nodes;
    EdgeUtil edgeUtil = new EdgeUtil();

    @BeforeEach
    void setUp(){
        nodes = new ArrayList<>();

        ArrayList<String> vertexs = new ArrayList<>();
        vertexs.add("A");
        vertexs.add("B");
        vertexs.add("C");

        for (String id : vertexs) {
            Vertex location = new Vertex(id);
            nodes.add(location);
        }
    }

    @Test
    void shouldConstructCorrectGraph() {
        Edge edge = edgeUtil.generateEdge("Edge_0", "A", "B", 5, nodes);
        ArrayList<Edge> edges = new ArrayList<>();
        edges.add(edge);

        Graph graph = new Graph(nodes, edges);

        assertEquals(graph.getEdges(), edges);
        assertEquals(graph.getVertexes(), nodes);
        assertEquals(graph.getVertexById("A"), nodes.get(0));
    }
}
