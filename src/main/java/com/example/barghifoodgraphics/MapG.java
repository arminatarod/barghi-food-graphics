package com.example.barghifoodgraphics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class MapG {
    // ye data structure baraye graph
    private int n, m;
    private int[] dad;
    private double[] dis;
    private boolean[] isInQueue;
    private class edge {
        public edge(int weight, int u, int i) {
            this.weight = weight;
            this.u = u;
            traffic = 1.0;
            v = i;
        }
        private int weight, u, v;

        public int getV() {
            return v;
        }

        private double traffic;
        public void setU(int u) {
            this.u = u;
        }
        public void setWeight(int weight) {
            this.weight = weight;
        }
        public int getU() {
            return u;
        }
        public int getWeight() {
            return weight;
        }

        public double getTraffic() {
            return traffic;
        }

        public void setTraffic(double traffic) {
            this.traffic = traffic;
        }
    }

    public int getN() {
        return n;
    }
    public void setTraffic(int u, int v, double traffic) {
        for (ArrayList<edge> i : graph) {
            for (edge j : i) {
                if (j.getU() == u && j.getV() == v){
                    j.setTraffic(traffic);
                    return;
                }
                if (j.getU() == v && j.getV() == u) {
                    j.setTraffic(traffic);
                    return;
                }
            }
        }
    }
    public double getTraffic(int u, int v) {
        for (ArrayList<edge> i : graph) {
            for (edge j : i) {
                if (j.getU() == v && j.getV() == u) {
                    return j.getTraffic();
                }
                if (j.getU() == u && j.getV() == v) {
                    return j.getTraffic();
                }
            }
        }
        return 1.0;
    }

    public int getM() {
        return m;
    }

    private ArrayList<ArrayList<edge>> graph;
    public void readGraphFromFile(String address) throws FileNotFoundException {
        File myObj = new File(address);
        Scanner myReader;
        try {
            myReader = new Scanner(myObj);
        }
        catch (FileNotFoundException koft) {
            throw koft;
        }
        n = myReader.nextInt();
        m = myReader.nextInt();
        graph = new ArrayList<ArrayList<edge>>();
        for (int i = 0; i < n + 1; i++) {
            graph.add(new ArrayList<edge>());
        }
        for (int i = 0; i < m; i++) {
            int u = myReader.nextInt(), v = myReader.nextInt(), w = myReader.nextInt();
            graph.get(u).add(new edge(w, v, u));
            graph.get(v).add(new edge(w, u, v));
        }
        myReader.close();
    }
    public int getDistance (int node1, int node2){
        dis = new double[n];
        dad = new int[n];
        isInQueue = new boolean[n];
        for (int i = 0; i < n; i++)
            dis[i] = 999999;
        LinkedList<Integer> queue= new LinkedList<Integer>();
        dis[node1] = 0;
        queue.addLast(node1);
        while (!queue.isEmpty()) {
            int koft = queue.getFirst();
            queue.removeFirst();
            isInQueue[koft] = false;
            for (edge a : graph.get(koft))
                if (dis[a.getU()] > dis[koft] + a.getWeight() * a.getTraffic()) {
                    dis[a.getU()] = dis[koft] + a.getWeight() * a.getTraffic();
                    dad[a.getU()] = koft;
                    if (!isInQueue[a.getU()]) {
                        isInQueue[a.getU()] = true;
                        queue.addLast(a.getU());
                    }
                }
        }
        return (int)dis[node2];
    }
    private static ArrayList<Integer> decodePath(int node2) {
        ArrayList<Integer> ans = new ArrayList<Integer>();

        return ans;
    }
    public Path getShortestPath (int node1, int node2){
        dis = new double[n];
        dad = new int[n];
        isInQueue = new boolean[n];
        for (int i = 0; i < n; i++)
            dis[i] = 9999;
        LinkedList<Integer> queue= new LinkedList<Integer>();
        dis[node1] = 0;
        queue.addLast(node1);
        while (!queue.isEmpty()) {
            int koft = queue.getFirst();
            queue.removeFirst();
            isInQueue[koft] = false;
            for (edge a : graph.get(koft))
                if (dis[a.getU()] > dis[koft] + a.getWeight() * a.getTraffic()) {
                    dis[a.getU()] = dis[koft] + a.getWeight() * a.getTraffic();
                    dad[a.getU()] = koft;
                    if (!isInQueue[a.getU()]) {
                        isInQueue[a.getU()] = true;
                        queue.addLast(a.getU());
                    }
                }
        }
        Path ans = new Path();
        ans.setTime((int)dis[node2]);
        ArrayList<Integer> tmp = new ArrayList<Integer>();
        while (node2 != node1) {
            tmp.add(node2);
            node2 = dad[node2];
        }
        for (int i = tmp.size() - 1; i > -1; i--)
            ans.addNode(tmp.get(i));
        return ans;
    }

}
