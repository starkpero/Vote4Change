<%-- 
    Document   : votingresponse
    Created on : 3 Jun, 2021, 7:32:33 AM
    Author     : acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="evoting.dto.CandidateInfo" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--        <script src="jsscript/vote.js"></script>
        <script src="jsscript/jquery.js"></script>-->
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <link href="stylesheet/backgroundimage.css" rel="stylesheet">
        <link href="stylesheet/pageheader.css" rel="stylesheet">
        <link href="stylesheet/showcandidate.css" rel="stylesheet">
        <title>Voting Details</title>
    </head>
    
    <body>
        <%
          String userid = (String)session.getAttribute("userid");
          if(userid == null)
          {
                session.invalidate();
                response.sendRedirect("accessdenied.html");
                return;
          }
          CandidateInfo c = (CandidateInfo)session.getAttribute("candidate");
          StringBuffer sb = new StringBuffer();
          sb.append("<div class='sticky'><div class='candidate'>VOTE FOR CHANGE</div><br>");
          if(c == null)
          {
              sb.append("<div class='subcandidate'>Sorry your vote could not be casted</div>");
              sb.append("<div><h4 id='logout'><a href='LoginControllerServlet?logout=logout'>Logout</a></h4></div>");
              out.println(sb);
          }
          else
          {
              sb.append("<div class='subcandidate'>Thank you for Voting</div>");
              sb.append("<strong>You voted for</strong><br><img src = 'data:image/jpg;base64,"+c.getSymbol()+"' style = 'width:300px;height:200px;'/>");
              sb.append("<br><div class= 'candidateprofile'><p>Candidate Id: "+c.getCandidateId()+"<br>");
              sb.append("Candidate Name: "+c.getCandidateName()+"<br>");
              sb.append("Party: "+c.getParty()+"<br></div>");
              out.println(sb);
          }
        %>    
        
    </body>
</html>
