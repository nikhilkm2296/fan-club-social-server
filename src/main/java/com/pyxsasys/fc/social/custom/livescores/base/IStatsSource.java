package com.pyxsasys.fc.social.custom.livescores.base;

/**
 * This is the base interface for all the source for live scoresand other stats
 * @author nikhilkm
 */
public interface IStatsSource {

	/**
	 * Gets the source name
	 * @return the source name
	 */
	public String getSourceName();

	/**
	 * Gets the API key provided by the vendor
	 * @return the API key
	 */
	public String getSourceAPIKey();

	/**
	 * Gets the API to call to get standings
	 * @return the standings API
	 */
	public String getStandingsAPI(CompetitionsEnum competition);

	/**
	 * Gets the API to call to get live scores & live scores
	 * @return the fixtures and live score API
	 */
	public String getFixturesAPI(CompetitionsEnum competition);

}
