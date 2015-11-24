package Huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
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
		/*
		for (int i = 0; i < buchstaben.size(); i++) {
			if (buchstaben.get(i).getProzent() != 0) {
				System.out.println((char) i + ": " + buchstaben.get(i).getProzent() +"%");
			}

		}
		*/
		List<Buchstabe> mitCode = createCode(buchstaben);
		writeCode(mitCode);

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
	
	public static List<Buchstabe> createCode(List<Buchstabe> b){
		
		
		
		// Nicht vorkommende Buchstaben rausfiltern
		b = b.stream().filter(l -> l.getProzent()>0).collect(Collectors.toList());

		
		// Liste in temporäre Liste kopieren
				List<Buchstabe> temp = new ArrayList<Buchstabe>();
				for (Buchstabe l : b){
					temp.add(l);
				}
		// Der Häufigkeit nach sortieren
		/*temp
        .stream()
        .sorted((e1, e2) -> Integer.compare(e1.prozent,
                e2.prozent)).forEach(e -> System.out.println(e.wort +": " +e.prozent));
        		
		*/
		while (temp.size()>1){

		// 2 am wenigsten häufige Elemente rausnehmen und Code ergänzen
		Buchstabe b0 = temp.stream().min((a1,a2) -> a1.getProzent() - a2.getProzent()).get();
		temp.remove(b0);		
		for(Buchstabe bu : b){
			if (b0.getWort().contains(bu.getWort())){
			bu.addToCode(0);
			}
		}

		Buchstabe b1 =  temp.stream().min((a1,a2) -> a1.getProzent() - a2.getProzent()).get();
		temp.remove(b1);
		for(Buchstabe bu : b){
			if (b1.getWort().contains(bu.getWort())){
			bu.addToCode(1);			
			}
		}

		// neues Wort konstruieren und hinzufügen
		String neuesWort = b0.wort +b1.wort;
		int neueProzente = b0.prozent +b1.prozent;
		Buchstabe neu = new Buchstabe(neuesWort, neueProzente);
		temp.add(neu);
		System.out.println("Neu: " +neu.wort +": " +neu.prozent);
		
		

		}
		
		// alle Codes ausgeben
		for (Buchstabe bu : b){
			if (bu.getCode()!=""){
			System.out.println(bu.getWort() +": " +bu.getCode());
			}
		}
		return b;
	}
	
	
	// Schreibt die Codierungstabelle
	public static void writeCode(List<Buchstabe> b){
			
			File dec_tab = new File("dec_tab.txt");
			try {
				dec_tab.createNewFile();
			} catch (IOException e) {
				System.out.println("Fehler beim Erstellen der Datei");
			}

			FileWriter f1;
			try {
				f1 = new FileWriter("dec_tab.txt");
				for (Buchstabe bu : b){
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
		
		
		
	}
	
	

