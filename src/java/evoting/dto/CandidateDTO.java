/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dto;

import java.io.InputStream;

/**
 *
 * @author acer
 */
public class CandidateDTO {
    private String candidateId;
    private String party;
    private String city;
    private String userid;
    private InputStream symbol;
    
//    public CandidateDTO()
//    {
//        //When we have to call setters after creating an object
//    }

    @Override
    public String toString() {
        return "CandidateDTO{" + "candidateId=" + candidateId + ", party=" + party + ", city=" + city + ", userid=" + userid + ", symbol=" + symbol + '}';
    }

    public String getCandidateId() {
        //System.out.println("getCandidateId in DTO running succcessfully");
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
        
    }

    public InputStream getSymbol() {
        System.out.println("getSymbol in DTO running successfully");
        return symbol;
    }

    public void setSymbol(InputStream symbol) {
        this.symbol = symbol;
        //System.out.println("setSymbol in DTO working just fine ");
    }

    public CandidateDTO(String candidateId, String party, String city, String userid, InputStream symbol) {
        this.candidateId = candidateId;
        this.party = party;
        this.city = city;
        this.userid = userid;
        this.symbol = symbol;
        System.out.println("Constructor in DTO running successfully");
    }
    
    public CandidateDTO()
    {
        
    }
}
