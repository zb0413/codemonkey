package com.codemonkey.security;

import java.security.MessageDigest;

/**
 * MD5加密  
 * @author lintao 
*/
public class MD5Maker{
     public MD5Maker()
     {
    	 
     }
     public static final String GetMd5(String s){
    	 try
    	 {
    		 char hexDigits[] = {
    	             '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
    	             'a', 'b', 'c', 'd', 'e', 'f'
    	         };
    	         char str[];
    	         byte strTemp[] = s.getBytes();
    	         MessageDigest mdTemp = MessageDigest.getInstance("MD5");
    	         mdTemp.update(strTemp);
    	         byte md[] = mdTemp.digest();
    	         int j = md.length;
    	         str = new char[j * 2];
    	         int k = 0;
    	         for (int i = 0; i < j; i++)
    	         {
    	             byte byte0 = md[i];
    	             str[k++] = hexDigits[byte0 >>> 4 & 0xf];
    	             str[k++] = hexDigits[byte0 & 0xf];
    	         }

    	         return new String(str);
    	 }
    	 catch(Exception e)
    	 {
    		 e.printStackTrace();
    	 }
        return "" ;
     }
     
     public static void main(String[] args) {
    	 String strMd5 = MD5Maker.GetMd5("Realtek RTL8139 Family PCI Fast Ethernet NIC00-E0-4C-16-FD-CC");
    	 //String strMd5 = MD5Maker.calculateMD5("405dd8ba42822efb2054f082a751293f");
    	 System.out.println("%%%%%  :    " + strMd5);
	 }
}