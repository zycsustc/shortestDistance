package com.SEA.practice.modules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VertexTest {
    @Test
    void shouldConstructCorrectVertex() {
        String id = "A";
        Vertex vertex = new Vertex(id);

        assertEquals(id, vertex.getId());
    }
}
