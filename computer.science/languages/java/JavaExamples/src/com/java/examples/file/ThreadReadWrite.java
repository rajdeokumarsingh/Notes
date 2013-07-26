package com.java.examples.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ThreadReadWrite {

	public static void main(String[] args) {
		deleteFile();
		WriterThread wt = new WriterThread();
		wt.start();
		ReaderThread rt = new ReaderThread();
		rt.start();
	}
	
	static void deleteFile() {
		File file = new File("/tmp/thread.read.write");
		if (file.exists()) {
			file.delete();
		}
	}
	
	static File createFile() {
		File file = new File("/tmp/thread.read.write");
		if (file.exists()) {
			return file;
		}

		try {
			if (file.createNewFile()) {
				System.out.println("create file ok");
			} else {
				System.out.println("create file failed!");
				file = null;
			}
		} catch (IOException e) {
			file = null;
			e.printStackTrace();
		}
		return file;
	}


}

class WriterThread extends Thread {
	@Override
	public void run() {
		String line = "test file \n";
		FileWriter fwriter = null;
		try {
			fwriter = new FileWriter(ThreadReadWrite.createFile());
			for (int i = 0; i < 10; i++) {
				fwriter.write(i + " " + line);
				fwriter.flush();
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fwriter.close();
			System.out.println("write done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class ReaderThread extends Thread {
	@Override
	public void run() {
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader(ThreadReadWrite.createFile());
			br = new BufferedReader(fr, 1024);
			for (int i = 0; i < 20; i++) {
				System.out.println(br.readLine());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			fr.close();
			br.close();
			fr = null;
			br = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
