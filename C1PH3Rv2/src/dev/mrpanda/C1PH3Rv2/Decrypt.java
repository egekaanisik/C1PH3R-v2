package dev.mrpanda.C1PH3Rv2;

public class Decrypt {
	private String plain;
	private boolean isValid = true;
	
	/**
	 * Constructs a text decryption instance with the key.
	 * @param plain : text to be decrypted
	 * @param key : key to be decrypted with
	 */
	public Decrypt(String cipher, Key key) {
		if(!(cipher.length() > 294)) {
			this.isValid = false;
			return;
		} else if(!(cipher.charAt(0) == '#' && cipher.charAt(cipher.length() - 1) == '#' &&
				(cipher.charAt(cipher.length() - 2) == '%' || cipher.charAt(cipher.length() - 2) == '$' || cipher.charAt(cipher.length() - 2) == '&') &&
				(cipher.charAt(cipher.length() - 3) == '<' || cipher.charAt(cipher.length() - 3) == '>'))) {
			this.isValid = false;
			return;
		}
		
		key.setType(cipher.charAt(cipher.length()-2));
		key.setMultiplier(cipher.charAt(cipher.length()-3));
		
		if(!key.checkKeyIndicator(cipher.substring(cipher.length() - 285, cipher.length() - 3))) {
			this.isValid = false;
			return;
		} else if(cipher.substring(1, cipher.length() - 285).length() % 9 != 0) {
			this.isValid = false;
			return;
		}
		
		for(int i = 0; i < cipher.length(); i++) {
			if((int)(cipher.charAt(i)) == 32) {
				this.isValid = false;
				return;
			}
		}
		
		plain = "";
		String temp = "";
		
		for(int i = 1; i < cipher.length() - 285; i += 3)
			temp += key.getDecryptedValue(cipher.substring(i, i+3), true);
		key.resetCounter();
		
		for(int i = 0; i < temp.length() - 2; i += 3)
			plain += key.getDecryptedValue(temp.substring(i, i+3), false);
		key.resetCounter();
	}
	
	/**
	 * Returns the plaintext.
	 * @return the plaintext
	 */
	public String getPlain() {
		return plain;
	}
	
	/**
	 * Returns if the ciphertext is valid.
	 * @return true if it is valid, false otherwise
	 */
	public boolean isValid() {
		return isValid;
	}
}
