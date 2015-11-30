package Huffman;
//Lea Boesch und Irina Terribilini 3iCbb
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Decodierung {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String codedMessage = (readCodedMessage("output.dat"));
		List<Buchstabe> dectab = readDecTab("dec_tab.txt");
		decompress(codedMessage, dectab);
	}

	/*
	 * 9. Es soll also aus externen Dateien die Kodierungstabelle und das
	 * byte-Array eingelesen werden.
	 */
	// Einlesen des Byte-Arrays (codierte Nachricht) und byte-Array in einen
	// Bitstring umwandeln, von dem dann die letzte 1 und alle
	// folgenden Nullen abgeschnitten werden

	public static String readCodedMessage(String filename) {

		File file = new File(filename);
		byte[] bFile = new byte[(int) file.length()];
		try {
			FileInputStream fis = new FileInputStream(file);
			fis.read(bFile);
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String a = "";

		for (byte b : bFile) {

		    String s = Integer.toBinaryString(b & 255 | 256).substring(1);			
			a = a + s;
		}
		int indexOfLast1 = a.lastIndexOf("1");
		a = a.substring(0, indexOfLast1);
		return a;
	}

	

	// Einlesen der Kodierungstabelle
	public static List<Buchstabe> readDecTab(String filename) {
		BufferedReader in = null;
		String line;
		String res = "";
		try {
			in = new BufferedReader(new FileReader(filename));
			while ((line = in.readLine()) != null)
				res += line;
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] codes = res.split("-");
		ArrayList<Buchstabe> codierungstabelle = new ArrayList<Buchstabe>();

		for (String s : codes) {
			String[] asciiUndCode = s.split(":");
			int ascii = Integer.valueOf(asciiUndCode[0]);
			String code = asciiUndCode[1];
			Buchstabe bu = new Buchstabe();
			bu.setAscii(ascii);
			bu.setCode(code);
			codierungstabelle.add(bu);
		}

		return codierungstabelle;
	}

	// Eingelesene Nachricht decodieren
	public static void decompress(String codedMessage, List<Buchstabe> codierungstabelle) {

		File decryptedMessage = new File("decompress.txt");
		try {
			decryptedMessage.createNewFile();
		} catch (IOException e) {
			System.out.println("Fehler beim Erstellen der Datei");
		}
		FileWriter fw;
		try {
			fw = new FileWriter(decryptedMessage);

			while (codedMessage.length() > 0) {

				int i = 0;
				while (!codedMessage.startsWith(codierungstabelle.get(i).getCode())) {
					i++;
				}
				int ascii = codierungstabelle.get(i).getAscii();
				String code = codierungstabelle.get(i).getCode();
				if (codedMessage.length() > code.length()) {
					codedMessage = codedMessage.substring(code.length(), codedMessage.length());
				} else {
					codedMessage = "";
				}				
				fw.write(ascii);
			}
			fw.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Erstellen der Datei");
		}

	}

}
