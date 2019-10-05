class Token {

  public final static int ERROR = 0;
  public final static int LABLE = 1;
  public final static int HYPH = 2;
  public final static int APOS = 3;
  public final static int PUNCTUATION = 4;
  public final static int NEWLINE = 5;
  //public final static int UNTIL = 6;
  //public final static int READ = 7;
  //public final static int WRITE = 8;
  //public final static int ASSIGN = 9;
  //public final static int EQ = 10;
  //public final static int LT = 11;
  //public final static int GT = 12;
  //public final static int PLUS = 13;
  //public final static int MINUS = 14;
  //public final static int TIMES = 15;
  //public final static int OVER = 16;
  //public final static int LPAREN = 17;
  //public final static int RPAREN = 18;
  //public final static int SEMI = 19;
  public final static int WORD = 20;
  public final static int NUM = 21;

  public int m_type;
  public String m_value;
  public int m_line;
  public int m_column;
  
  Token (int type, String value, int line, int column) {
    m_type = type;
    m_value = value;
    m_line = line;
    m_column = column;
  }

  public String toString() {
    switch (m_type) {
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

