package com.SEA.practice;

import java.util.*;

public class ShortestPathService {
    public Vertex getVertexById(String id, List<Vertex> nodes){
        for (Vertex node : nodes) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return null;
    }
}
