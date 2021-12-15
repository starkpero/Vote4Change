<%-- 
    Document   : showcandidate
    Created on : 31 May, 2021, 9:06:09 AM
    Author     : acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "java.util.ArrayList , evoting.dto.CandidateInfo" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <script src="jsscript/vote.js"></script>
        <script src="jsscript/jquery.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
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
            "<div class='subcandidate'>Whom do you want to vote?</div>" + 
            "<div class='logout'><a href = 'login.html'>logout</a></div>"+
            "</div><div class = 'buttons'>");
            ArrayList<CandidateInfo> candidateList = (ArrayList<CandidateInfo>)request.getAttribute("candidateList");
            for(CandidateInfo c : candidateList)
            {
                sb.append("<input type = 'radio' name = 'flat' id='"+c.getCandidateId()+"' value = '"+c.getCandidateId()+"' onclick = 'addVote()'");
                //sb.append("<input type = 'radio' name = 'flat' id='cid' value = '"+c.getCandidateId()+"' onclick = 'addVote()'");
                sb.append("<label for='"+c.getCandidateId()+"'><img src = 'data:image/jpg;base64,"+c.getSymbol()+"' style = 'width:300px;height:200px;'/></label>");
                sb.append("<br><div class= 'candidateprofile'><p>Candidate Id: "+c.getCandidateId()+"<br>");
                sb.append("Candidate Name: "+c.getCandidateName()+"<br>");
                sb.append("Party: "+c.getParty()+"<br></div>");
            }
            out.println(sb);
            
        %> 
        
    </body>
           
    
