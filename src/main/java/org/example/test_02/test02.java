package org.example.test_02;

import com.google.gson.Gson;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class test02 {
    public static void main(String[] args) {
        int n = 5;
        int m = 4;

        double p = 0.5;
        double q = 1 - p;

        double[][] w = new double[m][m];
        String filePath = "F:\\project\\paper_md\\paper_md\\src\\main\\java\\org\\example\\test_02\\data\\train.txt";
        String filePath_social = "F:\\project\\paper_md\\paper_md\\src\\main\\java\\org\\example\\test_02\\data\\trust_data.txt";
        Graph<String, DefaultWeightedEdge> graph = new SimpleGraph<>(DefaultWeightedEdge.class);
        Graph<Integer, DefaultWeightedEdge> graph_user = new SimpleGraph<>(DefaultWeightedEdge.class);
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
        try (Scanner scanner = new Scanner(new File(filePath_social))) {
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(" ");
                Integer u = Integer.parseInt(line[0]);
                Integer v = Integer.parseInt(line[1]);
                Graphs.addEdgeWithVertices(graph_user, u, v);
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
        boolean[][] user_edgeCache = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                user_edgeCache[i][j] = graph_user.containsEdge(i + 1, j + 1);
            }
        }
        DecimalFormat df = new DecimalFormat("#.00000000");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                double sum = 0;
                double sum2 = 0;
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
                    w[i][j] = p * Double.parseDouble(df.format(sum / graph.degreeOf("i" + (j + 1))));
                }

                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        if(graph.containsVertex("u" + (k + 1)) && graph_user.containsVertex(l + 1)) {

                            boolean k_i = edgeCache[k][i];
                            boolean l_j = edgeCache[l][j];
                            boolean k_l = user_edgeCache[k][l];
                            if(k_i && l_j && k_l) {
                                double deg = graph.degreeOf("u" + (k + 1)) * graph_user.degreeOf(l + 1);
                                sum2 += 1 / deg;
                            }
                        }
                    }
                }
                if(graph.containsVertex("i" + (j + 1))) {
                    w[i][j] = w[i][j] + q * Double.parseDouble(df.format(sum2 / graph.degreeOf("i" + (j + 1))));
                }
            }
        }

        Gson gson = new Gson();
        String json = gson.toJson(w);
        try {
            FileWriter writer = new FileWriter("small_social.json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

