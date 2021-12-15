<%-- 
    Document   : registrationresponse
    Created on : 3 May, 2021, 8:07:07 AM
    Author     : acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
  boolean result = (boolean)request.getAttribute("result");
  boolean userfound = (boolean)request.getAttribute("userfound");
  //String username = (String)request.getAttribute("username");
  if(userfound == true)
      out.println("uap");
  else if(result==true)
      out.println("success");
  else
       out.println("error");
          
          

%>
