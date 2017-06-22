package com.zycus.scriptExecutor.utility;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Utility to provide Encryption of Strings.
 * 
 * @author harshvardhan.dudeja
 *
 */
public class EncryptionUtil {
	/**
	 * Method To Decode the Password according to ZycusDS.jar for Tomcat or
	 * other
	 * 
	 * @param secret
	 *            is the password to be decoded
	 * @return decoded password
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static final String decodeForTomcat(String secret)
			throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		byte[] kbytes = "-5f0cf48fcfae2bb".getBytes();
		SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");

		BigInteger n = new BigInteger(secret, 16);
		byte[] encoding = n.toByteArray();

		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(2, key);
		byte[] decode = cipher.doFinal(encoding);
		return new String(decode);
	}

	/**
	 * Method To Decode the Password according to jbossx-security-client.jar for
	 * JBoss only
	 * 
	 * @param secret
	 *            is the password to be decoded
	 * @return decoded password
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 */
	public static final String decodeForJBoss(String secret)
			throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

		byte[] kbytes = "jaas is the way".getBytes();
		SecretKeySpec key = new SecretKeySpec(kbytes, "Blowfish");

		BigInteger n = new BigInteger(secret, 16);
		byte[] encoding = n.toByteArray();
		if (encoding.length % 8 != 0) {
			int length = encoding.length;
			int newLength = (length / 8 + 1) * 8;
			int pad = newLength - length;
			byte[] old = encoding;
			encoding = new byte[newLength];
			for (int i = old.length - 1; i >= 0; i--) {
				encoding[(i + pad)] = old[i];
			}
		}
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(2, key);
		byte[] decode = cipher.doFinal(encoding);
		return new String(decode);
	}
}
