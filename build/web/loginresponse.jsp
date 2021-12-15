<%-- 
    Document   : LoginResponse
    Created on : 6 May, 2021, 9:36:40 AM
    Author     : acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String userid = (String)request.getAttribute("userid");
    String result = (String)request.getAttribute("result");
    if(userid!=null && result!=null)   //although userid will never be null if the user is coming from LoginServlet
    {
        HttpSession sess = request.getSession(); //jsp has it's own session ,we can use that also
        sess.setAttribute("userid", userid);
        String url="";
        if(result.equalsIgnoreCase("admin"))
        {
            url = "AdminControllerServlet;jsessionid="+sess.getId();
        }
        else
        {
            url = "VotingControllerServlet;jsessionid="+sess.getId();
        }
        out.println(url);
    }
    else
    {
        out.println("error");
    }
    
%>
