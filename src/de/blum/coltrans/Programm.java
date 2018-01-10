package de.blum.coltrans;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Programm {
	
	public static void main(String args[]) throws IOException {
		//List<String> clear = Files.readAllLines(Paths.get("raven.txt"));  //raven ohne aenderungen
                //List<String> clear = Files.readAllLines(Paths.get("01234.txt"));    //raven mit 01234 am ende (genau in einem block)
		List<String> clear = Files.readAllLines(Paths.get("computer.txt"));//computer
                //System.out.print(clear.toString());
		StringBuilder sb =  new StringBuilder();
		for(String line : clear) {
			sb.append((Encryptor.encrypt(line.replaceAll("\\s", "").trim().replaceAll("\\s", "_"), new int[] {1, 0, 3, 2, 4})));
			sb.append(" ");
		}
//		StringBuilder sb2 = new StringBuilder();
//		//String secret = sb.toString();
//		for(int i = 1; i < sb.length() / 5; i++) {
//			String block = sb.substring(0, 5);
//			sb = sb.replace(0, 5, "");
//		    if(i % 10 == 0 && i > 0) {
//		    	sb2.append(block + System.lineSeparator());
//		    } else {
//		    	sb2.append(block + " ");
//		    }
//		}
                Files.delete(Paths.get("secret.txt"));
		Files.write( Paths.get("secret.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE);
                
              Decryptor decryptor1 = new Decryptor("secret.txt","01234");
//                Decryptor decryptor2 = new Decryptor("secret.txt","01");
//
//                Decryptor decryptor3 = new Decryptor("secret.txt","ancient");
//                
//                Decryptor decryptor4 = new Decryptor("secret.txt","raven");
              //  Decryptor decryptor5 = new Decryptor("secret.txt","\"Nevermore.\"");
                
                 Decryptor decryptor6 = new Decryptor("secret.txt","computer");


	}
        
        public void decryptComputer () throws IOException {
            		List<String> clear = Files.readAllLines(Paths.get("computer.txt"));//computer
                //System.out.print(clear.toString());
		StringBuilder sb =  new StringBuilder();
		for(String line : clear) {
			sb.append((Encryptor.encrypt(line.replaceAll("\\s", "").trim().replaceAll("\\s", "_"), new int[] {1, 0, 3, 2, 4})));
			sb.append(" ");
		}
                                Files.delete(Paths.get("secret.txt"));
		Files.write( Paths.get("secret.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE);
                
                                 Decryptor decryptor6 = new Decryptor("secret.txt","computer");
        }
        
        public void decryptRaven () throws IOException {
        	List<String> clear = Files.readAllLines(Paths.get("raven.txt"));  //raven ohne aenderungen
                //List<String> clear = Files.readAllLines(Paths.get("01234.txt"));    //raven mit 01234 am ende (genau in einem block)
		//List<String> clear = Files.readAllLines(Paths.get("computer.txt"));//computer
                //System.out.print(clear.toString());
		StringBuilder sb =  new StringBuilder();
		for(String line : clear) {
			sb.append((Encryptor.encrypt(line.replaceAll("\\s", "").trim().replaceAll("\\s", "_"), new int[] {1, 0, 3, 2, 4})));
			sb.append(" ");
		}
//		StringBuilder sb2 = new StringBuilder();
//		//String secret = sb.toString();
//		for(int i = 1; i < sb.length() / 5; i++) {
//			String block = sb.substring(0, 5);
//			sb = sb.replace(0, 5, "");
//		    if(i % 10 == 0 && i > 0) {
//		    	sb2.append(block + System.lineSeparator());
//		    } else {
//		    	sb2.append(block + " ");
//		    }
//		}
                Files.delete(Paths.get("secret.txt"));
		Files.write( Paths.get("secret.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE);

//                Decryptor decryptor3 = new Decryptor("secret.txt","ancient");
//                
                Decryptor decryptor4 = new Decryptor("secret.txt","raven");
                Decryptor decryptor5 = new Decryptor("secret.txt","\"Nevermore.\"");
                
        }

}
