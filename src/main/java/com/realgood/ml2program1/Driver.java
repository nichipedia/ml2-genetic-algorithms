package com.realgood.ml2program1;

import com.realgood.ml2program1.models.ProteinSequence;
import com.realgood.ml2program1.parser.ProteinSequenceParser;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by NicholasMoran on 2/8/18.
 */
public class Driver {
    private static final Random rando = new Random();
    private static final int max = 100;

    public static void main(String[] args) {
        test();
    }

    private static void test() {
        ProteinSequenceParser repo = new ProteinSequenceParser("/Users/NicholasMoran/Downloads/input.txt");
        for (ProteinSequence sequence:repo.getProteinSequences()) {
            System.out.println("Acid: " + sequence.toString());
            System.out.println("Fitness: " + sequence.getFitness());
        }
    }

    private static void hillClimb() {
        for (int i = 0; i < max; i++) {
            Boolean[] current = generateBinaryInput();
            boolean local = false;
            while (!local) {
                Boolean[] next = findLargest(generateNeighbors(current));
                if (evaluate(current) < evaluate(next)) {
                    current = next;
                } else {
                    local = !local;
                }
            }
            System.out.print(evaluate(current) + ", ");
        }
    }


    private static Boolean[] generateBinaryInput() {
        Boolean[] boolArray = new Boolean[40];
        for (int i = 0; i < 40; i++) {
            boolArray[i] = rando.nextBoolean();
        }
        return boolArray;
    }

    private static Boolean[] findLargest(List<Boolean[]> neighbors) {
        int largest = 0;
        Boolean[] next = null;
        for (Boolean[] neighbor:neighbors) {
            int temp = evaluate(neighbor);
            if (largest < temp) {
                largest = temp;
                next = neighbor;
            }
        }
        return next;
    }

    private static List<Boolean[]> generateNeighbors(Boolean[] seed) {
        List<Boolean[]> binary = new LinkedList<>();
        Boolean[] current = seed;
        for (int i = 0; i < 40; i++) {
            current = swapRandomBit(seed);
            binary.add(current);
        }
        return binary;
    }

    private static Boolean[] swapRandomBit(Boolean[] binary) {
        Boolean[] temp = binary;
        int index = rando.nextInt(40);
        temp[index] = !temp[index];
        return temp;
    }

    private static int evaluate(Boolean[] binary) {
        return Math.abs((12 * countTrue(binary)) - 160);
    }

    private static int countTrue(Boolean[] binary) {
        int count = 0;
        for (Boolean value:binary) {
            if (value) {
                count++;
            }
        }
        return count;
    }

}
