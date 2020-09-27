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
	        System.out.println("Operação de escrita realizada com sucesso!.");
	      } catch (IOException e) {
	        System.out.println("Ocorreu um erro no método de escrita.");
	        e.printStackTrace();
	      }
 	}
	
	/**
	 * O objetivo dessa thread é copiar o conteudo do arquivo original que sofreu alteracoes mais recentes para um arquivo que ainda nao foi sincronizado
	 * @param filePath1	arquivo que sofreu alteracoes mais recentes
	 * @param filePath2	arquivo a ser atualizado
	 */
	public void CopyFileIntoAnother(String filePath1, String filePath2) {
		try {
//String s = readFileAsString(filePath1);
			FileWriter myWriter = new FileWriter(filePath2);
			myWriter.write(readFileAsString(filePath1));
			myWriter.close();
			System.out.println("\nArquivo"+"\n"+filePath1+"\n"+"copiado com sucesso em: "+"\n"+filePath2+"e em:"+filePath3);
		} catch (IOException e) {
			System.out.println("Um erro ocorreu no método sync.");
			e.printStackTrace();
		} 
	}
}
