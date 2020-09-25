package package1;
import java.io.*;
import java.util.*;
//import java.io.BufferedReader;
//import java.io.FileReader;
import java.util.concurrent.*;
import package1.Operations;

public class Principal {
	    static Semaphore readLock = new Semaphore(1,true);
	    static Semaphore writeLock = new Semaphore(1,true);
	    static int readCount = 0;
	    static Operations op = new Operations();
	    private static LinkedList<TextFile> listaDeConteudo = new LinkedList<TextFile>(); // aqui era <String>
	    private static LinkedList<Thread> listaDeThreads = new LinkedList<Thread>();
	    static TextFile f1,f2,f3,f4;
	    static TextFile tocopy = new TextFile();
	    static Write write = new Write();
	    static Read read = new Read();
	    static Sync sync = new Sync();
	    
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
	    	
//	    	System.out.println("aqui"+"\n"+listaDeConteudo.get(0).getFilePath());
//	    	System.out.println("\nget1"+"\n"+listaDeConteudo.get(1).getFilePath());
//	    	System.out.println("\nget2"+"\n"+listaDeConteudo.get(2).getFilePath());
	    	
	    }
	   
	   static void manageToCopyFile() {
		   f4 = new TextFile();
		   f4.setFilePath("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\tocopy.txt");
	   }
	   
	    
//	    public static String manageCopyText() throws IOException {
//	    	return op.readFileAsString("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\tocopy.txt");
//	    }
	    
//	    public static void addContent() throws IOException {
//	    	listaDeConteudo.add(op.readFileAsString("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file1.txt"));
//	    	listaDeConteudo.add(op.readFileAsString("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file2.txt"));
//	    	listaDeConteudo.add(op.readFileAsString("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file3.txt"));
//	    	System.out.println("aqui"+"\n"+listaDeConteudo.get(0));
//	    	System.out.println("\nget1"+"\n"+listaDeConteudo.get(1));
//	    	System.out.println("\nget2"+"\n"+listaDeConteudo.get(2));
//	    }
	    
	    /**
	     * Sorteia um arquivo para que as operações sejam realizadas!
	     * @return string identica ao conteúdo presente no arquivo sorteado!
	     */
	    static int sortOut() {
//	    	String s1;
//	    	String s2;
	    	int rand;
	    	int max = 2; 
	        int min = 0; 
	        int range = max - min + 1; 
	  
//	        for (int i = 0; i < 10; i++) { 
	             rand = (int)(Math.random() * range) + min; 
//	            System.out.println("rand:"+rand); 
//	        }
	        return rand;
	   }

