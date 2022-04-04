package webSearchEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordFinder {
	
	
	// Positions and Occurrences of the words
	
		public static int findWord(String data, String word, String fileName) {
			int counter = 0;

			int offset = 0;
			webSearchEngine.BoyerMoore boyerMoore = new webSearchEngine.BoyerMoore(word);
			for (int location = 0; location <= data.length(); location += offset + word.length()) {
				offset = boyerMoore.search(word, data.substring(location));
				if ((offset + location) < data.length()) {
					counter++;
				}
			}
			if (counter != 0) {
				System.out.println("Found in HTML file --> " + fileName+" --> "+counter+" times"); // Founded from which HTML file..
			}
			return counter;
		}

				
}
