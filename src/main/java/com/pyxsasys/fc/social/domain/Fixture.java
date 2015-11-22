package com.pyxsasys.fc.social.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Fixture.
 */

@Document(collection = "fixture")
public class Fixture implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("match_id")
    private String matchId;

    @NotNull
    @Field("team_one_id")
    private String teamOneId;

    @NotNull
    @Field("team_two_id")
    private String teamTwoId;

    @NotNull
    @Field("team_one")
    private String teamOne;

    @NotNull
    @Field("team_two")
    private String teamTwo;

    @Field("team_one_score")
    private String teamOneScore;

    @Field("team_two_score")
    private String teamTwoScore;

    @Field("match_time")
    private String matchTime;

    @Field("ht_score")
    private String htScore;

    @Field("ft_score")
    private String ftScore;

    @NotNull
    @Field("match_date")
    private ZonedDateTime matchDate;

    @Field("match_event")
    List<FixtureEvent> matchEvent;

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

    public List<FixtureEvent> getMatchEvent() {
        return matchEvent;
    }

    public void setMatchEvent(List<FixtureEvent> matchEvent) {
        this.matchEvent = matchEvent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Fixture fixture = (Fixture) o;

        if (!Objects.equals(id, fixture.id))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fixture{" + "id=" + id + ", matchId='" + matchId + "'" + ", teamOneId='" + teamOneId + "'"
                + ", teamTwoId='" + teamTwoId + "'" + ", teamOne='" + teamOne + "'" + ", teamTwo='" + teamTwo + "'"
                + ", teamOneScore='" + teamOneScore + "'" + ", teamTwoScore='" + teamTwoScore + "'" + ", matchTime='"
                + matchTime + "'" + ", htScore='" + htScore + "'" + ", ftScore='" + ftScore + "'" + ", matchDate='"
                + matchDate + "'" + '}';
    }
}
