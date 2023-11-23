
public class MatrixMultiplication {

    public static void main(String[] args) {
        int[][] matrixA = Matrix.Array_A;
        int[][] matrixB = Matrix.Array_B;
        int matrixARows = matrixA.length;
        int matrixACols = matrixA[0].length;
        int matrixBCols = matrixB[0].length;

        int[][] resultMatrix = new int[matrixARows][matrixBCols];
        Thread[] threads = new Thread[Matrix.Thread_Count];
        int segmentSize = matrixARows / Matrix.Thread_Count;

        for (int i = 0; i < Matrix.Thread_Count; i++) {
            int startIndex = i * segmentSize;
            int endIndex = (i + 1) * segmentSize - 1;
            threads[i] = new Thread(new MatMultiplyThread(startIndex, endIndex, matrixA, matrixB, resultMatrix));
            threads[i].start();
        }

        // Wait for all threads to finish
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the result matrix
        System.out.println("Result Matrix:");
        for (int[] row : resultMatrix) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }
}

class Matrix {
    public static final int[][] Array_A = {{1, 2, 3}, {4, 5, 6}};
    public static final int[][] Array_B = {{10, 11}, {20, 21}, {30, 31}};
    public static final int Thread_Count = 2;
}

class MatMultiplyThread implements Runnable {
    int startIndex;
    int endIndex;
    int[][] matrixA;
    int[][] matrixB;
    int[][] resultMatrix;

    MatMultiplyThread(int _startIndex, int _endIndex, int[][] _matrixA, int[][] _matrixB, int[][] _resultMatrix) {
        startIndex = _startIndex;
        endIndex = _endIndex;
        matrixA = _matrixA;
        matrixB = _matrixB;
        resultMatrix = _resultMatrix;
    }

    @Override
    public void run() {
        for (int i = startIndex; i <= endIndex; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                for (int k = 0; k < matrixB.length; k++) {
                    resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
    }
}
