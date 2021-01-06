package com.edu.agh.easist.easistserver.resource.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationData {
    private Long id;
    private Long patientId;
    private String patientUsername;
    private Long doctorId;
    private String doctorUsername;
    private Boolean isActive;
    private Boolean gotAccepted;
}
