package com.pyxsasys.fc.social.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.pyxsasys.fc.social.domain.enumeration.FCSocialFixtureEventTypeEnum;

/**
 * A FixtureEvent.
 */

@Document(collection = "fixture_event")
public class FixtureEvent implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("event_id")
    private String eventId;

    @NotNull
    @Field("match_id")
    private String matchId;

    @NotNull
    @Field("event_minute")
    private String eventMinute;

    @NotNull
    @Field("event_team")
    private String eventTeam;

    @NotNull
    @Field("event_player")
    private String eventPlayer;

    @NotNull
    @Field("event_type")
    private FCSocialFixtureEventTypeEnum eventType;

    @NotNull
    @Field("event_result")
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

        FixtureEvent fixtureEvent = (FixtureEvent) o;

        if ( ! Objects.equals(id, fixtureEvent.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FixtureEvent{" +
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
