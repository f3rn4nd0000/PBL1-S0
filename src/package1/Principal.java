package package1;
import java.io.*;
import java.util.*;
//import java.io.BufferedReader;
//import java.io.FileReader;
import java.util.concurrent.*;
//import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.lang.Object;

import package1.Operations;

public class Principal {
	    
		static Semaphore readLock = new Semaphore(1,true);
	    static Semaphore writeLock = new Semaphore(1,true);
	    static int readCount = 0;
	    static int syncCount = 0;
	    
	    static Operations op = new Operations();
	    private static LinkedList<TextFile> listaDeConteudo = new LinkedList<TextFile>(); // aqui era <String>
	    private static ArrayList<Thread> arrayDeThreads = new ArrayList<Thread>();
	    private static ArrayList<Thread> arrayDeSincronizacao = new ArrayList<Thread>();
	    
	    Write write = new Write();
	    Read read = new Read();
	    Sync sync = new Sync();
	    
	    static TextFile f1,f2,f3,f4;
	    static TextFile tocopy = new TextFile();
	
	    //este método instancia os 3 arquivos de texto
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

	    static int sortOut() {
	    	int rand;
	    	int max = 2; 
	        int min = 0; 
	        int range = max - min + 1; 
	  
	        rand = (int)(Math.random() * range) + min; 
	        return rand;
	   }
	    
	    static int sortOutPlus11(){
	    	int rand;
	    	int max = 11; 
	    	int min = 0; 
	    	int range = max - min + 1; 

	    	rand = (int)(Math.random() * range) + min; 
	    	return rand;
	    }
	    
	    static Thread sortOutThread() {
	    	int rand;
	    	int max = 2; 
	        int min = 0; 
	        int range = max - min + 1; 
	  
	             rand = (int)(Math.random() * range) + min; 
	        return arrayDeThreads.get(rand);
	   }
	    
	    static void manageThreads() {
		    //são determinadas durante as sessões que deverão ser criadas 4 threads de leitura e 4 de escrita
		   //aqui sao criadas 4 threads de leitura e 4 de escrita
		    Thread t1 = new Thread(read);
	    	t1.setName("thread de leitura");
	    	Thread t2 = new Thread(read);
	    	t2.setName("thread de leitura");
	        Thread t3 = new Thread(read);
	        t3.setName("thread de leitura");
	        Thread t4 = new Thread(read);
	        t4.setName("thread de leitura");
	        Thread t5 = new Thread(write);
	        t5.setName("thread de escrita");
	        Thread t6 = new Thread(write);
	        t6.setName("thread de escrita");
	        Thread t7 = new Thread(write);
	        t7.setName("thread de escrita");
	        Thread t8 = new Thread(write);
	        t8.setName("thread de escrita");
		   
	        arrayDeThreads.add(t1);
	        arrayDeThreads.add(t2);
	        arrayDeThreads.add(t3);
	        arrayDeThreads.add(t4);
	        arrayDeThreads.add(t5);
	        arrayDeThreads.add(t6);
	        arrayDeThreads.add(t7);
	        arrayDeThreads.add(t8);
	        
		   	Sync sync1 = new Sync();
		   	Sync sync2 = new Sync();
		   	Sync sync3 = new Sync();

		   	sync1.setTextoOriginal(f1.getContent());
		   	sync1.setCopia1(f2.getContent());
		   	sync1.setCopia2(f3.getContent());
		   	
		   	sync2.setTextoOriginal(f2.getContent());
		   	sync2.setCopia1(f1.getContent());
		   	sync2.setCopia2(f3.getContent());
		   	
		   	sync3.setTextoOriginal(f3.getContent());
		   	sync3.setCopia1(f2.getContent());
		   	sync3.setCopia2(f1.getContent());
		   	
		   	Thread s1 = new Thread(sync1);
		   	s1.setName("thread de sync");
		   	Thread s2 = new Thread(sync2);
		   	s2.setName("thread de sync");
		   	Thread s3 = new Thread(sync3);
		   	s3.setName("thread de sync");
		   	
		   	arrayDeThreads.add(s1);
		   	arrayDeThreads.add(s2);
		   	arrayDeThreads.add(s3);
		   
		   for(int i = 0; i < arrayDeThreads.size(); i++) {
			   System.out.println("thread "+i+"="+arrayDeThreads.get(i).getName()+"\n");
		   }
	   }

	    public static void main(String[] args) throws Exception {
	    	
	    	manageTextFiles();
	    	manageThreads();
//	    	threadsDeSincronizacao();
	    	int resultado_rand = sortOut();

	    	String fileContent = listaDeConteudo.get(resultado_rand).getContent(); //pega o conteúdo armazenado no arquivo sorteado, isto será útil para ler, porém não para escrever
	    	String filePath = listaDeConteudo.get(resultado_rand).getFilePath();  //pega o caminho do diretório
	    	read.setReadable(fileContent);
	    	read.setFilePath(filePath);
	    	write.setWritable(fileContent);
	    	write.setFilePath(filePath);
	    	sync.setTextoOriginal(filePath);
	    	//Executa um numero minimo de vezes, min = 3;
//	    	int min = 4;//(sortOut() +1)*3;
	    	
	    	//aqui as threads de leitura e escrita executarão seus serviços
	    	for(int i = 0; i < min; i++) {
	    		int resultado_thread = sortOutPlus11(); //seleciona um numero de 0 a 11 para selecionar qual thread entrará em acao
	    	}
//	    		int resultado_thread_sync = sortOut();	
//	    		Thread random_thread = arrayDeThreads.get(resultado_thread);
//	    		if(resultado_thread <= 3) {
//	    			 random_thread = new Thread(read);
//	    	    	 random_thread.setFilePath(filePath);
//	    		}
//	    		random_thread.start();
//	    	}
//	    	Read read = new Read();	
//	    	Write write = new Write();
//	    	Sync sync = new Sync();
//	    	write.setSync(sync);
	    	System.out.println("setWritable:"+write.getWritable()+"\n");
	    	System.out.println("setFilePath:"+write.getFilePath()+"\n");
	    }
	}


