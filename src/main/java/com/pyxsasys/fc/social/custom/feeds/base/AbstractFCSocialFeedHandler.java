package com.pyxsasys.fc.social.custom.feeds.base;

import java.util.Stack;

import org.xml.sax.helpers.DefaultHandler;

import com.pyxsasys.fc.social.web.rest.dto.FeedDTO;

/**
 * The base handler for XML feeds processing
 * @author nikhilkm
 */
public abstract class AbstractFCSocialFeedHandler extends DefaultHandler {

	/**
	 * Gets all the feeds available
	 * @return {@link Stack} of all {@link FeedDTO}
	 */
	abstract public Stack<FeedDTO> getFeeds();

}
