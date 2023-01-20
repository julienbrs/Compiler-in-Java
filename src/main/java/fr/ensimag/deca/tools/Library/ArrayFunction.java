package fr.ensimag.deca.tools.Library;

public class ArrayFunction {
    
    private static float[][] matrice = {{2, -1, 0}, {-1, 2, -1}, {0, -1, 2}};
    private static float[][] matriceUno = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
    private static float[][] matriceUn = {{1, 1, 1}, {1, 0, 1}, {1, 1, 1}};

    private static float[][] mat = {{1, 2}, {3, 4}};

    private static float[] array = {3, 5, 4};

    public static void printMat(float[][] matrice) {
        System.out.print("[");
        for (int j = 0; j < matrice.length; j++) {
            System.out.print("[");
            for (int i = 0; i < matrice[j].length-1; i++) {
                System.out.print(matrice[j][i] + ", ");
            }
            System.out.print(matrice[j][matrice[j].length-1]);
            System.out.print("]");
            if (j == matrice[0].length - 1) {
                System.out.print("]");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        //printMat(deepCopy(matrice, matriceUno));
        //System.out.println(compare(matriceUn, matriceUno));
        //System.out.print(compare(matriceUno, matriceUno));
        // System.out.println(trace(matrice));

        // printMat(matrice);
        // System.out.println();
        // printMat(add(matrice, matriceUno));
        // System.out.println();
        // printMat(matrice);

        //printMat(deepCopyOfRange(matrice, 1, 1, 1, 2));
        //System.out.println("___ SUB :");
        //printMat(sub(matrice, matriceUno));
        // System.out.println("___ MULT SCALAR :");
        // printMat(multScalar(matrice, 3));
        // System.out.println("___ MULT MATRIX :");
        // printMat(multMatrix(matrice, multScalar(matriceUno, 2)));
        // System.out.println("___ MULT MATRIX :");
        // printMat(multMatrix(matrice, matriceUno));
        // System.out.println("___ FILL MATRIX :");
        // printMat(fill(mat, 2));
        System.out.println("___ SEARCH MATRIX :");
        System.out.println(search(mat, 5).indexI);
        System.out.println(search(mat, 5).indexJ);
        System.out.println(search(mat, 5).bool);
        // System.out.println("___ SORT MATRIX :");
        // float[] sorted = quicksort(array, 0, array.length-1);
        // System.out.println(sorted.length);
        // for (float f : sorted) {
        //     System.out.println(f);
        // }
    }

    public static float trace(float[][] matrice) {

        assert(matrice.length == matrice[0].length);

        float trace = 0;
        for (int i = 0; i < matrice.length; i++) {
            trace += matrice[i][i];
        }
        return trace;
    }

    public static float[][] add(float[][] A, float[][] B) {

        assert(A.length == B.length);
        assert(A[0].length == B[0].length);

        float[][] matrice = deepCopy(A);
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                matrice[i][j] += B[i][j];
            }
        }
        return matrice;
    }

    public static float[][] sub(float[][] A, float[][] B) {

        assert(A.length == B.length);
        assert(A[0].length == B[0].length);

        float[][] matrice = deepCopy(A);
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                matrice[i][j] -= B[i][j];
            }
        }
        return matrice;
    }

    public static float[][] multScalar(float[][] A, int lambda) {

        float[][] matrice = deepCopy(A);
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                matrice[i][j] *= lambda;
            }
        }
        return matrice;
    }

    public static float[][] multMatrix(float[][] A, float[][] B) {

        assert(A.length == B[0].length);
        assert(A[0].length == B.length);

        float[][] matrice = new float[A.length][B[0].length];
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice.length; j++) {
                for (int k = 0; k < matrice.length; k++) {
                    matrice[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return matrice;
    }

    /*
     * A généraliser (pas que matrices)
     */
    public static float[][] fill(float[][] matrice, float element) {
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                matrice[i][j] = element;
            }
        }
        return matrice;
    }

    public static Index search(float[][] matrice, float element) {
        int i = 0;
        int indexI = -1;
        int indexJ = -1;
        boolean b = false;
        while (i < matrice.length && !b) {
            int j = 0;
            while (j < matrice[0].length && !b) {
                if (matrice[i][j] == element) {
                    b = true;
                    indexI = i;
                    indexJ = j;
                }
                j++;
            }
            i++;
        }
        
        return new Index(indexI, indexJ, b);
    }

    public static int partition(float[] tab, int low, int high) {
        float pivot = tab[high];
        int index = low - 1;
        for (int i = low; i < high; i++) {
            if (tab[i] < pivot) {
                index = index + 1;
                float tmp = tab[i];
                tab[i] = tab[index];
                tab[index] = tmp;
            }
        }
        index = index + 1;
        float tmp = tab[high];
        tab[high] = tab[index];
        tab[index] = tmp;
        return index;
    }

    // faire que pour les tableaux de dimension 1
    public static float[] quicksort(float[] tab, int low, int high) {
        if (low < high) {
            int part = partition(tab, low, high);
            quicksort(tab, low, part-1);
            quicksort(tab, part + 1, high);
            return tab;
        }
        return null;
    }

    public static boolean compare(float[][] A, float[][] B) {
        int i = 0;
        while (i < A.length) {
            int j = 0;
            while (j < A[0].length) {
                if (A[i][j] != B[i][j]) {
                    return false;
                }
                j = j + 1;
            }
            i = i + 1;
        }
        return true;
    }

    // matrixCopy prend les valeurs de matrix
    public static float[][] deepCopy(float[][] matrix) {
        float[][] newMatrix = new float[matrix.length][matrix[0].length];
        int i = 0;
        while (i < matrix.length) {
            int j = 0;
            while (j < matrix[0].length) {
                newMatrix[i][j] = matrix[i][j];
                j = j + 1;
            }
            i = i + 1;
        }
        return newMatrix;
    }


    public static float[][] deepCopyOfRange(float[][] matrix, int indexI, int indexJ, int indexY, int indexZ) {
        float[][] newMatrix = new float[indexY-indexI+1][indexZ-indexJ+1];
        int i = indexI;
        while (i < indexY+1) {
            int j = indexJ;
            while (j < indexZ+1) {
                newMatrix[i-indexI][j-indexJ] = matrix[i][j];
                j = j + 1;
            }
            i = i + 1;
        }
        return newMatrix;
    }
}

class Index {
    protected int indexI;
    protected int indexJ;
    protected boolean bool;

    public Index(int indexI, int indexJ, boolean bool) {
        this.indexI = indexI;
        this.indexJ = indexJ;
        this.bool = bool;
    }
}
