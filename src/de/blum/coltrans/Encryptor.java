package de.blum.coltrans;

/**
*
* @author oliver
*/
public class Encryptor {
	
	public static String encrypt(String text, int[] key) {
		
		/*
		 * extend text with '_', so it can be divided through key length.
		 */
		int mod = key.length - text.length() % key.length;
		for(int i = 0; i < mod; i++) {
			text += "_";
		}
		
		char[] plain = text.toCharArray();
		char[] secret = new char[plain.length];
		
		/*
		 * sort text characters given by key
		 */
		int j = 0;
		for(int i = 0; i < plain.length; i++) {
			secret[i] = (char) plain[key[j]];
			j++;
			if(j == key.length) {
				//update key values to new text indices after all transposition are done.
				for (int k = 0; k < key.length; k++) {
					key[k] += key.length;
				}
				j = 0;
			}
		}
		
		char[][] coltrans = new char[(secret.length / key.length)][key.length];
		
		/*
		 * write secret into two-dimensional array with x rows and y columns
		 */
		int i = 0;
		for (int x = 0; x < coltrans.length; x++) {
			for (int y = 0; y < coltrans[0].length; y++) {
				if (i < secret.length) {
					coltrans[x][y] = secret[i];
					i++;
				}
			}
		}
		
		/*
		 * read from this two-dimensional array with y rows and x columns.
		 */
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
