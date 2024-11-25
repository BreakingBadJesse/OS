import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Practise {
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        Random rd  = new Random();
        System.out.println("Enter the no. of pages:");
        int n = sc.nextInt();
        System.out.println("Enter the no. of frames:");
        int nFrames = sc.nextInt();

        int refString[] = {6, 1, 1, 2, 0, 3, 4, 6, 0, 2, 1, 2, 1, 2, 0, 3, 2, 1,2,0};
        // for(int i = 0;i < n;i++)
        //     refString[i] =  rd.nextInt(8);
        System.out.println(Arrays.toString(refString));
        System.out.println("Pagefaults in FIFO:"+ FIFO(refString,nFrames));
        System.out.println("Pagefaults in LRU:"+ LRU(refString,nFrames));


    }

    public static int FIFO(int[] refString,int nFrames){
        int pagefaults = 0;
        int[] frames = new int[nFrames];
        Arrays.fill(frames,-1);
        int index = 0;
        for(int page: refString){
            boolean pageFound = false;
            //check if page is present or not in frame
            for(int frame:frames){
                if(frame == page){
                    pageFound = true;
                    break;
                }
            }

            //if page is not present
            if(!pageFound){
                frames[index] = page;
                index = (index + 1) % nFrames;
                pagefaults++;
            }
        }
        return pagefaults;
    }

    public static int LRU(int[] refString,int nFrames){
        int pageFaults = 0;
        int[] frames = new int[nFrames];
        int[] lastUsed = new int[nFrames];
        int time = 0;
        Arrays.fill(frames, -1); // Initialize frames
        Arrays.fill(lastUsed, -1); // Initialize usage timestamps
        for(int page: refString){
            boolean pageFound = false;
            //check if page is present in frame 
            for(int i = 0;i < nFrames;i++){
                if(frames[i] == page){
                    pageFound = true;
                    lastUsed[i] = time++;
                    break;
                }
            }

            if(!pageFound){
                pageFaults++;
                int lruIndex = -1;
                //find the lease recently used page
                int minTime = Integer.MAX_VALUE;
                for(int i = 0;i < frames.length;i++){
                    if(lastUsed[i] < minTime){
                        minTime = lastUsed[i];
                        lruIndex = i;
                    }
                }
                frames[lruIndex] = page;
                lastUsed[lruIndex] = time++;
            }
        }
        return pageFaults;
    }
}
