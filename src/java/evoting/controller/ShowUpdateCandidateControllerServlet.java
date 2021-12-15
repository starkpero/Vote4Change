/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.CandidateDAO;
import evoting.dto.CandidateDetails;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author acer
 */
public class ShowUpdateCandidateControllerServlet extends HttpServlet {

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
        PrintWriter pw = response.getWriter();
        HttpSession sess = request.getSession();
        String userid = (String)sess.getAttribute("userid");
        if(userid==null)
        {
            sess.invalidate();
            response.sendRedirect("accessdenied.html");
            return;
        }
        
        
        String candidate = (String)request.getParameter("id");
        String candiDetails = (String)request.getParameter("cdetails"); 
        if(candidate!=null && candidate.equals("getid"))
        {
            try
            {
                ArrayList<String> candidateid = CandidateDAO.getcandidateID();
                JSONObject json = new JSONObject();
                StringBuffer sb = new StringBuffer();
                for(String cid : candidateid)
                {
                    sb.append("<option value='"+cid+"'>"+cid+"</option>");
                }
                System.out.println(sb);
                json.put("cid", sb.toString());
                pw.println(json);
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
                RequestDispatcher rd = request.getRequestDispatcher("showexception.jsp");
                request.setAttribute("Exception", ex);
                rd.forward(request, response);
            }
            
        }
        else if(candiDetails!=null)
        {
            try{
            CandidateDetails cDetails = CandidateDAO.getDetailsById(candiDetails);  //set usid value to respective candidateid in js
            String userId = cDetails.getUserId();
            String candidateName = cDetails.getCandidateName();
            String party = cDetails.getParty();
            String str = "<img src='data:image/jpg;base64,"+cDetails.getSymbol()+"'style='width:300px;height:200px;'/>";
            //String userId = CandidateDAO.getUserId(usid);  //this also takes candidateID.
            ArrayList<String> city = CandidateDAO.getCity();
            
                //pw.println(username);
                JSONObject json = new JSONObject();
                StringBuffer sb = new StringBuffer();
                StringBuffer sb1 = new StringBuffer();
                sb1.append(str);
                for(String c: city)
                {
                    sb.append("<option value='"+c+"'>"+c+"</option>");
                    
                }
                System.out.println(sb);
                json.put("userId", userId);
                json.put("candidateName", candidateName);
                json.put("city", sb.toString());
                json.put("party", party);
                json.put("image", sb1.toString());
                pw.println(json);
                
                //set values in json
            }
            catch(Exception ex)
            {
                RequestDispatcher rd = request.getRequestDispatcher("showexception.jsp");
                request.setAttribute("Exception", ex);
                rd.forward(request, response);
            }
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

}
