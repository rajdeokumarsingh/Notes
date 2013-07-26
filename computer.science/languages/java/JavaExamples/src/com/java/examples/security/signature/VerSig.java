package com.java.examples.security.signature;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class VerSig {
    private static final String fileName = "/home/jiangrui/.signature_test/change.fmradio.frw.txt";
    private static final String sigFileName = "/home/jiangrui/.signature_test/change.fmradio.frw.txt_sig";
    private static final String pubKeyFile = "/home/jiangrui/.signature_test/pubkey";

    private static PublicKey mPubKey;
    private byte[] mSigToVerifies;

    public static void main(String[] args) {
        /* Verify a DSA signature */
        VerSig verSig = new VerSig();

        try {
            verSig.loadPublicKey();
            verSig.loadSignature();
            verSig.verifySignature();

        } catch (Exception e) {
            System.err.println("Caught exception " + e.toString());
        }
    }

    private void verifySignature() throws NoSuchAlgorithmException,
            NoSuchProviderException, InvalidKeyException,
            IOException, SignatureException {
        Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
        sig.initVerify(mPubKey); // need public key to verify signature

        FileInputStream datafis = new FileInputStream(fileName);
        BufferedInputStream bufin = new BufferedInputStream(datafis);
        byte[] buffer = new byte[1024];
        int len;
        while (bufin.available() != 0) {
            len = bufin.read(buffer);
            sig.update(buffer, 0, len);
        }
        bufin.close();

        boolean verifies = sig.verify(mSigToVerifies);
        System.out.println("signature verifies: " + verifies);
    }

    private void loadSignature() throws IOException {
        FileInputStream sigfis = new FileInputStream(sigFileName);
        mSigToVerifies = new byte[sigfis.available()];
        sigfis.read(mSigToVerifies);
        sigfis.close();
    }

    private void loadPublicKey() throws IOException,
            NoSuchAlgorithmException, NoSuchProviderException,
            InvalidKeySpecException {
        // Read public key from file
        FileInputStream keyfis = new FileInputStream(pubKeyFile);
        byte[] mEncKey = new byte[keyfis.available()];
        keyfis.read(mEncKey);
        keyfis.close();

		/*
         * instantiate a DSA public key from its encoding
		 * 
		 * KeyFactory class provides conversions between opaque keys (of type
		 * Key) and key specifications
		 * 
		 * key specifications transparent representations of the underlying key
		 * material
		 * 
		 * With an opaque key you can obtain the algorithm name, format name,
		 * and encoded key bytes, but not the key material which may consist of
		 * the key itself and the algorithm parameters used to calculate the key
		 * 
		 * first you need a key specification assuming that the key was encoded
		 * according to the X.509 standard which is the case, for example, if
		 * the key was generated with the built-in DSA key-pair generator
		 * supplied by the SUN provider
		 */
        // Get key specification
        X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(mEncKey);
        // Convert key specification to opaque key
        KeyFactory keyFactory = KeyFactory.getInstance("DSA", "SUN");
        mPubKey = keyFactory.generatePublic(pubKeySpec);
    }

    void loadPrivateKey(String fileName) throws IOException,
            NoSuchAlgorithmException, InvalidKeySpecException {

        FileInputStream keyfis = new FileInputStream(fileName);
        byte[] encKey = new byte[keyfis.available()];
        keyfis.read(encKey);
        keyfis.close();

        PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(encKey);

        KeyFactory keyFactory = KeyFactory.getInstance("DSA");
        PrivateKey privKey = keyFactory.generatePrivate(privKeySpec);
    }
}
