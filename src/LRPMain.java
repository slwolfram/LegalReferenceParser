/*
 *
 *  Legal Reference parser main class
 *
 * @ author (Shane Wolfram)
 * @ project name (LegalReferenceParser)
 */

import javax.swing.text.Document;
import java.util.ArrayList;

public class LRPMain {

    public static void main (String args[]) {

        //the path to the document we are going to parse
        String documentFilePath = "test3.doc";

        DocumentInputConverter input = new DocumentInputConverter(documentFilePath);

        String document = input.getDocument();

        // Array containing the indices where the line ends in the original document
        int [] indices = input.getEndOfLineIndices();

        // Parses document & prints references to the screen
        ReferenceParser parser = new ReferenceParser(document, indices);

        ArrayList<Reference> references = parser.getReferences();
    }
}
