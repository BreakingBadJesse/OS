#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <time.h>

#define MAX 10  // Max size of the matrix

// Matrices
int A[MAX][MAX], B[MAX][MAX], C[MAX][MAX];

// Dimensions of the matrices
int rows, cols;

// Structure to pass data to threads
typedef struct {
    int row;
    int col;
} MatrixData;

// Thread function for matrix addition
void* add_matrices(void* arg) {
    MatrixData* data = (MatrixData*)arg;
    int row = data->row;
    int col = data->col;

    // Perform addition for a single element
    C[row][col] = A[row][col] + B[row][col];

    pthread_exit(0);
}

int main() {
    int i, j;
    pthread_t threads[MAX][MAX];
    MatrixData data[MAX][MAX];

    // Seed the random number generator
    srand(time(0));

    // Input matrix dimensions
    printf("Enter the number of rows and columns (Max %d): ", MAX);
    scanf("%d %d", &rows, &cols);

    if (rows > MAX || cols > MAX) {
        printf("Matrix size exceeds the maximum limit of %d.\n", MAX);
        return -1;
    }

    // Generate random elements for matrices A and B
    printf("Matrix A (%d x %d):\n", rows, cols);
    for (i = 0; i < rows; i++) {
        for (j = 0; j < cols; j++) {
            A[i][j] = rand() % 100;  // Random number between 0 and 99
            printf("%d ", A[i][j]);
        }
        printf("\n");
    }

    printf("Matrix B (%d x %d):\n", rows, cols);
    for (i = 0; i < rows; i++) {
        for (j = 0; j < cols; j++) {
            B[i][j] = rand() % 100;  // Random number between 0 and 99
            printf("%d ", B[i][j]);
        }
        printf("\n");
    }

    // Create threads for each element of the result matrix
    for (i = 0; i < rows; i++) {
        for (j = 0; j < cols; j++) {
            data[i][j].row = i;
            data[i][j].col = j;
            pthread_create(&threads[i][j], NULL, add_matrices, &data[i][j]);
        }
    }

    // Join all threads
    for (i = 0; i < rows; i++) {
        for (j = 0; j < cols; j++) {
            pthread_join(threads[i][j], NULL);
        }
    }

    // Print the result matrix
    printf("Resultant Matrix (A + B):\n");
    for (i = 0; i < rows; i++) {
        for (j = 0; j < cols; j++) {
            printf("%d ", C[i][j]);
        }
        printf("\n");
    }

    return 0;
}
