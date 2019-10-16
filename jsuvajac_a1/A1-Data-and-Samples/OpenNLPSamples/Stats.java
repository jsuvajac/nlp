import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import opennlp.tools.stemmer.PorterStemmer;
/*
 *
 */
public class Stats {
    /*
    *
    */   
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
    /*
    * This utillitiy function calculates the average of a list of integers
    * as a double and returns it.
    */
    public static double avg(List<Integer> list) {
        if (list.size() == 0)
            return 0.0;
        double sum = 0.0;
        double len = list.size();
        for (Integer i: list)
            sum += i;
        
        return sum / len;
    }
    public static void main(String args[]) throws Exception{     
      
        //Load and instantiate the Tokenizer 
        InputStream tokenStream = new FileInputStream("OpenNLP_models/en-token.bin"); 
        TokenizerModel tokenModel = new TokenizerModel(tokenStream); 
        TokenizerME tokenizer = new TokenizerME(tokenModel); 

        //Create an instance of stemmer
        PorterStemmer stemmer = new PorterStemmer();

        for( String arg : args ) {
            // read the input file
            String input = readString(arg);
            String linArr[] = input.split("\n");

            int currTokCount = 0;
            int currLineCount = 0;
            Boolean foundText = false;
        
            List<Integer> numLines = new ArrayList<Integer>();
            List<Integer> numTokens = new ArrayList<Integer>();
            List<Integer> tokensPerSentence = new ArrayList<Integer>();
            List<Double> avgTokensPerDoc = new ArrayList<Double>();
            
            List<String> lines = new ArrayList<String>();
            // Slit the input file into a list of lines (strings)
            for (String l: linArr)
                lines.add(l);

            // remove the text and title tags for easier parsing
            lines.removeIf( line -> line.contains("$TEXT"));
            lines.removeIf( line -> line.contains("$TITLE"));

            // parsing the lines into the line and toke count lists
            for (String line: lines) {
                if (!line.contains("$DOC")){
                    currLineCount++;
                    String tok[] = line.split(" ");
                    currTokCount+=tok.length;
                    tokensPerSentence.add(tok.length);
                }

                if (lines.indexOf(line) == lines.size()-1){
                    numLines.add(currLineCount);
                    numTokens.add(currTokCount);
                    currLineCount = 0;
                    currTokCount = 0;
                }
                else if (line.contains("$DOC") && lines.indexOf(line)!=0){
                    numLines.add(currLineCount);
                    numTokens.add(currTokCount);
                    currLineCount = 0;
                    currTokCount = 0;
                }

            }
            // Printing output
            System.out.println("total document count: " + numLines.size() + "\n");
            System.out.println("Total Sentences:");
            System.out.println("\tmax: " + Collections.max(numLines));
            System.out.println("\tmin: " + Collections.min(numLines)); 
            System.out.printf("\tavg: %.2f\n\n",avg(numLines));

            System.out.println("Total Tokens:");
            System.out.println("\tmax: " + Collections.max(numTokens));
            System.out.println("\tmin: " + Collections.min(numTokens)); 
            System.out.printf("\tavg: %.2f\n\n",avg(numTokens));

            System.out.println("Avg tokens per line for all documents:");
            System.out.printf("\tavg: %.2f\n\n",avg(tokensPerSentence));
            
            System.out.println("Avg tokens per document:");
            int c = 0, start = 0, offset = 0;
            double sum = 0.0;
            // Loop calculates and print the average tokens per news article
            for (int size: numLines){
                int i;
                for (i = start; i<size+start; i++) {
                    sum += tokensPerSentence.get(i);
                }
                start=i;
                System.out.printf("\t%.2f\n",sum/size);
                sum = 0;
            }
        }
    }
}
