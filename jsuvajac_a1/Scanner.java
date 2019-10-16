import java.io.InputStreamReader;
/*
 * This program does the splitting of sentences into tokens
 * the backbone of the class was borrwoed from the given samples
 */
public class Scanner {
    private Lexer scanner = null;
    public Scanner(Lexer lexer) {
        scanner = lexer;
    }
    // Wrapper for next token from jflex
    public Token getNextToken() throws java.io.IOException {
        return scanner.yylex();
    }

    public static void main(String argv[]) {
        try {
            Scanner scanner = new Scanner(new Lexer(new InputStreamReader(System.in)));
            Token tok = null;
            Boolean lastNewline = true;
            while ((tok = scanner.getNextToken()) != null) {
                // Print a newline for each newline token
                if (tok.m_type == tok.NEWLINE){
                    lastNewline = true;
                    System.out.println("");
                }
                // Process appostrophised words
                else if (tok.m_type == tok.APOS) {
                    //System.Iout.println(tok.m_value);
                    int index = tok.m_value.length()-3;
                    try {
                        if (tok.m_value.charAt(index) == '\''){
                            if (!lastNewline) {
                                System.out.print(" ");
                            }
                            System.out.print(tok.m_value.substring(0,index)
                                            + " "
                                            + tok.m_value.substring(index, index+3));
                        }
                        else{
                            if (!lastNewline) {
                                System.out.print(" ");
                            }
                            System.out.print(tok.m_value);
                        }
                    } catch(Exception e) {
                        if (!lastNewline) {
                            System.out.print(" ");
                        }
                        System.out.print(tok.m_value);

                    }
                // Process hyphanated words
                } else if (tok.m_type == tok.HYPH) {
                    int hCount = 0;
                    for (int i = 0; i < tok.m_value.length(); i++)
                        if (tok.m_value.charAt(i) == '-')
                            hCount++;
                    if (hCount > 2){
                        String[] components = tok.m_value.split("-");
                        for (String c: components){
                            if (c.indexOf("\'") < 0)
                                System.out.print(c + " - ");
                            else{
                                int index = c.indexOf("\'");
                                System.out.print(c.substring(0,index)
                                                 + " \' "
                                                 + c.substring(index+1, c.length())
                                                 );
                            }
                        }
                    }
                    else{
                        if (lastNewline) {
                            System.out.print(" ");
                            lastNewline = false;
                        }
                        System.out.print(" " + tok.m_value);
                    }
                // All other types of words
                } else {
                    if (lastNewline) {
                        System.out.print(tok.m_value);
                        lastNewline = false;
                    }
                    else
                        System.out.print(" "+tok.m_value);
                }
            }
        } catch (Exception e) {
            System.out.println("Unexpected exception:");
            e.printStackTrace();
        }
    }
}