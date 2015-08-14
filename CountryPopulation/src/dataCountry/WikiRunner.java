package dataCountry;

import java.util.ArrayList;
import java.util.List;

public class WikiRunner {

	/**
	 * Main method
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		WikiController wc = new WikiController();

		List<CountryModel> countries = new ArrayList<CountryModel>();

		//populate countries	
		wc.handleCountry(countries);


		// populate capitals
		countries = wc.getCapital(countries);
		System.out.println("Writing to Capital finished");

		//create CSV file
		wc.handleOutputToCSV(countries);
		System.out.println("Writing to CSV finished");

		//create TEXT file
		wc.handleOutputToTXT(countries);
		System.out.println("Writing to text-file finished");

		//create JSON file
		wc.handleOutputToJSON(countries);
		System.out.println("Writing to JSON finished");
		
		
		System.out.println("\n");

		System.out.print("All Files has been successfully created");

	}
}
