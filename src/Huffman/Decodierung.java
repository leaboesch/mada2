package Huffman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Decodierung {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(readCodedMessage("output.dat"));
		
	}

	/*
	 * 9. Es soll also aus externen Dateien die Kodierungstabelle und das
	 * byte-Array eingelesen werden.
	 */
	// Einlesen des Byte-Arrays (codierte Nachricht) und byte-Array in einen
	// Bitstring umwandeln, von dem dann die letzte 1 und alle
	// folgenden Nullen abgeschnitten werden

	public static String readCodedMessage(String filename) {

		File codierterText = new File(filename);
		byte[] byteArray = new byte[(int) codierterText.length()];
		FileInputStream fis;
		try {
			fis = new FileInputStream(filename);
			fis.read(byteArray);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String a = "";
		String adding = "";

		for (byte b : byteArray) {
			adding = Integer.toBinaryString(256 + (int) b);

			adding.substring(adding.length() - 8);
			a = a + adding;
		}

		int indexOfLast1 = a.lastIndexOf("1");
		a = a.substring(0, indexOfLast1);
		return a;
	}

	// Einlesen der Kodierungstabelle

	public void readDecTab(String filename) {
		String fileFrom = "dec_tab.txt";
		BufferedReader in = null;
		String line;
		String res = "";
		try {
			in = new BufferedReader(new FileReader(fileFrom));
			while ((line = in.readLine()) != null)
				res += line;
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<Buchstabe> codierungstabelle = new ArrayList<Buchstabe>();
	}

}
