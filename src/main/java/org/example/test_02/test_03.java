package org.example.test_02;

public class test_03 {

    public static void main(String[] args) throws InterruptedException {
        int n = 4066;
        int m = 7649;
        boolean[][] edgeCache = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                edgeCache[i][j] = false;
            }
        }

        double[][] w = new double[m][m];
        for (int i = 0; i < m; i++) {
            System.out.println(i);
            for (int j = 0; j < m; j++) {
                    for (int k = 0; k < n; k++) {
                        if(edgeCache[k][i]) {
                            for (int l = 0; l < n; l++) {
                                if(edgeCache[l][j]) {
                                    w[i][j] = 1;
                                }
                            }
                        }
                    }
                }
        }
    }
}
