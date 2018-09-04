/*
* Reads txt document input line by line, converting to a single string. This avoids "end of line"
* issues (if a reference is split between lines.) Also generates a list of indices for the document
* string which mark the position of end-of-lines in the original document. That way, we can track
* where the matched references are located in the original document as opposed to just the
* string.
*
*  @author (Shane Wolfram)
*  @ main class (LRP Main)
* @ project name (LegalReferenceParser)
*
*  @ bugs
* */

import java.io.*;
import java.util.ArrayList;

public class DocumentInputConverter
{

    // the full document as a string
    private String document;

    // tracks end of line indices
    private int [] endOfLineIndices;

    /*
    * See class header
    *
    * @ param (String filepath) (The path to the document file that we
    *   will attempt to read)
    * */

    public DocumentInputConverter(String filepath) {

        BufferedReader in = null;

        // a list of document lines
        ArrayList<String> lines = new ArrayList<String>();

        try {

            //open reader
            in = new BufferedReader(new FileReader(new File(filepath)));

            String line;

            while ((line = in.readLine()) != null) {

                //add line to list
                lines.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                //close reader
                in.close();

            } catch (IOException e ) {
                e.printStackTrace();
            }
        }

        setDocument(ConvertLineArrayToString(lines));
        setEndOfLineIndices(GenerateEndOfLineIndices(lines));
    }

    /*
    *  Generates end-of-line indices, which used by the ReferenceParser class
    *  to map "document String" indices to the line and column index of the original document
    *
    *  @ param (ArrayList<String> lines) (an ArrayList of the lines of text from the source document)
    *
    *  @  return (int [] indices) (an int array where entries 0...n contain the indices of the document-string
    *  that map to the LAST character of lines 0...n in the original document.
    *
    * */

    private int[] GenerateEndOfLineIndices(ArrayList<String> lines) {

        //check for valid input
        if (lines == null || lines.get(0) == null) {
            return null;
        }

        // see method header
        int [] indices = new int [lines.size()];

        //set first entry
        indices[0] = lines.get(0).length();

        // For the next entries, add the current line length to the
        // previous entry
        for (int i = 1; i < lines.size(); i++) {
            indices[i] = lines.get(i).length() + indices[i - 1];
        }

        return indices;
    }

    /*
     *  Simply joins the lines ArrayList together into a single string
     *
     *  @ param (ArrayList<String> lines) (an ArrayList of the lines of text from the source document)
     *
     *  @  return (String doc) (the full document as a single string)
     *
     * */

    private String ConvertLineArrayToString(ArrayList<String> lines) {

        //check for valid input
        if (lines == null || lines.get(0) == null) {
            return null;
        }

        return String.join("", lines);
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public int[] getEndOfLineIndices() {
        return endOfLineIndices;
    }

    public void setEndOfLineIndices(int[] endOfLineIndices) {
        this.endOfLineIndices = endOfLineIndices;
    }
}
