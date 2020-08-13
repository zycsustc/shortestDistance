package com.SEA.practice;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Util {

    public Vertex getVertexById(String id, List<Vertex> nodes) {
        for (Vertex node : nodes) {
            if (node.getId().equals(id)) {
                return node;
            }
        }
        return null;
    }

    public LinkedList<Vertex> getLinkedVertexByStops(List<String> stops, List<Vertex> nodes) {
        LinkedList<Vertex> path = new LinkedList<>();
        for(String stop: stops){
            path.add(getVertexById(stop, nodes));
        }return path;
    }
}
