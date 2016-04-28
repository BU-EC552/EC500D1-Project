/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.bu.ec500projectcommandline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONArray;

/**
 *
 * @author arashkh
 */
public class CommandlineParser {

    private BLATJob blatJob;
    private String sequence;
    private String jsonpath = System.getProperty("user.dir") + "/output/EnhancedBLAT.json";

    public CommandlineParser(String jobId) {
        blatJob = new BLATJob(jobId);
    }

    public CommandlineParser(String jobId, String sequence) {
        blatJob = new BLATJob(jobId, sequence);
    }

    private Options optionsBuilder() {
        Options options = new Options();
        options.addOption("h", "help", true, "Shows this dialog.");
        options.addOption("g", "genome", true, "Genome that the search is performed on. Default: 'Mouse'");
        options.addOption("a", "assembly", true, "Assembly database used for that certain genome. Default: 'Dec. 2011 (GRCm38/mm10)'");
        options.addOption("u", "query", true, "Query type that is used. Default: 'BLAT's guess'");
        options.addOption("s", "sort", true, "Sort output parameter. Default: 'query,score'");
        options.addOption("o", "output", true, "Output type of this query. Default: 'hyperlink'");
        options.addOption("q", "sequence", true, "Sequence to search. REQUIRED.");
        options.addOption("e", "expansions", true, "Expansions to search. Default: '10,5,2'");
        options.addOption("j", "jsonpath", true, "Path to save JSON. Default: './'");
        return options;
    }

    public void parseOptions(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        Options options = optionsBuilder();
        CommandLine line = parser.parse(options, args);
        if (line.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("blat", options);
        } else {
            if (line.hasOption("genome")) {
                blatJob.setGenome(line.getOptionValue("genome"));
            }
            if (line.hasOption("assembly")) {
                blatJob.setAssembly(line.getOptionValue("assembly"));
            }
            if (line.hasOption("query")) {
                blatJob.setQuery(line.getOptionValue("query"));
            }
            if (line.hasOption("sort")) {
                blatJob.setSort(line.getOptionValue("sort"));
            }
            if (line.hasOption("output")) {
                blatJob.setOutput(line.getOptionValue("output"));
            }
            if (line.hasOption("sequence")) {
                sequence = line.getOptionValue("sequence");
                //System.out.println("SEQ: " + line.getOptionValue("sequence"));
            }
            if (line.hasOption("expansions")) {
                blatJob.setExpansions(line.getOptionValue("expansions"));
                //System.out.println("EXP: " + line.getOptionValue("expansions"));
            }
            if (line.hasOption("jsonpath")) {
                jsonpath = line.getOptionValue("jsonpath");
                //System.out.println("EXP: " + line.getOptionValue("expansions"));
            }
        }
    }

    public ArrayList<String[]> getResults(boolean expansion) {
        ArrayList<String[]> result = new ArrayList();
        try {
            result = blatJob.getResults(expansion);
        } catch (IOException ex) {
            Logger.getLogger(CommandlineParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public void printResults(boolean expansion) {
        try {
            blatJob.printResults(blatJob.getResults(expansion));
        } catch (IOException ex) {
            Logger.getLogger(CommandlineParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<String[]> run() {
        ArrayList<String[]> result = new ArrayList();
        String[] sequenceArray = sequence.split(">");
        for (int i = 0; i < sequenceArray.length; i++) {
            blatJob.setSequence(sequenceArray[i]);
            result.addAll(blatJob.runBLAT());
        }
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < result.size(); i++) {
            jsonArray.put(new JSONArray(Arrays.asList(result.get(i))));
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(jsonArray.toString());
        String prettyJsonString = gson.toJson(je);

        File file = new File(jsonpath);
        file.getParentFile().mkdirs();
        FileWriter writer;
        try {
            writer = new FileWriter(file);
            writer.write(prettyJsonString);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(CommandlineParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
}
