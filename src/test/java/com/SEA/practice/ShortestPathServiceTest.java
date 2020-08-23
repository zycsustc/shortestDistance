package com.SEA.practice;

import com.SEA.practice.modules.Vertex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ShortestPathServiceTest {
    private final ShortestPathService shortestPathService = new ShortestPathService();

    @Test
    void shouldGetCorrectShortestPath() {
        LinkedList<Vertex> shortestPath = shortestPathService.getShortestPath("A", "C");
        assertEquals("A", shortestPath.get(0).getId());
        assertEquals("B", shortestPath.get(1).getId());
        assertEquals("C", shortestPath.get(2).getId());
    }

    @Test
    void shouldGetCorrectDistanceOfExactPath() {
        ArrayList<String> stopsIdList = new ArrayList<>();
        stopsIdList.add("A");
        stopsIdList.add("B");
        stopsIdList.add("C");

        String distance = shortestPathService.getDistanceOfExactPath(stopsIdList);

        assertEquals("9", distance);
    }

    @Test
    void shouldGetCorrectPathsWithConditionOnStops() {
        ArrayList<String> path = shortestPathService.getPathsWithConditionOnStops("A", "C", "Equal", 4);

        assertEquals(3, path.size());
    }

    @Test
    void shouldGetCorrectPathsWithMaxDistance() {
        ArrayList<String> path = shortestPathService.getPathsWithMaxDistance("C", "C", 30);

        assertEquals(7, path.size());
    }
}
