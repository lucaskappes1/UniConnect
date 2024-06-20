package br.com.uniconnect.dtos;

import br.com.uniconnect.entities.UserRole;

public record PendingUserApprovalRequestDTO(Long id, UserRole role, ApprovalStatus approvalStatus) {

}
