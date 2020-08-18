package com.SEA.practice.common;

import com.SEA.practice.modules.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class GraphUtilTest {
    private ArrayList<Vertex> nodes;
    GraphUtil graphUtil = new GraphUtil();

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
    void getVertexById() {
        Vertex expectedVertex = new Vertex("A");
        Vertex vertex = graphUtil.getVertexById("A", nodes);

        assertEquals(expectedVertex.getId(), vertex.getId());
    }

    @Test
    void getLinkedVertexByStops() {
        ArrayList<String> stops = new ArrayList<>();
        stops.add("A");
        stops.add("B");

        LinkedList<Vertex> path = graphUtil.getLinkedVertexByStops(stops, nodes);
        Vertex vertexA = new Vertex("A");
        Vertex vertexB = new Vertex("B");

        LinkedList<Vertex> expectedPath = new LinkedList<>();
        expectedPath.add(vertexA);
        expectedPath.add(vertexB);

        assertTrue(path.size()==expectedPath.size());
    }
}
