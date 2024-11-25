import java.util.Random;
import java.util.Scanner;

public class Practise {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        Random rd = new Random();

        System.out.println("Enter the no. of processes:");
        int n = sc.nextInt();
        System.out.println("Enter the no. of resources:");
        int m = sc.nextInt();

        int max[][] = new int[n][m];
        int allocated[][] = new int[n][m];
        int need[][] = new int[n][m];
        int avail[] = new int[m];

        System.out.println("Max resources matrix (generated randomly):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                max[i][j] = rd.nextInt(10) + 1;
                System.out.print(max[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Allocated resources matrix (generated randomly):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                allocated[i][j] = rd.nextInt(max[i][j] + 1);
                System.out.print(allocated[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Need matrix (calculated):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                need[i][j] = max[i][j] - allocated[i][j];
                System.out.print(need[i][j] + " ");
            }
            System.out.println();
        }

        int totalRes[] = new int[m];
        System.out.println("Total resources (generated randomly):");
        for (int j = 0; j < m; j++) {
            int totalAlloc = 0;
            for (int i = 0; i < n; i++) {
                totalAlloc += allocated[i][j];
            }
            totalRes[j] = totalAlloc + rd.nextInt(10) + 1; // Ensure total >= allocated
            System.out.print(totalRes[j] + " ");
        }
        System.out.println();

        System.out.println("Available resources (calculated):");
        for (int j = 0; j < m; j++) {
            int totalAlloc = 0;
            for (int i = 0; i < n; i++) {
                totalAlloc += allocated[i][j];
            }
            avail[j] = totalRes[j] - totalAlloc;
            System.out.print(avail[j] + " ");
        }
        System.out.println();

        // Safety Algorithm
        boolean[] finish = new boolean[n];
        int[] work = new int[m];
        System.arraycopy(avail, 0, work, 0, m);

        int[] safeSequence = new int[n];
        int count = 0;

        boolean safe = true;
        while (count < n) {
            boolean found = false;

            for (int i = 0; i < n; i++) {
                if (!finish[i]) {
                    int j;
                    for (j = 0; j < m; j++) {
                        if (need[i][j] > work[j]) {
                            break;
                        }
                    }

                    if (j == m) { // If all resources are available for process i
                        for (int k = 0; k < m; k++) {
                            work[k] += allocated[i][k];
                        }
                        safeSequence[count++] = i;
                        finish[i] = true;
                        found = true;
                    }
                }
            }

            if (!found) {
                safe = false;
                break;
            }
        }

        if (safe) {
            System.out.println("System is in safe state.");
            System.out.print("Safe sequence is: ");
            for (int i = 0; i < n; i++) {
                System.out.print("P" + safeSequence[i] + " ");
            }
            System.out.println();
        } else {
            System.out.println("System is not in safe state.");
        }

        sc.close();
    }
}
