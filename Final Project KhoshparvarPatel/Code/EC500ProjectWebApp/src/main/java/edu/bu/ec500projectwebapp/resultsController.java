/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.bu.ec500projectwebapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.cli.ParseException;

/**
 *
 * @author arashkh
 */
public class resultsController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] args = {"-g", request.getParameter("org"),
            "-a", request.getParameter("db"),
            "-u", request.getParameter("type"),
            "-s", request.getParameter("sort"),
            "-o", request.getParameter("output"),
            "-q", request.getParameter("userSeq").replaceAll("[^a-zA-Z>]", ""),//Has to be done here because of the whitespaces
            "-e", request.getParameter("seqExpansion"),
            "-j", System.getProperty("user.dir") + "/output/EnhancedBLAT.json"};

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<style>");
            out.println("table {");
            out.println("border-collapse: collapse;");
            out.println("width: 100%;");
            out.println("}");
            out.println("th, td {");
            out.println("text-align: left;");
            out.println("padding: 8px;");
            out.println("}");
            out.println("tr:nth-child(even){background-color: #f2f2f2}");
            out.println("th {");
            out.println("background-color: #4CAF50;");
            out.println("color: white;");
            out.println("}");
            out.println("</style>");
            out.println("<title>Servlet resultsController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet resultsController at " + request.getContextPath() + "</h1>");
            out.println("<h4>Results</h4>");

            ArrayList<String[]> result = run(args);
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>Seq No.</th>");
            out.println("<th>Score</th>");
            out.println("<th>Start</th>");
            out.println("<th>End</th>");
            out.println("<th>QSize</th>");
            out.println("<th>Identity</th>");
            out.println("<th>Chromosome</th>");
            out.println("<th>Strand</th>");
            out.println("<th>Start</th>");
            out.println("<th>End</th>");
            out.println("<th>Span</th>");
            out.println("<th>Sequence</th>");
            out.println("</tr>");
            int j = 0;
            for (String[] strArr : result) {
                out.println("<tr>");
                if (strArr[strArr.length - 1].toLowerCase().equals(strArr[strArr.length - 1])) {
                    j++;
                    out.println("<th>" + j + "</th>");
                } else {
                    out.println("<td>\"</td>");
                }
                for (int i = 3; i < strArr.length; i++) {
                    out.print("<td>" + strArr[i] + "</td>");
                }
                out.println("</tr>");
                //out.println("<br>");
            }
            //out.println("</p>");
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private ArrayList<String[]> run(String[] args) {
        System.setProperty("jsse.enableSNIExtension", "false");
        String[] argsInternal = {"-q atggcttgatcaatgggact"};
        CommandlineParser cmp = new CommandlineParser("job");
        try {
            cmp.parseOptions(args);
        } catch (ParseException ex) {
            Logger.getLogger(resultsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<String[]> result = cmp.run();
        return result;
    }

}
