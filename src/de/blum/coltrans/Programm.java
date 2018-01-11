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
        Files.delete(Paths.get("secret.txt"));
        Files.write(Paths.get("secret.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE);

        Decryptor decryptor = new Decryptor("secret.txt", "computer", 5);
        Decryptor decryptor2 = new Decryptor("secret.txt", "that", 3); // wort kürzer als blocklaenge
    }

    public static void exampleRaven() throws IOException {
        List<String> clear = Files.readAllLines(Paths.get("raven.txt"));

        StringBuilder sb = new StringBuilder();
        for (String line : clear) {
            sb.append((Encryptor.encrypt(line.replaceAll("\\s", "").trim().replaceAll("\\s", "_"), new int[]{1, 0, 3, 2, 4})));
        }

        Files.delete(Paths.get("secret.txt"));
        Files.write(Paths.get("secret.txt"), sb.toString().getBytes(), StandardOpenOption.CREATE);
               
        Decryptor decryptor1 = new Decryptor("secret.txt", "raven", 3);
        Decryptor decryptor2 = new Decryptor("secret.txt","ancient",3); 
    }

}
