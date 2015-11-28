package Huffman;

public class Buchstabe {
	 int ascii;
	 int prozent;
	 String code;
	 String wort;
	
	public Buchstabe(int a, int p){
		ascii = a;
		prozent = p;
		code = "";
		wort = "" +(char)a;
	}
	
	public Buchstabe(String b, int p){
		ascii = 300;
		prozent = p;
		code = "";		wort = b;		
	}
	
	public Buchstabe(){		
	}
	
	public void setAscii(int ascii){
		this.ascii = ascii;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	public int getProzent(){
		return prozent;
	}
	
	public String getWort(){
		return wort;
	}
	
	public String getCode(){
		return code;
	}
	
	public int getAscii(){
		return ascii;
	}
	
	public void addToCode(int i){
		if (i==0){
			code= "0" +code;
		}
		if (i==1){
			code = "1"+code;
		}
	}

}
