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
public class VoteDTO {

    @Override
    public String toString() {
        return "VoteDTO{" + "candidatedId=" + candidatedId + ", voterId=" + voterId + '}';
    }

    public VoteDTO(String candidatedId, String voterId) {
        this.candidatedId = candidatedId;
        this.voterId = voterId;
    }
    private String candidatedId;
    private String voterId;

    public String getCandidatedId() {
        return candidatedId;
    }

    public void setCandidatedId(String candidatedId) {
        this.candidatedId = candidatedId;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }
}
