package org.example.test_02;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;
import com.google.gson.Gson;

public class test01 {
    public static void main(String[] args) throws IOException {
        int n = 5;
        int m = 4;
        double[][] w = new double[m][m];
        String filePath = "F:\\project\\paper_md\\paper_md\\src\\main\\java\\org\\example\\test_02\\data\\train.txt";
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
        boolean[][] edgeCache = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                edgeCache[i][j] = graph.containsEdge("u" + (i + 1), "i" + (j + 1));
            }
        }
        DecimalFormat df = new DecimalFormat("#.00000000");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                double sum = 0;
                for (int l = 0; l < n; l++) {
                    boolean containsEdgeI = edgeCache[l][i];
                    boolean containsEdgeJ = edgeCache[l][j];
                    if (containsEdgeI && containsEdgeJ) {
                        double degree = graph.degreeOf("u" + (l + 1));
                        if (degree != 0) {
                            sum += 1.0 / degree;
                        }
                    }
                }
                if (graph.containsVertex("i" + (j + 1))) {
                    w[i][j] = Double.parseDouble(df.format(sum / graph.degreeOf("i" + (j + 1))));
                }
            }
        }

        Gson gson = new Gson();
        String json = gson.toJson(w);
        try {
            FileWriter writer = new FileWriter("small.json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
