package com.pyxsasys.fc.social.custom.livescores.source.footballapi.model;

/**
 * The model class for the events of the fixture
 * @author nikhilkm
 */
public class FootballAPIFixtureEventsModel {

	String event_id;
	String event_match_id;
	String event_type;
	String event_minute;
	String event_team;
	String event_player;
	String event_player_id;
	String event_result;

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public String getEvent_match_id() {
		return event_match_id;
	}

	public void setEvent_match_id(String event_match_id) {
		this.event_match_id = event_match_id;
	}

	public String getEvent_type() {
		return event_type;
	}

	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	public String getEvent_minute() {
		return event_minute;
	}

	public void setEvent_minute(String event_minute) {
		this.event_minute = event_minute;
	}

	public String getEvent_team() {
		return event_team;
	}

	public void setEvent_team(String event_team) {
		this.event_team = event_team;
	}

	public String getEvent_player() {
		return event_player;
	}

	public void setEvent_player(String event_player) {
		this.event_player = event_player;
	}

	public String getEvent_player_id() {
		return event_player_id;
	}

	public void setEvent_player_id(String event_player_id) {
		this.event_player_id = event_player_id;
	}

	public String getEvent_result() {
		return event_result;
	}

	public void setEvent_result(String event_result) {
		this.event_result = event_result;
	}
}
