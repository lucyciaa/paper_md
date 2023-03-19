package org.example.test_01;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class test_01 {
    public static void main(String[] args) {
        int n = 4066;
        int m = 7649;
        double[][] w = new double[m][m];
        String filePath = "F:\\project\\paper_md\\paper_md\\src\\main\\java\\org\\example\\test_01\\data\\train.txt";
        Graph<String, DefaultWeightedEdge> graph = new SimpleGraph<>(DefaultWeightedEdge.class);
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                String u = line[0];
                String v = line[1];
                Graphs.addEdgeWithVertices(graph, "u" + u, "i" + v);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                double sum = 0;
                for (int l = 0; l < n; l++) {
                        try {
                            sum += graph.containsEdge("u" + (l + 1), "i" + (i + 1)) ? 1 : 0 * (graph.containsEdge("u" + (l + 1), "i" + (j + 1)) ? 1 : 0) / graph.degreeOf("u" + (l + 1));
                        } catch (ArithmeticException e) {
                            // b等于0时会抛出异常，将sum设置为0
                            sum = 0;
                        }
                }
                if (graph.containsVertex("i" + (j + 1))) {
                    DecimalFormat df = new DecimalFormat("#.00000000");
                    w[i][j] = Double.parseDouble(df.format(sum / graph.degreeOf("i" + (j + 1))));
                }
            }
        }
        System.out.println(w);
    }
}
