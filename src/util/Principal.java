package util;
import java.io.*;
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
    private LinkedList fileList = new LinkedList();   
    TextFile file1 = new TextFile();
    
    
    
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
    
    
    static class Synchronize implements Runnable {
    	@Override
    	public  void run() {
    	try {
    			Read read = new Read();
    			Write write = new Write();
    			
    		} catch (InterruptedException e) {
            System.out.println(e.getMessage());
    		}
    	}
    }
    
    public static void main(String[] args) throws Exception {
        Read read = new Read();
        Write write = new Write();
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
