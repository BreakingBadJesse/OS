import java.util.Arrays;
import java.util.Random;
public class ForkWaitDemo {

    public static void main(String[] args) throws InterruptedException {
        int[] arr = new int[6];
        Random random = new Random();

        for(int i = 0;i < arr.length;i++){
            arr[i] = random.nextInt(100+1);
        }
        System.out.println(Arrays.toString(arr));

        // Fork-like behavior by creating a child thread
        Thread childThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Child process started.");
                
                // Child process sorts the array using bubble sort
                bubbleSort(arr);
                System.out.println("Child process sorted array: " + Arrays.toString(arr));
                
                try {
                    // Simulate a zombie state by sleeping (finished but not joined by parent)
                    Thread.sleep(10000); // Simulates child being in zombie state for 10 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Child process finished.");
            }
        });

        // Start the child thread (Fork-like)
        childThread.start();

        System.out.println("Parent process started.");

        // Parent sorts the array using bubble sort (Simulating its own sorting)
        bubbleSort(arr);
        System.out.println("Parent process sorted array: " + Arrays.toString(arr));

        // Simulate orphan state by finishing parent process early
        Thread.sleep(2000); // Let the parent finish before the child to simulate orphan state
        System.out.println("Parent process finished before child.");

        // Join the child process to simulate wait() and clean up the zombie state
        childThread.join();

        System.out.println("Parent waited for child process to finish.");
    }

    // Bubble sort algorithm used by both parent and child
    private static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    // Swap arr[j] and arr[j+1]
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
}
