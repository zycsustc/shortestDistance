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
        dijkstraAlgorithm.execute(source);
        if(source.equals(target)){
            return dijkstraAlgorithm.getShortestPathSameStartAndEnd(source, dijkstraAlgorithm);
        } else {
            return dijkstraAlgorithm.getShortestPathDifferentStartAndEnd(target);
        }
    }

    public ArrayList<LinkedList<Vertex>> getPathsWithConditionOnStops(Vertex source, Vertex target, DijkstraAlgorithm dijkstraAlgorithm,
                                                                        String condition, int number){
        dijkstraAlgorithm.execute(source);
        if(source.equals(target)){
            return dijkstraAlgorithm.getPathsByConditionOnStopsSameStartAndEnd(source, dijkstraAlgorithm, condition, number);
        } else {
            return null;
        }
    }

}
