package package1;
import java.io.*;
import java.io.BufferedReader;
import java.util.*;
import java.util.concurrent.*;
//import java.util.concurrent.atomic.AtomicInteger;
import java.lang.Thread;
import java.lang.Object;

public class Principal {
 
    static Semaphore readLock = new Semaphore(1);
    static Semaphore writeLock = new Semaphore(1);
    static Semaphore synchronize = new Semaphore(1);	// criei classe synchronize que irá copiar o conteudo de uma file e colar em outra file
    static int readCount = 0;
    private LinkedList<TextFile> fileList = new LinkedList<TextFile>();   //aqui serão armazenados os arquivos de texto
    private TextFile file1,file2,file3;
    	
    
    
    //A ideia eh retornar uma string para usar como parametro em textfile.content! Aproveitar e usar tbm para o sync()!
	static String readFileAsString(String filePath) throws IOException {
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
    
	//método para armazenar o conteúdo de um arquivo na classe TextFile
     static void storeContent(String filePath, TextFile file_x) throws IOException {
    	 String s1 = readFileAsString(filePath);
    	 file_x.setContent(s1);    	 
    }
    
    
    static class Read implements Runnable {
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

    static class Write implements Runnable {
        @Override
        public void run() {
            try {
                writeLock.acquire();
                System.out.println("Thread "+Thread.currentThread().getName() + " is WRITING");
                Thread.sleep(2500);
                System.out.println("Thread "+Thread.currentThread().getName() + " has finished WRITING");
                writeLock.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
 /* static class Synchronize implements Runnable {
    	@Override
    	public  void run() {
    	try {
    			Read read = new Read();
    			Write write = new Write();
    			
    		} catch (InterruptedException e) {
            System.out.println(e.getMessage());
    		}
    	}
    } */
    
    private boolean checkLastUpdate() {
    	
    }
    
    public static void main(String[] args) throws Exception {
        
    	Read read = new Read();
        Write write = new Write();
        //Synchronize sync = new Synchronize();
        LinkedList<TextFile> fileList = new LinkedList<TextFile>();
        TextFile file1 = new TextFile();
        TextFile file2 = new TextFile();
        TextFile file3 = new TextFile();
        
        
        //armazena os conteúdos dos arquivos de texto nas classes!
        storeContent("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file1.txt",file1); 
        storeContent("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file2.txt",file2);
        storeContent("C:\\Users\\ffern\\eclipse-workspace\\OpenReadText\\src\\file3.txt",file3);
        
        
        System.out.println("conteudo do arquivo 1:"+file1.getContent());
        
        //adiciona as classes que representam os arquivos numa lista de arquivos!
        fileList.add(file1);
        fileList.add(file2);
        fileList.add(file3);
        
		/*
		 * Serão criadas 5 threads escritoras e 1 leitora, apenas 
		 * inicialmente, porém a ideia é usar 5 threads SINCRONIZADORAS
		 * 1 LEITORA E INDEFINIDAS ESCRITORAS
		 */        
        
        Thread t1 = new Thread(write);
        t1.setName("thread1");
        Thread t2 = new Thread(write);
        t2.setName("thread2");
        Thread t3 = new Thread(write);
        t3.setName("thread3");
        Thread t4 = new Thread(write);
        t4.setName("thread4");
        Thread t5 = new Thread(write);
        t5.setName("thread5");
        Thread t6 = new Thread(read);
        t6.setName("thread6");

        t6.start();
        t1.start();
       // t6.start();
        t3.start();
        t2.start();
        t4.start();
        t5.start();
/*
 * Observe que há várias threads escrevendo e que a thread de leitura 
 * sempre interrompe a execução destas, portanto cumprindo assim a sua função,
 * porém observe ainda que as threads precisam ter chamadas randômicas. As threads
 * de leitura e escrita porém só irão realizar estas operações em um dos arquivos escolhidos.        
 */
        
        
/*
 * Observe que as threads de leitura seguem a ordem 
 * estabelecida pela  escrita do código, porém as threads 
 * de escrita não o fazem        
 */
        
    }
}
