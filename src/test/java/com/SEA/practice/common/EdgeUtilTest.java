package com.SEA.practice.common;

import com.SEA.practice.modules.Edge;
import com.SEA.practice.modules.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EdgeUtilTest {

    private ArrayList<Vertex> nodes;

    @BeforeEach
    void setUp(){
        nodes = new ArrayList<>();

        ArrayList<String> vertexs = new ArrayList<>();
        vertexs.add("A");
        vertexs.add("B");

        for (String id : vertexs) {
            Vertex location = new Vertex(id);
            nodes.add(location);
        }
    }

    @Test
    void shouldGenerateCorrectEdge() {
        EdgeUtil edgeUtil = new EdgeUtil();
        GraphUtil graphUtil = new GraphUtil();
        Edge edge = edgeUtil.generateEdge("Edge_0", "A", "B", 5, nodes);

        Edge expectedEdge = new Edge("Edge_0", graphUtil.getVertexById("A", nodes),
                graphUtil.getVertexById("B", nodes), 5);

        assertEquals(expectedEdge.getId(), edge.getId());
        assertEquals(expectedEdge.getSource(), edge.getSource());
        assertEquals(expectedEdge.getDestination(), edge.getDestination());
        assertEquals(expectedEdge.getWeight(), edge.getWeight());
    }
}
