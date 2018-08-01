package com.wzlab.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author wzlab
 *
 */
public class Server {
	public static void main(String[] args){
		int i = 0;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(9000);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(true){
		try {
			
			System.out.println("服务器即将启动，等待客户端连接...");
			Socket socket = serverSocket.accept();
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bf = new BufferedReader(isr);
			String request = null;
			while((request=bf.readLine())!=null){
				System.out.println("客户端说："+request);
				i++;
			}
			socket.shutdownInput();
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(os);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pw.write("^FT==OK");
			pw.flush();
			System.out.println("我回复了");
			pw.close();
			os.close();
			bf.close();
			isr.close();
			is.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
}
