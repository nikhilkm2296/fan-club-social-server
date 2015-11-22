package com.pyxsasys.fc.social.custom.livescores.source.footballapi.model;

import java.util.List;

public class FootballApiFixtureResponseModel {

	List<FootballAPISourceFixtureModel> matches;

	public List<FootballAPISourceFixtureModel> getMatches() {
		return matches;
	}

	public void setMatches(List<FootballAPISourceFixtureModel> matches) {
		this.matches = matches;
	}
}
