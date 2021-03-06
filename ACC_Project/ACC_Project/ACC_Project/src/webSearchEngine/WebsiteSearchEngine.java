package webSearchEngine;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Pattern;

public class WebsiteSearchEngine {
	
	private static Scanner inputValue = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		WebsiteSearchEngine engine = new WebsiteSearchEngine();
		String value = "n";	//Setting the value to no at initial stage
		
		System.out.println("++++++++++++ Web Search Engine +++++++++++++\n");
		
		do {

			System.out.println(" 1. Press 1 to search the pre-defined website --> https://www.geeksforgeeks.org/");
			System.out.println(" 2. Press 2 to input a URL for the search");
			System.out.println(" 3. Press 3 to Exit ");
		
			int input = inputValue.nextInt();

			switch (input) {
			case 1:
				value = engine.webSearch("https://www.geeksforgeeks.org/");
				break;

			case 2:
				System.out.println("\nPlease Enter valid URL for example 'https://www.abc.com/'");
				String url = inputValue.next();
				value = engine.webSearch(url);
				break;
				
			case 3:
				System.out.println("The Program has Stopped");
				value = "n";
				break;

			default:
				System.out.println("Please Enter Valid Input");
				value = "y";

			}
		} while (value.equals("y"));


		System.out.println("++++++++++++ THANK YOU ++++++++++++++++");

	}

	private String webSearch(String url) {
		
		if(!isValid(url)) {
			 System.out.println("The url: " + url + " is invalid or not reachable");
			 System.out.println("Please try again\n");
			 return "y";
		}
		
		System.out.println("The url: " + url + " is valid\n");
		
		System.out.println("Web Crawling Started...");
		WebCrawler.startCrawler(url, 0); //crawling the URL
		System.out.println("Web Crawling Compelted...");

		// Hash table is used instead of Hash Map as it don't allow null value in insertion
		Hashtable<String, Integer> filesList = new Hashtable<String, Integer>();
		
		String choice = "y";
		System.out.println("\nWould you like to search words? Enter(y/n)?");
		choice =inputValue.next();
		
		while (choice.equals("y")) {
			System.out.println("\nEnter The Word:");
			
			String word = inputValue.next();
			int occurance = 0;
			int fileNumber = 0;
			filesList.clear();
			try {
					System.out.println("\nFinding the words from files: "+word+"\n");
					File files = new File(PathFinder.textFilesPath);
	
					File[] fileArray = files.listFiles();
	
					for (int i = 0; i < fileArray.length; i++) {
	
						In data = new In(fileArray[i].getAbsolutePath());
	
						String txt = data.readAll();
						data.close();
						Pattern p = Pattern.compile("::");
						String[] file_name = p.split(txt);
						occurance = WordFinder.findWord(txt, word.toLowerCase(), file_name[0]); // search word in txt files
	
						if (occurance != 0) {
							filesList.put(file_name[0], occurance);
							fileNumber++;
						}
						
					}
	
					if(fileNumber>0) {
					System.out.println("\nNumber of Files that contain the following word : \"" + word + "\" is : " + fileNumber);
					}else {
						System.out.println("\n File not found with that contains the word : \""+ word+"\"");
						TextSuggestion.suggestAltWord(word.toLowerCase()); 
						// Suggests another word if entered word not found
					}
	
					Ranking.rankFiles(filesList, fileNumber); 
					//Ranks the files based on frequency of word count
					

				} catch (Exception e) {
					System.out.println("Exception:" + e);
				}
			System.out.println("\nWould you like to search another Word? Enter(y/n)?");
			choice =inputValue.next();
		}
		
		
		
		deleteFiles();

		
		System.out.println("\nWould you like to return to main menu(y/n)?");
		return inputValue.next();
	}

	private void deleteFiles() {
		String choice = "y";
		System.out.println("\nAll the files are sorted and located at: "+ PathFinder.htmlFilesPath);
		System.out.println("\nWould you like to delete all the files? Enter(y/n)?");
		choice =inputValue.next();
		if(choice.equals("y")) {
			File files = new File(PathFinder.textFilesPath);
			File[] fileArray = files.listFiles();
	
			for (int i = 0; i < fileArray.length; i++) {
				fileArray[i].delete();
			}
			
			File fileshtml = new File(PathFinder.htmlFilesPath);
			File[] fileArrayhtml = fileshtml.listFiles();
	
			for (int i = 0; i < fileArrayhtml.length; i++) {
				
				fileArrayhtml[i].delete();
			}
		}
	}
	
	/**
	 * It will validate url entered by user with DNS
	 * @param url
	 * @return
	 */
	public boolean isValid(String url)
    {
        /* Try creating a valid URL */
        try {
        	System.out.println("Validating URL...");
        	URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            //Sending the request
            conn.setRequestMethod("GET");
            int response = conn.getResponseCode();
            if(response==200) {
            	 return true;
            }else {
            	return false;
            }
           
        }
          
        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }

}
