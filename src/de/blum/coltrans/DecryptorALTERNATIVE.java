package de.blum.coltrans;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author tina
 */
public class DecryptorALTERNATIVE {

    String secretTxt;
    String known;
    // int[] testBlockLenghts = {2, 5, 10};
    int[] testBlockLenghts = {2,5};
    List<Integer> charPositions = new ArrayList<>(); //positionen von known chars in secretTXT

    public DecryptorALTERNATIVE(String secretPath, String knownPlaintext) throws IOException {

        known = knownPlaintext.toLowerCase();
        List<String> secret = Files.readAllLines(Paths.get(secretPath));
        StringBuilder sb = new StringBuilder();
        for (String line : secret) {
            sb.append(line);
        }
        secretTxt = sb.toString().toLowerCase();

        System.out.print("\n\nknown plaintext: " + known);
        for (int charIndex = 0; charIndex < secretTxt.length(); charIndex++) {
            int keyCandidate = known.indexOf(secretTxt.charAt(charIndex));
            if (keyCandidate >= 0) {
                charPositions.add(charIndex);
            }
        }
        System.out.print("\nchars found at" + charPositions.toString());
        guessBlockLength();
    }

    private void guessBlockLength() {
        for (int currBlockLenght : testBlockLenghts) {
            int colLenght = ((secretTxt.length() - 1) / currBlockLenght);
            System.out.print("\n\n\tTRY block length: " + currBlockLenght + " col length: " + colLenght);
            List<Integer> charPos = new ArrayList<>();
//            for (Iterator<Integer> iterator = charPositions.iterator(); iterator.hasNext();) {
//                int pos = iterator.next();
//                int coherentChars =0;
//                for (int i = 1; i < known.length(); i++) {
//                    if (charPositions.contains(pos + i * colLenght) || charPositions.contains(pos - i * colLenght)) {
//                        //if (pos != lastPos || pos==0) {
//                        coherentChars++;
//                        System.out.println(coherentChars);
//                        if (!charPos.contains(pos)) {
//                            charPos.add(pos);
//                        }
//                    }
//                }
//                if(coherentChars==known.length()-1 ){//&& !charPos.contains(pos)){
//                     System.out.println("true"+charPos.contains(pos));
//                                  if (!charPos.contains(pos)) {
//                        }
//                }
//            }
            //System.out.println("\n");
            for (Iterator<Integer> iterator = charPositions.iterator(); iterator.hasNext();) {
                int pos = iterator.next();
                int coherentChars = 0;
                //System.out.println("\n"+ pos);
                for (int i = 1; i < known.length(); i++) {
                    if (charPositions.contains(pos + i * colLenght)) {
                        coherentChars++;
                        //System.out.println("\t"+coherentChars);
                    }
                }

                if (coherentChars == (known.length() - 1) && !charPos.contains(pos)) {
                    charPos.add(pos);
                    for (int i = 1; i < known.length(); i++) {
                        charPos.add(pos + i * colLenght);
                        //System.out.println("\ntrue " + (pos + i * colLenght));
                    }
                }
                //System.out.println("\n"+ pos);
            }
            if (charPos.size() > 0) {
                System.out.print("\n\tremaining char positions" + charPos.toString());
                highlightSecret(currBlockLenght, charPos);
                if (charPos.size() % known.length() == 0) {
                    System.out.print("\n\tknown words found: " + charPos.size() / known.length());
                    guessKey(currBlockLenght, charPos);
                }
            } else {
                System.out.print("\n\t\tnothing found");
            }

        }

    }

    private void guessKey(int blockLenght, List<Integer> positions) {
        if (blockLenght <= known.length()) {
            int[] assumedKey = new int[blockLenght];
            for (int i = 0; i <= blockLenght - 1; i++) {
                if (i < positions.size()) {
                    int keyCandidate = known.indexOf(secretTxt.charAt(positions.get(i)));
                    if (keyCandidate >= 0) {
                        assumedKey[i] = keyCandidate;
                    }
                }
            }
            System.out.print("\n\tassumed key:" + Arrays.toString(assumedKey));
        }else{
            int[] assumedKey = new int[known.length()];
            for (int i = 0; i <= known.length() - 1; i++) {
                if (i < positions.size()) {
                    int keyCandidate = known.indexOf(secretTxt.charAt(positions.get(i)));
                    if (keyCandidate >= 0) {
                        assumedKey[i] = keyCandidate;
                    }
                }
            }
            System.out.print("\n\tassumed key (partial):" + Arrays.toString(assumedKey));
        }

    }

    private void highlightSecret(int blockLenght, List<Integer> positions) {
        System.out.print("\n\t");
        for (int charIndex = 0; charIndex < secretTxt.length(); charIndex++) {
            if (positions.contains(charIndex)) {
                System.out.print((char) 27 + "[31m" + secretTxt.charAt(charIndex) + (char) 27 + "[0m");
            } else {
                System.out.print(secretTxt.charAt(charIndex));
            }
            if ((charIndex + 1) % blockLenght == 0) {
                System.out.print(" ");
            }
        }
//                if (positions.size() % known.length() == 0) {
//                    System.out.print("\nknown words found: " + positions.size() / known.length());
//                    guessKey(blockLenght, positions);
//                }

    }

}
