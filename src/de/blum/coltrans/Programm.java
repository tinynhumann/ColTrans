package de.blum.coltrans;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Programm {

    public static void main(String args[]) throws IOException {
        exampleComputer();
        exampleRaven();
    }

    public static void exampleComputer() throws IOException {
        List<String> clear = Files.readAllLines(Paths.get("computer.txt"));
        //System.out.print(clear.toString());
        StringBuilder sb = new StringBuilder();
        for (String line : clear) {
            sb.append((Encryptor.encrypt(line.replaceAll("\\s", "").trim().replaceAll("\\s", "_"), new int[]{1, 0, 3, 2, 4})));
        }
        String groupSecret = groupSecret(sb.toString());
        Files.delete(Paths.get("secret.txt"));
        Files.write(Paths.get("secret.txt"), groupSecret.getBytes(), StandardOpenOption.CREATE);

        Decryptor decryptor = new Decryptor("secret.txt", "computer", 5);
        Decryptor decryptor2 = new Decryptor("secret.txt", "that", 3); // wort kürzer als blocklaenge
    }

    public static void exampleRaven() throws IOException {
        List<String> clear = Files.readAllLines(Paths.get("raven.txt"));

        StringBuilder sb = new StringBuilder();
        for (String line : clear) {
            sb.append((Encryptor.encrypt(line.replaceAll("\\s", "").trim().replaceAll("\\s", "_"), new int[]{1, 0, 3, 2, 4})));
        }
        String groupSecret = groupSecret(sb.toString());
        Files.delete(Paths.get("secret.txt"));
        Files.write(Paths.get("secret.txt"), groupSecret.getBytes(), StandardOpenOption.CREATE);
               
        Decryptor decryptor1 = new Decryptor("secret.txt", "raven", 3);
        Decryptor decryptor2 = new Decryptor("secret.txt","ancient",3); 
    }
    
    private static String groupSecret(String secret) {
        StringBuilder groupedSecret =  new StringBuilder();
        int group=0;
        for(int i = 1; i<=secret.length(); i++) {
        	groupedSecret.append(secret.substring(i-1, i));
        	if(i % 5 == 0) {
        		groupedSecret.append(" ");
        		group++;
        	}
        	if(group==10) {
        		group=0;
        		groupedSecret.append(System.lineSeparator());
        	}
        }
        return groupedSecret.toString();
    }

}
