package com.realgood.ml2program1.parser;

import com.realgood.ml2program1.models.ProteinSequence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
/*******************************************
 * <h1>Protein Sequence Parser</h1>         *
 * Class used to parse proteins from input  *
 * @author Nicholas Moran                   *
 * @version 1.0                             *
 *                                          *
 *******************************************/
public class ProteinSequenceParser {
    private final List<ProteinSequence> proteinSequences;

    /****************************************************
     * Constructor for Parser object.
     * @param binaryInputFilePath = String file path to the input file
     */
    public ProteinSequenceParser(String binaryInputFilePath) {
        proteinSequences = generateProteinSequences(new File(binaryInputFilePath));
    }

    /*************************************************
     * Constructor for Parser object.
     * @param binaryInputFilePath = File object for the input.
     */
    public ProteinSequenceParser(File binaryInputFilePath) {
        proteinSequences = generateProteinSequences(binaryInputFilePath);
    }

    /*****************************************************
     * Function to parse input file and return protein sequences
     * @param inputPath Location of input file
     * @return List of ProteinSequence
     */
    private List<ProteinSequence> generateProteinSequences(File inputPath) {
        List<ProteinSequence> temp = new LinkedList<>();
        try {
            String line = null;
            FileReader reader = new FileReader(inputPath);
            BufferedReader buffer = new BufferedReader(reader);
            while ((line = buffer.readLine()) != null) {
                if (line.length() == 0 || line.charAt(0) == 'T') {
                    System.out.println("Skipping Total Protein Number");
                } else if (line.charAt(0) == 'S') {
                    String aminoAcid = parseAminoAcid(line);
                    line = buffer.readLine();
                    int fitness = parseFittness(line);
                    temp.add(new ProteinSequence(aminoAcid, fitness));
                } else {
                    throw new Exception("Something is wrong with input file");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;
    }

    /****
     * Function to parse only the h and p characters out of a line
     * @param line = String line from input file
     * @return Returns only the h and p charactars from that line
     */
    private String parseAminoAcid(String line) {
        String temp = "";
        for (int i = 0; i < line.length(); i++) {
            char index = line.charAt(i);
            if (index == 'p' || index == 'h') {
                temp += index;
            } else {
                System.out.println("Skipping Nonsense");
            }
        }
        return temp;
    }

    /****
     * Function to extract the fitness out of a line from the input file
     * @param line
     * @return
     */
    private int parseFittness(String line) {
        String temp = "";
        int i;
        for (i = 0; line.charAt(i) != '-'; i++) {
            System.out.println("Ain't found the fitness yet!");
        }
        while (i < line.length()) {
            temp += line.charAt(i++);
        }
        return Integer.parseInt(temp);
    }

    /****
     * Getter to return a List of ProteinSequence
     * @return List<ProteinSequence>
     */
    public List<ProteinSequence> getProteinSequences() {
        return this.proteinSequences;
    }
}
