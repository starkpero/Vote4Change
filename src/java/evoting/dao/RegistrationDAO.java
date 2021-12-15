/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.UserDetails;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author acer
 */
public class RegistrationDAO {
    private static PreparedStatement ps,ps1;
    static
    {
    try
    {
        ps=DBConnection.getConnection().prepareStatement("Select * from user_details where adhar_no=?");
        ps1=DBConnection.getConnection().prepareStatement("Insert into user_details values(?,?,?,?,?,?,?,?)");
        
    }
    catch(SQLException ex)
    {
        ex.printStackTrace();
    }
    }
    
    public static boolean searchUser(String userid) throws SQLException
            //userid=adharid
    {
        ps.setString(1, userid);
//        ResultSet  rs = ps.executeQuery();
//        if(rs.next())
//            return true;
//        else
//            return false;
// -------OR------------
//          ResultSet  rs = ps.executeQuery();
//          return rs.next();
//--------OR------------
// When an object is being created for calling just one method then java recommends to make that object anonymous
         return ps.executeQuery().next();
    }
    public static boolean registerUser(UserDetails user)throws SQLException
    {
        ps1.setString(1, user.getUserid());
        ps1.setString(2, user.getPassword());
        ps1.setString(3, user.getUsername());
        ps1.setString(4, user.getAddress());
        ps1.setString(5, user.getCity());
        ps1.setString(6, user.getEmail());
        ps1.setLong(7, user.getMobile());
        ps1.setString(8, "Voter");
        return ps1.executeUpdate()==1;
        
        
    }
}
