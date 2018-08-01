package controller;
import java.io.IOException;
import java.net.ServerSocket;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Service;
public class ServerSocketListener implements ServletContextListener{

	
	 private SocketThread socketThread;
	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	 @Override  
	    public void contextDestroyed(ServletContextEvent arg0) {  
	        if(null!=socketThread && !socketThread.isInterrupted())  
	           {  
	            socketThread.closeSocketServer();  
	            socketThread.interrupt();  
	           }  
	    }  
	  
	    @Override  
	    public void contextInitialized(ServletContextEvent arg0) {  
	        // TODO Auto-generated method stub  
	        if(null==socketThread)  
	        {  
	         //新建线程类  
	         socketThread=new SocketThread(null);  
	         //启动线程  
	         socketThread.start();  
	        }  
	    }  
}
