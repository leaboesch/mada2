package Huffman;

//Lea Boesch & Irina Terribilini 3iCbb
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class Einlesen {
    public static void main(String[] args) {

        BigInteger[] textFile = readFile("ascii.txt");
        // TODO einlesen(textFile, "chiffre.txt", "text-d.txt");

    }

    // Textdatei einlesen
    public static BigInteger[] readFile(String filename) {
        FileReader f;
        int c;
        String code = "";
        String[] splitkeys = new String[2];
        try {
            f = new FileReader(filename);
            while ((c = f.read()) != -1) {
                code = code + (char) c;
            }
            code = code.replaceAll("[()]", "");
            splitkeys = code.split(",");
            f.close();
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Datei");
        }
    }

}
