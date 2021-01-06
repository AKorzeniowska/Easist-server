package com.edu.agh.easist.easistserver.resource.models;

import com.edu.agh.easist.easistserver.resource.data.InvitationData;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Data
@NoArgsConstructor
public class Invitation {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private Boolean isActive;
    @Column
    private Boolean gotAccepted;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Patient patient;

    public Invitation(InvitationData invitationData){
        this.isActive = invitationData.getIsActive();
        this.gotAccepted = invitationData.getGotAccepted();
    }
}
