package com.java.examples.security.signature;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

public class GenSig {
	private static final String fileName = 
			"/home/jiangrui/.signature_test/change.fmradio.frw.txt";
	private static final String sigFileName = 
			"/home/jiangrui/.signature_test/change.fmradio.frw.txt_sig";
	private static final String pubKeyFile = 
			"/home/jiangrui/.signature_test/pubkey";

	// signature of the file
	private byte[] mRealSig;

	private PrivateKey mPrivateKey;
	private PublicKey mPublicKey;

	private void savePubKey2File() throws IOException {
		byte[] key = mPublicKey.getEncoded();
		FileOutputStream keyfos = new FileOutputStream(pubKeyFile);
		keyfos.write(key);
		keyfos.close();
	}

	private void saveSign2File() throws IOException {
		FileOutputStream fos = new FileOutputStream(sigFileName);
		fos.write(mRealSig);
		fos.close();
	}

	private void generateKeys() throws NoSuchAlgorithmException,
			NoSuchProviderException {
		// Algorithm is "DSA", provider is "SUN"
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");

		// Source of randomness is "SHA1PRNG", key length is 1024
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
		random.setSeed(10000);
		keyGen.initialize(1024, random);

		// Generate public key and private key
		KeyPair pair = keyGen.generateKeyPair();
		mPrivateKey = pair.getPrivate();
		mPublicKey = pair.getPublic();
	}

	/*
	 * generate a signature for the file
	 */
	private void signData(String fileName) throws NoSuchAlgorithmException,
			NoSuchProviderException, InvalidKeyException,
            IOException, SignatureException {

		// Algorithm is "SHA1withDSA", provider is "SUN"
		// SHA-1 message digest algorithm
		Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");

		// Using private key for signature
		dsa.initSign(mPrivateKey);

		// supply data for Signature
		FileInputStream fis = new FileInputStream(fileName);
		BufferedInputStream bufin = new BufferedInputStream(fis);
		byte buffer[] = new byte[1024];
		int len;
		while ((len = bufin.read(buffer)) >= 0) {
			dsa.update(buffer, 0, len);
		}
		bufin.close();

		mRealSig = dsa.sign();
	}

	public static void main(String[] args) {
		/* Verify a DSA signature */
		
		GenSig genSig = new GenSig();
		
		try {
			genSig.generateKeys();
			genSig.signData(fileName);
			genSig.saveSign2File();
			genSig.savePubKey2File();

		} catch (Exception e) {
			System.err.println("Caught exception " + e.toString());
		}
	}
}
