/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.VoteDAO;
import evoting.dto.CandidateInfo;
import evoting.dto.VoteDTO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author acer
 */
public class AddVoteControllerServlet extends HttpServlet {

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
            RequestDispatcher rd = null;
            HttpSession session = request.getSession();
            String userid = (String)session.getAttribute("userid");
            if(userid == null)
            {
                session.invalidate();
                response.sendRedirect("accessdenied.html");
                return;
            }
            
            try
            {
                String candidateId = (String)request.getParameter("candidateid");
                VoteDTO vote = new VoteDTO(candidateId,userid);
                boolean result = VoteDAO.addVote(vote);   //This result could be false bcos of any reason(not bcos there
                                                         //might be some problem with the DB cuz then the code will enter into
                                                        //catch block but due to something else like network issues or any other
                                                       //reason of such kind
                CandidateInfo candidate = VoteDAO.getVote(candidateId);
                if(result == true)
                    session.setAttribute("candidate", candidate);  //So that jsp could fetch candidatedetails when it gets called 
                                                                  //directly from ajax,if this would have been called directly from
                                                                 //the servlet we would've set the attributes in request object but here
                                                                //this is not the case...
                request.setAttribute("result", result);
                rd = request.getRequestDispatcher("verifyvote.jsp");
                
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                request.setAttribute("exception", ex);
                rd = request.getRequestDispatcher("showexception.jsp");
            }
            finally
            {
                rd.forward(request, response);
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
