package com.coding.util;

import java.nio.LongBuffer;


public class Util {
    
	public static String getHexString(byte[] codes ){
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<codes.length;i++){
			byte b = codes[i];
			int value = b & 0xFF;
			String strHex = Integer.toHexString(value);
			if(strHex.length()< 2){
				strHex = "0" + strHex;
			}		
			buffer.append(strHex);
		}
		return buffer.toString();
	}	
  
    public static int getInt(byte[] bytes) {
    	String hex = getHexString(bytes);
    	return Integer.parseInt(hex, 16);
    }  
  
    public static long getLong(byte[] bytes) {
    	bytes = fill(8, bytes);
        return (0xffL & (long) bytes[0]) | (0xff00L & ((long) bytes[1] << 8)) | (0xff0000L & ((long) bytes[2] << 16))  
                | (0xff000000L & ((long) bytes[3] << 24)) | (0xff00000000L & ((long) bytes[4] << 32))  
                | (0xff0000000000L & ((long) bytes[5] << 40)) | (0xff000000000000L & ((long) bytes[6] << 48))  
                | (0xff00000000000000L & ((long) bytes[7] << 56));  
    }  
  
    public static float getFloat(byte[] bytes) {
    	//bytes = fill(2, bytes);
        return Float.intBitsToFloat(getInt(bytes));  
    }  
  
    public static double getDouble(byte[] bytes) {  
        long l = getLong(bytes);  
        return Double.longBitsToDouble(l);  
    }  
  
    private static byte[] fill(int length,byte[] bytes){
    	int len = bytes.length;
    	if(len>=length){
    		return bytes;
    	}
    	byte[] newBytes = new byte[length];
    	for (int i = length-1,j = 1; i > 0; i--) {
    		if(len-j>=0){
    			newBytes[i] = bytes[len-j];
    			j++;
    		}else{
    			newBytes[i] = 0;
    		}
		}
    	return newBytes;
    }
    
    public static byte[] getBytes(long data) {  
        byte[] bytes = new byte[8];  
        bytes[0] = (byte) (data & 0xff);  
        bytes[1] = (byte) ((data >> 8) & 0xff);  
        bytes[2] = (byte) ((data >> 16) & 0xff);  
        bytes[3] = (byte) ((data >> 24) & 0xff);  
        bytes[4] = (byte) ((data >> 32) & 0xff);  
        bytes[5] = (byte) ((data >> 40) & 0xff);  
        bytes[6] = (byte) ((data >> 48) & 0xff);  
        bytes[7] = (byte) ((data >> 56) & 0xff);  
        return bytes;  
    }  
    
    public static void main(String[] args) {
		long l = 232l;
		String s = new String(getBytes(l));
		System.out.println(s);
	}
  
}
