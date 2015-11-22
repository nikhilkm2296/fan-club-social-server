package com.pyxsasys.fc.social.custom.feeds.base;

/**
 * This is the base interface for all the RSS Feed processors in FC Social. This
 * would be a thread which would poll the URL to get the lastest feeds.
 * @author nikhilkm
 */
public interface IFeedProcessor extends Runnable {

	/**
	 * This is the method which will be called for processing the feeds from
	 * different sources.
	 * @param feedInfo {@link IFeed} instance
	 */
	public void process(IFeed feedInfo);

	/**
	 * This is the method which will be called for stopping the processing of
	 * feeds from different sources.
	 */
	public void stop();

}
