package util;
import java.io.*;
import java.util.*;

public class Principal {
	
	public static void main(String args[]) {
		
		
		//aqui dentro ser� chamado aleatoriamente os m�todos de escrita em arquivo e de sincroniza��o.
		
		// PROBLEMA : SENDO QUE SE TRATAM DE CHAMADAS ALEAT�RIAS COMO IREMOS GARANTIR QUE AS OPERA��ES N�O SEJAM S� NO file1.txt??
		// POIS IMAGINE SE AS CHAMADAS S�O ALEAT�RIAS ENT�O N�O PODEMOS TER CERTEZA QUE S� O file1 SER� CHAMADO!!!		
		
		
	}
	
	private static void sincronizar(){
		
		try {
			FileReader reader1 = new FileReader("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file1.txt");
			FileReader reader2 = new FileReader("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file2.txt");
			FileReader reader3 = new FileReader("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file3.txt");
		
			
			BufferedReader bufferedReader1 = new BufferedReader(reader1);
			BufferedReader bufferedReader2 = new BufferedReader(reader2);
			BufferedReader bufferedReader3 = new BufferedReader(reader3);
			
			
			if(bufferedReader2 != bufferedReader1){
			    //se os arquivos nao forem iguais torna-os iguais  
				try {
			            FileWriter writer = new FileWriter("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file2.txt", true);
			            BufferedWriter bufferedWriter = new BufferedWriter(writer);
			            bufferedWriter.write(bufferedReader);
			            bufferedWriter.newLine();
			            bufferedWriter.write("See You Again!");
			 
			            bufferedWriter.close();
			        } catch (IOException e) {
			            e.printStackTrace();
			        }
				
			}
			
			
			String line1,line2,line3;
			
			
            while ((line1 = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
		
		
		} catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
}
