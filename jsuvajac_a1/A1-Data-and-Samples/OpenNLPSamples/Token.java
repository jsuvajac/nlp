/*
 * This class is the definition for tokens for doc.flex
 * it is used for classifying tokens defined in doc.flex
 */
class Token {

    public final static int ERROR = 0;
    public final static int LABLE = 1;
    public final static int HYPH = 2;
    public final static int APOS = 3;
    public final static int PUNCTUATION = 4;
    public final static int NEWLINE = 5;
    public final static int DOCSTR = 6;
    public final static int WORD = 20;
    public final static int NUM = 21;

    public int m_type;
    public String m_value;
    public int m_line;
    public int m_column;

    Token(int type, String value, int line, int column) {
        m_type = type;
        m_value = value;
        m_line = line;
        m_column = column;
    }

    /*
     * This function was used mostly for debugging.
     * It converts each type of toke into a string representation.
     */
    public String toString() {
        switch (m_type) {
        case DOCSTR:
            return "DOCSTR(" + m_value + ")";
        case LABLE:
            return "LABLE(" + m_value + ")";
        case HYPH:
            return "HYPH(" + m_value + ")";
        case APOS:
            return "APOS(" + m_value + ")";
        case WORD:
            return "WORD(" + m_value + ")";
        case NUM:
            return "NUM(" + m_value + ")";
        case PUNCTUATION:
            return "PUNCTUATION(" + m_value + ")";
        case NEWLINE:
            return "NEWLINE";
        default:
            return "UNKNOWN(" + m_value + ")";
        }
    }
}