/* 	    static TextFile sortOut() {
//	    	String s1;
//	    	String s2;
	    	int rand;
	    	int max = 2; 
	        int min = 0; 
	        int range = max - min + 1; 
	  
//	        for (int i = 0; i < 10; i++) { 
	             rand = (int)(Math.random() * range) + min; 
//	            System.out.println("rand:"+rand); 
//	        }
	        return listaDeConteudo.get(rand);
	   } */
	    
	    //SORTEIA QUAL THREAD SERÁ ESCOLHIDA PARA SER USADA POR TAL PROGRAMA, ENFIM!
	    static Thread sortOutThread() {
	    	int rand;
	    	int max = 2; 
	        int min = 0; 
	        int range = max - min + 1; 
	  
//	        for (int i = 0; i < 10; i++) { 
	             rand = (int)(Math.random() * range) + min; 
//	            System.out.println("rand:"+rand); 
//	        }
	        return listaDeThreads.get(rand);
	   }
	    
	   static void manageThreads() {
		   
		   //são determinadas durante as sessões que deverão ser criadas 4 threads de leitura e 4 de escrita
		   for(int i = 0 ; i < 4; i++) {
	        	Thread t_read = new Thread(read);
	        	Thread t_write = new Thread(write);
	        	Thread t_sync = new Thread(sync);
	        	t_read.setName("thread de leitura"+i+1);
	        	t_write.setName("thread de escrita"+i+1);
	        	t_sync.setName("thread de sincronização"+i+1);
	        	listaDeThreads.add(t_read);
	        	listaDeThreads.add(t_write);
	        	listaDeThreads.add(t_sync);
	        }
	   }
	    
	    static class Read extends Operations implements Runnable {
	      	
	    	private String readable;
	    	private String filePath;
//	    	Read read = new Read();
	    	//construtor
//	    	public Read(String readable2) { 
//	    		this.readable = readable2;
//	    	}
	    	
	    	public void setReadable(String readable) {
	    		this.readable = readable;
	    		this.filePath = filePath;
	    	}
	    		
	    	public String getReadable() {
	    		return readable;
	    	}
	    	
	    	public void setFilePath(String filePath) {
	    		this.filePath = filePath;
	    	}
	    	
	    	public String getFilePath() {
	    		return filePath;
	    	}

	    	@Override
	        public void run() {
	            try {
	                readLock.acquire();
	                readCount++;
	                if (readCount == 1) {
	                    writeLock.acquire();
	                }
	                readLock.release();
	                //Reading section
	                System.out.println("Thread "+Thread.currentThread().getName() + " está LENDO");
//	                System.out.println(this.getReadable());
	                Thread.sleep(1500);
  	                System.out.println("Thread "+Thread.currentThread().getName() + " finalizou a operação de LEITURA");

	                //Releasing section
	                readLock.acquire();
	                readCount--;
	                if(readCount == 0) {
	                    writeLock.release();
	                }
	                readLock.release();
	            } catch (InterruptedException e) {
	                System.out.println(e.getMessage());
	            } 
	        }
	    }

	    static class Write extends Operations implements Runnable {
	    	
	    	private String writable;
	    	private String filePath;
	    	private Sync sync;
	    	
	    	
	    	public Write() {
	    		this.writable = writable;
	    		this.filePath = filePath;
	    		this.sync = sync;
	    	}
	    	
	    	public String getWritable() {
	    		return writable;
	    	}
	    	
	    	public void setWritable(String newWritable) {
	    		this.writable = newWritable;
	    	}
	    	
	    	public String getFilePath() {
	    		return filePath;
	    	}
	    	
	    	public void setFilePath(String newFilePath) {
	    		this.filePath = newFilePath;
	    	}
	    	
	    	public Sync getSync() {
	    		return sync;
	    	}
	    	
	    	public void setSync(Sync sync) {
	    		this.sync = sync;
	    	}
	    	
	    	/*
	    	 * Como fazer com que a primeira execucao seja de leitura do arquivo tocopy e as outras sejam aleatorias??? 
	    	 * OU
	    	 * Como garantir que filecopy seja constantemente atualizado???
	    	 * 
	    	 * Acho que vou ter que pedir para o usuário poder digitar o texto para que seja atualizado!
	    	 */

			@Override
	    	public void run() {
	    		try {
	    			writeLock.acquire();
	    			System.out.println("Thread "+Thread.currentThread().getName() + " está ESCREVENDO");
//	    			readFileAsString(getWritable());
	    			writeStringAtFile(getWritable(),getFilePath());
//	    			Sync sync = new Sync();
	    			
	    			getSync().CopyFileIntoAnother(getFilePath(), sync.getFilePath1());
	    			sync.CopyFileIntoAnother(getFilePath(), sync.getFilePath2());
	    			Thread.sleep(2500);
	    			System.out.println("Thread "+Thread.currentThread().getName() + " finalizou operação ESCRITA");
	    			
//	    			Sync sync = new Sync();
//	    			Thread t5 = new Thread(sync);
//	    			t5.setName("thread5");
//	    			t5.start();
	    			writeLock.release();
	    		} catch (InterruptedException e) {
	    			System.out.println(e.getMessage());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    }
	    
	    static class Sync extends Operations implements Runnable {
	    	
	    	private String filePath1;
	    	private String filePath2;
	    	
	    	public Sync() {
	    		this.filePath1 = filePath1;
	    		this.filePath2 = filePath2;
	    	}
	    	
	    	public String getFilePath1() {
	    		return filePath1;
	    	}
	    	
	    	public void setFilePath1(String filePath1) {
	    		this.filePath1 = filePath1;
	    	}
	    	
	    	public String getFilePath2() {
	    		return filePath2;
	    	}
	    	
	    	public void setFilePath2(String filePath2) {
	    		this.filePath2 = filePath2;
	    	}
	    	@Override
			public void run() {
				  try {
//					  	readLock.acquire();
		                writeLock.acquire();
		                System.out.println("Thread "+Thread.currentThread().getName() + " está SINCRONIZANDO");
		                Thread.sleep(2500);
		                CopyFileIntoAnother(getFilePath1(),getFilePath2());
		                System.out.println("Thread "+Thread.currentThread().getName() + " finalizou a operação de SINCRONIZAÇÃO");
		                writeLock.release();
		            } catch (InterruptedException e) {
		                System.out.println(e.getMessage());
		            } 
			}
	    }
	    
	    /**
	     * EM CADA EXECUÇÃO DO PROGRAMA APENAS UM ARQUIVO É SELECIONADO COMO FONTE DE LEITURA PARA QUE 
	     * @param args
	     * @throws Exception
	     */
	    public static void main(String[] args) throws Exception {
	    	
//	    	Principal execucao = new Principal();
	    	manageTextFiles();
	    	int resultado_rand = sortOut();
	    	System.out.println("resultado_rand:"+resultado_rand);
//	    	manageCopyText();
//	    	addContent();
//	    	The operations must be realized within the file that will be sorted in the method below!!!
	    	String fileContent = listaDeConteudo.get(resultado_rand).getContent(); //pega o conteúdo armazenado no arquivo sorteado, isto será útil para ler, porém não para escrever
	    	String filePath = listaDeConteudo.get(resultado_rand).getFilePath();	//pega o caminho do diretório
	 //   	op.readFileAsString(filePath);
//	    	System.out.println("fileContent:\n"+fileContent);	//só pra conferir
//	    	System.out.println("filePath:\n"+filePath);	//só pra conferir
	    	
	    	Read read = new Read();	
	    	Write write = new Write();
	    	Sync sync = new Sync();
	    	//aqui determinamos que as operacoes só serão realizadas no arquivo que foi sorteado

	    	read.setReadable(fileContent);
	    	read.setFilePath(filePath);
	    	
	    	write.setWritable(fileContent);
	    	write.setFilePath(filePath);
	    	
	    	sync.setFilePath1(listaDeConteudo.get(resultado_rand).getFilePath());
	    	sync.setFilePath2(listaDeConteudo.get(listaDeConteudo.size()-1-resultado_rand).getFilePath());
	    	
	    	//filepath3 que é o arquivo que ainda não foi atualizado será dado por (get(resultado_rand)+get(size()-1))/2
	    	
	    	//sync.setFilePath3((listaDeConteudo.get(listaDeConteudo.size()-1).getFilePath()+listaDeConteudo.get(resultado_rand).getFilePath())/2);
//	    	System.out.println("setWritable:"+write.getWritable()+"\n");
	    	System.out.println("setFilePath:"+write.getFilePath()+"\n");
//como vamos fazer pra randomizar a criação de threads?!	    	
//PROBLEMA : APENAS THREADS DE LEITURA ESTÃO SENDO EXECUTADAS!

// TEMOS OUTRO PROBLEMA AS THREADS ESTÃO ESCREVENDO EM SI MESMAS.	    	
	    	
	    	
	    	Thread t1 = new Thread(read);
	    	t1.setName("thread1");
	    	Thread t2 = new Thread(read);
	    	t2.setName("thread2");
//	        Thread t3 = new Thread(read);
//	        t3.setName("thread3");
//	        Thread t4 = new Thread(read);
//	        t4.setName("thread4");
	        
	        Thread t5 = new Thread(write);
	        t5.setName("thread5");
	        Thread t6 = new Thread(write);
	        t6.setName("thread6");
//	        Thread t7 = new Thread(write);
//	        t7.setName("thread7");
//	        Thread t8 = new Thread(write);
//	        t8.setName("thread8");
	        
//	        Thread t9 = new Thread(sync);
//	        t9.setName("thread9");
	        
	        t1.start();
	    	t2.start();
//	        t3.start();
//	        t4.start(); 
	        t5.start();
	        t6.start();
//	        t7.start();
//	        t8.start();
//	        t9.start();
	    }
	}


