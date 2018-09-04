public class ReferenceParserCodes {


    /*
     *   This class contains the regex patterns used by the ReferenceParser class to match references.
     *   The structure of the full matcher algorithm (which will ideally match all references thrown at it)
     *   is represented by the outline below. Individual components are defined beneath that.
     *
     *   KNOWN BUGS:
     *   1. lists of chapters with prefixed subchapters (e.g. subchapter iv. of Chapter 3, subchapter x. of
     *           Chapter 5) will match "subchapter iv. of Chapter 3, subchapter x." and "Chapter 5" ...
     *           Will need to be fixed by adding lookahead to SUBCHAPTER_END_R and SUBCHAPTER_END_O
     *
     * Outline  --
     *
     * I. REFERENCE_R (required)
     *       A. SUBCHAPTER_FRONT_R (optional)
     *           1. SUBCHAPTER_PREFIX (required) -- subchapter | subchapters | subch |
     *                   subchs | subch. | subchs.
     *           2. SUBCHAPTER_NUM (required) -- any roman numeral (say, less than 500)
     *           3. BELONGS_TO (optional) -- of | ??    or SEPARATOR (see below)
     *       B. CHAPTER_R (required)
     *           1. CHAPTER_PREFIX (required) -- chapter | chapters | ch. | ch | chs. | chs
     *               | section | sections | s. | s | ss. | ss
     *           2. AGENCY (optional) -- (see agency codes below)
     *           3. CHAPTER_NUM (required) -- any nonnegative integer
     *           4. SECTION_NUM (optional) -- decimal point + any nonnegative integer
     *           5. SUBSECTION_A (optional)
     *       C. SUBCHAPTER_END_R (optional)
     *           1. SEPARATOR (required) -- , | ; | ??
     *           2. SUBCHAPTER_PREFIX (required) -- subchapter | subch | subch.
     *           3. SUBCHAPTER_NUM (required) -- any roman numeral (say, less than 500)
     *
     * II. REFERENCE_SPAN (optional)
     *       A. SPAN_KEYWORD (required)
     *       B. REFERENCE_O (same as REFERENCE_R except ALL subcomponents are OPTIONAL.
     *               so "_O" components are used instead of the above "_R" components
     *
     * III. REFERENCE_LIST (optional)
     *       A. any number of:
     *           1. LIST_KEYWORD (required) -- SEPARATOR | and | or
     *           2. REFERENCE_O (required - by default)
     *           3. REFERENCE_SPAN (optional)
     *
     * ********************************************************************************/
    /***************************************
     * Connector/Separator Codes
     **/

    public static final String BELONGS_TO = "(?i)\\s*of\\s*";

    public static final String SEPARATOR = "(?i)(\\s*(\\,|\\;)\\s*)";

    public static final String LIST_KEYWORD = "(?i)(\\s*\\,\\s*|\\s*\\;\\s*|\\s*and\\s*|\\s*or\\s*){1,2}";

    public static final String SPAN_KEYWORD = "\\s*(to|through)\\s*";

    /***********************************************************
     * Chapter Components
     * */
    ////////////////// CHAPTER PREFIX CODES //////////////////////////////////////////////////

    //matches "chapter", "ch", and "ch."
    private static final String CH_PREF = "(?i)\\s*(chapter|ch\\.)\\s*";

    //matches "chapters", "chs", and "chs."
    private static String CH_PREF_PL = "(?i)\\s*(chapters|chs\\.)\\s*";

    //matches "section", "s.", and "s"
    private static String SEC_PREF = "(?i)\\s*(section|s\\.)\\s*";

    //matches "sections", "ss.", and "ss"
    private static String SEC_PREF_PL = "(?i)\\s*(sections|ss\\.)\\s*";

    //matches chapter | chapters | ch. | ch | chs. | chs | section | sections | s. | s | ss. | ss
    public static String CHAPTER_PREF = "(" + CH_PREF + "|" + CH_PREF_PL + "|" + SEC_PREF + "|" + SEC_PREF_PL + ")";
    /////////////////// END CHAPTER PREFIX CODES ////////////////////////////////////////

    ///////////// SUBSECTION CODES //////////////////////////////////////////////////////////
    // Matches single subsection references and spans

    public static final String SUBSECTION_SPAN = SPAN_KEYWORD + "(?i)\\s*[(\\([a-z]{1,3}\\))|(\\([0-9]{1,3}\\))|([a-z]{1,3}\\.)]{1,3}";
    public static final String SUBSECTION_A = "(?i)\\s*(\\([a-z]{1,3}\\)\\s*(\\([0-9]{1,3}\\))?\\s*([a-z]{1,3}\\.)?)\\s*" + "(" + SUBSECTION_SPAN + ")?";
    ///////////// END SUBSECTION CODES ////////////////////////////////////////////////

    //Matches any of the administrative codes listed at https://docs.legis.wisconsin.gov/code/admin_code
    private static final String AGENCY = "(?i)\\s*(Accy|Adm|ATCP|A-E|AB|AT|CB|DCF|Chir|CSB|DOC" +
            "|Cos|CVRB|DE|DI|DC|EL|WEM|ETF|ERC|ER|ER-MRS|ETH|DFI|FD|Game|GHSS" +
            "|DHS|HAS|HA|HEA|HS|Ins|IB|JC|Jus|KB|LIRC|LES|RB|MPSW|MTBT|Med|DMA" +
            "|NR|N|NHA|OT|Opt|PAC|Phar|PT|Pod|Psy|PD|PI|PR|PSC|RAD|RR|REEB|Tax|SPS" +
            "|SFP|TA|TCS|Tour|Trans|Trans-RR|UWS|VA|VE|WFSB|DWD)\\s*";

    // Matches any nonnegative integer
    private static final String CHAPTER_NUM = "\\s*[0-9]{1,}\\s*";

    // Matches a decimal point followed by any nonnegative integer
    private static final String SECTION_NUM = "\\s*\\.[0-9]{1,}\\s*";

    public static final String CHAPTER_R = CHAPTER_PREF + "(" + AGENCY + ")?" + CHAPTER_NUM + "(" + SECTION_NUM + ")?(" + SUBSECTION_A + ")?";
    public static final String CHAPTER_O = "(" + CHAPTER_PREF + ")?(" + AGENCY + ")?" + CHAPTER_NUM + "(" + SECTION_NUM + ")?(" + SUBSECTION_A + ")?";

    /****************************************************
     * Subchapter components
     **/

    // matches "subchapter", "subch", and "subch."
    private static String SUBCH_PREF = "(?i)\\s*(subchapter|subch\\.?)\\s*";

    // matches "subchapters", "subchs", and "subchs."
    private static String SUBCH_PREF_PL = "(?i)\\s*(subchapters|subchs\\.?)\\s*";

    // matches all subchapter prefixes
    public static String SUBCHAPTER_PREF = "(" + SUBCH_PREF + "|" + SUBCH_PREF_PL + ")";

    // matches roman numeral values
    private static String SUBCHAPTER_NUM = "(?i)\\s[IVXLC]{1,}\\s*";
    //SUBCHAPTER_PREF + "(?!\\s*[IVXLC]{1,}\\s*of)" + SUBCHAPTER_NUM
    // matches postfixed subchapters (e.g. chapter 42, subchapter iv). Also matches postfixed subchapter spans. Requires a seperator (, or ;) to be used.
    public static final String SUBCHAPTER_END_R = SEPARATOR + SUBCHAPTER_PREF +  "((" + "(?!\\s*[IVXLC]{1,}\\s*(of|to))" + SUBCHAPTER_NUM + ")|(" + SUBCHAPTER_NUM + SPAN_KEYWORD + "(?!\\s*[IVXLC]{1,}\\s*of)" + SUBCHAPTER_NUM + "))";
    public static final String SUBCHAPTER_END_O = SEPARATOR + "(" + SUBCHAPTER_PREF + ")?" +  "((" + "(?!\\s*[IVXLC]{1,}\\s*(of|to))" + SUBCHAPTER_NUM + ")|(" + SUBCHAPTER_NUM + SPAN_KEYWORD + "(?!\\s*[IVXLC]{1,}\\s*of)" + SUBCHAPTER_NUM + "))";

    // matches prefixed subchapters (e.g. subchapter iv of chapter 42). Also matches prefixed subchapter spans. Requires the word "of" to follow the subch.
    public static final String SUBCHAPTER_FRONT_R = SUBCHAPTER_PREF + SUBCHAPTER_NUM + "(" + SPAN_KEYWORD + SUBCHAPTER_NUM + ")?" + BELONGS_TO;
    public static final String SUBCHAPTER_FRONT_O = "(" + SUBCHAPTER_PREF + ")?" + SUBCHAPTER_NUM + "(" + SPAN_KEYWORD + SUBCHAPTER_NUM + ")?" + BELONGS_TO;

    /****************************************************
     *  Reference patterns
     **/
    // matches references with sections, subsections, and subchapters- requires a chapter and subchapter prefixes
    public static final String REFERENCE_R = "(" + SUBCHAPTER_FRONT_R + ")?" + CHAPTER_R + "(" + SUBCHAPTER_END_R + ")?";

    // same as above except doesn't require chapter or subchapter prefixes
    public static final String REFERENCE_O = "(" + SUBCHAPTER_FRONT_O + ")?" + CHAPTER_O + "(" + SUBCHAPTER_END_O + ")?";

    // matches reference spans
    public static final String REFERENCE_SPAN = SPAN_KEYWORD + REFERENCE_O;

    // matches additional references in a list (after using REFERENCE_R to identify the first)
    //public static final String REFERENCE_LIST = "(" + LIST_KEYWORD + REFERENCE_O + "(" + REFERENCE_SPAN + ")?)*";
    public static final String REFERENCE_LIST = LIST_KEYWORD + REFERENCE_O;

    // matches any reference
    public static final String ALL_REFERENCES = "(" + SUBCHAPTER_FRONT_R + ")?" + CHAPTER_R + "(" + SUBCHAPTER_END_R + ")?" + "(" + REFERENCE_SPAN + ")?" + "(" + REFERENCE_LIST + ")*";
    // public static final String CHAPTERS_ONLY =
}