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
		if(plain.length() == 0 || !plain.matches("\\A\\p{ASCII}*\\z")) {
			this.isValid = false;
			return;
		}
		
		key.setType();
		key.setMultiplier();
		
		cipher = "#";
		String temp = "";
		
		for(int i = 0; i < plain.length(); i++)
			temp += key.getEncryptedValue(plain.charAt(i), true);
		key.resetCounter();
		
		for(int i = 0; i < temp.length(); i++)
			cipher += key.getEncryptedValue(temp.charAt(i), false);
		key.resetCounter();
		
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
