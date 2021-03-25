package dev.mrpanda.C1PH3Rv2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Key {
	private String[] key;
	private int type;
	private int multiplier;
	private File keyFile;
	private int keyIndex = 0;
	private String firstLine = "";
	
	/**
	 * Constructs a key with the given file. If the file has value init, reads the file to create a new key instance from it. If there is no data,
	 * then generates a new key.
	 * @param keyFile : the key file
	 * @throws IOException : if the file cannot be read, throws
	 */
	public Key(File keyFile) throws IOException {
		this.keyFile = keyFile;
		Scanner in = new Scanner(keyFile);
		
		if(in.hasNext()) {
			if(isValid()) {
				in.nextLine();
				key = new String[51];
				for(int i = 1; i < 52; i++) {
					this.key[i-1] = in.nextLine();
				}
			} else {
				in.close();
				return;
			}
		} else {
			this.key = getKey();
			
			FileWriter writer = new FileWriter(keyFile);
			writer.write(toString());
			writer.flush();
			writer.close();
		}
		
		in.close();
	}
	
	/**
	 * Returns a random key block with 51 keys init.
	 * @return a unique key
	 */
	private String[] getKey() {
		String[] key = new String[51];
		
		for(int i = 0; i < key.length; i++) {
			key[i] = getRandomizedChar();
		}
		
		return key;
	}
	
	/**
	 * Returns a key string with 94 unique ASCII characters.
	 * @return a key string
	 */
	private String getRandomizedChar() {
		List<Character> list = new ArrayList<Character>();
		
		for(int i = 33; i <= 126; i++) {
			list.add(((char) i));
		}

		Collections.shuffle(list);
		
		String k = "";
		for(Character c : list) {
			k += c;
		}
		
		return k;
	}
	
	/**
	 * Encrypts the given character with the key.
	 * @param c : character to be encrypted
	 * @param isFirst : is it the first round of encryption
	 * @return a 3-character encrypted string block
	 */
	public String getEncryptedValue(char c, boolean isFirst) {
		String toReturn;
		char value = 0;
		
		if(((int) c) >= 33 && ((int) c) <= 126) {
			int index = (int) c - 33;
	
			value = key[(keyIndex * 3) + type].charAt(index);
			
			value = (char)((int)value + ((type + 1) * multiplier));
			if(value > 126)
				value = (char)((int)value - 94);
		} else if((int) c == 10){
			value = '\u00B5';
		} else if((int) c == 32){
			value = '\u00A1';
		} else if(c == '\u00A1'){
			value = '\u00A3';
		} else if(c == '\u00B5') {
			value = '\u00B1';
		}
		
		if(type == 0) {
			toReturn = String.valueOf(value) + String.valueOf((char)((Math.random() * 94) + 33)) + String.valueOf((char)((Math.random() * 94) + 33));
		} else if(type == 1) {
			toReturn = String.valueOf((char)((Math.random() * 94) + 33)) + String.valueOf(value) + String.valueOf((char)((Math.random() * 94) + 33));
		} else {
			toReturn = String.valueOf((char)((Math.random() * 94) + 33)) + String.valueOf((char)((Math.random() * 94) + 33)) + String.valueOf(value);
		}
		
		if(isFirst) {
			if(multiplier == 1) {
				if(keyIndex + 1 == 17) {
					keyIndex = 0;
				} else {
					keyIndex++;
				}
			} else {
				if(keyIndex + 2 > 16) {
					keyIndex = 0;
				} else {
					keyIndex += 2;
				}
			}
		} else {
			if(multiplier == 2) {
				if(keyIndex + 1 == 17) {
					keyIndex = 0;
				} else {
					keyIndex++;
				}
			} else {
				if(keyIndex + 2 > 16) {
					keyIndex = 0;
				} else {
					keyIndex += 2;
				}
			}
		}
		
		return toReturn;
	}
	
	/**
	 * Decrypts the given character with the key.
	 * @param chunk : 3-character block to be decrypted
	 * @param isFirst : is it the first round of decryption
	 * @return the original character value
	 */
	public String getDecryptedValue(String chunk, boolean isFirst) {
		String toReturn = null;
		char value = chunk.charAt(type);
		
		int index = (keyIndex * 3) + type;
		
		if(((int) value) >= 33 && ((int) value) <= 126) {
			value = (char)((int)value - ((type + 1) * multiplier));
			if(value < 33)
				value = (char)((int)value + 94);
			
			for(int i = 0; i < key[index].length(); i++) {
				if(key[index].charAt(i) == value) {
					toReturn = String.valueOf((char)(i + 33));
				} else {
					continue;
				}
			}
		} else if(value == '\u00B1'){
			toReturn = "\u00B5";
		} else if(value == '\u00A3'){
			toReturn = "\u00A1";
		} else if(value == '\u00B5'){
			toReturn = "\n";
		} else if(value == '\u00A1') {
			toReturn = " ";
		}
		
		if(isFirst) {
			if(multiplier == 2) {
				if(keyIndex + 1 == 17) {
					keyIndex = 0;
				} else {
					keyIndex++;
				}
			} else {
				if(keyIndex + 2 > 16) {
					keyIndex = 0;
				} else {
					keyIndex += 2;
				}
			}
		} else {
			if(multiplier == 1) {
				if(keyIndex + 1 == 17) {
					keyIndex = 0;
				} else {
					keyIndex++;
				}
			} else {
				if(keyIndex + 2 > 16) {
					keyIndex = 0;
				} else {
					keyIndex += 2;
				}
			}
		}
		
		return toReturn;
	}
	
	/**
	 * Returns a String which represents the key.
	 */
	public String toString() {
		String str = "-------------------------------------------KEY BEGIN-------------------------------------------\n";
		for(int i = 0; i < key.length; i++) {
			str += key[i] + "\n";
		}
		str += "--------------------------------------------KEY END--------------------------------------------";
		
		return str;
	}
	
	/**
	 * Returns the key type indicator.
	 * @return the type indicator
	 */
	public char getTypeIndicator() {
		if(type == 0)
			return '$';
		else if(type == 1)
			return '%';
		else
			return '&';
	}
	
	/**
	 * Sets the type via the indicator character.
	 * @param indicator : type indicator
	 */
	public void setType(char indicator) {
		if(indicator == '$')
			this.type = 0;
		else if(indicator == '%')
			this.type = 1;
		else
			this.type = 2;
	}
	
	/**
	 * Sets the type randomly.
	 */
	public void setType() {
		type = (int)(Math.random() * 3);
	}
	
	/**
	 * Returns the multiplication type indicator.
	 * @return the multiplier indicator
	 */
	public char getMultiplierIndicator() {
		if(multiplier == 1)
			return '<';
		else
			return '>';
	}
	
	/**
	 * Sets the multiplier via the indicator character.
	 * @param indicator : multiplier indicator
	 */
	public void setMultiplier(char indicator) {
		if(indicator == '<')
			this.multiplier = 1;
		else
			this.multiplier = 2;
	}
	
	/**
	 * Sets the multiplier randomly.
	 */
	public void setMultiplier() {
		multiplier = (int)(Math.random() * 2) + 1;
	}
	
	/**
	 * Resets the key index counter.
	 */
	public void resetCounter() {
		this.keyIndex = 0;
	}
	
	/**
	 * Returns the key file.
	 * @return the key file
	 */
	public File getFile() {
		return keyFile;
	}
	
	/**
	 * Returns the key indicator string.
	 * @return the key indicator
	 */
	public String getKeyIndicator() {
		String s = "";
		
		for(int i = 0; i < firstLine.length(); i++) {
			s += getEncryptedValue(firstLine.charAt(i), false);
		}
		resetCounter();
		
		return s;
	}
	
	/**
	 * Checks the key indicator for being the same as key instance.
	 * @param indicator : the key indicator
	 * @return true if the key indicator matches, false otherwise
	 */
	public boolean checkKeyIndicator(String indicator) {
		if(indicator.length() != 282)
			return false;
		
		String s = "";
		
		for(int i = 0; i < indicator.length() - 2; i += 3) {
			s += getDecryptedValue(indicator.substring(i, i+3), true);
		}
		resetCounter();

		if(firstLine.equals(s))
			return true;
		else
			return false;
	}
	
	/**
	 * Checks the key for being valid.
	 * @return true if the key is valid, false otherwise
	 * @throws FileNotFoundException : if the key file is not found, throws
	 */
	public boolean isValid() throws FileNotFoundException {
		if(keyFile.getAbsolutePath().length() < 5) {
			return false;
		} else if(!keyFile.getAbsolutePath().substring(keyFile.getAbsolutePath().length()-3).equals("txt")) {
			return false;
		} else if(!keyFile.exists()){
			return false;
		}
		
		Scanner file = new Scanner(keyFile);
		
		boolean isValid = true;
			
		int count = 0;
		while(file.hasNextLine()) {
			file.nextLine();
			count++;
		}
			
		if(count != 53) {
			isValid = false;
		} else {
			file.close();
			file = new Scanner(keyFile);
				
			String firstLine = file.nextLine();
				
			if(firstLine.equals("-------------------------------------------KEY BEGIN-------------------------------------------")) {
				List<Character> list = new ArrayList<Character>();
					
				for(int i = 33; i <= 126; i++) {
					list.add((char)(i));
				}
					
				for(int i = 0; i < 52; i++) {
					String line = file.nextLine();
					if(i == 51) {
						if(!line.equals("--------------------------------------------KEY END--------------------------------------------")) {
							isValid = false;
							break;
						}
					} else {
						if(line.length() != 94) {
							isValid = false;
							break;
						} else {
							for(int j = 0; j < 94; j++) {
								if(!list.contains(line.charAt(j))) {
									isValid = false;
									break;
								}
								
								for(int r = 0; r < j; r++) {
									if(line.charAt(r) == line.charAt(j)) {
										isValid = false;
										break;
									}
								}
								
								if(!isValid)
									break;
							}
							
							if(i == 0)
								this.firstLine = line;
									
							if(!isValid)
								break;
						}
					}
				}
			} else {
				isValid = false;
			}
		}
			
		file.close();
			
		return isValid;
	}
}
