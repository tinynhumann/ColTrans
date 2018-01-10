package de.blum.coltrans;

import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.Files.list;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author tina
 */
public class DecryptorALT2 {

    String secretTxt;
    String known;
    //int assumedBlocklenght;
    int[] testBlockLenghts = {2, 5, 7};

    public DecryptorALT2(String secretPath, String knownPlaintext) throws IOException {
        known = knownPlaintext.toLowerCase();
        // System.out.println((char)27 + "[33m"+known);
        List<String> secret = Files.readAllLines(Paths.get(secretPath));
        StringBuilder sb = new StringBuilder();
        for (String line : secret) {
            sb.append(line);
            //sb.append(" ");
        }
        secretTxt = sb.toString().toLowerCase();
        //System.out.print("\n\n" + secretTxt.length());
        System.out.print("\n\nknown plaintext: " + known);
        getBlockLenghtAndKey();
    }

    private void getBlockLenghtAndKey() {
        for (int currBlockLenght : testBlockLenghts) {
            int colLenght = ((secretTxt.length() - 1) / currBlockLenght);
            System.out.print("\n\nTRY block length: " + currBlockLenght + " col length: " + colLenght);
            List<Integer> assumedKey = new ArrayList<>();
            List<Integer> charPositions = new ArrayList<>();

            // List<Integer> indexOfChars;
            int wordOcc = 0;

            int lastCandidate;
            for (int charIndex = 0; charIndex < secretTxt.length(); charIndex++) {
                //System.out.print(secretTxt.charAt(charIndex));
                int keyCandidate = known.indexOf(secretTxt.charAt(charIndex));
                if (keyCandidate >= 0) {
                    charPositions.add(charIndex);
                    //System.out.print(charIndex + " ");
                }
            }
            System.out.print("\nchars found at"+charPositions.toString());
//            for(int index:charPositions){
//                System.out.print(index+" ");
//                for(int j = 1; j<=known.length();j++){
//                    if(charPositions.contains(index+(j*colLenght)) ){// || charPositions.contains(index-(j*colLenght))){
//                    System.out.print(index+" true");
//                    }else{
//                        charPositions.remove(index);
//                    }
//                }
//            }
            
            for (Iterator<Integer> iterator = charPositions.iterator(); iterator.hasNext();) {
                int pos = iterator.next();
                int wordlenght =0;
                //for (int j = known.length()-1; j >= 1; j--) { 
                    //System.out.print("\n"+j+"\n");
                    
                    if (charPositions.contains(pos + colLenght)  || charPositions.contains(pos - colLenght)) {
                        
                        //System.out.print("\n"+pos);
                        wordlenght++;
                    } else {
                        iterator.remove();
                        //System.out.print(pos + " removed \n");
                        //break;
                    }
                //}
            }
            System.out.print("\nremaining char positions"+charPositions.toString());
            
            
            
            
            if (assumedKey.size() == currBlockLenght) {
                System.out.print("\nblock length: " + currBlockLenght + " assumedkey: " + assumedKey.toString());
            }

        }
    }
//
//    private void getBlockLenghtAndKey() {
//        for (int currBlockLenght : testBlockLenghts) {
//            int colLenght = ((secretTxt.length() - 1) / currBlockLenght);
//            System.out.print("\n TRY block length: " + currBlockLenght + " col length: " + colLenght + "\n");
//            List<Integer> assumedKey = new ArrayList<>();
//            List<Integer> charPositions = new ArrayList<>();
//
//            // List<Integer> indexOfChars;
//            int wordOcc=0;
//
//            int lastCandidate;
//            for (int charIndex = 0; charIndex < secretTxt.length(); charIndex++) {
//                //System.out.print(secretTxt.charAt(charIndex));
//                int keyCandidate = known.indexOf(secretTxt.charAt(charIndex));
//                if (keyCandidate >= 0 && (charIndex + colLenght < secretTxt.length())) {
//
//                    int next = known.indexOf(secretTxt.charAt(charIndex + colLenght));
//                    if (next >= 0) {
//                        System.out.print(charIndex + "_" + secretTxt.charAt(charIndex) + "_");
//                        System.out.print(charIndex + colLenght + "_" + secretTxt.charAt(charIndex + colLenght) + " ");
//                        //assumedKey[currentKey]= keyCandidate;
//                        assumedKey.add(keyCandidate);
//                        charPositions.add(charIndex);
//                        lastCandidate=(charIndex + colLenght);
//                    }
//                } else if (keyCandidate >= 0) {
//                    int prev = known.indexOf(secretTxt.charAt(charIndex - colLenght));
//                    if (prev >= 0) {
//                        System.out.print(charIndex + "_" + secretTxt.charAt(charIndex) + "_");
//                        System.out.print(charIndex - colLenght + "_" + secretTxt.charAt(charIndex - colLenght) + " ");
//                        //assumedKey[currentKey]= keyCandidate;
//                        assumedKey.add(keyCandidate);
//                        lastCandidate=(charIndex);
//                        charPositions.add(charIndex);
//                    }
//                }
//                
//            }
//            if(assumedKey.size()==currBlockLenght){
//            System.out.print("\nblock length: "+currBlockLenght +" assumedkey: " + assumedKey.toString());
//            }
//        }
//    }

    private void decrypt() {

    }
}
