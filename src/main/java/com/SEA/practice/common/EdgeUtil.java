package com.SEA.practice.common;

import com.SEA.practice.modules.Edge;
import com.SEA.practice.modules.Vertex;

import java.util.ArrayList;

public class EdgeUtil {

    GraphUtil graphUtil = new GraphUtil();

    public Edge generateEdge(String laneId, String sourceLoc, String destLoc, int cost, ArrayList<Vertex> nodes) {
        Edge edge = new Edge(laneId, graphUtil.getVertexById(sourceLoc, nodes),
                graphUtil.getVertexById(destLoc, nodes), cost );
        return edge;
    }
}
