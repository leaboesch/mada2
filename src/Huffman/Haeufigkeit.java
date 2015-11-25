package Huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Haeufigkeit {
	public static void main(String[] args) {

		int[] alleZeichen = new int[256]; // Array f�r ASCII-Code
		int[] alleZeichenSortiert = bubblesort(alleZeichen);

		for (int i = 0; i < alleZeichenSortiert.length; i++) {
			System.out.print(alleZeichenSortiert[i] + ", ");
		}

		
		
		String test = readFile("test.txt");
		List<Buchstabe> buchstaben = wieHaeufig(test);
		List<Buchstabe> mitCode = createCode(buchstaben);
		createCode(mitCode);
		codieren(test, mitCode);
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

	
	
	public static String readFile(String filename) {

		/*
		 * 1. Eine Textdatei (ASCII kodiert) soll eingelesen werden.
		 */
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

	public static List<Buchstabe> wieHaeufig(String text) {

		/*
		 * 2. Es soll eine Tabelle angelegt werden, in der fur jedes der 128
		 * moglichen ASCII-Zeichen drinsteht, wie oft das entsprechende Zeichen
		 * in der Textdatei vorkommt. (Hinweis: (int) c macht aus einem
		 * character c den entsprechenden ASCII-Wert).
		 */
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
			prozente[i] = (int) d;
		}

		List<Buchstabe> pr = new ArrayList<Buchstabe>();
		for (int i = 0; i < prozente.length; i++) {
			pr.add(new Buchstabe(i, prozente[i]));
		}
		return pr;
	}

	public static List<Buchstabe> createCode(List<Buchstabe> b) {

		/*
		 * 3. Aus dieser Häufigkeitstabelle soll eine Huffman-Kodierung fur
		 * die Zeichen konstruiert werden, die in der Datei vorkommen.
		 *
		 */

		// Nicht vorkommende Buchstaben rausfiltern
		b = b.stream().filter(l -> l.getProzent() > 0).collect(Collectors.toList());

		// Liste in temporäre Liste kopieren
		List<Buchstabe> temp = new ArrayList<Buchstabe>();
		for (Buchstabe l : b) {
			temp.add(l);
		}

		while (temp.size() > 1) {

			// 2 am wenigsten häufige Elemente rausnehmen und Code ergänzen
			Buchstabe b0 = temp.stream().min((a1, a2) -> a1.getProzent() - a2.getProzent()).get();
			temp.remove(b0);
			for (Buchstabe bu : b) {
				if (b0.getWort().contains(bu.getWort())) {
					bu.addToCode(0);
				}
			}

			Buchstabe b1 = temp.stream().min((a1, a2) -> a1.getProzent() - a2.getProzent()).get();
			temp.remove(b1);
			for (Buchstabe bu : b) {
				if (b1.getWort().contains(bu.getWort())) {
					bu.addToCode(1);
				}
			}

			// neues Wort konstruieren und hinzufügen
			String neuesWort = b0.wort + b1.wort;
			int neueProzente = b0.prozent + b1.prozent;
			Buchstabe neu = new Buchstabe(neuesWort, neueProzente);
			temp.add(neu);
			System.out.println("Neu: " + neu.wort + ": " + neu.prozent);

		}

		// alle Codes ausgeben
		for (Buchstabe bu : b) {
			if (bu.getCode() != "") {
				System.out.println(bu.getWort() + ": " + bu.getCode());
			}
		}
		return b;
	}

	public static void writeCode(List<Buchstabe> b) {

		/*
		 * 4. Die Huffman-Kodierungstabelle soll in einer externen Datei dec
		 * tab.txt in der Form: ASCII-Code von Zeichen1:Code von
		 * Zeichen1-ASCII-Code von Zeichen2:Code von Zeichen2-. . . gespeichert
		 * werden.
		 */
		File dec_tab = new File("dec_tab.txt");
		try {
			dec_tab.createNewFile();
		} catch (IOException e) {
			System.out.println("Fehler beim Erstellen der Datei");
		}

		FileWriter f1;
		try {
			f1 = new FileWriter("dec_tab.txt");
			for (Buchstabe bu : b) {
				f1.write(String.valueOf(bu.getAscii()));
				f1.write(":");
				f1.write(bu.getCode());
				f1.write("-");
			}

			f1.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Erstellen der Datei");
		}
	}

	public static void codieren(String text, List<Buchstabe> b) {

		/*
		 * 5. Die eingelesene Textdatei soll entsprechend der Huffman-Kodierung
		 * in einen Bitstring kodiert werden.
		 */
		int i = 0;
		String bitstring = "";
		while (i < text.length()) {
			int j = 0;
			while ((char) b.get(j).getAscii() != text.charAt(i)) {
				j++;
			}
			bitstring = bitstring + b.get(j).getCode();
			i++;
		}

		/*
		 * 6. An diesen Bitstring soll eine 1 und anschliessend so viele Nullen
		 * dran gehangt werden, bis der Bitstring eine Lange hat, die ein
		 * Vielfaches von 8 ist.
		 */
		System.out.println(bitstring);
		bitstring = bitstring + "1";
		while (bitstring.length() % 8 != 0) {
			bitstring = bitstring + "0";
		}
		System.out.println(bitstring);

		/*
		 * KÖNNTE FUNKTIONIEREN, KANN ES ABER NOCH NICHT TESTEN
		 * 
		 * 7. Aus diesem erweiterten Bitstring soll ein byte-Array erstellt
		 * werden, in dem je 8 aufeinanderfolgende Zeichen zu je einem byte
		 * zusammengefasst werden.
		 * 
		 */
		
		int length = bitstring.length()/8;
		byte[] byteArray = new byte[length];
		
		for (int j=0; j<length; j++){
			String temp = bitstring.substring(j*8, j*8+8);
			// string in byte umwandeln
			int intwert = Integer.valueOf(temp, 2);			
			byteArray[j] = (byte)intwert;			
		}
		
		
		
		/*
		 * 8. Dieses byte-Array soll in einer externen Datei output.dat
		 * gespeichert werden.
		 */
		File codierterText = new File("output.dat");
		try {
			codierterText.createNewFile();
		} catch (IOException ee) {
			System.out.println("Fehler beim Erstellen der Datei");
		}
		try {
			FileOutputStream fos = new FileOutputStream(codierterText);
			fos.write(byteArray);
			fos.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Schreiben");
		}

	}
}
