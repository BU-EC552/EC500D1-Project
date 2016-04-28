/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.bu.ec500projectwebapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author arashkh
 */
public class BLATJob {

    private final static String url = "https://genome.ucsc.edu/cgi-bin/hgBlat";
    private String jobId;
    private String genome,
            assembly,
            query,
            sort,
            output,
            sequence,
            expansions;

    public BLATJob(String jobId, String genome, String assembly, String query, String sort, String output, String sequence, String expansions) {
        this.jobId = jobId;
        this.genome = genome;
        this.assembly = assembly;
        this.query = query;
        this.sort = sort;
        this.output = output;
        this.sequence = sequence;
        this.expansions = expansions;
    }

    
    
    public BLATJob(String jobId, String sequence) {
        this.jobId = jobId;
        genome = "Mouse";
        assembly = "Dec. 2011 (GRCm38/mm10)";
        query = "BLAT's guess";
        sort = "query,score";
        output = "hyperlink";
        expansions = "10,5,2";
        this.sequence = sequence;
    }

    public BLATJob(String jobId) {
        this.jobId = jobId;
        genome = "Mouse";
        assembly = "Dec. 2011 (GRCm38/mm10)";
        query = "BLAT's guess";
        sort = "query,score";
        output = "hyperlink";
        expansions = "10,5,2";
    }
    
    

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getGenome() {
        return genome;
    }

    public void setGenome(String genome) {
        this.genome = genome;
    }

    public String getAssembly() {
        return assembly;
    }

    public void setAssembly(String assembly) {
        this.assembly = assembly;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getExpansions() {
        return expansions;
    }

    public void setExpansions(String expansions) {
        this.expansions = expansions;
    }
    

    @Override
    public String toString() {
        return "BLATJob{" + "jobId=" + jobId + ", genome=" + genome + ", assembly=" + assembly + ", query=" + query + ", sort=" + sort + ", output=" + output + ", sequence=" + sequence + '}';
    }
    
    public ArrayList<String[]> getResults(boolean expansion) throws IOException {
        ArrayList<String[]> result = new ArrayList();

        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(this.url);
        postMethod.addParameter("org", this.genome);
        postMethod.addParameter("db", this.assembly);
        postMethod.addParameter("type", this.query);
        postMethod.addParameter("sort", this.sort);
        postMethod.addParameter("output", this.output);
        postMethod.addParameter("userSeq", this.sequence);
        try {
            httpClient.executeMethod(postMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String resp = "";
        InputStream responseStream = null;
        if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
            resp = postMethod.getResponseBodyAsString(100000);
            responseStream = postMethod.getResponseBodyAsStream();
        } else {
            //...postMethod.getStatusLine();
        }

        Document doc = Jsoup.parse(responseStream, null, url);
        Elements pres = doc.select("pre");
        //System.out.println("Element Selected:\n" + pres.toString());
        for (Element element : pres){
            String[] lines = element.text().split("\\r?\\n");
            for (int i = 2; i<lines.length; i++){
                ArrayList<String> temp = new ArrayList<>();
                temp.addAll(Arrays.asList(lines[i].split("\\s+")));
                if (expansion) temp.add(extendSequence(url,element.select("a").get(1).attr("href")));
                result.add(temp.toArray(new String[0]));
            }
        }

        /*int startIndex = resp.indexOf("<PRE>   ACTIONS      QUERY           SCORE START  END QSIZE IDENTITY CHRO STRAND  START    END      SPAN");
        String respFocus = resp.substring(startIndex, resp.indexOf("</PRE>", startIndex));
        respFocus = respFocus.substring(respFocus.lastIndexOf("</A>") + 4);
        String[] respArray = respFocus.split("\\s+");
        String identity = respArray[6],
                chromosome = respArray[7],
                startUser = respArray[3],
                endUser = respArray[4],
                startGenome = respArray[9],
                endGenome = respArray[10];
        result.add(identity);
        result.add(chromosome);
        result.add(startUser);
        result.add(endUser);
        result.add(startGenome);
        result.add(endGenome);*/
        return result;
    }
    
    private static String extendSequence(String baseUrl, String url) throws IOException{
        String result = "";
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(baseUrl.substring(0, baseUrl.lastIndexOf("/")) + url.substring(url.indexOf("/", 3)));
        try {
            httpClient.executeMethod(postMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream responseStream = null;
        if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
            responseStream = postMethod.getResponseBodyAsStream();
        } else {
            //...postMethod.getStatusLine();
        }
        Document doc = Jsoup.parse(responseStream, null, url);
        String frameUrl = doc.select("frame").get(1).attr("src").toString();
        postMethod = new PostMethod(baseUrl.substring(0, baseUrl.length()-15) + frameUrl.substring(2));
        try {
            httpClient.executeMethod(postMethod);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (postMethod.getStatusCode() == HttpStatus.SC_OK) {
            responseStream = postMethod.getResponseBodyAsStream();
        } else {
            //...postMethod.getStatusLine();
        }
        doc = Jsoup.parse(responseStream, null, url);
        
        result = doc.select("pre").get(1).text().replaceAll("[^A-Za-z]", "");
        //result = doc.select("pre").get(0).toString();
        return result;
    }
    
    
    public void printResults( ArrayList<String[]> results){
        if (results ==  null || results.isEmpty()){
            System.out.println("No information found in the results.");
            return;
        }
        for (int i = 0; i<results.size(); i++){
            String[] strArr = results.get(i);
            System.out.println("Result No " + (i+1));
            for (int j = 0; j<strArr.length; j++){
                System.out.println(strArr[j]);
            }
        }
    }

    public ArrayList<String[]> runBLAT() {
        ArrayList<String[]> result = new ArrayList<>();
        try {
            result.addAll(this.getResults(true));
        } catch (IOException ex) {
            Logger.getLogger(BLATJob.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (expansions == null || expansions.isEmpty()){
            return result;
        }
        else{
            ArrayList<Integer> expansionsInt = new ArrayList<>();
            for (String str : expansions.split("\\s*,\\s*")){
                expansionsInt.add(Integer.parseInt(str));
            }
            try {
                result.addAll(this.runBLAT(result.get(0)[result.get(0).length-1], expansionsInt));
            } catch (IOException ex) {
                Logger.getLogger(BLATJob.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        result.get(0)[result.get(0).length-1] = sequence;
        return result;
    }
    
    public ArrayList<String[]> runBLAT(String fullSequence, ArrayList<Integer> expansionsInt) throws IOException {
        ArrayList<String[]> result = new ArrayList<>();
        String[] sequenceArray = fullSequence.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
        //System.out.println("Test SequenceArray: " + sequenceArray[1]);
        for (int i : expansionsInt) {
            String sequenceInternal = sequenceArray[0].substring(sequenceArray[0].length()-i) + sequenceArray[1] + sequenceArray[2].substring(0, i+1);
            //System.out.println(sequenceInternal);
            //sequenceInternal = sequenceInternal.toLowerCase();
            BLATJob bj = new BLATJob("job", sequenceInternal);
            ArrayList<String[]> temp = bj.getResults(true);
            for (int j = 0; j<temp.size(); j++){
                temp.get(j)[temp.get(j).length-1] = sequenceInternal;
            }
            result.addAll(temp);
        }
        return result;
    }
}
