package utils;

import java.security.KeyStore;

import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.ClientInfo;
import com.iotplatform.utils.PropertyUtil;

public class AuthUtil {
	
	private static NorthApiClient northApiClient = null;
	
	public static NorthApiClient initApiClient() {
		if (northApiClient != null) {
			return northApiClient;
		}
		northApiClient = new NorthApiClient();
			
        PropertyUtil.init("./src/resources/application.properties");
		
		ClientInfo clientInfo = new ClientInfo();
        clientInfo.setPlatformIp(PropertyUtil.getProperty("platformIp"));
        clientInfo.setPlatformPort(PropertyUtil.getProperty("platformPort"));
        clientInfo.setAppId(PropertyUtil.getProperty("appId"));
//        clientInfo.setSecret(PropertyUtil.getProperty("secret"));
        clientInfo.setSecret(PropertyUtil.getProperty("secret"));
        
        try {
			northApiClient.setClientInfo(clientInfo);
			northApiClient.initSSLConfig();
		} catch (NorthApiException e) {
			System.out.println(e.toString());
		}        
        
        return northApiClient;
    }
	
	public static String getAesPropertyValue(String propertyName) {
		String aesPwd = "123456"; //this is a test AES password
        
 //     String originalProperty = "gPnTWO52yrobtjyobykkf12P8f4a";
  //    byte[] temp = AesUtil.encrypt(originalProperty, aesPwd);
  //    String hexStrResult = HexParser.parseByte2HexStr(temp);
  //    System.out.println("encrypted secret hex sting is ："  + hexStrResult);
      
		PropertyUtil.init("./src/resources/application.properties");
		byte[] secret = HexParser.parseHexStr2Byte(PropertyUtil.getProperty(propertyName));
		return new String(AesUtil.decrypt(secret, aesPwd));
	}
}
