package com.pyxsasys.fc.social.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.pyxsasys.fc.social.domain.enumeration.FCSocialFixtureEventTypeEnum;

/**
 * A DTO for the FixtureEvent entity.
 */
public class FixtureEventDTO implements Serializable {

    private String id;

    @NotNull
    private String eventId;

    @NotNull
    private String matchId;

    @NotNull
    private String eventMinute;

    @NotNull
    private String eventTeam;

    @NotNull
    private String eventPlayer;

    @NotNull
    private FCSocialFixtureEventTypeEnum eventType;

    @NotNull
    private String eventResult;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getEventMinute() {
        return eventMinute;
    }

    public void setEventMinute(String eventMinute) {
        this.eventMinute = eventMinute;
    }

    public String getEventTeam() {
        return eventTeam;
    }

    public void setEventTeam(String eventTeam) {
        this.eventTeam = eventTeam;
    }

    public String getEventPlayer() {
        return eventPlayer;
    }

    public void setEventPlayer(String eventPlayer) {
        this.eventPlayer = eventPlayer;
    }

    public FCSocialFixtureEventTypeEnum getEventType() {
        return eventType;
    }

    public void setEventType(FCSocialFixtureEventTypeEnum eventType) {
        this.eventType = eventType;
    }

    public String getEventResult() {
        return eventResult;
    }

    public void setEventResult(String eventResult) {
        this.eventResult = eventResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FixtureEventDTO fixtureEventDTO = (FixtureEventDTO) o;

        if ( ! Objects.equals(id, fixtureEventDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FixtureEventDTO{" +
            "id=" + id +
            ", eventId='" + eventId + "'" +
            ", matchId='" + matchId + "'" +
            ", eventMinute='" + eventMinute + "'" +
            ", eventTeam='" + eventTeam + "'" +
            ", eventPlayer='" + eventPlayer + "'" +
            ", eventType='" + eventType + "'" +
            ", eventResult='" + eventResult + "'" +
            '}';
    }
}
