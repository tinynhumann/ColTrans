package de.blum.coltrans;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author tina
 */
public class Decryptor {

    String secretTxt;
    String known;
    List<Integer> testBlockLenghts = new ArrayList<>();
    
    int matchThreshold = 5;
    int blockLengthMin = 3;
    int blockLengthMax = 6;

    public Decryptor(String secretPath, String knownPlaintext, int threshold) throws IOException {
        known = knownPlaintext.toLowerCase();
        matchThreshold = threshold;
        List<String> secret = Files.readAllLines(Paths.get(secretPath));
        StringBuilder sb = new StringBuilder();
        for (String line : secret) {
             sb.append(line);
        }
        secretTxt = sb.toString().toLowerCase();
        testBlockLenghts = findBlockLengths(secretTxt.length() - 1);
        System.out.println("\n\nknown plaintext: \"" + known + "\" secret length: " + (secretTxt.length() - 1) + " possible block lengths: " + testBlockLenghts.toString());
        guessBlockLength();
    }

    private void guessBlockLength() {
        for (int currBlockLenght : testBlockLenghts) {
            if (currBlockLenght >= blockLengthMin && currBlockLenght <= blockLengthMax) {

                int noOfBlocks = ((secretTxt.length() - 1) / currBlockLenght);
                System.out.println("\n\tTRY\tblock length: " + currBlockLenght + "\t no of blocks: " + noOfBlocks);
                
                char[][] reconstructedBlocks = reconstructBlocks(currBlockLenght, noOfBlocks);
                List<Integer> matches = matchBlocks(reconstructedBlocks);
                
                if (matches.size() >= 1) {
                    for (int b = 0; b < noOfBlocks; b++) {
                        String line = b + "\t" + Arrays.toString(reconstructedBlocks[b]);
                        if (matches.contains(b)) {
                            System.out.println("\t" + (char) 27 + "[31m" + line + (char) 27 + "[0m");
                        } else {
                            System.out.println("\t" + line);
                        }
                    }
                } else {
                    System.out.println("\tSKIP - no matching blocks found");
                }
            }
        }
    }

    private List<Integer> matchBlocks(char[][] blocks) {
        int noOfBlocks = blocks.length;
        List<Integer> matchedBlocks = new ArrayList<>();
        
        for (int block = 0; block < noOfBlocks; block++) {
            int[] assumedKey;
            int matches = 0;
            int blockLength = blocks[block].length;
            assumedKey = new int[blockLength];
            
            for (int c = 0; c < blockLength; c++) {
                assumedKey[c] = known.indexOf(blocks[block][c]);
                if (assumedKey[c] >= 0) {
                    matches++;
                } else {
                    matches--;
                }
            }

            if (matches == matchThreshold) {
                matchedBlocks.add(block);
                int offset = known.length();
                for (int j = 0; j < blockLength; j++) {
                    int currIndex = known.indexOf(blocks[block][j]);
                    if (currIndex < offset) {
                        offset = currIndex;
                    }
                }
                for (int j = 0; j < assumedKey.length; j++) {
                    assumedKey[j] = assumedKey[j] - offset;
                }
                System.out.println("\tpossible key for " + block + ": " + Arrays.toString(assumedKey));
            }
        }
        return matchedBlocks;
    }

    private char[][] reconstructBlocks(int blockLenght, int colLength) {
        char[][] originalBlocks = new char[colLength][blockLenght];
        int i = 0;
        for (int c = 0; c < blockLenght; c++) {
            for (int col = 0; col < colLength; col++) {
                if (i < secretTxt.length() - 1) {
                    originalBlocks[col][c] = secretTxt.charAt(i);
                    i++;
                }
            }
        }
        return originalBlocks;
    }

    public List<Integer> findBlockLengths(int zahl) {
        int zahlhalbe = zahl / 2; // es reicht bis zahl/2 zu pruefen
        List<Integer> teiler = new ArrayList<>();
        for (int i = 1; i <= zahlhalbe; i++) {
            if (zahl % i == 0) {
                teiler.add(i);
            }
        }
        return teiler;
    }
}
