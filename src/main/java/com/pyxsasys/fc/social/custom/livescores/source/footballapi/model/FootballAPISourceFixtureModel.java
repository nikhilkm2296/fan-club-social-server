package com.pyxsasys.fc.social.custom.livescores.source.footballapi.model;

import java.util.List;

/**
 * This is the model class for getting fixture related data from
 * football-api.com
 * @author nikhilkm
 */
public class FootballAPISourceFixtureModel {

	String match_id;
	String match_comp_id;
	String match_date;
	String match_formatted_date;
	String match_status;
	String match_time;
	String match_commentary_available;
	String match_localteam_id;
	String match_localteam_name;
	String match_localteam_score;
	String match_visitorteam_id;
	String match_visitorteam_name;
	String match_visitorteam_score;
	String match_ht_score;
	String match_ft_score;
	List<FootballAPIFixtureEventsModel> match_events;

	public String getMatch_ft_score() {
		return match_ft_score;
	}

	public void setMatch_ft_score(String match_ft_score) {
		this.match_ft_score = match_ft_score;
	}

	public List<FootballAPIFixtureEventsModel> getMatch_events() {
		return match_events;
	}

	public void setMatch_events(List<FootballAPIFixtureEventsModel> match_events) {
		this.match_events = match_events;
	}

	public String getMatch_id() {
		return match_id;
	}

	public void setMatch_id(String match_id) {
		this.match_id = match_id;
	}

	public String getMatch_comp_id() {
		return match_comp_id;
	}

	public void setMatch_comp_id(String match_comp_id) {
		this.match_comp_id = match_comp_id;
	}

	public String getMatch_date() {
		return match_date;
	}

	public void setMatch_date(String match_date) {
		this.match_date = match_date;
	}

	public String getMatch_formatted_date() {
		return match_formatted_date;
	}

	public void setMatch_formatted_date(String match_formatted_date) {
		this.match_formatted_date = match_formatted_date;
	}

	public String getMatch_status() {
		return match_status;
	}

	public void setMatch_status(String match_status) {
		this.match_status = match_status;
	}

	public String getMatch_time() {
		return match_time;
	}

	public void setMatch_time(String match_time) {
		this.match_time = match_time;
	}

	public String getMatch_commentary_available() {
		return match_commentary_available;
	}

	public void setMatch_commentary_available(String match_commentary_available) {
		this.match_commentary_available = match_commentary_available;
	}

	public String getMatch_localteam_id() {
		return match_localteam_id;
	}

	public void setMatch_localteam_id(String match_localteam_id) {
		this.match_localteam_id = match_localteam_id;
	}

	public String getMatch_localteam_name() {
		return match_localteam_name;
	}

	public void setMatch_localteam_name(String match_localteam_name) {
		this.match_localteam_name = match_localteam_name;
	}

	public String getMatch_localteam_score() {
		return match_localteam_score;
	}

	public void setMatch_localteam_score(String match_localteam_score) {
		this.match_localteam_score = match_localteam_score;
	}

	public String getMatch_visitorteam_id() {
		return match_visitorteam_id;
	}

	public void setMatch_visitorteam_id(String match_visitorteam_id) {
		this.match_visitorteam_id = match_visitorteam_id;
	}

	public String getMatch_visitorteam_name() {
		return match_visitorteam_name;
	}

	public void setMatch_visitorteam_name(String match_visitorteam_name) {
		this.match_visitorteam_name = match_visitorteam_name;
	}

	public String getMatch_visitorteam_score() {
		return match_visitorteam_score;
	}

	public void setMatch_visitorteam_score(String match_visitorteam_score) {
		this.match_visitorteam_score = match_visitorteam_score;
	}

	public String getMatch_ht_score() {
		return match_ht_score;
	}

	public void setMatch_ht_score(String match_ht_score) {
		this.match_ht_score = match_ht_score;
	}
}
