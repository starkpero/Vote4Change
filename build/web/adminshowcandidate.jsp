<%-- 
    Document   : adminshowcandidate
    Created on : 24 May, 2021, 7:23:53 PM
    Author     : acer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "org.json.JSONObject" %>
<%@page import = "evoting.dto.CandidateDetails" %>
<%@page import = "java.util.ArrayList" %>
<%
        String userid=(String)session.getAttribute("userid");
        if(userid==null)
        {
            response.sendRedirect("accessdenied.html");
            return;
        }
        String result = (String)request.getAttribute("result");
        StringBuffer sb = new StringBuffer();
        JSONObject json = new JSONObject();
        if(result!=null && result.equalsIgnoreCase("candidatelist"))
        {
            ArrayList<String> candidateId = (ArrayList<String>)request.getAttribute("candidateid");
            sb.append("<option value=''>Choose Id</option>");
            for(String id: candidateId)
                {
                    sb.append("<option value='"+id+"'>"+id+"</option>");
                }
            System.out.println(sb);
            json.put("cid", sb.toString());
            
        }
        else if(result!=null && result.equals("details"))
        {
            CandidateDetails cd = (CandidateDetails)request.getAttribute("candidate");
            String str = "<img src='data:image/jpg;base64,"+cd.getSymbol()+"'style='width:300px;height:200px;'/>";
            sb.append("<table><tr><th>UserId:</th><td>"+cd.getUserId()+"</td></tr>");
            sb.append("<tr><th>Candidate Name</th><td>"+cd.getCandidateName()+"</td></tr>");
            sb.append("<tr><th>City:</th><td>"+cd.getCity()+"</td></tr>");
            sb.append("<tr><th>Party:</th><td>"+cd.getParty()+"</td></tr>");
            sb.append("<tr><th>Symbol:</th><td>"+str+"</td></tr></table>");
            System.out.println(sb);
            json.put("details", sb.toString());
            
        }
        
        
        out.println(json);
%>