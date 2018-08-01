/**
 * 
 */
package start;

import aos.framework.core.server.AOSServer;

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
		AOSServer aosServer = new AOSServer();
		aosServer.setWebContext("/cdz");
		aosServer.setPort(9090);
		aosServer.start();
	}

}
