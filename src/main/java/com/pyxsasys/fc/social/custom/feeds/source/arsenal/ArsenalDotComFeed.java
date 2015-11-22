package com.pyxsasys.fc.social.custom.feeds.source.arsenal;

import com.pyxsasys.fc.social.custom.feeds.base.AbstractFeed;

/**
 * This is the feed for arsenal.com feeds
 * @author nikhilkm
 */
public class ArsenalDotComFeed extends AbstractFeed {

	/** The feed URL */
	private static String feedURL = "http://feeds.arsenal.com/arsenal-news";
	
	/** Keeping the polling interval at 10 minutes (600 seconds) */
	Integer pollInterval = 600;
	
	/** This is the name for Arsenal.com source feeds */
	private static String sourceName = "Arsenal.com";
	
	private String pubDatePattern = "EEE, d MMM yyyy HH:mm:ss Z";
	
	/**
	 * Constructor which passes only the feed URL
	 * @param feedURL the feed URL
	 */
	public ArsenalDotComFeed() {
		super(feedURL);
	}

	@Override
	public Integer getPollInterval() {
		return this.pollInterval;
	}

	@Override
	public void setPollInterval(Integer pollInterval) {
		this.pollInterval = pollInterval;
	}

	@Override
	public String getSource() {
		return sourceName;
	}

    @Override
    public String getDatePattern() {
        return pubDatePattern;
    }
}
