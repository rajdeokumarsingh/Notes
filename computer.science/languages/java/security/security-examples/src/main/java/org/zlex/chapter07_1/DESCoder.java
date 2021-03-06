/**
 * 2009-10-5
 */
package org.zlex.chapter07_1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * DES安全编码组件
 * 
 * @author 梁栋
 * @version 1.0
 */
public abstract class DESCoder {

	private static final String PKEY_PATH = "/tmp/pkey";

	/**
	 * 密钥算法 <br>
	 * Java 6 只支持56bit密钥 <br>
	 * Bouncy Castle 支持64bit密钥
	 */
	public static final String KEY_ALGORITHM = "DES";

	/**
	 * 加密/解密算法 / 工作模式 / 填充方式
	 */
	public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5PADDING";

	/**
	 * 转换密钥
	 * 
	 * @param key
	 *            二进制密钥
	 * @return Key 密钥
	 * @throws Exception
	 */
	private static Key toKey(byte[] key) throws Exception {

		// 实例化DES密钥材料
		DESKeySpec dks = new DESKeySpec(key);

		// 实例化秘密密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory
				.getInstance(KEY_ALGORITHM);

		// 生成秘密密钥
		SecretKey secretKey = keyFactory.generateSecret(dks);

		return secretKey;
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {

		// 还原密钥
		Key k = toKey(key);

		// 实例化
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

		// 初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, k);

		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {

		// 还原密钥
		Key k = toKey(key);

		// 实例化
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

		// 初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, k);

		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 生成密钥 <br>
	 * Java 6 只支持56bit密钥 <br>
	 * Bouncy Castle 支持64bit密钥 <br>
	 * 
	 * @return byte[] 二进制密钥
	 * @throws Exception
	 */
	public static byte[] initKey() throws Exception {
		if(isFileExist()) {
			return readPKey();
		}
		
		/*
		 * 实例化密钥生成器
		 * 
		 * 若要使用64bit密钥注意替换 将下述代码中的KeyGenerator.getInstance(CIPHER_ALGORITHM);
		 * 替换为KeyGenerator.getInstance(CIPHER_ALGORITHM, "BC");
		 */
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

		/*
		 * 初始化密钥生成器 若要使用64bit密钥注意替换 将下述代码kg.init(56); 替换为kg.init(64);
		 */
		kg.init(56, new SecureRandom());

		// 生成秘密密钥
		SecretKey secretKey = kg.generateKey();
		byte[] pkey = secretKey.getEncoded(); 
		writePKey(pkey);
		
		// 获得密钥的二进制编码形式
		return pkey;
	}

	private static boolean isFileExist() {
		File file = new File(PKEY_PATH);
		return file.exists();
	}

	private static File createFile() {
		File file = new File(PKEY_PATH);
		if (file.exists()) {
			return file;
		}

		try {
			if (file.createNewFile()) {
				System.out.println("create file ok");
			} else {
				System.out.println("create file failed!");
				file = null;
			}
		} catch (IOException e) {
			file = null;
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * Store the key to a file.
	 * 
	 * @param key
	 * @throws IOException
	 */
	private static void writePKey(byte[] key) {
		System.out.println("writePKey");
		
		File file = createFile();
		if (file == null) {
			System.out.println("can not create file");
			return;
		}

		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(file);
			fout.write(key);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/** Read the key from a file
	 * @return
	 */
	private static byte[] readPKey() {
		System.out.println("readPKey");
		
		File file = createFile();
		if (file == null) {
			return null;
		}

		byte[] data = new byte[(int) file.length()];

		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			fin.read(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}
}
