package package1;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;


/**
 * Classe principal do programa.
 * @author Fernando Mota Freitas
 *
 */
public class Principal {

	static Semaphore readLock = new Semaphore(1,true);
	static Semaphore writeLock = new Semaphore(1,true);
	static int readCount = 0;

	private static LinkedList<TextFile> listaDeConteudo = new LinkedList<TextFile>(); // aqui ser�o armazenados os arquivos de texto!
	
	static Operations op = new Operations();
	
	static Write write = new Write();
	static Read read = new Read();
	static Sync sync = new Sync();

	static TextFile f1,f2,f3; //arquivos de texto

	//este m�todo instancia os 3 arquivos de texto
	static void manageTextFiles() throws IOException {

		f1 = new TextFile();
		f2 = new TextFile();
		f3 = new TextFile();

		f1.setFilePath("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file1.txt");
		f2.setFilePath("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file2.txt");
		f3.setFilePath("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file3.txt");

		f1.setContent(op.readFileAsString(f1.getFilePath()));
		f2.setContent(op.readFileAsString(f2.getFilePath()));
		f3.setContent(op.readFileAsString(f3.getFilePath()));

		listaDeConteudo.add(f1);
		listaDeConteudo.add(f2);
		listaDeConteudo.add(f3);
	}

	
/**
 * M�todo mais usado durante o programa que realizar� os sorteios
 * @return  numero de 0 a 2
 */
	static int sortOut() {
		int rand;
		int max = 2; 
		int min = 0; 
		int range = max - min + 1; 

		rand = (int)(Math.random() * range) + min; 
		return rand;
	}


/**
 * M�todo para sortear alguma das threads que estar� inserida na array de threads
 * @return	n�mero de 0 a 7
 */
	static int sortOutPlus8() {
		int rand;
		int max = 7; 
		int min = 0; 
		int range = max - min + 1; 

		rand = (int)(Math.random() * range) + min; 
		return rand;
	}

	/**
	 * M�todo principal de execu��o do programa
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		manageTextFiles();

		//Abaixo estamos instanciando as 4 threads de leitura, 4 de escrita e 3 threads de sincroniza��o
		Thread t1 = new Thread(read);
		t1.setName("thread de leitura 1");
		Thread t2 = new Thread(read);
		t2.setName("thread de leitura 2");
		Thread t3 = new Thread(read);
		t3.setName("thread de leitura 3");
		Thread t4 = new Thread(read);
		t4.setName("thread de leitura 4");
		Thread t5 = new Thread(write);
		t5.setName("thread de escrita 1");
		Thread t6 = new Thread(write);
		t6.setName("thread de escrita 2");
		Thread t7 = new Thread(write);
		t7.setName("thread de escrita 3");
		Thread t8 = new Thread(write);
		t8.setName("thread de escrita 4");
		
		Thread[] arrayDeThread = new Thread[8];

		arrayDeThread[0] = t1;
		arrayDeThread[1] = t2;
		arrayDeThread[2] = t3;
		arrayDeThread[3] = t4;
		arrayDeThread[4] = t5;
		arrayDeThread[5] = t6;
		arrayDeThread[6] = t7;
		arrayDeThread[7] = t8;

		Sync sync1 = new Sync();
		Sync sync2 = new Sync();
		Sync sync3 = new Sync();

		sync1.setTextoOriginal(f1.getFilePath());
		sync1.setCopia1(f2.getFilePath());
		sync1.setCopia2(f3.getFilePath());

		sync2.setTextoOriginal(f2.getFilePath());
		sync2.setCopia1(f1.getFilePath());
		sync2.setCopia2(f3.getFilePath());

		sync3.setTextoOriginal(f3.getFilePath());
		sync3.setCopia1(f2.getFilePath());
		sync3.setCopia2(f1.getFilePath());

		Thread s1 = new Thread(sync1);
		s1.setName("thread de sync");
		Thread s2 = new Thread(sync2);
		s2.setName("thread de sync");
		Thread s3 = new Thread(sync3);
		s3.setName("thread de sync");

		int numero_de_execucoes = 1 + sortOut() + sortOut() ; //numero maximo de loops que o programa realiza = 4
		
		for(int i = 0 ; i < numero_de_execucoes; i++) {

			int resultado_rand = sortOut();

			String fileContent = listaDeConteudo.get(resultado_rand).getContent(); //pega o conte�do armazenado no arquivo sorteado, isto ser� �til para ler, por�m n�o para escrever
			String filePath = listaDeConteudo.get(resultado_rand).getFilePath();  //pega o caminho do diret�rio

			read.setReadable(fileContent);
			read.setFilePath(filePath);
			write.setWritable(fileContent);
			write.setFilePath(filePath);
			

			int min = (sortOut() +1)*3; //valor minimo de execucoes  =  3
			int num_threads = sortOut() + sortOut() + sortOut() ; //valor minimo de threads a serem executadas simultaneamente (=6) !
			Thread[] executingThread = new Thread[num_threads];

			for(int j = 0; j < min; j++) {
				for(int k = 0; k < num_threads; k++) {
					int resultado_thread = sortOutPlus8(); //seleciona um numero de 0 a 8 para selecionar qual thread entrar� em acao
					arrayDeThread[resultado_thread].start();
					//trecho abaixo garante que se uma thread de escrita executar, ent�o todas as threads de sincroniza��o tamb�m ir�o
					if(resultado_thread>=4) {
						if(resultado_rand==0) {	//resultado_rand � o numero que definir� qual dos tres arquivos ser�o realizadas opera��es de leitura/escita
							s1.start();
						}
						else if(resultado_rand==1) {
							s2.start();
						}
						else if(resultado_rand==2){
							s3.start();
						}
					}
				}
			}
		}
	}
}


