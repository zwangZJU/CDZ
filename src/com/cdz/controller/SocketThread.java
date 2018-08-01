/**
 * 
 */
package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Administrator
 *
 */
public class SocketThread extends Thread{
	private ServerSocket serverSocket = null;  
    
    public SocketThread(ServerSocket serverScoket){  
        try {  
            if(null == serverSocket){  
                this.serverSocket = new ServerSocket(5003);  
                System.out.println("socket start.................");  
            }  
        } catch (Exception e) {  
            System.out.println("SocketThread创建socket服务出错");  
            e.printStackTrace();  
        }  
  
    }  
      
    public void run(){  
        while(!this.isInterrupted()){ 
	             /*try {
					new MultiServerThread(serverSocket.accept()).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
            try {  
                Socket socket = serverSocket.accept();  
                socket.setSoTimeout(1000*60*60*50); 
                if(null != socket && !socket.isClosed()){     
                    //处理接受的数据  
                    new MultiServerThread(socket).start();  
                }  
                 
                  
            }catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  
      
      
    public void closeSocketServer(){  
       try {  
            if(null!=serverSocket && !serverSocket.isClosed())  
            {  
             serverSocket.close();
             System.out.println("关闭连接。。。。。。。。。。。");
            }  
       } catch (IOException e) {  
        // TODO Auto-generated catch block  
        e.printStackTrace();  
       }  
     } 
}
