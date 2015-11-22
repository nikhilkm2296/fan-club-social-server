package com.pyxsasys.fc.social.custom.feeds.base;

public abstract class AbstractFeed implements IFeed {

	/** This is the feed URL */
	String feedURL = null;
	
	/**
	 * Constructor takes only the feed URL
	 * @param feedURL the feed URL
	 */
	public AbstractFeed(String feedURL) {
		if(feedURL == null) {
			throw new IllegalArgumentException("The feedUrl cannot be null");
		}
		this.feedURL = feedURL;
	}
	
	@Override
	public String getFeedURL() {
		return feedURL;
	}

	@Override
	public void setFeedURL(String feedURL) {
		this.feedURL = feedURL;
	}
}
