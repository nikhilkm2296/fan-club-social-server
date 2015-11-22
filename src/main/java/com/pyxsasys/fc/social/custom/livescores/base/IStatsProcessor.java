package com.pyxsasys.fc.social.custom.livescores.base;

import java.util.List;

import com.pyxsasys.fc.social.web.rest.dto.FixtureDTO;

/**
 * This is the base class for stats processing based on the given
 * {@link IStatsSource}
 * @author nikhilkm
 *
 */
public interface IStatsProcessor {

	/**
	 * Process the source
	 * @param source {@link IStatsSource} instance
	 */
	public void process(IStatsSource source);

	/**
	 * Gets the list of fixtures for the next 7 days
	 * @return list of fixtures
	 */
	public List<FixtureDTO> getFixtures();

}
