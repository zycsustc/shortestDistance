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

    public LinkedList<Vertex> getShortestPath(Vertex source, Vertex target, DijkstraAlgorithm dijkstraAlgorithm){
        if(source.equals(target)){
            dijkstraAlgorithm.execute(source);
            return dijkstraAlgorithm.getShortestPathSameStartAndEnd(source, dijkstraAlgorithm);
        } else {
            dijkstraAlgorithm.execute(source);
            return dijkstraAlgorithm.getShortestPathDifferentStartAndEnd(target);
        }
    }


}
