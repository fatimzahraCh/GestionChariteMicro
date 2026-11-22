package com.ecom.participationservice.dto;

import jakarta.validation.constraints.NotNull;

public class CreateParticipationDto {

    @NotNull(message = "L'identifiant de l'utilisateur est obligatoire")
    private Long userId;

    @NotNull(message = "L'identifiant de l'action est obligatoire")
    private Long actionId;

    public CreateParticipationDto() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }
}
