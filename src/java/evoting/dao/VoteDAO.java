/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.CandidateInfo;
import evoting.dto.VoteDTO;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author acer
 */
public class VoteDAO {
    private static PreparedStatement ps1,ps2,ps3,ps4;
    private static Statement st;
    
    static
    {
        try
        {
            ps1= DBConnection.getConnection().prepareStatement("Select candidate_id from voting where voter_id=?");
            ps2= DBConnection.getConnection().prepareStatement("Select candidate_id,username,party,symbol from candidate,user_details where candidate.user_id=user_details.adhar_no and candidate.candidate_id=?");
            ps3= DBConnection.getConnection().prepareStatement("Insert into voting values(?,?)");
            ps4= DBConnection.getConnection().prepareStatement("select candidate_id,count(*) as votes_obt from voting group by candidate_id order by votes_obt desc");
            st = DBConnection.getConnection().createStatement();
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    //this method checks whether the candidate has voted or not 
    public static String getCandidateId(String userid) throws SQLException
    {
        System.out.println("Calling setter in getCandidateId of voteDAO");
        ps1.setString(1, userid);
        System.out.println("Setter in getCandidateID of VoteDAO successfully called");
        ResultSet rs = ps1.executeQuery();
        System.out.println("Query successfully executed in getCandidateID of VoteDAO");
        while(rs.next())
        {
            return rs.getString(1);
        }
        
        return null;
    }
    
    public static CandidateInfo getVote(String candidateid) throws Exception
    {
        ps2.setString(1, candidateid);
        ResultSet rs = ps2.executeQuery();
        CandidateInfo cInfo = new CandidateInfo();
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
            cInfo.setSymbol(base64Image);
            cInfo.setCandidateId(candidateid);
            cInfo.setCandidateName(rs.getString(2));
            cInfo.setParty(rs.getString(3));
            
        }
        return cInfo;
    }
    
    
    public static boolean addVote(VoteDTO obj) throws SQLException
    {
        ps3.setString(1, obj.getCandidatedId());
        ps3.setString(2, obj.getVoterId());
        return (ps3.executeUpdate()!=0);
    }
    
    
    
    public static Map<String,Integer> getResult() throws SQLException
    {
        Map<String,Integer> result = new LinkedHashMap<>();
        ResultSet rs = ps4.executeQuery();
        while(rs.next())
        {
            result.put(rs.getString(1), rs.getInt(2));
        }
        return result;
    }
    
    public static int getVoteCount() throws SQLException
    {
        ResultSet rs = st.executeQuery("select count(*) from voting");
        if(rs.next())
            return rs.getInt(1);
        return 0;
    }
}
