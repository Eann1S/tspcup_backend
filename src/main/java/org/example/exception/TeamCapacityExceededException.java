package org.example.exception;

import static org.example.message.ErrorMessage.TEAM_CAPACITY_IS_EXCEEDED;

public class TeamCapacityExceededException extends RuntimeException {

    public TeamCapacityExceededException(String teamName, int maxTeamCapacity) {
        super(TEAM_CAPACITY_IS_EXCEEDED.formatWith(teamName, maxTeamCapacity));
    }
}
