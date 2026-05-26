package com.github.cidarosa.moderation.service.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModerationOutputDTO {

    private UUID commentId;
    private Boolean approved;
    private String reason;

    public boolean isApproved(){

        return Boolean.TRUE.equals(getApproved());
    }
}
