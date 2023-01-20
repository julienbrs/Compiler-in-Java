package fr.ensimag.deca.tools.Library;

public class ArrayFunction {
    
    private static float[][] matrice = {{2, -1, 0}, {-1, 2, -1}, {0, -1, 2}};
    private static float[][] matriceUno = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};

    private static float[][] mat = {{1, 2}, {3, 4}};

    public static void printMatrice(float[][] matrice) {
        for (int j = 0; j < matrice.length; j++) {
            for (int i = 0; i < matrice[j].length; i++) {
                System.out.print(matrice[j][i] + " ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        System.out.println(trace(matrice));
        printMatrice(add(matrice, matrice));
        System.out.println("___ SUB :");
        printMatrice(sub(matrice, matriceUno));
        System.out.println("___ MULT SCALAR :");
        printMatrice(multScalar(matrice, 3));
        System.out.println("___ MULT MATRIX :");
        printMatrice(multMatrix(matrice, multScalar(matriceUno, 2)));
        System.out.println("___ MULT MATRIX :");
        printMatrice(multMatrix(mat, mat));
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


        float[][] matrice = A;
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

        float[][] matrice = A;
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                matrice[i][j] -= B[i][j];
            }
        }
        return matrice;
    }

    public static float[][] multScalar(float[][] A, int lambda) {

        float[][] matrice = A;
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
     *  -> compare ( otherArray ) recursivement
        -> copy ( newLength )
        -> copyOfRange ( newLength, from, to )
        -> toString ()
    */


    /*
     * A généraliser (pas que matrices)
     */
    public void fill(float[][] matrice, float element) {
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice.length; j++) {
                matrice[i][j] = element;
            }
        }
    }

    public Index search(float[][] matrice, float element) {
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
        if (!b) { // si l'element n'est pas présent
            return new Index(indexI, indexJ, b);
        }
        return new Index(indexI, indexJ, b);
    }

    public int partition(float[] tab, int low, int high) {
        float pivot = tab[high];
        int index = low - 1;
        for (int i = low; i < high; i++) {
            if (tab[i] <= pivot) {
                index = index + 1;
                float cur = tab[i];
                tab[i] = tab[index];
                tab[index] = cur;
            }
        }
        index = index + 1;
        float cur = tab[high];
        tab[high] = tab[index];
        tab[index] = cur;
        return index;
    }

    public float[] quicksort(float[] tab, int low, int high) {
        assert(tab.length == 1);
        assert(low >= high || low < 0);
        int part = partition(tab, low, high);
        quicksort(tab, low, part - 1);
        quicksort(tab, part + 1, high);
        return tab;
    }

    public boolean compare(float[][] A, float[][] B) {
        boolean bool = false;
        // TODO
        return bool;
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
