package webSearchEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HTMLParser {

	/**
	 * this method will save the html file into system
	 * 
	 * @param document
	 * @param url
	 */
	public static void webPageSaver(Document page, String url) {
		try {
			PrintWriter html = new PrintWriter( new FileWriter(PathFinder.htmlFilesPath + page.title().replace('/', '_') + ".html"));
			html.write(page.toString());
			html.close();
			htmlToText(PathFinder.htmlFilesPath + page.title().replace('/', '_') + ".html", url, page.title().replace('/', '_') + ".txt");

		} catch (Exception e) {

		}

	}

	/**
	 * this method will save html file content to .txt file
	 * 
	 * @param htmlfile
	 * @param url
	 * @param filename
	 * @throws Exception
	 */
	public static void htmlToText(String htmlfile, String url, String filename) throws Exception {
		File file = new File(htmlfile);
		Document document = Jsoup.parse(file, "UTF-8");
		String info = document.text().toLowerCase();
		info = url + "==" + info;
		PrintWriter writer = new PrintWriter(PathFinder.textFilesPath + filename);
		writer.println(info);
		writer.close();
	}
}
