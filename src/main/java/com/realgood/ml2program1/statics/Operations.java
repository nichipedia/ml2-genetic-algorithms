package com.realgood.ml2program1.statics;

import com.realgood.ml2program1.models.AminoAcid;
import com.realgood.ml2program1.models.ProteinSequence;
import com.realgood.ml2program1.models.ProteinSequencePlan;

import java.util.Random;

/**
 * Created by NicholasMoran on 2/11/18.
 */
public class Operations {
    private static final Random rando = new Random();

    public static ProteinSequence cross(ProteinSequence father, ProteinSequence mother) {
        return null;
    }

    public static void mutate(ProteinSequence sequence) {
        int index = rando.nextInt(sequence.getLength());
        sequence.swapAcid(index);
    }
}
