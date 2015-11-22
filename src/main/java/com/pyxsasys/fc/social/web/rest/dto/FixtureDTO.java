package com.pyxsasys.fc.social.web.rest.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the Fixture entity.
 */
public class FixtureDTO implements Serializable {

    private String id;

    @NotNull
    private String matchId;

    @NotNull
    private String teamOneId;

    @NotNull
    private String teamTwoId;

    @NotNull
    private String teamOne;

    @NotNull
    private String teamTwo;

    private String teamOneScore;

    private String teamTwoScore;

    private String matchTime;

    private String htScore;

    private String ftScore;

    @NotNull
    private ZonedDateTime matchDate;

    List<FixtureEventDTO> matchEvents;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getTeamOneId() {
        return teamOneId;
    }

    public void setTeamOneId(String teamOneId) {
        this.teamOneId = teamOneId;
    }

    public String getTeamTwoId() {
        return teamTwoId;
    }

    public void setTeamTwoId(String teamTwoId) {
        this.teamTwoId = teamTwoId;
    }

    public String getTeamOne() {
        return teamOne;
    }

    public void setTeamOne(String teamOne) {
        this.teamOne = teamOne;
    }

    public String getTeamTwo() {
        return teamTwo;
    }

    public void setTeamTwo(String teamTwo) {
        this.teamTwo = teamTwo;
    }

    public String getTeamOneScore() {
        return teamOneScore;
    }

    public void setTeamOneScore(String teamOneScore) {
        this.teamOneScore = teamOneScore;
    }

    public String getTeamTwoScore() {
        return teamTwoScore;
    }

    public void setTeamTwoScore(String teamTwoScore) {
        this.teamTwoScore = teamTwoScore;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getHtScore() {
        return htScore;
    }

    public void setHtScore(String htScore) {
        this.htScore = htScore;
    }

    public String getFtScore() {
        return ftScore;
    }

    public void setFtScore(String ftScore) {
        this.ftScore = ftScore;
    }

    public ZonedDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(ZonedDateTime matchDate) {
        this.matchDate = matchDate;
    }

    public List<FixtureEventDTO> getMatchEvents() {
        return matchEvents;
    }

    public void setMatchEvents(List<FixtureEventDTO> matchEvents) {
        this.matchEvents = matchEvents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FixtureDTO fixtureDTO = (FixtureDTO) o;

        if (!Objects.equals(id, fixtureDTO.id))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FixtureDTO{" + "id=" + id + ", matchId='" + matchId + "'" + ", teamOneId='" + teamOneId + "'"
                + ", teamTwoId='" + teamTwoId + "'" + ", teamOne='" + teamOne + "'" + ", teamTwo='" + teamTwo + "'"
                + ", teamOneScore='" + teamOneScore + "'" + ", teamTwoScore='" + teamTwoScore + "'" + ", matchTime='"
                + matchTime + "'" + ", htScore='" + htScore + "'" + ", ftScore='" + ftScore + "'" + ", matchDate='"
                + matchDate + "'" + '}';
    }
}
