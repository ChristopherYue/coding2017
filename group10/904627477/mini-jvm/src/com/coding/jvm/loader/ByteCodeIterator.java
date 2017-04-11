package com.coding.jvm.loader;

public class ByteCodeIterator {
	
	private byte[] codes;
	private int index;
	
	
	public ByteCodeIterator(byte[] codes) {
		super();
		this.codes = codes;
		this.index = 0;
	}
	
	public void skip(int index){
		this.index = index;
	}

	public byte[] read(int length){
		if(codes==null||length<0){
			throw new IllegalArgumentException();
		}
		if(!has(length)){
			length = codes.length - index;
		}
		byte[] result = new byte[length];
		for(int i=0;i<length;i++){
			result[i] = codes[index+i];
		}
		index = index + length;
		return result;
	}
	
	public boolean has(int length){
		if(codes==null||length<0){
			throw new IllegalArgumentException();
		}
		if(index+length<=codes.length){
			return true;
		}else{
			return false;
		}
	}
	

}
