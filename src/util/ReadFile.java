package util;
import java.util.*;
import java.io.*;
//Lê de arquivo

public class ReadFile {
				
	    public static void main(String[] args) {
	    	
	    	 try {
		            FileReader reader = new FileReader("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file1.txt");
		            BufferedReader bufferedReader = new BufferedReader(reader);
	
		            String line;
		 
		            while ((line = bufferedReader.readLine()) != null) {
		                System.out.println(line);
		            }
		            reader.close();
		 
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
	    }
}

		
		
		


	

