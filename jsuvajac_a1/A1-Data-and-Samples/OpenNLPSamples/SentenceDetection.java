import java.io.FileInputStream; 
import java.io.InputStream;  
import java.io.InputStreamReader;
import java.io.BufferedReader;

import opennlp.tools.sentdetect.SentenceDetectorME; 
import opennlp.tools.sentdetect.SentenceModel;  

/* Sentence Detection
 * This program splits the inputted articles from LA times
 * into the same format but the title and text of each document
 * is split into a sentence per line
 */
public class SentenceDetection { 
   /*
   * this function was borrowed from the given samples
   */ 
   public static String readString(String arg) throws Exception {
      InputStream infile = new FileInputStream(arg);
      BufferedReader buf = new BufferedReader(new InputStreamReader(infile));
      StringBuilder sb = new StringBuilder();
      String line = buf.readLine();
      while( line != null ) {
        sb.append(line + " ");
        line = buf.readLine();
      }
      return sb.toString();  
   }
   public static void main(String args[]) throws Exception { 
   
      //Load sentence detector model 
      InputStream modelData = new FileInputStream("OpenNLP_models/en-sent.bin"); 
      SentenceModel model = new SentenceModel(modelData); 
       
      //Instantiate SentenceDetectorME 
      SentenceDetectorME detector = new SentenceDetectorME(model);  
    
      //Allow multiple files to be processed
      for (String arg : args ) {
        //Split a file into sentences
        String sentences[] = detector.sentDetect(readString(arg)); 

        //Print the sentences 
        for(String sent : sentences){
            // Add newline characters befor and after the title and text tags
            sent = sent.replaceAll("[\n| ]*\\$TITLE[\n| ]*", "\n\\$TITLE\n");
            sent = sent.replaceAll("[\n| ]*\\$TEXT[\n| ]*", "\n\\$TEXT\n");
            if (sent.charAt(0) == '\n')
               sent = sent.substring(1);
            System.out.print(sent+"\n");  
         }
      }
   } 
}