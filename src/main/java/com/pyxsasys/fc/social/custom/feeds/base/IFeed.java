package com.pyxsasys.fc.social.custom.feeds.base;

/**
 * This is the base interface for all the Feeds in the FC social app
 * @author nikhilkm
 */
public interface IFeed {

    /**
     * This would return the feed URL to connect to for the Feeds
     * @return String representing the feed URL
     */
    public String getFeedURL();

    /**
     * This would set the feed URL to be connected to
     * @param feedURL the feed URL
     */
    public void setFeedURL(String feedURL);

    /**
     * Gets the polling interval for the this particular feed
     * @return {@link Integer} representing the polling interval
     */
    public Integer getPollInterval();

    /**
     * Sets the polling interval
     * @param pollInterval the polling interval
     */
    public void setPollInterval(Integer pollInterval);

    /**
     * Gets the source name for the feeds
     * @return {@link String} representing the source name
     */
    public String getSource();

    /**
     * Gets the source date format
     * @return {@link String} representing the date format
     */
    public String getDatePattern();
}
