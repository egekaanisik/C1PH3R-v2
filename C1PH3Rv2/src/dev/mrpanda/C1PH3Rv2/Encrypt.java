package dev.mrpanda.C1PH3Rv2;


public class Encrypt {
	private String cipher;
	private boolean isValid = true;
	
	/**
	 * Constructs a text encryption instance with the key.
	 * @param plain : text to be encrypted
	 * @param key : key to be encrypted with
	 */
	public Encrypt(String plain, Key key) {
		if(!plain.matches("\\A\\p{ASCII}*\\z")) {
			this.isValid = false;
			return;
		}
		
		key.setType();
		key.setMultiplier();
		
		cipher = "#";
		
		for(int i = 0; i < plain.length(); i++)
			cipher += key.getEncryptedValue(plain.charAt(i));

		cipher += key.getKeyIndicator() + String.valueOf(key.getMultiplierIndicator()) + String.valueOf(key.getTypeIndicator()) + "#";
	}
	
	/**
	 * Returns the ciphered text.
	 * @return the ciphertext
	 */
	public String getCipher() {
		return cipher;
	}
	
	/**
	 * Returns if the plaintext is valid.
	 * @return true if it is valid, false otherwise
	 */
	public boolean isValid() {
		return isValid;
	}
}
