import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.util.Scanner;
import java.util.Set;
import java.util.Iterator;
import java.util.ArrayList; 
import java.util.HashMap; 
import java.util.TreeMap; 
import java.util.Arrays; 
import java.util.List;
import java.lang.Math; 
//import java.util.Collections;

import opennlp.tools.stemmer.PorterStemmer;

public class OnlineProcessor {
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
    public static void main(String args[]) {
        if (args.length != 3) 
            System.err.println("Usage:\n java -cp $(opennlpclasspath) OnlineProcessor <dictionary.txt> <postings.txt> <docids.txt>");
        List<String> dictionary = null; 
        List<String> postings = null; 
        List<String> docids= null; 
        try{
            dictionary = new ArrayList<String>(Arrays.asList(readString(args[0]).split("\n")));
            postings = new ArrayList<String>(Arrays.asList(readString(args[1]).split("\n")));
            docids = new ArrayList<String>(Arrays.asList(readString(args[2]).split("\n")));
        } catch (Exception e) {
            System.err.println("Could not read input files");
        }
        int numStems = Integer.parseInt(dictionary.remove(0));
        int numEntries = Integer.parseInt(postings.remove(0));
        int numDocs = Integer.parseInt(docids.remove(0));



        // Loading these into hashmaps for easier traversal
        HashMap<String, Integer> stems = new HashMap<String, Integer>();
        HashMap<Integer, ArrayList<String>> docs = new HashMap<Integer, ArrayList<String>>();
        int previous = 0;
        // Create hashmap for the stems with the stems as keys
        for (String s: dictionary){
            String[] spl = s.split(" ");
            Integer curr = Integer.parseInt(spl[1]);
            stems.put(spl[0], previous);
            // offset
            previous += curr;
        }
        // Create hashmap for docids with local id as the key
        for (String s: docids){
            String[] spl = s.split(" ");
            Integer num = Integer.parseInt(spl[1]);
            String id  = spl[0];
            String title = String.join(" ",
                            Arrays.copyOfRange(spl, 2, spl.length));
            ArrayList<String> l = new ArrayList<String>();
            l.add(id);
            l.add(title);
            docs.put(num, l);
        }


        // Input loop
        System.out.println("Enter a query or (q/quit to quit)");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String input = "";
            try{
                input = br.readLine();
            } catch(Exception e){
                e.printStackTrace();
            }

            // quit condition
            if ( "q".equals(input.toLowerCase()) || "quit".equals(input.toLowerCase()))
                break;
            
            // Tokenize input and insert into treemap
            TreeMap<String,Integer> map = new TreeMap<String, Integer>();
            String[] tokens = input.split(" ");
            for(int i = 0; i < tokens.length; i++){
                if (map.containsKey(tokens[i])) 
                    map.put(tokens[i], map.get(tokens[i])+1);
                else
                    map.put(tokens[i], 1);
            }
            Set<String> keys = map.keySet();
            Iterator<String> iter = keys.iterator();
            System.out.println("Input:");
            while (iter.hasNext()) {
                String key = iter.next();
                System.out.print(key + ":" + map.get(key) + " ");
            } 
            System.out.println();

            // Weight calculation
            iter = keys.iterator();
            while (iter.hasNext()) {
                String key = iter.next();
                Integer offset = stems.get(key);
                Integer tf = 0;
                Integer df = 0;
                if (offset != null){

                    String test = postings.get(offset);
                    String[] sp = test.split(" ");
                    tf = Integer.parseInt(sp[1]);
                    
                }

                for (String s: dictionary){
                    String[] spl = s.split(" ");
                    Integer curr = Integer.parseInt(spl[1]);
                    if (spl[0].equals(key)){
                        df = curr;
                    }
                }
                System.out.println("tf: "+tf+" df: "+df+" N: "+ map.get(key));
                Double weight = (double)tf * Math.log((double)map.get(key)/(double)tf);
                System.out.println("'"+key+"' weight: "+weight);
            }
        }
    }

        
}
