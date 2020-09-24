package package1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class Operations {
	
/**
 * @param filePath
 * @return String contendo os dados no arquivo cujo caminho do diretório é filePath
 * @throws IOException
 */
  	public String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        FileReader file_r = new FileReader(filePath);
        BufferedReader reader = new BufferedReader(file_r);
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
	}
	
  	/**
  	 * Escreve a string s no arquivo cujo diretório é igual a filePath
  	 * @param s string a ser preenchida no
  	 * @param filePath arquivo de endereço filepath = "C:\blabla"
  	 */
	public void writeStringAtFile(String s, String filePath) throws IOException {	
	    try {
	        FileWriter myWriter = new FileWriter(filePath,true);
	        myWriter.write(s);
	        myWriter.close();
	        System.out.println("Successfully wrote to the file.");
	      } catch (IOException e) {
	        System.out.println("An error occurred on write method.");
	        e.printStackTrace();
	      }
 	}
	
	public void CopyFileIntoAnother(String filePath1, String filePath2) {
		try {
			//readFileAsString(filePath1);
			FileWriter myWriter = new FileWriter(filePath2);
			myWriter.write(readFileAsString(filePath1));
			myWriter.close();
			System.out.println("Successfully copied"+"\n"+filePath1+"\n"+" into"+"\n"+filePath2);
		} catch (IOException e) {
			System.out.println("An error occurred on sync method.");
			e.printStackTrace();
		}
		
		
	}
	
}
