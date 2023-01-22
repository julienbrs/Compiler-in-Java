package fr.ensimag.deca.tools.Library;

/*
 * Calcul du déterminant, de l'inverse et du rang, par la méthode de Gauss-Jordan
 */

public class ArrayLib {

    private static float[][] matrice = {{2, -1, 0}, {-1, 2, -1}, {0, -1, 2}};

    public static void printMatrice(float[][] matrice) {
        for (int j = 0; j < matrice.length; j++) {
            for (int i = 0; i < matrice[j].length; i++) {
                System.out.print(matrice[j][i] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        printMatrice(inverse(matrice));
        System.out.println(det(matrice));
        System.out.println(rang(matrice));
    }

    private static RangDet gaussJordan(float[][] matrix) {

        int indexPivot = -1;
        float det = 1;
        int rang = matrix.length;

        for (int j = 0; j < matrix.length; j++) {
            // on cherche la valeur maximale de chaque colonne
            float max = matrix[0][j];
            int indexMax = 0;
            for (int i = 0; i < matrix.length; i++) {
                if (Math.abs(matrix[i][j]) > Math.abs(max)) {
                    max = matrix[i][j];
                    indexMax = i;
                }
            }
            det *= max;
            if (max != 0) {
                indexPivot ++;
                for (int k = 0; k < matrix[0].length; k++) {
                    matrix[indexMax][k] = matrix[indexMax][k] / max; // Diviser la ligne indexMax par max
                }
                if (indexMax != indexPivot) {
                    det *= -1;
                    // on échange les lignes indexMax et indexPivot
                    for (int k = 0; k < matrix[0].length; k++) {
                        float coeff = matrix[indexMax][k];
                        matrix[indexMax][k] = matrix[indexPivot][k];
                        matrix[indexPivot][k] = coeff;
                    }
                }
                for (int i = 0; i < matrix.length; i++) {
                    if (i != indexPivot) {
                        float multiplicateur = matrix[i][j];
                        for (int k = 0; k < matrix[0].length; k++) {
                            matrix[i][k] = matrix[i][k] - matrix[indexPivot][k] * multiplicateur;
                        }
                    }
                }
            } else {
                rang --;
            }
        }
        return new RangDet(rang, det);
    }

    public static float[][] inverse(float[][] matrix) {
        assert(matrix.length == matrix[0].length);
        float[][] matrixWithIdentity = new float[matrix.length][2 * matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrixWithIdentity[i][j] = matrix[i][j];
            }
            for (int j = matrix.length; j < 2 * matrix.length; j++) {
                if (i == j - 3) {
                    matrixWithIdentity[i][j] = 1;
                } else {
                    matrixWithIdentity[i][j] = 0;
                }
            }
        }

        gaussJordan(matrixWithIdentity);
        float[][] mat = new float[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                mat[i][j] = matrixWithIdentity[i][j+3];
            }
        }
        return mat;
    }

    public static float det(float[][] matrix) {
        return gaussJordan(matrix).det;
    }

    public static float rang(float[][] matrix) {
        return gaussJordan(matrix).rang;
    }

}

class RangDet {
    protected int rang;
    protected float det;

    public RangDet(int rang, float det) {
        this.rang = rang;
        this.det = det;
    }
}