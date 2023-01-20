package fr.ensimag.deca.tools.Library;

public class Brouillon {

    public static void main(String[] args) {
        ArrayLib test = new ArrayLib();
        float[][] tab = {{2, -1, 0}, {-1, 2, -1}, {0, -1, 2}};

        for (float[] fs : tab) {
            for (float fs2 : fs) {
                System.out.print(fs2 + " ");
            }
            System.out.println();
        }

        float deter = test.det(tab);

        for (float[] fs : tab) {
            for (float fs2 : fs) {
                System.out.print(fs2 + " ");
            }
            System.out.println();
        }
    }
    
}

// Calcul du déterminant, de l'inverse et du rang par la méthode de Gauss-Jordan

class ArrayLib {

    

    IntFloat gaussJordan(float[][] matrix) {

        int indexPivot = -1;
        float det = 1;
        int rang = matrix.length;

        int j = 0;
        int i = 0;
        float max = matrix[0][0];
        int indexMax = 0;
        int k = 0;
        float coeff = matrix[indexMax][0];
        float multiplicateur = matrix[0][0];
        IntFloat res = new IntFloat();

        while (j < matrix.length) {
            // on cherche la valeur maximale de chaque colonne
            max = matrix[0][j];
            indexMax = 0;
            i = 0;
            while (i < matrix.length) {
                if (Math.abs(matrix[i][j]) > Math.abs(max)) {
                    max = matrix[i][j];
                    indexMax = i;
                }
                i = i + 1;
            }
            det = det * max;
            
            if (max != 0) {
                indexPivot = indexPivot + 1;
                k = 0;
                while (k < matrix[0].length) {
                    matrix[indexMax][k] = matrix[indexMax][k] / max; // Diviser la ligne indexMax par max
                    k = k + 1;
                }
                if (indexMax != indexPivot) {
                    det = det * (-1);
                    // on échange les lignes indexMax et indexPivot
                    k = 0;
                    while (k < matrix[0].length) {
                        coeff = matrix[indexMax][k];
                        matrix[indexMax][k] = matrix[indexPivot][k];
                        matrix[indexPivot][k] = coeff;
                        k = k + 1;
                    }
                }
                i = 0;
                while (i < matrix.length) {
                    if (i != indexPivot) {
                        multiplicateur = matrix[i][j];
                        k = 0;
                        while (k < matrix[0].length) {
                            matrix[i][k] = matrix[i][k] - matrix[indexPivot][k] * multiplicateur;
                            k = k + 1;
                        }
                    }
                    i = i + 1;
                }
            } else {
                rang = rang - 1;
            }
            j = j + 1;
        }
        res.rang = rang;
        res.det = det;
        return res;
    }

    float[][] inverse(float[][] matrix) {
        //assert(matrix.length == matrix[0].length);
        float[][] matrixWithIdentity = new float[matrix.length][2 * matrix.length];
        int i = 0;
        int j = 0;
        float[][] mat = new float[matrix.length][matrix.length];
        while (i < matrix.length) {
            j = 0;
            while (j < matrix.length) {
                matrixWithIdentity[i][j] = matrix[i][j];
                j = j + 1;
            }
            while (j < 2 * matrix.length) {
                if (i == j - 3) {
                    matrixWithIdentity[i][j] = 1;
                } else {
                    matrixWithIdentity[i][j] = 0;
                }
                j = j + 1;
            }
            i = i + 1;
        }

        gaussJordan(matrixWithIdentity);
        
        i = 0;
        while (i < matrix.length) {
            j = 0;
            while (j < matrix.length) {
                mat[i][j] = matrixWithIdentity[i][j+3];
                j = j + 1;
            }
            i = i + 1;
        }
        return mat;
    }

    float det(float[][] matrix) {
        return gaussJordan(matrix).det;
    }

    float rang(float[][] matrix) {

        return gaussJordan(matrix).rang;
    }

}

class IntFloat {
    protected int rang;
    protected float det;
}
