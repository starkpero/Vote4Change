<%-- 
    Document   : showexception
    Created on : 3 May, 2021, 8:17:07 AM
    Author     : acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
  Exception ex = (Exception)request.getAttribute("exception");
  System.out.println("Exception is :" +ex);
  out.println("Some exception occured " + ex.getMessage());
%>