package com.angelhack.wuw.google;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * @author anurag.kapur
 * 
 */
public class NewsSearch {

	private static final Logger LOGGER = Logger.getLogger(NewsSearch.class);

	public List getResults(String query) {
		
		List results = null;
		
		try {
			String urlTemplate = "https://news.google.com/news/feeds?q=" + URLEncoder.encode(query, "UTF-8")
					+ "&output=rss";
			URL url = new URL(urlTemplate);
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(url));
			results = feed.getEntries();

			/*for (Iterator i = feed.getEntries().iterator(); i.hasNext();) {
				SyndEntry entry = (SyndEntry) i.next();
				System.out.println(entry.getTitle() + " " + entry.getLink());
			}*/
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return results;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NewsSearch search = new NewsSearch();
		search.getResults("Angel Hack");
	}
}