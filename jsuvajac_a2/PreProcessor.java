import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;


import opennlp.tools.stemmer.PorterStemmer;

public class PreProcessor { 
    public static void printLines(HashMap<Integer, String> l) {
        l.forEach((i, line) -> {
            //System.out.println(i + " " + line);
            System.out.println(line);
        });
    }

    // Copied from the example code
    public static String readString(String arg) throws Exception {
        InputStream infile = new FileInputStream(arg);
        BufferedReader buf = new BufferedReader(new InputStreamReader(infile));
        StringBuilder sb = new StringBuilder();
        String line = buf.readLine();
        while( line != null ) {
            sb.append(line + "\n");
            line = buf.readLine();
        }
        return sb.toString();  
    }
    public static void main(String args[]) throws Exception{
        if (args.length != 2) 
            System.err.println("Usage:\n java -cp (openNLPClassPath) Preprocessor <inFileTokenized> <stopWordFile>");

        //Create an instance of stemmer
        PorterStemmer stemmer = new PorterStemmer();


        // Read in tokenized input file and stopword fil
        String[] input = readString(args[0]).split("\n");
        List<String> stopWords = new ArrayList<String>(
            Arrays.asList(readString(args[1]).split("\n")));

        // allLines conatins all of the lines form the tokenized input
        HashMap<Integer, String> allLines = new HashMap<Integer, String>();
        HashMap<Integer, String> dirtyLines = new HashMap<Integer, String>();
        {
            int i = 1;
            for (String line: input) {
                allLines.put(i++, line);
            }
        }
        dirtyLines = (HashMap)allLines.clone();
        dirtyLines.entrySet().removeIf( l -> l.getValue().contains("$TEXT"));
        dirtyLines.entrySet().removeIf( l -> l.getValue().contains("$TITLE"));
        dirtyLines.entrySet().removeIf( l -> l.getValue().contains("$DOC"));
        // dirtyLines contains only the text lines to be updated

        // Set to lowercase
        dirtyLines.replaceAll((i, line) -> {
            return line.toLowerCase();
        });

        // Remove numbers punctuation and stopwords
        for (Entry entry : dirtyLines.entrySet()) {
            Integer key = (Integer) entry.getKey();
            String[] tok = ((String)entry.getValue()).split("[ \t]+");
            List<String> tokList = new ArrayList<String>(Arrays.asList(tok));

            tokList.removeIf( t -> 
                (!t.matches("[A-Za-z]") && t.length() == 1 )
                || t.matches("('+'|'-)?[0-9]+")
            );
            
            tokList.removeIf( t ->
                stopWords.contains(t)
            );

            for(int i = 0; i < tokList.size(); i++){

                tokList.set(i,stemmer.stem(tokList.get(i)));
            }
            String out = String.join(" ", tokList);

            dirtyLines.put(key, out);
        };

        //printLines(allLines);

        // Update Changed values in all lines
        dirtyLines.forEach((i, line) -> {
            allLines.compute(i, (k, v) -> line);
        });
        //System.out.println("---------------------");
        printLines(allLines);

            
    }
}
