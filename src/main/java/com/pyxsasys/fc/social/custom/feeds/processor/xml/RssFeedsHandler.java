package com.pyxsasys.fc.social.custom.feeds.processor.xml;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.pyxsasys.fc.social.custom.feeds.base.AbstractFCSocialFeedHandler;
import com.pyxsasys.fc.social.web.rest.dto.FeedDTO;

/**
 * This class handles all the RSS Feeds to extract required information
 * 
 * @author nikhilkm
 */
public class RssFeedsHandler extends AbstractFCSocialFeedHandler {

    private String datePattern = null;

    private static final String ITEM = "item";
    private static final String LINK = "link";
    private static final String DESCRIPTION = "description";
    private static final String TITLE = "title";
    private static final String PUB_DATE = "pubDate";

    boolean item = false;
    boolean link = false;
    boolean descr = false;
    boolean title = false;
    boolean pubDate = false;

    Stack<FeedDTO> s = new Stack<>();

    public RssFeedsHandler(String dateFormat) {
        this.datePattern = dateFormat;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals(ITEM)) {
            item = true;
            s.push(getNewFeedModel());
        }
        if (item) {
            if (qName.equals(LINK)) {
                link = true;
            } else if (qName.equals(DESCRIPTION)) {
                descr = true;
            } else if (qName.equals(TITLE)) {
                title = true;
            } else if (qName.equals(PUB_DATE)) {
                pubDate = true;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals(ITEM)) {
            item = false;
        } else if (qName.equals(LINK)) {
            link = false;
        } else if (qName.equals(DESCRIPTION)) {
            descr = false;
        } else if (qName.equals(TITLE)) {
            title = false;
        } else if (qName.equals(PUB_DATE)) {
            pubDate = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (item) {
            FeedDTO peek = s.peek();
            if (link) {
                peek.setLink(new String(ch, start, length));
            } else if (descr) {
                peek.setDescription(new String(ch, start, length));
                descr = false;
            } else if (title) {
                peek.setTitle(new String(ch, start, length));
            } else if (pubDate) {
                String pubDateStr = new String(ch, start, length);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern(datePattern);
                ZonedDateTime zdt = ZonedDateTime.parse(pubDateStr, dtf);
                peek.setPublicationDate(zdt);
            }
        }
    }

    private FeedDTO getNewFeedModel() {
        return new FeedDTO();
    }

    @Override
    public Stack<FeedDTO> getFeeds() {
        return s;
    }
}
