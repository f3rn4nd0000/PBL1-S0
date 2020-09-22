package package1;
import java.io.*;
import java.util.*;
//import java.io.BufferedReader;
//import java.io.FileReader;
import java.util.concurrent.*;


public class Principal {
	    static Semaphore readLock = new Semaphore(1);
	    static Semaphore writeLock = new Semaphore(1);
	    static int readCount = 0;
	    static Operations op = new Operations();
	    private static LinkedList<TextFile> listaDeConteudo = new LinkedList<TextFile>(); // aqui era <String>
	    static TextFile f1,f2,f3;
	    static TextFile tocopy = new TextFile();
	    
	    public static void manageTextFiles() throws IOException {
	    	
	    	f1 = new TextFile();
	    	f2 = new TextFile();
	    	f3 = new TextFile();
	    	
	    	f1.setFilePath("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file1.txt");
	    	f2.setFilePath("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file2.txt");
	    	f3.setFilePath("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file3.txt");
	
	    	f1.setContent(op.readFileAsString("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file1.txt"));
	    	f2.setContent(op.readFileAsString("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file2.txt"));
	    	f3.setContent(op.readFileAsString("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file3.txt"));
	    
	    	listaDeConteudo.add(f1);
	    	listaDeConteudo.add(f2);
	    	listaDeConteudo.add(f3);
	    	
//	    	System.out.println("aqui"+"\n"+listaDeConteudo.get(0).getFilePath());
//	    	System.out.println("\nget1"+"\n"+listaDeConteudo.get(1));
//	    	System.out.println("\nget2"+"\n"+listaDeConteudo.get(2));
	    	
	    }
	    
	    public static String manageCopyText() throws IOException {
	    	return op.readFileAsString("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\tocopy.txt");
	    }
	    
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
	    static TextFile sortOut() {
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
	   }
	    
	    static class Read extends Operations implements Runnable {
	      	
	    	private String readable;
//	    	Read read = new Read();
	    	
	    	//construtor
//	    	public Read(String readable2) { 
//	    		this.readable = readable2;
//	    	}
	    	
	    	public void setReadable(String readable2) {
	    		this.readable = readable2;
	    	}
	    		
	    	public String getReadable() {
	    		return readable;
	    	}

	    	@Override
	        public void run() {
	            try {
	                //Acquire Section
	                readLock.acquire();
	                readCount++;
	                if (readCount == 1) {
	                    writeLock.acquire();
	                }
	                readLock.release();
	                //Reading section
	                System.out.println("Thread "+Thread.currentThread().getName() + " is READING");
	                System.out.println(this.getReadable());
	                Thread.sleep(1500);
  	                System.out.println("Thread "+Thread.currentThread().getName() + " has FINISHED READING");

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
	    	
	    	String writable;
	    	String filePath;
//	    	public Write() {
//	    		this.writable = writable;
//	    	}
	    	
	    	public String getWritable() {
	    		return writable;
	    	}
	    	
	    	public void setWritable(String writable) {
	    		this.writable = writable;
	    	}
	    	
	    	public String getFilePath() {
	    		return filePath;
	    	}
	    	
	    	public void setFilePath(String filePath) {
	    		this.filePath = filePath;
	    	}
	    	
	    	@Override
	    	public void run() {
	    		try {
	    			writeLock.acquire();
	    			System.out.println("Thread "+Thread.currentThread().getName() + " is WRITING");
	    			this.writeStringAtFile(this.getWritable(), this.getFilePath());
	    			Thread.sleep(2500);
	    			System.out.println("Thread "+Thread.currentThread().getName() + " has finished WRITING");
	    			
//	    			Sync sync = new Sync();
//	    			Thread t5 = new Thread(sync);
//	    			t5.setName("thread5");
//	    			t5.start();
	    			writeLock.release();
	    		} catch (InterruptedException e) {
	    			System.out.println(e.getMessage());
	    		}
	    	}
	    }
	    
	    static class Sync implements Runnable {
	    	
			@Override
			public void run() {
				  try {
					  	readLock.acquire();
		                writeLock.acquire();
		                System.out.println("Thread "+Thread.currentThread().getName() + " is SYNCHRONIZING");
		                Thread.sleep(2500);
		                System.out.println("Thread "+Thread.currentThread().getName() + " has finished SYNCHRONIZING");
		                writeLock.release();
		            } catch (InterruptedException e) {
		                System.out.println(e.getMessage());
		            } 
			}
	    }

	    public static void main(String[] args) throws Exception {
	    	manageTextFiles();
	    	manageCopyText();
	    //	addContent();
	    	//The operations must be realized with the file that will be sorted in the method below!!!
	    	String fileContent = sortOut().content; //pega o conteúdo armazenado no arquivo sorteado
	    	String filePath = sortOut().filePath;	//pega o caminho do diretório
//	    	System.out.println("fileContent:\n"+fileContent);	//só pra conferir
//	    	System.out.println("filePath:\n"+filePath);	//só pra conferir
	    	Read read = new Read();	//passo um construtor com parâmetro para poder ser lido pela classe
	    	Write write = new Write();
	    	//aqui determinamos que as operacoes só serão realizadas no arquivo que foi sorteado
	    	read.setReadable(fileContent);
	    	write.setWritable(fileContent);
	    	write.setFilePath(filePath);
	    	
//	        Thread t3 = new Thread(write);
//	        t3.setName("thread3");
	        Thread t1 = new Thread(read);
	        t1.setName("thread1");
	        Thread t2 = new Thread(write);
	        t2.setName("thread2");
//	        Thread t4 = new Thread(read);
//	        t4.setName("thread4");
	        t1.start();
//	        t3.start();
	        t2.start();
//	        t4.start();
	    }
	}

