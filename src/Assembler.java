import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Coordinate the translation of MAL assembly code to text-based binary.
 *
 * @author mb2153.
 */
public class Assembler {
    // The lines of the input file.
    private List<String> input;
    // Where to write the output.
    private PrintWriter output;

    /**
     * Create an assembler.
     *
     * @param inputFile  The input file.
     * @param outputFile The output file.
     */
    public Assembler(String inputFile, String outputFile) throws IOException {
        input = Files.readAllLines(Paths.get(inputFile));
        output = new PrintWriter(new FileWriter(outputFile));
    }

    /**
     * Translate the input file, line by line.
     */
    public void assemble() {
        for (String line : input) {
            translateOneInstruction(line);
        }
        output.close();
    }

    /**
     * Translate one line of MAL assembly code to text-based binary.
     *
     * @param line The line to translate.
     */
    private void translateOneInstruction(String line) {
        // This just repeats the input line and does not translate.
        // Replace this with code to convert the instruction to
        // its binary equivalent.

        //System.out.println(line);

        // Opcodes: All opcodes (instruction names) are either 3 (e.g., ADD), 4 (e.g., COPY) or 5 (e.g., LOADN)
        // case-sensitive alphabetic characters long, entirely in upper-case.
        String opCode = "";
        int charCount = 0;

        int i = 0;
        while (i < line.length() && Character.isWhitespace(line.charAt(i))) {
            i++;
        }

        for (; i < line.length(); i++) {
            char currentChar = line.charAt(i);
            if (!Character.isWhitespace(currentChar)) {
                opCode = opCode + currentChar;
                charCount++;
            } else {
                break;
            }
        }
        //System.out.println(line);

        //ADD	0010
        //SUB	0011
        //JMP	0100
        //JGT	0101
        //JLT	0110
        //JEQ	0111

        String reg1 = "";
        String reg2 = "";
        String number = "";
        String binary = "";

        //this boolean tracks whether the current character should be a part of reg1 or reg2
        //if it is true then the next char that isn't a whitespace should be reg1
        //but if it is false then the next char that isn't whitespace should be reg2
        //essentially it's there for a whitespace check before and after the comma. In the brief it says:
        //"Commas: A comma is used to separate two operands in those instructions that have two operands.
        //There may be whitespace before and/or after a comma or no whitespace at all."
        boolean isReg1 = true;

        //the top (most significant) 4 bits
        String firstFour = "";
        //5th and 6th bit
        String fiveAndSix = "";
        //seventh and eighth bit
        String sevenAnd8 = "";

        int num = 0;

        if (charCount == 3) {
            switch (opCode) {
                case "ADD" -> {
                    //ADD	0010
                    //grab two registers
                    //"Add the value in reg2 to the value in reg1, leaving the result in reg1."

                    //start the loop after the space that comes after the word "ADD"
                    //i is already set to the value where the word ADD ends at this point because of
                    //the logic that finds the opCode.
                    //this while loop will be in every case and will correctly find the index
                    //where the opcode starts no matter how many whitespaces.
                    while (i < line.length() && Character.isWhitespace(line.charAt(i))) {
                        i++;
                    }

                    //find reg1 and reg2
                    for (; i < line.length(); i++) {
                        char currentChar = line.charAt(i);
                        if (!Character.isWhitespace(currentChar) && currentChar != ',') {
                            if (isReg1) {
                                reg1 = reg1 + currentChar;
                            } else {
                                reg2 = reg2 + currentChar;
                            }
                        } else if (currentChar == ',') {
                            isReg1 = false;
                        }
                    }

                    //ADD	0010
                    firstFour = "0010";
                    fiveAndSix = "";
                    sevenAnd8 = "";

                    //logic for translating line
                    if (reg1.equals("A")) {
                        fiveAndSix = "01";
                    } else if (reg1.equals("D")) {
                        fiveAndSix = "10";
                    }
                    if (reg2.equals("A")) {
                        sevenAnd8 = "01";
                    } else if (reg2.equals("D")) {
                        sevenAnd8 = "10";
                    }

                    //write the line to the output file!
                    line = firstFour + fiveAndSix + sevenAnd8;
                    output.println(line);

                }


//                    for (int i = 4; i < line.length(); i++) {
//                        char currentChar = line.charAt(i);
//                        //System.out.print(currentChar);
//                        if (!Character.isWhitespace(currentChar) && currentChar != ',') {
//                            if (isReg1) {
//                                reg1 = reg1 + currentChar;
//                            } else {
//                                reg2 = reg2 + currentChar;
//                            }
//                        } else if (currentChar == ',') {
//                            isReg1 = false;
//                        }
//                    }
//
////                    System.out.println();
////                    System.out.println("reg1 = " + reg1);
////                    System.out.println("reg2 = " + reg2);
////                    System.out.println();
//
//                    //ADD	0010
//                    firstFour = "0010";
//                    fiveAndSix = "";
//                    sevenAnd8 = "";
//
//                    //logic for translating line
//                    if (reg1.equals("A")) {
//                        fiveAndSix = "01";
//                    } else if (reg1.equals("D")) {
//                        fiveAndSix = "10";
//                    }
//                    if (reg2.equals("A")) {
//                        sevenAnd8 = "01";
//                    } else if (reg2.equals("D")) {
//                        sevenAnd8 = "10";
//                    }
//
//                    //write the line to the output file!
//                    line = firstFour + fiveAndSix + sevenAnd8;
//                    output.println(line);

                case "SUB" -> {
                    //SUB	0011
                    //same as ADD pretty much

                    while (i < line.length() && Character.isWhitespace(line.charAt(i))) {
                        i++;
                    }

                    //find reg1 and reg2
                    //start the loop after the space that comes after the word "SUB"
                    for (; i < line.length(); i++) {
                        char currentChar = line.charAt(i);
                        //System.out.print(currentChar);
                        if (!Character.isWhitespace(currentChar) && currentChar != ',') {
                            if (isReg1) {
                                reg1 = reg1 + currentChar;
                            } else {
                                reg2 = reg2 + currentChar;
                            }
                        } else if (currentChar == ',') {
                            isReg1 = false;
                        }
                    }

//                    System.out.println();
//                    System.out.println("reg1 = " + reg1);
//                    System.out.println("reg2 = " + reg2);
//                    System.out.println();

                    //SUB	0011
                    firstFour = "0011";
                    fiveAndSix = "";
                    sevenAnd8 = "";

                    //logic for translating line
                    if (reg1.equals("A")) {
                        fiveAndSix = "01";
                    } else if (reg1.equals("D")) {
                        fiveAndSix = "10";
                    }
                    if (reg2.equals("A")) {
                        sevenAnd8 = "01";
                    } else if (reg2.equals("D")) {
                        sevenAnd8 = "10";
                    }

                    //write the line to the output file!
                    line = firstFour + fiveAndSix + sevenAnd8;
                    output.println(line);
                }
                case "JMP" -> {

                    //start the loop after the space that comes after the word "JMP"
                    //find the numeric constant

                    while (i < line.length() && Character.isWhitespace(line.charAt(i))) {
                        i++;
                    }

                    for (; i < line.length(); i++) {
                        char currentChar = line.charAt(i);
                        if (!Character.isWhitespace(currentChar)) {
                            number = number + currentChar;
                        }
                    }
                    num = Integer.parseInt(number);
                    //System.out.println(num);

                    //JMP	0100
                    firstFour = "0100";
                    fiveAndSix = "00";
                    sevenAnd8 = "00";
                    line = firstFour + fiveAndSix + sevenAnd8;
                    output.println(line);

                    //convert number to binary
                    binary = String.format("%8s", Integer.toBinaryString(num)).replace(' ', '0');
                    output.println(binary);
                }
                case "JLT" -> {
                    while (i < line.length() && Character.isWhitespace(line.charAt(i))) {
                        i++;
                    }
                    for (; i < line.length(); i++) {
                        char currentChar = line.charAt(i);
                        //System.out.print(currentChar);
                        if (!Character.isWhitespace(currentChar) && currentChar != ',') {
                            if (isReg1) {
                                reg1 = reg1 + currentChar;
                            } else {
                                number = number + currentChar;
                            }
                        } else if (currentChar == ',') {
                            isReg1 = false;
                        }
                    }

                    //JLT	0110
                    firstFour = "0110";
                    if (reg1.equals("A")) {
                        fiveAndSix = "01";
                    } else if (reg1.equals("D")) {
                        fiveAndSix = "10";
                    }
                    sevenAnd8 = "00";
                    line = firstFour + fiveAndSix + sevenAnd8;
                    output.println(line);
                    num = Integer.parseInt(number);
                    binary = String.format("%8s", Integer.toBinaryString(num)).replace(' ', '0');
                    output.println(binary);
                }
                case "JGT" -> {
                    while (i < line.length() && Character.isWhitespace(line.charAt(i))) {
                        i++;
                    }
                    for (; i < line.length(); i++) {
                        char currentChar = line.charAt(i);
                        //System.out.print(currentChar);
                        if (!Character.isWhitespace(currentChar) && currentChar != ',') {
                            if (isReg1) {
                                reg1 = reg1 + currentChar;
                            } else {
                                number = number + currentChar;
                            }
                        } else if (currentChar == ',') {
                            isReg1 = false;
                        }
                    }

                    //JGT	0101
                    firstFour = "0101";
                    if (reg1.equals("A")) {
                        fiveAndSix = "01";
                    } else if (reg1.equals("D")) {
                        fiveAndSix = "10";
                    }
                    sevenAnd8 = "00";
                    line = firstFour + fiveAndSix + sevenAnd8;
                    output.println(line);
                    num = Integer.parseInt(number);
                    binary = String.format("%8s", Integer.toBinaryString(num)).replace(' ', '0');
                    output.println(binary);
                }
                case "JEQ" -> {
                    while (i < line.length() && Character.isWhitespace(line.charAt(i))) {
                        i++;
                    }
                    for (; i < line.length(); i++) {
                        char currentChar = line.charAt(i);
                        //System.out.print(currentChar);
                        if (!Character.isWhitespace(currentChar) && currentChar != ',') {
                            if (isReg1) {
                                reg1 = reg1 + currentChar;
                            } else {
                                number = number + currentChar;
                            }
                        } else if (currentChar == ',') {
                            isReg1 = false;
                        }
                    }

                    //JEQ	0111
                    firstFour = "0111";
                    if (reg1.equals("A")) {
                        fiveAndSix = "01";
                    } else if (reg1.equals("D")) {
                        fiveAndSix = "10";
                    }
                    sevenAnd8 = "00";
                    line = firstFour + fiveAndSix + sevenAnd8;
                    output.println(line);
                    num = Integer.parseInt(number);
                    binary = String.format("%8s", Integer.toBinaryString(num)).replace(' ', '0');
                    output.println(binary);
                }
                default -> {
                }
                //charCount is not 3
            }
        } else if (charCount == 4) {
            //COPY	1000
            //charCount is not 4
            while (i < line.length() && Character.isWhitespace(line.charAt(i))) {
                i++;
            }
            if (opCode.equals("COPY")) {//"Copy the value in reg2 into reg1."
                for (; i < line.length(); i++) {
                    char currentChar = line.charAt(i);
                    //System.out.print(currentChar);
                    if (!Character.isWhitespace(currentChar) && currentChar != ',') {
                        if (isReg1) {
                            reg1 = reg1 + currentChar;
                        } else {
                            reg2 = reg2 + currentChar;
                        }
                    } else if (currentChar == ',') {
                        isReg1 = false;
                    }
                }

                //COPY	 1000
                firstFour = "1000";
                fiveAndSix = "";
                sevenAnd8 = "";

                //logic for translating line
                if (reg1.equals("A")) {
                    fiveAndSix = "01";
                } else if (reg1.equals("D")) {
                    fiveAndSix = "10";
                }
                if (reg2.equals("A")) {
                    sevenAnd8 = "01";
                } else if (reg2.equals("D")) {
                    sevenAnd8 = "10";
                }

                //write the line to the output file!
                line = firstFour + fiveAndSix + sevenAnd8;
                output.println(line);
            }
        } else if (charCount == 5) {
            //LOADN	0000
            //LOADA	0001
            //STORE	1001
            switch (opCode) {
                case "STORE" -> {
                    while (i < line.length() && Character.isWhitespace(line.charAt(i))) {
                        i++;
                    }
                    //"Store the value in register A to memory location num."
                    for (; i < line.length(); i++) {
                        char currentChar = line.charAt(i);
                        if (!Character.isWhitespace(currentChar)) {
                            number = number + currentChar;
                        }
                    }
                    num = Integer.parseInt(number);
                    //System.out.println(num);

                    //STORE 1001
                    firstFour = "1001";
                    fiveAndSix = "00";
                    sevenAnd8 = "00";
                    line = firstFour + fiveAndSix + sevenAnd8;
                    output.println(line);

                    //convert number to binary
                    binary = String.format("%8s", Integer.toBinaryString(num)).replace(' ', '0');
                    output.println(binary);
                }
                case "LOADN" -> {
                    while (i < line.length() && Character.isWhitespace(line.charAt(i))) {
                        i++;
                    }
                    //"Load the value num into register A. Note that register A is implicit and is not encoded in the translation."
                    for (; i < line.length(); i++) {
                        char currentChar = line.charAt(i);
                        if (!Character.isWhitespace(currentChar)) {
                            number = number + currentChar;
                        }
                    }
                    num = Integer.parseInt(number);
                    //System.out.println(num);

                    //LOADN 0000
                    firstFour = "0000";
                    fiveAndSix = "00";
                    sevenAnd8 = "00";
                    line = firstFour + fiveAndSix + sevenAnd8;
                    output.println(line);

                    //convert number to binary
                    binary = String.format("%8s", Integer.toBinaryString(num)).replace(' ', '0');
                    output.println(binary);
                }
                case "LOADA" -> {
                    while (i < line.length() && Character.isWhitespace(line.charAt(i))) {
                        i++;
                    }
                    //"Load the value stored at memory location num into register A. Note that register A is implicit and is not encoded in the translation."
                    for (; i < line.length(); i++) {
                        char currentChar = line.charAt(i);
                        if (!Character.isWhitespace(currentChar)) {
                            number = number + currentChar;
                        }
                    }
                    num = Integer.parseInt(number);
                    //System.out.println(num);

                    //LOADA 0001
                    firstFour = "0001";
                    fiveAndSix = "00";
                    sevenAnd8 = "00";
                    line = firstFour + fiveAndSix + sevenAnd8;
                    output.println(line);

                    //convert number to binary
                    binary = String.format("%8s", Integer.toBinaryString(num)).replace(' ', '0');
                    output.println(binary);
                }
                default -> {
                }
            }
        }

        //System.out.println(opCode);
        opCode = "";
        charCount = 0;

        //output.println(line);
    }

}



