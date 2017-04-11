package com.coding.jvm.loader;

import com.coding.jvm.clz.AccessFlag;
import com.coding.jvm.clz.ClassFile;
import com.coding.jvm.clz.ClassIndex;
import com.coding.jvm.constant.ClassInfo;
import com.coding.jvm.constant.ConstantInfo;
import com.coding.jvm.constant.ConstantPool;
import com.coding.jvm.constant.DoubleInfo;
import com.coding.jvm.constant.FieldRefInfo;
import com.coding.jvm.constant.FloatInfo;
import com.coding.jvm.constant.IntegerInfo;
import com.coding.jvm.constant.InterfaceMethodRefInfo;
import com.coding.jvm.constant.LongInfo;
import com.coding.jvm.constant.MethodRefInfo;
import com.coding.jvm.constant.NameAndTypeInfo;
import com.coding.jvm.constant.NullConstantInfo;
import com.coding.jvm.constant.StringInfo;
import com.coding.jvm.constant.UTF8Info;
import com.coding.util.Util;


public class ClassFileParser {

	//Packages a byte array into a ClassFile object
	public ClassFile parse(byte[] codes) {
		if(codes==null){
			return null;
		}
		ByteCodeIterator codeIter = new ByteCodeIterator(codes);
		if(!isClassByteArray(codeIter)){
			return null;
		}
		ClassFile claFile = new ClassFile();
		claFile.setMinorVersion(Util.getInt(codeIter.read(2)));
		claFile.setMajorVersion(Util.getInt(codeIter.read(2)));
		claFile.setConstPool(parseConstantPool(codeIter));
		claFile.setAccessFlag(parseAccessFlag(codeIter));
		claFile.setClassIndex(parseClassInfex(codeIter));
		return claFile;
	}
	
	

	private boolean isClassByteArray(ByteCodeIterator codeIte) {
		if(codeIte.has(4)){
			String magic = Util.getHexString(codeIte.read(4));
			if("cafebabe".equals(magic.toLowerCase())){
				return true;
			}
		}
		return false;
	}



	private AccessFlag parseAccessFlag(ByteCodeIterator iter) {
		AccessFlag accessFlag = new AccessFlag(Util.getInt(iter.read(2)));
		return accessFlag;
	}

	private ClassIndex parseClassInfex(ByteCodeIterator iter) {
		ClassIndex claIndex = new ClassIndex();
		claIndex.setThisClassIndex(Util.getInt(iter.read(2)));
		claIndex.setSuperClassIndex(Util.getInt(iter.read(2)));
		return claIndex;

	}

	private ConstantPool parseConstantPool(ByteCodeIterator iter) {
		int constantNum = Util.getInt(iter.read(2))-1;
		ConstantPool pool = new ConstantPool();
		pool.addConstantInfo(new NullConstantInfo());
		while(constantNum>0){
			int type = Util.getInt(iter.read(1));
			switch (type) {
			case 1:
				UTF8Info utf8Info = new UTF8Info(pool);
				int len = Util.getInt(iter.read(2));
				utf8Info.setLength(len);
				utf8Info.setValue(new String(iter.read(len)));
				pool.addConstantInfo(utf8Info);
				break;
			case 3:
				IntegerInfo intInfo = new IntegerInfo(pool);
				intInfo.setValue(Util.getInt(iter.read(4)));
				pool.addConstantInfo(intInfo);
				break;
			case 4:
				FloatInfo floatInfo = new FloatInfo(pool);
				floatInfo.setValue(Util.getFloat(iter.read(4)));
				pool.addConstantInfo(floatInfo);
				break;
			case 5:
				LongInfo longInfo = new LongInfo(pool);
				longInfo.setValue(Util.getLong(iter.read(8)));
				pool.addConstantInfo(longInfo);
				break;
			case 6:
				DoubleInfo doubleInfo = new DoubleInfo(pool);
				doubleInfo.setValue(Util.getDouble(iter.read(8)));
				pool.addConstantInfo(doubleInfo);
				break;
			case 7:
				ClassInfo classInfo = new ClassInfo(pool);
				classInfo.setUtf8Index(Util.getInt(iter.read(2)));
				pool.addConstantInfo(classInfo);
				break;	
			case 8:
				StringInfo strInfo = new StringInfo(pool);
				strInfo.setIndex(Util.getInt(iter.read(2)));
				pool.addConstantInfo(strInfo);
				break;
			case 9:
				FieldRefInfo frefInfo = new FieldRefInfo(pool);
				frefInfo.setClassInfoIndex(Util.getInt(iter.read(2)));
				frefInfo.setNameAndTypeIndex(Util.getInt(iter.read(2)));
				pool.addConstantInfo(frefInfo);
				break;
			case 10:
				MethodRefInfo mrefInfo = new MethodRefInfo(pool);
				mrefInfo.setClassInfoIndex(Util.getInt(iter.read(2)));
				mrefInfo.setNameAndTypeIndex(Util.getInt(iter.read(2)));
				pool.addConstantInfo(mrefInfo);
				break;
			case 11:
				InterfaceMethodRefInfo imrefInfo = new InterfaceMethodRefInfo(pool);
				imrefInfo.setClassInfoIndex(Util.getInt(iter.read(2)));
				imrefInfo.setNameAndTypeIndex(Util.getInt(iter.read(2)));
				pool.addConstantInfo(imrefInfo);
				break;
			case 12:
				NameAndTypeInfo ntInfo = new NameAndTypeInfo(pool);
				ntInfo.setIndex1(Util.getInt(iter.read(2)));
				ntInfo.setIndex2(Util.getInt(iter.read(2)));
				pool.addConstantInfo(ntInfo);
				break;
			default:
				break;
			}
			constantNum--;
		}
		return pool;
	}

	
}
