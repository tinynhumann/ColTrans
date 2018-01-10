package de.blum.coltrans;

public class Encryptor {
	
	public static String encrypt(String text, int[] key) {
		
		int mod = key.length - text.length() % key.length;
		for(int i = 0; i < mod; i++) {
			text += "_";
		}
		
		char[] plain = text.toCharArray();
		char[] secret = new char[plain.length];
		
		int j = 0;
		for(int i = 0; i < plain.length; i++) {
			secret[i] = (char) plain[key[j]];
			j++;
			if(j == key.length) {
				for (int k = 0; k < key.length; k++) {
					key[k] += key.length;
				}
				j = 0;
			}
		}
		
		char[][] coltrans = new char[(secret.length / key.length)][key.length];
		
		int i = 0;
		for (int x = 0; x < coltrans.length; x++) {
			for (int y = 0; y < coltrans[0].length; y++) {
				if (i < secret.length) {
					coltrans[x][y] = secret[i];
					i++;
				}
			}
		}
		
		i = 0;
		secret = new char[((secret.length / key.length)) * key.length];
		for (int x = 0; x < coltrans[0].length; x++) {
			for (int y = 0; y < coltrans.length; y++) {
				secret[i] = coltrans[y][x];
				i++;
			}
		}
		
		return String.valueOf(secret);
	}
	
}
