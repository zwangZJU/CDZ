/**
 * 
 */
package start;

import com.iotplatform.client.NorthApiException;

import aos.framework.core.server.AOSServer;
import utils.Request;

/**
 * @author Administrator
 *
 */
public class CDZStart {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				try {
					Thread.sleep(30000);
					String url = "http://localhost:9090/cdz/api/do.jhtml?router=appApiService.getAccessToken";
					//String juid= "2c058be38ab941daa186b9ee9aef52f8";
					//Request.sendPost(url,"");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}.start();
		
		new Thread() {
			public void run() {
				try {
					Thread.sleep(35000);
					try {
						String accessToken = ConnectPlatform.initPlatform();
						//Thread.sleep(30000);
						String url = "http://localhost:9090/cdz/api/do.jhtml?router=appApiService.getNBIoTAccessToken";
						//String juid= "2c058be38ab941daa186b9ee9aef52f8";
						Request.sendPost(url,"accessToken="+accessToken);
						//SubscribeNotifyResource.this.recvAddDeviceNotify(addDevice_NotifyMessage)
					} catch (NorthApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//String result = ConnectPlatform.getAccessTocken();
					//System.out.println(result);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}.start();
		
		
		AOSServer aosServer = new AOSServer();
		aosServer.setWebContext("/cdz");
		aosServer.setPort(9090);
		aosServer.start();
	}

}
