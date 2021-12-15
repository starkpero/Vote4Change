/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dto;

/**
 *
 * @author acer
 */
public class UserDetails {

    @Override
    public String toString() {
        return "UserDetails{" + "Username=" + Username + ", password=" + password + ", email=" + email + ", userid=" + userid + ", address=" + address + ", mobile=" + mobile + ", city=" + city + '}';
    }
    private String Username;
    private String password;
    private String email;
    private String userid;
    private String address;
    private long mobile;
    private String city;

    public UserDetails(String Username, String password, String email, String userid, String address, long mobile, String city) {
        this.Username = Username;
        this.password = password;
        this.email = email;
        this.userid = userid;
        this.address = address;
        this.mobile = mobile;
        this.city = city;
    }
    public UserDetails()
            {
                
            }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
}
