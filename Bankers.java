// Source code is decompiled from a .class file using FernFlower decompiler.
import java.util.Random;
import java.util.Scanner;

public class BankersAlgorithm {
   public BankersAlgorithm() {
   }

   public static void main(String[] var0) {
      Scanner var1 = new Scanner(System.in);
      Random var2 = new Random();
      System.out.print("Enter number of processes: ");
      int var3 = var1.nextInt();
      System.out.print("Enter number of resources: ");
      int var4 = var1.nextInt();
      int[][] var5 = new int[var3][var4];
      int[][] var6 = new int[var3][var4];
      int[][] var7 = new int[var3][var4];
      int[] var8 = new int[var4];
      System.out.println("Max matrix (generated randomly):");

      int var9;
      int var10;
      for(var9 = 0; var9 < var3; ++var9) {
         for(var10 = 0; var10 < var4; ++var10) {
            var5[var9][var10] = var2.nextInt(10) + 1;
            System.out.print(var5[var9][var10] + " ");
         }

         System.out.println();
      }

      System.out.println("Allocation matrix (generated randomly):");

      for(var9 = 0; var9 < var3; ++var9) {
         for(var10 = 0; var10 < var4; ++var10) {
            var6[var9][var10] = var2.nextInt(var5[var9][var10] + 1);
            System.out.print(var6[var9][var10] + " ");
         }

         System.out.println();
      }

      System.out.println("Available resources (generated randomly):");

      for(var9 = 0; var9 < var4; ++var9) {
         var8[var9] = var2.nextInt(10) + 1;
         System.out.print(var8[var9] + " ");
      }

      System.out.println();
      System.out.println("Need Matrix:");

      for(var9 = 0; var9 < var3; ++var9) {
         for(var10 = 0; var10 < var4; ++var10) {
            var7[var9][var10] = var5[var9][var10] - var6[var9][var10];
            System.out.print(var6[var9][var10] + " ");
         }

         System.out.println();
      }

      boolean[] var18 = new boolean[var3];
      int[] var19 = new int[var4];
      System.arraycopy(var8, 0, var19, 0, var4);
      int[] var11 = new int[var3];
      int var12 = 0;
      boolean var13 = true;

      while(var12 < var3) {
         boolean var14 = false;

         for(int var15 = 0; var15 < var3; ++var15) {
            if (!var18[var15]) {
               int var16;
               for(var16 = 0; var16 < var4 && var7[var15][var16] <= var19[var16]; ++var16) {
               }

               if (var16 == var4) {
                  for(int var17 = 0; var17 < var4; ++var17) {
                     var19[var17] += var6[var15][var17];
                  }

                  var11[var12++] = var15;
                  var18[var15] = true;
                  var14 = true;
               }
            }
         }

         if (!var14) {
            var13 = false;
            break;
         }
      }

      if (var13) {
         System.out.println("System is in a safe state.\nSafe sequence is: ");

         for(int var20 = 0; var20 < var3; ++var20) {
            System.out.print(var11[var20] + " ");
         }

         System.out.println();
      } else {
         System.out.println("System is not in a safe state.");
      }

   }
}
