import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
/*
 *
 */
public class POSTagging {

   public static String readString(String arg) throws Exception {
      InputStream infile = new FileInputStream(arg);
      BufferedReader buf = new BufferedReader(new InputStreamReader(infile));
      StringBuilder sb = new StringBuilder();
      String line = buf.readLine();
      while (line != null) {
         sb.append(line + "\n");
         line = buf.readLine();
      }
      return sb.toString();
   }

   public static void main(String args[]) throws Exception {

      // Load and instantiate the POSTagger
      InputStream posTagStream = new FileInputStream("OpenNLP_models/en-pos-maxent.bin");
      POSModel posModel = new POSModel(posTagStream);
      POSTaggerME posTagger = new POSTaggerME(posModel);

      for (String arg : args) {
         String lines[] = readString(arg).split("\n");
         for (String line: lines) {
            
            if (line.charAt(0) != '$'){
               // Split text into space-separated tokens
               String tokens[] = line.split("[ \n]+");

               // Tagging all tokens
               String tags[] = posTagger.tag(tokens);
               
               // Printing the token-tag pairs
               for (int i = 0; i < tokens.length; i++)
                  System.out.print(tokens[i] + "/" + tags[i] + " ");
               System.out.println("");
      
            }
            else {
               System.out.println(line);
            }
   
         }
      }
   }
}
