/**
 * 
 */
package utils;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 */
public class Helper {
	public static byte[] hexStringToByteArray(String s) {
		byte[] b = new byte[s.length() / 2];
		for (int i = 0; i < b.length; i++) {
			int index = i * 2;
			int v = Integer.parseInt(s.substring(index, index + 2), 16);
			b[i] = (byte) v;
		}
		return b;
	}
	public static String fillString(char fillChar, int count){
		// creates a string of 'x' repeating characters
		char[] chars = new char[count];
		while (count>0) chars[--count] = fillChar;
		return new String(chars);
	}
	 public static String AsciiStringToString(String content) {//ASCII码字符串转数字字符串
	        String result = "";
	        int length = content.length() / 2;
	        for (int i = 0; i < length; i++) {
	            String c = content.substring(i * 2, i * 2 + 2);
	            int a = hexStringToAlgorism(c);
	            char b = (char) a;
	            String d = String.valueOf(b);
	            result += d;
	        }
	        return result;
	    }
	 public static int hexStringToAlgorism(String hex) {
	        hex = hex.toUpperCase();
	        int max = hex.length();
	        int result = 0;
	        for (int i = max; i > 0; i--) {
	            char c = hex.charAt(i - 1);
	            int algorism = 0;
	            if (c >= '0' && c <= '9') {
	                algorism = c - '0';
	            } else {
	                algorism = c - 55;
	            }
	            result += Math.pow(16, max - i) * algorism;
	        }
	        return result;
	    }
	public static Map<String,Socket> socketMap=new HashMap<String,Socket>();
}
