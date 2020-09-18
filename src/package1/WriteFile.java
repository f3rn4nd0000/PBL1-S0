package package1;
import java.util.*;
import java.io.*;
//Escreve em arquivo.

public class WriteFile {
	
	public static void main(String[] args) {
	      try {
	            FileWriter writer = new FileWriter("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file1.txt", true);
	            BufferedWriter bufferedWriter = new BufferedWriter(writer);
	 
	            bufferedWriter.write("Hello World");
	            bufferedWriter.newLine();
	            bufferedWriter.write("See You Again!");
	 
	            bufferedWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 
	}
	
	
	
}
