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
	    }
	    
	    /**
	     * Sorteia um arquivo para que as operações sejam realizadas!
	     * @return string identica ao conteúdo presente no arquivo sorteado!
	     */
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
	    
	    //SORTEIA QUAL THREAD SERÁ ESCOLHIDA PARA SER USADA POR TAL PROGRAMA, ENFIM!
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
	   
	   //estas devem existir SEMPRE!
	   static void threadsDeSincronizacao(){
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
		   	Thread s2 = new Thread(sync2);
		   	Thread s3 = new Thread(sync3);

		   	  arrayDeSincronizacao.add(s1);
		      arrayDeSincronizacao.add(s2);
		      arrayDeSincronizacao.add(s3);
	   }
	   
	   
	    static class Read extends Operations implements Runnable {
	      	
	    	private String readable;
	    	private String filePath;

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
	            	//Acquire section
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
//	    	private Sync sync = new Sync();
	    	
	    	public Write() {
	    		this.writable = writable;
	    		this.filePath = filePath;
//	    		this.sync = sync;
	    	}
	    	
	    	//pega o conteudo que será copiado e colado no novo arquivo
	    	public String getWritable() {
	    		return writable;
	    	}
	    	public void setWritable(String newWritable) {
	    		this.writable = newWritable;
	    	}
	    	//pega o conteúdo do arquivo que terá as informações escritas
	    	public String getFilePath() {
	    		return filePath;
	    	}
	    	public void setFilePath(String newFilePath) {
	    		this.filePath = newFilePath;
	    	}
//	    	public Sync getSync() {
//	    		return sync;
//	    	}
//	    	public void setSync(Sync sync) {
//	    		this.sync = sync;
//	    	}
	    	
			@Override
	    	public void run() {
	    		try {
	    			writeLock.acquire();
	    			System.out.println("Thread "+Thread.currentThread().getName() + " está ESCREVENDO");
	    			writeStringAtFile(getWritable(),getFilePath());
	    			Thread.sleep(2500);
	    			System.out.println("Thread "+Thread.currentThread().getName() + " finalizou operação ESCRITA");
	    			writeLock.release();
	    		} catch (InterruptedException e) {
	    			System.out.println(e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    }

    	private class FileHolder extends TextFile {   //semelhante a classe stickholder
    		private TextFile text = new TextFile();
    		private BlockingQueue<TextFile> holder = new ArrayBlockingQueue<>(1);
    		
    		public FileHolder() { putDown(); }
    		public void pickUp() {
    			try {
    				holder.take();
    			} catch(InterruptedException e) {
    				throw new RuntimeException(e);
    			}
    		}
    		public void putDown() {
    			try {
    				holder.put(text);
    			} catch(InterruptedException e) {
    				throw new RuntimeException(e);
    			}
    		}
    	}
	    
	    static class Sync extends Operations implements Runnable {		//semelhante a classe Philosopher
	    	
	    	private String textoOriginal;
	    	private String copia1;
	    	private String copia2;
	    	
//	    	private FileHolder copy2;
	    	
	    	public String getTextoOriginal() {
				return textoOriginal;
			}

			public void setTextoOriginal(String textoOriginal) {
				this.textoOriginal = textoOriginal;
			}

			public Sync() {
	    		this.textoOriginal = textoOriginal;
	    		this.copia1 = copia1;
	    		this.copia2 = copia2;
	    	}
	    	
	    	public String getCopia1() {
				return copia1;
			}

			public void setCopia1(String copia1) {
				this.copia1 = copia1;
			}

			public String getCopia2() {
				return copia2;
			}

			public void setCopia2(String copia2) {
				this.copia2 = copia2;
			}

			@Override
	    	public void run() {

	    		while(true) {	    		
	    			CopyFileIntoAnother(textoOriginal,copia1);
	    			System.out.println("texto"+textoOriginal+"copiado com sucesso em copia:"+copia1+"\n");
	    			CopyFileIntoAnother(textoOriginal,copia2);
	    			System.out.println("texto"+textoOriginal+"copiado com sucesso em copia"+copia2+"\n");
	    			System.out.println("Sincronizacao realizada com sucesso!");
	    		}
	    	}
	    }
	    
	    static class SyncPhilosophers {
	    	private FileHolder[]  fh ;   //fh  = fileholder
	    	private Sync[] syncronizers ;
	    	
	    	public SyncPhilosophers(int n) {
	    		fh = new FileHolder[n];
	    		Arrays.setAll(fh, i -> new FileHolder());
	    		syncronizers = new Sync[n];
	    		Arrays.setAll(syncronizers, i -> new Sync(i,File));
	    		
	    	}
	    }
	    
	    /**
	     * EM CADA EXECUÇÃO DO PROGRAMA APENAS UM ARQUIVO É SELECIONADO COMO FONTE DE LEITURA PARA QUE AS THREADS SEJAM EXECUTADAS!
	     * @param args
	     * @throws Exception
	     */
	    public static void main(String[] args) throws Exception {
	    	
	    	manageTextFiles();
	    	manageThreads();
	    	threadsDeSincronizacao();
	    	
	    	int resultado_rand = sortOut();

	    	String fileContent = listaDeConteudo.get(resultado_rand).getContent(); //pega o conteúdo armazenado no arquivo sorteado, isto será útil para ler, porém não para escrever
	    	String filePath = listaDeConteudo.get(resultado_rand).getFilePath();  //pega o caminho do diretório
	    	
	    	//Executa um numero minimo de vezes, min = 3;
	    	int min = 4;//(sortOut() +1)*3;
	    	
	    	//aqui as threads de leitura e escrita executarão seus serviços
	    	for(int i = 0; i < min; i++) {
	    		int resultado_thread = sortOutPlus11(); //seleciona um numero de 0 a 11 para selecionar qual thread entrará em acao
//	    		int resultado_thread_sync = sortOut();	
	    		Thread random_thread = arrayDeThreads.get(resultado_thread);
	    		random_thread.get
	    		random_thread.start();
	    	}
	    	
//	    	Read read = new Read();	
//	    	Write write = new Write();
//	    	Sync sync = new Sync();

	    	read.setReadable(fileContent);
	    	read.setFilePath(filePath);
	    	
	    	write.setWritable(fileContent);
	    	write.setFilePath(filePath);
//	    	write.setSync(sync);
	    	
	    	System.out.println("setWritable:"+write.getWritable()+"\n");
	    	System.out.println("setFilePath:"+write.getFilePath()+"\n");
	    	
//como vamos fazer pra randomizar a criação de threads?!	    	
//PROBLEMA : APENAS THREADS DE LEITURA ESTÃO SENDO EXECUTADAS!
// TEMOS OUTRO PROBLEMA AS THREADS ESTÃO ESCREVENDO EM SI MESMAS.	    	
/*	    	Thread t1 = new Thread(read);
	    	t1.setName("thread1");
	    	Thread t2 = new Thread(read);
	    	t2.setName("thread2");
	        Thread t3 = new Thread(read);
	        t3.setName("thread3");
	        Thread t4 = new Thread(read);
	        t4.setName("thread4");
	        Thread t5 = new Thread(write);
	        t5.setName("thread5");
	        Thread t6 = new Thread(write);
	        t6.setName("thread6");
	        Thread t7 = new Thread(write);
	        t7.setName("thread7");
	        Thread t8 = new Thread(write);
	        t8.setName("thread8");*/
//	        Thread t9 = new Thread(sync);
//	        t9.setName("thread9");
	        
/*	        t1.start();
	    	t2.start();
	        t3.start();
	        t4.start(); 
	        t5.start();
	        t6.start();
	        t7.start();
	        t8.start(); */
//	        t9.start();
	    }
	}


