/**
 * 
 */
package start;

import com.iotplatform.client.NorthApiException;

import aos.framework.core.server.AOSServer;
import service.NbIotService;
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
						String accessToken = NbIotService.initPlatform();
						String url = "http://localhost:9090/cdz/api/do.jhtml?router=nbIotService.getNBIoTAccessToken";	
						Request.sendPost(url,"accessToken="+accessToken);
				
					} catch (NorthApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
