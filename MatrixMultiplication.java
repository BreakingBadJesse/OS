import java.util.Random;

class MatrixMultiplication {

    static final int MATRIX_SIZE = 4;
    static int[][] matA = new int[MATRIX_SIZE][MATRIX_SIZE];
    static int[][] matB =new int[MATRIX_SIZE][MATRIX_SIZE];
    static int[][] matC = new int[MATRIX_SIZE][MATRIX_SIZE]; // Resultant matrix


    public static void main(String[] args) {
        Random random = new Random();

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                matA[i][j] = random.nextInt(10) + 1;
                matB[i][j] = random.nextInt(10) + 1;
            }
        }


        Thread[] threads = new Thread[MATRIX_SIZE * MATRIX_SIZE];

        // Create and start threads for each element in the resultant matrix
        int threadCount = 0;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                threads[threadCount] = new Thread(new MultiplyTask(i, j));
                threads[threadCount].start();
                threadCount++;
            }
        }

        // Wait for all threads to complete
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Display the resultant matrix
        System.out.println("Resultant Matrix:");
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.print(matC[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Task to perform multiplication for one element in the resultant matrix
    static class MultiplyTask implements Runnable {
        int row, col;

        MultiplyTask(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void run() {
            matC[row][col] = 0;
            for (int k = 0; k < MATRIX_SIZE; k++) {
                matC[row][col] += matA[row][k] * matB[k][col];
            }
        }
    }
}