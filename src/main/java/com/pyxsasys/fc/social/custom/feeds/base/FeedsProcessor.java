package com.pyxsasys.fc.social.custom.feeds.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.pyxsasys.fc.social.custom.feeds.processor.xml.RssFeedsHandler;
import com.pyxsasys.fc.social.custom.feeds.source.arsenal.ArsenalDotComFeed;
import com.pyxsasys.fc.social.domain.Feed;
import com.pyxsasys.fc.social.domain.FeedConfig;
import com.pyxsasys.fc.social.repository.FeedConfigRepository;
import com.pyxsasys.fc.social.repository.FeedRepository;
import com.pyxsasys.fc.social.web.rest.dto.FeedDTO;
import com.pyxsasys.fc.social.web.rest.mapper.FeedMapper;

public class FeedsProcessor implements IFeedProcessor {

    /** {@link FeedMapper} instance **/
    FeedMapper feedMapper;

    FeedRepository feedRepository;

    /** The logger instance */
    private final Logger log = LoggerFactory.getLogger(FeedsProcessor.class);

    /** Flag to indicate to keep polling or stop the processor */
    volatile boolean keepPolling = true;

    /** This would contain all the information about feed */
    protected FeedConfigRepository feedConfigRepository = null;

    /** {@link FeedsProcessor} singleton instance **/
    private static FeedsProcessor feedsProcessor = null;

    /**
     * Constructor takes only the {@link IFeed} instance
     * 
     * @param feedInfo the {@link IFeed} instance
     */
    private FeedsProcessor(FeedConfigRepository feedConfigRepository, FeedMapper feedMapper,
            FeedRepository feedRepository) {
        this.feedConfigRepository = feedConfigRepository;
        this.feedMapper = feedMapper;
        this.feedRepository = feedRepository;
    }

    public static FeedsProcessor getProcessor(FeedConfigRepository feedConfigRepository, FeedMapper feedMapper,
            FeedRepository feedRepository) {
        if (feedsProcessor == null) {
            return new FeedsProcessor(feedConfigRepository, feedMapper, feedRepository);
        }
        return feedsProcessor;
    }

    @Override
    public void run() {
        log.debug("Started the feed processor thread");
        while (keepPolling) {
            log.debug("Processing again after sleep");
            try {
                List<FeedConfig> allFeedConfig = feedConfigRepository.findAll();
                log.debug("Got feed configurations avaliable. [ " + allFeedConfig + " ]");
                for (FeedConfig feedConfig : allFeedConfig) {
                    String feedClass = feedConfig.getFeedClass();
                    log.debug("Processing feed class : [ " + feedClass + " ]");
                    Class<?> ifeedInst = Class.forName(feedClass);
                    IFeed feedInfo = (IFeed) ifeedInst.newInstance();
                    process(feedInfo);
                }
                log.debug("FeedsProcessor sleeping for 10 mins now.");
                Thread.sleep(600 * 60 * 1000L);
            } catch (InterruptedException e) {
                log.error("Error executing processing feeds ..", e);
            } catch (ClassNotFoundException e) {
                log.error("Error executing processing feeds ..", e);
            } catch (InstantiationException e) {
                log.error("Error executing processing feeds ..", e);
            } catch (IllegalAccessException e) {
                log.error("Error executing processing feeds ..", e);
            }
        }
    }

    /**
     * Parses the feeds from the given XML string
     * @param feed the XML string
     * @param dateFormat the date pattern
     */
    private AbstractFCSocialFeedHandler parseAndGetTheFeeds(String feed, String dateFormat) {
        SAXParserFactory saxParserFact = SAXParserFactory.newInstance();
        RssFeedsHandler feedsHandler = new RssFeedsHandler(dateFormat);
        try {
            SAXParser saxParser = saxParserFact.newSAXParser();
            InputSource inputSource = new InputSource(new StringReader(feed));
            inputSource.setEncoding("UTF-8");
            saxParser.parse(inputSource, feedsHandler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feedsHandler;
    }

    /**
     * Gets the results/feeds XML from the connection established
     * 
     * @param connection the {@link HttpURLConnection} instance
     * @return string of the XML feed
     */
    private String readTheURLResult(HttpURLConnection connection) {
        InputStream is = null;
        StringBuilder feeds = new StringBuilder();

        try {
            is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                feeds.append(line);
            }
        } catch (IOException e) {
            log.error("Error reading response from feed URL..", e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }
        return feeds.toString();
    }

    /**
     * Connects to the URL specified and returns the connection object
     * 
     * @param feedURL the feed URL
     * @return {@link HttpURLConnection} instance
     */
    private HttpURLConnection getConnection(String feedURL) {
        log.debug("Connecting to [ " + feedURL + " ]");
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(feedURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10 * 1000);
            connection.setConnectTimeout(10 * 1000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            log.debug("Connected to [ " + feedURL + " ]");
        } catch (MalformedURLException e) {
            log.error("URL passed doesn't seem to be correct..", e);
        } catch (IOException e) {
            log.error("URL passed doesn't seem to be correct..", e);
        }
        return connection;
    }

    @Override
    public void process(IFeed feedInfo) {
        log.debug("Processing feed [ " + feedInfo + " ]");
        HttpURLConnection connection = getConnection(feedInfo.getFeedURL());
        if (connection != null) {
            String feed = readTheURLResult(connection);
            AbstractFCSocialFeedHandler feedsHandler = parseAndGetTheFeeds(feed, feedInfo.getDatePattern());
            Stack<FeedDTO> feeds = feedsHandler.getFeeds();
            log.debug("Got [ " + feeds.size() + " ] feeds from [ " + feedInfo.getFeedURL() + " ]");

            List<Feed> allFeeds = feedRepository.findAll();
            List<String> lstOfLinks = new ArrayList<>();

            for (Feed eachFeed : allFeeds) {
                lstOfLinks.add(eachFeed.getLink());
            }

            for (FeedDTO feedDTO : feeds) {
                String link = feedDTO.getLink();
                if (!lstOfLinks.contains(link)) {
                    log.debug("Saving feed with link [ " + link + " ]");
                    feedDTO.setSource(feedInfo.getSource());
                    feedRepository.save(feedMapper.feedDTOToFeed(feedDTO));
                }
            }
        }
    }

    public static void startFeeds() {
    }

    @Override
    public void stop() {
        this.keepPolling = false;
    }
    
    public static void main(String[] args) {
        FeedsProcessor feedsProcessor = new FeedsProcessor(null, null, null);
        feedsProcessor.process(new ArsenalDotComFeed());
    }
}
