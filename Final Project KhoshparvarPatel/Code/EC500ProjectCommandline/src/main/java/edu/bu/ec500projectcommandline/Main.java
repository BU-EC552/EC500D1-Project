/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.bu.ec500projectcommandline;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author arashkh
 */
public class Main {
    public static void main(String[] args) {
        String[] argsInternal = {"-h"};
        if (null != args) 
            argsInternal = args;
        System.setProperty("jsse.enableSNIExtension", "false");
        //String[] argsInternal = {"-q", "atggcttgatcaatgggact>tatccttgctgtaactgcaa"};
        CommandlineParser cmp = new CommandlineParser("job");
        try {
            cmp.parseOptions(argsInternal);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<String[]> result = cmp.run();
        for (String[] strArr : result){
            for (int i = 0; i<strArr.length; i++){
                System.out.print(strArr[i] + "\t");
            }
            System.out.println("");
        }
    }
}
