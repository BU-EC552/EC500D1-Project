/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.bu.ec500projecttests;

import edu.bu.ec500projectwebapp.CommandlineParser;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author arashkh
 */
public class tests {
    public static void main(String[] args) {
        // To overcome the problem caused by Java 7 SNI
        System.setProperty("jsse.enableSNIExtension", "false");
        String[] argsInternal = {"-q", "atggcttgatcaatgggact>tatccttgctgtaactgcaa"};
        CommandlineParser cmp = new CommandlineParser("job");
        try {
            cmp.parseOptions(args);
        } catch (ParseException ex) {
            Logger.getLogger(tests.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<String[]> result = cmp.run();
        for (String[] strArr : result){
            for (int i = 0; i<strArr.length; i++){
                System.out.print(strArr[i] + "\t");
            }
            System.out.println("");
        }
        /*ArrayList<String[]> result = null;
        try {
            result = sendReq("https://genome.ucsc.edu/cgi-bin/hgBlat",
                    "Mouse",
                    "Dec. 2011 (GRCm38/mm10)",
                    "BLAT's guess",
                    "query,score",
                    "hyperlink",
                    "atggcttgatcaatgggact");
        } catch (IOException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String[] strArr : result){
            System.out.println("New Record:");
            for (int i = 0; i<strArr.length; i++){
                System.out.println(strArr[i]);
            }
        }*/
        /*ArrayList<String> results = new ArrayList();
        String[] argsInternal = {"-q atggcttgatcaatgggact"};
        try {
            parseOptions(argsInternal);
        } catch (ParseException ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (userSeq != null) {
            try {
                results = sendReq(url, org, db, type, sort, output, userSeq);
            } catch (IOException ex) {
                Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            IOException ex = new IOException("Sequence is a mandatory field with no default. Please enter it as part of your command.");
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        }
        printResults(results);*/
    }
}
