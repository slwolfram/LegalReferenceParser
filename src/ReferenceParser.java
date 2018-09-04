/*
*
*  Uses Java.Util.regex.Matcher to parse the document-string references matching the
 * regex defined by the pattern parameter. Prints matching results to the screen, along
 * with their starting and ending line and column in the original document.
 *
 * @ author (Shane Wolfram)
 * @ main class (LRP Main)
 * @ project name (LegalReferenceParser)
 */

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReferenceParser {

    private ArrayList<Reference> references;
    private int [] endOfLineIndices;

    /*
    *  Constructor. Sets field variables and calls matcher method
    *
    *  @ param (String document) (the document we are parsing, converted to a string)
    *  @ param (int [] indices) (a list of document-string indices which correspond to the
    *       end-of-line indices of the original doc (obtained from DocumentInputConverter)
    *
    * */
    public ReferenceParser(String document, int [] indices) {

        // set field variables
        references = new ArrayList<>();
        endOfLineIndices = indices;

        // start parsing using document and specified regex
        MatchReferences(document, ReferenceParserCodes.ALL_REFERENCES);
    }

    /*
    * Uses Java.Util.regex.Matcher to parse the document-string references matching the
    * regex defined by the pattern parameter. Prints matching results to the screen, along
    * with their starting and ending line and column in the original document.
    *
    * @param (String document) (the document-string we are matching)
    * @param (String pattern) (the regex we are doing the matching with)
    *
    * */
    public void MatchReferences(String document, String pattern) {

        // set pattern
        Pattern checkRegex = Pattern.compile(pattern);

        // set matcher to use document
        Matcher regexMatcher = checkRegex.matcher(document);

        System.out.println("-------------------------------------------");

        while(regexMatcher.find()) {

            if (regexMatcher.group().length() != 0) {

                // print matched pattern
                System.out.println(regexMatcher.group().trim());

                // print pattern location
                System.out.println("Start Index: " + indexToString(regexMatcher.start()));
                System.out.println("End Index: " + indexToString(regexMatcher.end()));
                System.out.println("-------------------------------------------");
            }
        }
    }

    public Reference parseMatchedReference(String reference) {

        return null;
    }

    public ArrayList<Reference> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<Reference> references) {
        this.references = references;
    }

    /* Converts the an index of the document as a string to its row and column value in the original
    *  document
    *
    * @ param (int stringIndex) (an index in the document-string)
    * @ return (a integer array with the matching row, column value in the original document)
    *
    * */
    public int[] convertStringIndexToDocumentIndex(int stringIndex) {

        for (int i = 0; i < endOfLineIndices.length-1; i++) {
            if (stringIndex <= endOfLineIndices[0]) {
                return new int [] {1, stringIndex + 1};
            }
            if (stringIndex > endOfLineIndices[i] && stringIndex <= endOfLineIndices[i+1]) {
                return new int [] {i+2, stringIndex - endOfLineIndices[i] + 1};
            }
        }
        return null;
    }

    /* Calls convertStringIndexToDocumentIndex on an index and converts the
     * result to a string which can then be printed to the screen
     *
     * @ param (index) (an index in the document-string)
     * @ return (a string telling the matching row and column in the original document)
     *
     * */
    public String indexToString(int index) {

        int rowCol [] = convertStringIndexToDocumentIndex(index);

        if (rowCol == null || rowCol.length != 2) {
            System.exit(-1);
        }

        return "line " + rowCol[0] + ", column " + rowCol[1];
    }
}
