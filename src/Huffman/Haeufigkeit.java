package Huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Haeufigkeit {
	public static void main(String[] args) {

		int[] alleZeichen = new int[256]; // Array f�r ASCII-Code
		int[] alleZeichenSortiert = bubblesort(alleZeichen);

		for (int i = 0; i < alleZeichenSortiert.length; i++) {
			System.out.print(alleZeichenSortiert[i] + ", ");
		}

		
		
		String test = readFile("test.txt");
		List<Buchstabe> buchstaben = wieHaeufig(test);
		for (int i = 0; i < buchstaben.size(); i++) {
			if (buchstaben.get(i).getProzent() != 0) {
				System.out.println((char) i + ": " + buchstaben.get(i).getProzent() +"%");
			}

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

	/**
	 * Berechnet die prozentuale Häufigkeit der Buchstaben im Parameter-String. 
	 * Unbenutzte Ascii-Werte werden auf 0 gesetzt.
	 * @param text
	 * @return 
	 */
	public static List<Buchstabe> wieHaeufig(String text) {
		int[] p = new int[128];
		for (int i = 0; i < p.length; i++) {
			p[i] = 0;
		}
		for (int i = 0; i < text.length(); i++) {
			p[text.charAt(i)]++;
		}
		int[] prozente = new int[128];
		for (int i = 0; i < p.length; i++) {
			double d = ((double) p[i]) / text.length() * 100;
			prozente[i] = (int)d;
		}
		
		List<Buchstabe> pr = new ArrayList<Buchstabe>();
		for (int i=0; i<prozente.length; i++){
			pr.add(new Buchstabe(i, prozente[i]));
		}
		return pr;
	}

	/**
	 * Liest ein File ein und gibt den Inhalt als String zurück.
	 * @param filename
	 * @return
	 */
	public static String readFile(String filename) {
		String fileContents = "";
		try {
			FileReader fileReader = new FileReader(filename);
			int i;
			while ((i = fileReader.read()) != -1) {
				char ch = (char) i;
				fileContents = fileContents + ch;
			}
		} catch (IOException e) {
			System.out.println("Fehler beim Einlesen");
		}
		return fileContents;
	}
	
	
}
