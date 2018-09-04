/*
*  This class contains the legal reference information, broken into specific attributes.
*
*  @ Shane Wolfram
* */

public class Reference {

    // The reference chapter. Statute chapters are numberic (viz. 1-995) but the administrative code chapters
    // are also given alphabetic codes to represent the agency (such as NR 100 for natural resources chapter 100.)
    // This class uses the "agency" field record the agency code.
    private int chapter;

    // Subchapters (distinct from sections/subsections) are given roman numerals
    private String subchapter;

    // Sections numbers generally contain the chapter and section numbers, separated by a decimal point. This field is
    // only used to contain the part representing the section - i.e. to the right of the decimal point.
    private int section;

    // e.g. 5.12 (a) (17) d. The subsection part of this is "(a) (17) d"
    private String subsection;

    // The type attribute is used to define the agency in references to the administrative code
    private String agency;

    // The line and line-index where the reference begins and ends in the original document
    private int [] startOfReference = new int [2];
    private int [] endOfReference = new int[2];



    public int getChapter() {
        return chapter;
    }

    public void setChapter(int chapter) {
        this.chapter = chapter;
    }

    public String getSubchapter() {
        return subchapter;
    }

    public void setSubchapter(String subchapter) {
        this.subchapter = subchapter;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public int[] getStartOfReference() {
        return startOfReference;
    }

    public void setStartOfReference(int[] startOfReference) {
        this.startOfReference = startOfReference;
    }

    public int[] getEndOfReference() {
        return endOfReference;
    }

    public void setEndOfReference(int[] endOfReference) {
        this.endOfReference = endOfReference;
    }

    public String toString() {

        String r = null;

        try {

            r = "Chapter: " + this.chapter + "\n";

            if (this.subchapter != null)
                r += "Subchapter: " + this.subchapter + "\n";
            if (this.section ==  0)
                r += "Section: " + this.section + "\n";
            if (this.subsection != null)
                r += "Subsection: " + this.subsection + "\n";

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return r;
    }
    public void print() {
        System.out.print(this.toString());
    }
}
