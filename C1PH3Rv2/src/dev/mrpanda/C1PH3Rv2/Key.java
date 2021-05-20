package dev.mrpanda.C1PH3Rv2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class Key {
	private String[] key;
	private int type;
	private int multiplier;
	private File keyFile;
	private int keyIndex = 0;
	private String firstLine = "";
	private Random random;
	private boolean isValid;
	
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
			if(validate()) {
				isValid = true;
				in.nextLine();
				key = new String[51];
				for(int i = 1; i < 52; i++) {
					this.key[i-1] = in.nextLine();
				}
			} else {
				isValid = false;
				in.close();
				return;
			}
		} else {
			this.key = getKey(false);
			isValid = true;
			
			FileWriter writer = new FileWriter(keyFile);
			writer.write(toString());
			writer.flush();
			writer.close();
		}
		
		in.close();
	}
	
	/**
	 * Returns a random key block with 51 keys init.
	 * @param isForCheck : an indicator that states if the key for is checking
	 * @return a unique key
	 */
	private String[] getKey(boolean isForCheck) {
		String[] key = new String[51];
		
		int i;
		
		if(isForCheck) {
			key[0] = this.firstLine;
			i = 1;
			
		} else
			i = 0;
		
		while(i < key.length) {
			key[i] = getRandomizedChar();
			
			if(i == 0) {
				this.firstLine = key[i];
				loadSeed();
			}
			
			i++;
		}
		
		loadSeed();
		
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
		
		if(random == null)
			Collections.shuffle(list);
		else
			Collections.shuffle(list, random);
		
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
		String seedCode = "mrpd";
		String seedStr = "";
		
		for(int i = 0; i < seedCode.length(); i++) {
			seedStr += (int)seedCode.charAt(i);
		}
		
		long seed = Long.parseLong(seedStr);
		
		List<Character> list = new ArrayList<Character>();
		
		for(int i = 33; i <= 126; i++) {
			list.add(((char) i));
		}
		
		Collections.shuffle(list, new Random(seed));
		
		String s = "";
		
		for(int i = 0; i < firstLine.length(); i++) {
			char ch = firstLine.charAt(i);
			
			if(((int)ch) + ((type + 1) * multiplier) > 126)
				ch = (char)((int)ch - 94);
			
			ch = (char)((int)ch + ((type + 1) * multiplier));
			
			int index = (int)ch - 33;
			
			ch = list.get(index);
			
			if(type == 0) {
				s += String.valueOf(ch) + String.valueOf((char)((Math.random() * 94) + 33)) + String.valueOf((char)((Math.random() * 94) + 33));
			} else if(type == 1) {
				s += String.valueOf((char)((Math.random() * 94) + 33)) + String.valueOf(ch) + String.valueOf((char)((Math.random() * 94) + 33));
			} else {
				s += String.valueOf((char)((Math.random() * 94) + 33)) + String.valueOf((char)((Math.random() * 94) + 33)) + String.valueOf(ch);
			}
		}
		
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
		
		String seedCode = "mrpd";
		String seedStr = "";
		
		for(int i = 0; i < seedCode.length(); i++) {
			seedStr += (int)seedCode.charAt(i);
		}
		
		long seed = Long.parseLong(seedStr);
		
		List<Character> list = new ArrayList<Character>();
		
		for(int i = 33; i <= 126; i++) {
			list.add(((char) i));
		}
		
		Collections.shuffle(list, new Random(seed));
		
		String s = "";
		
		for(int i = 0; i < indicator.length(); i += 3) {
			char ch = indicator.charAt(i + type);
			
			for(int j = 0; j < list.size(); j++) {
				if(ch == list.get(j)) {
					ch = (char)(j + 33);
					break;
				}
			}
			
			if(((int)ch) - ((type + 1) * multiplier) < 33)
				ch = (char)((int)ch + 94);
			
			ch = (char)((int)ch - ((type + 1) * multiplier));
			
			s += ch;
		}

		if(firstLine.equals(s))
			return true;
		else
			return false;
	}
	
	/**
	 * Loads a seed into a Random instance from the first line of the key.
	 */
	private void loadSeed() {
		long[] seedParts = new long[10];
		
		for(int j = 0; j < 10; j++) {
			String seedStr = "";
			for(int i = (this.firstLine.length() / 10) * j; i < ((this.firstLine.length() / 10) * (j+1)); i++) {
				seedStr += String.valueOf(((int)firstLine.charAt(i)) - 33);
			}
			seedParts[j] = Long.parseLong(seedStr);
		}
		
		long seed = 0;
		for(int i = 0; i < seedParts.length; i++) {
			seed += seedParts[i];
		}

		this.random = new Random(seed);
	}
	
	/**
	 * Returns if the already created key is valid.
	 * @return true if the key is valid, false otherwise
	 */
	public boolean isValid() {
		return isValid;
	}
	
	/**
	 * Validates the key for being equal to a constructed one.
	 * @return true if the key is valid, false otherwise
	 * @throws FileNotFoundException : if the key file is not found, throws
	 */
	private boolean validate() throws FileNotFoundException {
		if(keyFile.getAbsolutePath().length() < 6) {
			return false;
		} else if(!keyFile.getAbsolutePath().substring(keyFile.getAbsolutePath().length()-5).equals("mrpdk")) {
			return false;
		} else if(!keyFile.exists()){
			return false;
		}
		
		Scanner file = new Scanner(keyFile);
		
		if(!file.hasNext()) {
			file.close();
			return false;
		}
		
		List<String> lines = new ArrayList<String>();
		
		while(file.hasNextLine()) {
			lines.add(file.nextLine());
		}
		
		file.close();
				
		if(lines.size() != 53)
			return false;
		else if(!lines.get(0).equals("-------------------------------------------KEY BEGIN-------------------------------------------"))
			return false;
		else if(!lines.get(52).equals("--------------------------------------------KEY END--------------------------------------------"))
			return false;
		
		lines.remove(0);
		lines.remove(51);
		
		String firstLine = lines.get(0);
		
		if(firstLine.length() != 94)
			return false;
		
		for(int i = 0; i < firstLine.length(); i++) {
			char currentChar = firstLine.charAt(i);
			
			for(int j = 0; j < firstLine.length(); j++) {
				if(j == i)
					continue;
			
				if(firstLine.charAt(j) == currentChar || (int)firstLine.charAt(j) < 33 || (int)firstLine.charAt(j) > 126) {
					return false;
				}
			}
		}
		
		this.firstLine = firstLine;
		loadSeed();
		
		String[] keyLines = getKey(true);
		
		for(int i = 0; i < 51; i++) {
			if(!keyLines[i].equals(lines.get(i)))
				return false;
		}
		
		return true;
	}
}
