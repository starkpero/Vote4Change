/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.CandidateDAO;
import evoting.dto.UpdateCandidateDTO;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author acer
 */
public class UpdateCandidateControllerServlet extends HttpServlet {

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
          HttpSession sess = request.getSession();
          String userid = (String)sess.getAttribute("userid");
          if(userid == null)
          {
                sess.invalidate();
                response.sendRedirect("accessdenied.html");
                return;
          }
          
          try
          {
                DiskFileItemFactory df = new DiskFileItemFactory(); //For it's object to be passed in sfu as parameter
                                                                //since file can be too large so it could be temporarily
                                                                //saved on the disk.
                ServletFileUpload sfu = new ServletFileUpload(df);  //For calling parse method which returns ArrayList
                ServletRequestContext ctx = new ServletRequestContext(request); //Eventually data is received in request of
                                                                            //HttpServletRequest so it's object must have
                                                                           //to be passed as argument to parseRequest
                List<FileItem> multiList = sfu.parseRequest(ctx);
                ArrayList<String> objValues = new ArrayList<>();
                InputStream inp = null;
                for(FileItem fit : multiList)
                {
                    if(fit.isFormField())  //returns false if data is binary
                    {
                        String fname = fit.getFieldName();  //key(Name of FieldName)
                        String value = fit.getString();     //value corresponding to that fieldName
                        System.out.println("Inside if");
                        System.out.println(fname+":"+value);
                        objValues.add(value);
                    }
                    else
                    {
                        inp = fit.getInputStream();
                        String key = fit.getFieldName();   //key == files(input type)
                        String fileName = fit.getName();    //actual file(image) name
                        System.out.println("Inside else");
                        System.out.println(key+":"+fileName);
                    }
                }
                
                UpdateCandidateDTO candidate = new UpdateCandidateDTO(objValues.get(0),objValues.get(1),objValues.get(2),inp);
                System.out.println(candidate);
                boolean result = CandidateDAO.updateCandidate(candidate);
                System.out.println(result +" UpadateCandidateControllerServlet");
                if(result)
                rd = request.getRequestDispatcher("success.jsp");
                else
                rd = request.getRequestDispatcher("failure.jsp");
                
          }
          
          
            catch(Exception ex)
            {
//                rd = request.getRequestDispatcher("showexception.jsp");
//                request.setAttribute("Exception", ex);
//                rd.forward(request, response);
                  System.out.println("Exception in UpdateCandidateControllerServlet");
                  ex.printStackTrace();
            }
            finally 
            {
                if(rd!=null)
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
