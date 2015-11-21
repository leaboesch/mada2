package Huffman;

import java.math.BigInteger;
import java.util.HashMap;

public class Häufigkeit {
    public static void main(String[] args) {

        int[] alleZeichen = new int[256]; // Array für ASCII-Code
        int[] alleZeichenSortiert = bubblesort(alleZeichen);

        for (int i = 0; i < alleZeichenSortiert.length; i++) {
            System.out.print(alleZeichenSortiert[i] + ", ");
        }

    }

    public static int[] bubblesort(int[] alleZeichen) {
        int temp;
        for (int i = 1; i < alleZeichen.length; i++) {
            for (int j = 0; j < alleZeichen.length - i; j++) {
                if (alleZeichen[j] > alleZeichen[j + 1]) {
                    temp = alleZeichen[j];
                    alleZeichen[j] = alleZeichen[j + 1];
                    alleZeichen[j + 1] = temp;
                }

            }
        }
        return alleZeichen;
    }

}
