import java.util.Scanner;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.BufferedReader;
import java.io.PrintWriter;

import java.util.TreeMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

// Term represents an entry in the array list for offline processing
class Term {
    public int did;
    public int tf;
    public Term(int d, int t) {
        this.did = d;
        this.tf = t;
    }
    public String toString() {
        return this.did + " " + this.tf;
    }
}

public class OfflineProcessor {
    // method reads through the lines from an imput file
    // generates lists of docids, start positions and titles
    // wirtes these to a docid.txt file
    public static void genDocid(List<String> lines) {
        List<String> docIds = new ArrayList<String>();
        List<String> docTitle = new ArrayList<String>();
        List<Integer> docStartPos = new ArrayList<Integer>();
        int docCount = 0;
        boolean foundTitle = false;
        String title = "";
        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            // add the current title to the title list
            if (l.contains("$TEXT")){
                title.replaceAll("\n", "");
                docTitle.add(title);
                foundTitle = false;
            }
            // append current line to current title
            if (foundTitle == true){
                //System.out.println(l);
                if (lines.get(i-1).contains("$TITLE"))
                    title = l;
                else
                    title += " " + l;
            }
            if (l.contains("$TITLE"))
                foundTitle = true;
            // add the docid and the start line to their lists
            if (l.contains("$DOC")){
                docIds.add(l.split(" ")[1]);
                docStartPos.add(i+1);
                docCount++;
            }
        }
        // Print to docids.txt
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("docids.txt", "UTF-8");
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        writer.println(docCount);
        for (int i = 0; i < docCount; i++) {
            writer.println(docIds.get(i) + " " + docStartPos.get(i) + " " + docTitle.get(i));
        }
        writer.close();
    }
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        List<String> lines = new ArrayList<String>();

        // read file
        while (input.hasNextLine()) {
            String line = input.nextLine();
            lines.add(line);
        }
        // generate docids.txt
        genDocid(lines);
     
        //TreeMap for string-integer pairs 
        TreeMap<String, ArrayList<Term>> map = new TreeMap<String, ArrayList<Term>>();
        int currDoc = 1;
        boolean foundTitle = false;
        //load an input file into the TreeMap
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("$DOC"))
                currDoc = i + 1;
            else if (lines.get(i).contains("$TEXT")){
                foundTitle = false;
            }
            else if (foundTitle == true){
                continue;
            }
            else if (lines.get(i).contains("$TITLE"))
                foundTitle = true;
            else{
                String[] tokens = lines.get(i).split("[ \t]+");
                for (int j = 0; j < tokens.length; j++) {

                    if (map.containsKey(tokens[j])){
                        ArrayList<Term> curr = map.get(tokens[j]);
                        Term last = curr.get(curr.size()- 1);

                        if (last.did == currDoc){
                            last.tf += 1;
                        } else {
                            curr.add(new Term(currDoc, 1));
                        }
                    }
                    else{
                        Term t = new Term(currDoc,1);
                        ArrayList<Term> l = new ArrayList<Term>();
                        l.add(t);
                        map.put(tokens[j], l);
    
                    }
                }
            }

        }

        // display the TreeMap alphabetically
        Set<String> keys = map.keySet();
        Iterator<String> iter = keys.iterator();

        // Print dictionary
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("dictionary.txt", "UTF-8");
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        
        writer.println(keys.size());
        while (iter.hasNext()) {
            String key = iter.next();
            writer.println(key + " " + map.get(key).size());
        }
        writer.close();
        

        // Print postings
        try {
            writer = new PrintWriter("postings.txt", "UTF-8");
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        // Print total number
        int numEntries = 0;

        iter = keys.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            numEntries += map.get(key).size();
        }
        writer.println(numEntries);
        // Print each
        iter = keys.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            ArrayList<Term> terms = map.get(key);
            for(Term t: terms){
                writer.println(t.did+" "+t.tf);
            }
        }

        writer.close();
    }
}
