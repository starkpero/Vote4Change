/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.CandidateDTO;
import evoting.dto.CandidateDetails;
import evoting.dto.CandidateInfo;
import evoting.dto.UpdateCandidateDTO;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;

/**
 *
 * @author acer
 */
public class CandidateDAO {
    private static PreparedStatement ps,ps1,ps2,ps3,ps4,ps5,ps7,ps8;
    private static Statement st;
    static
    {
        try
        {
            ps=DBConnection.getConnection().prepareStatement("select max(candidate_id) from candidate");
            ps1=DBConnection.getConnection().prepareStatement("select username from user_details where adhar_no=?");
            ps2=DBConnection.getConnection().prepareStatement("select distinct city from user_details");
            ps3=DBConnection.getConnection().prepareStatement("Insert into candidate values(?,?,?,?,?)");
            st=DBConnection.getConnection().createStatement();
            ps4=DBConnection.getConnection().prepareStatement("Select * from candidate where candidate_id=?");
            ps5=DBConnection.getConnection().prepareStatement("Select candidate_id,username,party,symbol from candidate,user_details where candidate.user_id=user_details.adhar_no and candidate.city = (select city from user_details where adhar_no=?)");
            //ps6=DBConnection.getConnection().prepareStatement("Select candidate_id,username,party,symbol from candidate,user_details where candidate.user_id=user_details.adhar_no and candidate.candidate_id=?");
            ps7=DBConnection.getConnection().prepareStatement("Update candidate set party=?,city=?,symbol=? where candidate_id=?");
            ps8=DBConnection.getConnection().prepareStatement("select user_id from candidate where candidate_id =?");
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public static String getNewId() throws SQLException
    {
        ResultSet rs=ps.executeQuery();
//        if(rs.next())
//        {
//            return "C"+(100+rs.getString(1)+1);
//        }
//        else
//        {
//            return "C101";     //db specific since max might return null in some db
//        }
        
//        //Alternatively
//        rs.next();
//        return "C"+(100+rs.getString(1)+1);

        rs.next();
        String cid=rs.getString(1);
        if(cid==null)
            return "C101";
        else
        {
            int id=Integer.parseInt(cid.substring(1));
            return "C"+(id+1);
        }

    }
    
    public static String getUserNameById(String uid) throws SQLException
    {
        ps1.setString(1, uid);
        ResultSet rs = ps1.executeQuery();
        if(rs.next())
        {
            return rs.getString(1);
        }
        else
        {
            return null;
        }
    }
    
    public static ArrayList<String> getCity() throws SQLException
    {
        ArrayList<String> cityList = new ArrayList<>();
        ResultSet rs = ps2.executeQuery();
        while(rs.next())
        {
            cityList.add(rs.getString(1));
        }
        return cityList;
    }
    
    
   /* public static ArrayList<String> getCityNames() throws SQLException
    {
        ArrayList<String> cityList = new ArrayList<>();
        ResultSet rs = st.executeQuery("Select distinct city from user_details");
        while(rs.next())
        {
            cityList.add(rs.getString(1));
        }
        return cityList;
    }
    */
    
    
    public static boolean addCandidate(CandidateDTO obj) throws SQLException
    {
        //System.out.println("addCandidate in CandidateDAO running successfully");
        ps3.setString(1, obj.getCandidateId());
        ps3.setString(2, obj.getParty());
        ps3.setString(3, obj.getUserid());
        //System.out.println("Setters before symbol successfully executed");
        ps3.setBinaryStream(4, obj.getSymbol()); //This InputStream is not getting converted into Blob :((
        //System.out.println("Setters for symbol symbol successfully executed");
        ps3.setString(5, obj.getCity());
        //System.out.println("All Setters successfully executed");
        int c = ps3.executeUpdate();
        System.out.println("Record inserted "+ c);
        return c==1; 
        //return ps3.executeUpdate()!=0;
        
    }
            
    
    
    public static ArrayList<String> getcandidateID() throws SQLException
    {
        ArrayList<String> candidateIdList = new ArrayList<>();
        ResultSet rs = st.executeQuery("select candidate_id from candidate");
        while(rs.next())
        {
            candidateIdList.add(rs.getString(1));
        }
        System.out.println("Candidate ID list in DAO"+candidateIdList);
        return candidateIdList;
    }
    
    public static CandidateDetails getDetailsById(String cid) throws Exception
    {
        ps4.setString(1, cid);
        ResultSet rs = ps4.executeQuery();
        CandidateDetails cd = new CandidateDetails();
        Blob blob;
        InputStream inputStream;  //will hold what getBinaryStream returns
        byte[] buffer;      //image itself
        byte[] imageBytes;  //image size
        int bytesRead;
        String base64Image;
        ByteArrayOutputStream outputStream;
        if(rs.next())
        {
            blob = rs.getBlob(4);
            inputStream = blob.getBinaryStream(); 
            outputStream = new ByteArrayOutputStream();
            buffer = new byte[4096];
            bytesRead = -1;
            while((bytesRead = inputStream.read(buffer))!=-1)
            {
                outputStream.write(buffer,0,bytesRead);
            }
            imageBytes = outputStream.toByteArray();
            Base64.Encoder en = Base64.getEncoder();
            base64Image = en.encodeToString(imageBytes);
            cd.setSymbol(base64Image);
            cd.setCandidateId(cid);
            cd.setCandidateName(getUserNameById(rs.getString(3)));
            cd.setParty(rs.getString(2));
            cd.setCity(rs.getString(5));
            cd.setUserId(rs.getString(3));
            
        }
        return cd;
    }
    
    
    
    public static ArrayList<CandidateInfo> viewCandidate(String adhar_id) throws Exception
    {
        ArrayList<CandidateInfo> candidateList = new ArrayList<CandidateInfo>();
        ps5.setString(1, adhar_id);
        ResultSet rs = ps5.executeQuery();
        Blob blob;
        InputStream inputStream;  //will hold what getBinaryStream returns
        byte[] buffer;      //image itself
        byte[] imageBytes;  //image size
        int bytesRead;
        String base64Image;
        ByteArrayOutputStream outputStream;
        
        while(rs.next())
        {            
            blob = rs.getBlob(4);            
            inputStream = blob.getBinaryStream(); 
            outputStream = new ByteArrayOutputStream();
            buffer = new byte[4096];
            bytesRead = -1;
            while((bytesRead = inputStream.read(buffer))!=-1)
            {
                outputStream.write(buffer,0,bytesRead);
            }
            imageBytes = outputStream.toByteArray();
            Base64.Encoder en = Base64.getEncoder();
            base64Image = en.encodeToString(imageBytes);
            
            CandidateInfo candidate = new CandidateInfo();
            candidate.setSymbol(base64Image);
            candidate.setCandidateId(rs.getString(1));
            candidate.setCandidateName(rs.getString(2));
            candidate.setParty(rs.getString(3));
            
            candidateList.add(candidate);
        }
        //System.out.println("CandidateList in DAO" + candidateList);
        
        return candidateList;
    }
    
    
    
//    public static ArrayList<String> getAllCandidateId() throws SQLException
//    {
//        ResultSet rs = ps6.executeQuery();
//        ArrayList<String> cid = new ArrayList<>();
//        while(rs.next())
//        {
//             cid.add(rs.getString(1));
//        }
//        return cid;
//    }
    
    
    
    public static boolean updateCandidate(UpdateCandidateDTO obj) throws SQLException
    {
        ps7.setString(1, obj.getParty());
        ps7.setString(2, obj.getCity());
        ps7.setBinaryStream(3, obj.getSymbol());
        ps7.setString(4, obj.getCandidateId());
        
        return ps7.executeUpdate()!=0;
        
    }
    
    
    public static String getUserId(String cid) throws SQLException
    {
        ps8.setString(1, cid);
        ResultSet rs = ps8.executeQuery();
        //rs.next();
        //return rs.getString(1);
        if(rs.next())
        {
            return rs.getString(1);
        }
        else
        {
            return null;
        }
    }
}
