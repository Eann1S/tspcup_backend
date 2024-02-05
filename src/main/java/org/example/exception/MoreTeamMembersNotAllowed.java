package org.example.exception;

import static org.example.message.ErrorMessage.MORE_TEAM_MEMBERS_NOT_ALLOWED;

public class MoreTeamMembersNotAllowed extends RuntimeException {

    public MoreTeamMembersNotAllowed() {
        super(MORE_TEAM_MEMBERS_NOT_ALLOWED.getMessage());
    }
}
