import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ReaderWriter {
    private int readCount = 0;
    private int sharedData = 0;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition writeCondition = lock.newCondition();
    private boolean isWriting = false; // To track if a writer is active

    public void read(int readerId) throws InterruptedException {
        lock.lock();
        try {
            while (isWriting) {
                writeCondition.await(); // Wait if a writer is writing
            }
            readCount++;  // Reader starts reading
        } finally {
            lock.unlock();
        }

        System.out.println("\nReader " + readerId + ": Reading the shared data: " + sharedData);
        Thread.sleep(100);

        lock.lock();
        try {
            readCount--;  // Reader finished reading
            if (readCount == 0) {
                writeCondition.signal(); // Notify waiting writer if no readers left
            }
        } finally {
            lock.unlock();
        }
    }

    public void write(int writerId) throws InterruptedException {
        lock.lock();
        try {
            while (readCount > 0 || isWriting) {  // Wait if readers are reading or another writer is writing
                writeCondition.await();
            }
            isWriting = true;  // Writer starts writing
            sharedData++;
            System.out.println("\nWriter " + writerId + ": Writing to the shared data. New value: " + sharedData);
            Thread.sleep(100);
            isWriting = false;  // Writer finished writing
            writeCondition.signalAll();  // Notify readers and writers waiting
        } finally {
            lock.unlock();
        }
    }
}

class Reader implements Runnable {
    private final ReaderWriter readerWriter;
    private final int id;

    public Reader(ReaderWriter readerWriter, int id) {
        this.readerWriter = readerWriter;
        this.id = id;
    }

    public void run() {
        try {
            readerWriter.read(id);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Writer implements Runnable {
    private final ReaderWriter readerWriter;
    private final int id;

    public Writer(ReaderWriter readerWriter, int id) {
        this.readerWriter = readerWriter;
        this.id = id;
    }

    public void run() {
        try {
            readerWriter.write(id);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
public class Main {
    public static void main(String[] args) {
        ReaderWriter readerWriter = new ReaderWriter();
        Scanner scanner = new Scanner(System.in);

        int readerCount = 0;
        int writerCount = 0;

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Reader");
            System.out.println("2. Add Writer");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    readerCount++;
                    Thread reader = new Thread(new Reader(readerWriter, readerCount));
                    reader.start();
                    break;
                case 2:
                    writerCount++;
                    Thread writer = new Thread(new Writer(readerWriter, writerCount));
                    writer.start();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }
}
