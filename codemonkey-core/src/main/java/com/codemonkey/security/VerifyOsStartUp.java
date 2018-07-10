package com.codemonkey.security;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
  
/**  
 * 系统启动安全设置  
 * @author lintao  
 *  
 */  
public class VerifyOsStartUp {   
  
    /**   
     *  获取当前操作系统名称.   
     *  return 操作系统名称 例如:windows,Linux,Unix等.   
    */     
   public static String getOSName() {     
       return System.getProperty("os.name").toLowerCase();     
   }   
   
	public static List<String> getAllMacAddresses(){  
	    List<String> addresses = new ArrayList<String>();  
	    try  
	    {  
	        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();  
	        while(networkInterfaces.hasMoreElements())  
	        {  
	            NetworkInterface ni = networkInterfaces.nextElement();
	            byte[] mac = ni.getHardwareAddress();  
	            if(mac != null && mac.length != 0)  
	            {  
	                System.out.println(ni.getDisplayName()+ hexConverter(mac));
	                System.out.println(MD5Maker.GetMd5(ni.getDisplayName()+ hexConverter(mac)));
	                addresses.add(MD5Maker.GetMd5(ni.getDisplayName()+ hexConverter(mac)));  
	            }  
	        }  
	    }  
	    catch(SocketException e)  
	    {  
	        e.printStackTrace();  
	    }  
	  
	    return addresses;  
	}     
      
	public static String hexConverter(byte[] mac) {
		StringBuffer sb = new StringBuffer();
		if (mac != null && mac.length != 0) {
			sb.delete(0, sb.length());
			for (byte b : mac) {
				String hexString = Integer.toHexString(b & 0xFF);
				sb.append((hexString.length() == 1) ? "0" + hexString: hexString);
				sb.append("-");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString().toUpperCase();
	}
     /**   
      * 测试用的main方法.   
      *    
      * @param argc   
      *        运行参数.   
      */     
     public static void main(String[] argc) {     
    	List<?> list = getAllMacAddresses();
     	for(int i=0;i<=list.size()-1;i++){
     		System.out.println("mac地址为：" + list.get(i));
     	}  
     }     
}  
