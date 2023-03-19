package org.example.test_01;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;

public class test_04 {
    public static void main(String[] args) {
        Graph<Integer, DefaultWeightedEdge> graph_user = new SimpleGraph<>(DefaultWeightedEdge.class);
        Graphs.addEdgeWithVertices(graph_user, 1, 2);
        Graphs.addEdgeWithVertices(graph_user, 2, 1);
        Graphs.addEdgeWithVertices(graph_user, 1, 3);
        Graphs.addEdgeWithVertices(graph_user, 3, 1);
        Graphs.addEdgeWithVertices(graph_user, 2, 3);
        Graphs.addEdgeWithVertices(graph_user, 3, 2);
        System.out.println(graph_user.degreeOf(1));
        System.out.println(graph_user.degreeOf(2));
        System.out.println(graph_user.degreeOf(3));
    }
}
