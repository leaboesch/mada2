package Huffman;

import java.util.BitSet;

public class Buchstabe {
	 int ascii;
	 int prozent;
	 BitSet code;
	
	public Buchstabe(int a, int p){
		ascii = a;
		prozent = p;
		code = new BitSet();
	}
	
	public int getProzent(){
		return prozent;
	}
	
}
