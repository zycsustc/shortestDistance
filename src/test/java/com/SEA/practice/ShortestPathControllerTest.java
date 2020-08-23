package com.SEA.practice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ShortestPathControllerTest {

    @Autowired
    private ShortestPathController shortestPathController;

    @Test
    public void contexLoads() throws Exception {
        assertNotNull(shortestPathController);
    }

    @Test
    void getShortestPath() {
    }

    @Test
    void getPathsWithConditionOnStops() {
    }

    @Test
    void getDistanceOfExactPath() {
    }

    @Test
    void getPathsWithMaxDistance() {
    }
}
