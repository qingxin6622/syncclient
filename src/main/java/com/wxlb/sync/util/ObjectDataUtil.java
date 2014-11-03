package com.wxlb.sync.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectDataUtil {
	
	
	public static <T extends Serializable> byte[] object2bytes(T object) throws IOException{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(bout);
		oout.writeObject(object);
		return bout.toByteArray();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T bytes2object(byte[] bytes) throws IOException,ClassNotFoundException{
		if( bytes == null){
			return null;
		}
		ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
		ObjectInputStream oin = new ObjectInputStream(bin);
		return (T)oin.readObject();
	}

}
