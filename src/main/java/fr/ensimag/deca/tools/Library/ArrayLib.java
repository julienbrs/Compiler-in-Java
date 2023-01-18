package fr.ensimag.deca.tools.Library;

public class ArrayLib {

    private static int[][] matrice = {{2, -1, 9, 8, 5}, {-1, 2, -1, 5, 6}, {8, -1, 2, 1, 5}, {7, 2, 3, 2, 2}, {1, 5, 6, 4, 3}};

    public static void printMatrice(int[][] matrice) {
        for (int j = 0; j < matrice.length; j++) {
            for (int i = 0; i < matrice.length; i++) {
                System.out.print(matrice[j][i] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        printMatrice(gaussJordan(matrice));
    }

    public static int[][] gaussJordan(int[][] matrix) {
        printMatrice(matrix);
        System.out.println("---");

        int indexPivot = -1;
        
        for (int j = 0; j < matrix.length; j++) {
            int max = matrix[0][j];
            int indexMax = 0;
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                    indexMax = i;
                }
            }
            System.out.println("max " + max);
            System.out.println("indexMax " + indexMax);
            if (max != 0) {
                indexPivot ++;
                System.out.println(indexMax == indexPivot);
                for (int k = 0; k < matrix.length; k++) {
                    matrix[indexMax][k] = matrix[indexMax][k] / matrix[indexMax][j]; // Diviser la ligne indexMax par max
                }
                if (indexMax != indexPivot) {
                    // on échange les lignes indexMax et indexPivot
                    for (int k = 0; k < matrix.length; k++) {
                        int coeff = matrix[indexMax][k];
                        System.out.println(indexPivot);
                        matrix[indexMax][k] = matrix[indexPivot][k];
                        matrix[indexPivot][k] = coeff;
                    }
                }
                for (int i = 0; i < matrix.length; i++) {
                    if (i != indexPivot) {
                        for (int k = 0; k < matrix.length; k++) {
                            matrix[i][k] = matrix[i][k] - matrix[indexPivot][k] * matrix[i][j];
                        }
                    }
                }
                printMatrice(matrix);
                System.out.println("---");
            }
        }

        return matrix;
    }
}