package com.realgood.ml2program1.parser;

import com.realgood.ml2program1.models.ProteinSequence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by NicholasMoran on 2/11/18.
 */
public class ProteinSequenceParser {
    private final List<ProteinSequence> proteinSequences;

    public ProteinSequenceParser(String binaryInputFilePath) {
        proteinSequences = generateProteinSequences(new File(binaryInputFilePath));
    }

    public ProteinSequenceParser(File binaryInputFilePath) {
        proteinSequences = generateProteinSequences(binaryInputFilePath);
    }

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

    public List<ProteinSequence> getProteinSequences() {
        return this.proteinSequences;
    }
}
