<%-- 
    Document   : votedenied
    Created on : 8 Jun, 2021, 7:28:51 PM
    Author     : acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "evoting.dto.CandidateInfo" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--         <script src="jsscript/vote.js"></script>-->
<!--        <script src="jsscript/jquery.js"></script>-->
<!--        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>-->
        <link href="stylesheet/backgroundimage.css" rel="stylesheet">
        <link href="stylesheet/pageheader.css" rel="stylesheet">
        <link href="stylesheet/showcandidate.css" rel="stylesheet">
        <title>Show Candidate</title>
    </head>
    <body>    
        <%
            String userid=(String)session.getAttribute("userid");
            if(userid==null)
            {
                response.sendRedirect("accessdenied.html");
                return;
            }
            StringBuffer sb = new StringBuffer("");
            sb.append("<div class='sticky'><div class='candidate'>VOTE FOR CHANGE</div><br>"+
            "<div class='subcandidate'>You have already casted the vote</div>" + 
            "<div class='logout'><a href = 'login.html'>logout</a></div>"+
            "</div><div class = 'buttons'>");
            CandidateInfo cd = (CandidateInfo)request.getAttribute("candidate");
            sb.append("<br><div class= 'candidateprofile'><p>Candidate Id: "+cd.getCandidateId()+"<br>");
            sb.append("Candidate Name: "+cd.getCandidateName()+"<br>");
            sb.append("Party: "+cd.getParty()+"<br></div>");
            sb.append("<img src = 'data:image/jpg;base64,"+cd.getSymbol()+"' style = 'width:300px;height:200px;'/>");
            out.println(sb);
        %>
    </body>
</html>
