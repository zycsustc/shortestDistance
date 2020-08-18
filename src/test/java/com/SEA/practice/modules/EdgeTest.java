package com.SEA.practice.modules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeTest {
    @Test
    void shouldConstructCorrectEdge() {
        Vertex source = new Vertex("A");
        Vertex target = new Vertex("B");
        int weight = 5;
        String id = "id";
        Edge edge = new Edge(id, source, target, weight);

        assertEquals(edge.getWeight(), 5);
        assertEquals(edge.getSource(), source);
        assertEquals(edge.getDestination(), target);
        assertEquals(edge.getId(), id);
    }
}
