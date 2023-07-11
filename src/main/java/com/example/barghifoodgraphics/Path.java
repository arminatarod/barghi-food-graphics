package com.example.barghifoodgraphics;

import java.util.ArrayList;

public class Path {
    private int time, nodeCount;
    private ArrayList<Integer> pathNode;
    public int getNode(int index) {
        return pathNode.get(index);
    }

    public int getNodeCount() {
        return nodeCount;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void addNode(int u) {
        pathNode.add(u);
        nodeCount = pathNode.size();
    }
    public int nextNode(int u) {
        int i = 0;
        for (; i < pathNode.size(); i++)
            if (pathNode.get(i) == u)
                    break;
        if (i == pathNode.size() - 1)
            return -1;
        return pathNode.get(i + 1);
    }
}
