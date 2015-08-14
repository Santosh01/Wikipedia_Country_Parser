package dataCountry;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Santosh Dubey
 */
public class WikiController {

	//Output file names & location
	public static String csvOutputFilePath = "data-files/Country.csv";
	public static String txtOutputFilePath = "data-files/Country.txt";
	public static String jsonOutputFilePath = "data-files/Country.json";


	/*==============================================================================================================*/


	/**
	 * Function to get name of the country
	 */
	@SuppressWarnings("rawtypes")
	public void handleCountry(List<CountryModel> countries) {

		try {
			/**
			 * fetch country detail from below url 
			 * https://simple.wikipedia.org/wiki/List_of_countries_by_population
			 */
			Document wikiDoc = Jsoup.connect("https://simple.wikipedia.org/wiki/List_of_countries_by_population").timeout(30000).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2").get();

			// get main class of div
			Elements detailsDiv = wikiDoc.select(".wikitable.sortable tr");

			Iterator featureTrIter = detailsDiv.iterator();

			//Iterate through tr tag of Country and country details.
			while (featureTrIter.hasNext()) {

				// Single Element means one row.
				Element tr = (Element)featureTrIter.next();

				// divide row into the number of columns
				Elements tdScrap = tr.select("td");

				if(tdScrap.size() > 0) {
					// store data in to the country object.
					CountryModel country = new CountryModel();
					country.setRank(tdScrap.get(0).text());
					country.setName(tdScrap.get(1).text().replace("[", "").replaceAll("[0-9]","").replaceAll("]", "")); // new line
					country.setName(tdScrap.get(1).text().replace("[1]", ""));
					country.setPopulation(tdScrap.get(2).text());
					country.setDate(tdScrap.get(3).text());
					country.setPercentage(tdScrap.get(4).text());
					country.setSource(tdScrap.get(5).getElementsByTag("a").attr("href"));
					countries.add(country);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*==============================================================================================================*/
	/**
	 * Function to get capital name from country
	 * @throws IOException 
	 */
	@SuppressWarnings("rawtypes")
	public List<CountryModel> getCapital(List<CountryModel> countries) throws IOException {

		/**
		 * fetch country capital detail from the below url 
		 * https://simple.wikipedia.org/wiki/List_of_countries_by_population
		 */
		Document document = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_national_capitals_by_population").timeout(30000).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2").get();

		// get main class of div
		Elements element = document.select(".wikitable.sortable tr");
		Iterator itr = element.iterator();

		List<CountryModel> countryForCapital = new ArrayList<CountryModel>();

		//Iterate through tr tag of Country and country details.
		while (itr.hasNext()) {

			Element tr2 = (Element)itr.next();

			Elements tdScrap2 = tr2.select("td");

			if(tdScrap2.size() > 0) {
				CountryModel conForCapital = new CountryModel();
				conForCapital.setName(tdScrap2.get(1).text());
				conForCapital.setCapital(tdScrap2.get(2).text());
				countryForCapital.add(conForCapital);
			}
		}

		// Check if country is matched, then store capital in country object 
		for(CountryModel conForCapital : countryForCapital) {
			for(CountryModel country : countries) {
				if(country.getName().equals(conForCapital.getName())) {
					country.setCapital(conForCapital.getCapital());
					break;
				}
			}
		}

		return countries;
	}


	/*==============================================================================================================*/


	/**
	 * Funtion to create CSV file
	 * @param countries
	 * @throws Exception
	 */
	public void handleOutputToCSV(List<CountryModel> countries) throws Exception{

		//write output file
		String outputFileName = csvOutputFilePath;

		// create object of file writer to write file
		FileWriter writer = new FileWriter(outputFileName);
		for(CountryModel country : countries) {

			// set first row as a title
			if(country.getRank().equals("-")){
				country.setRank("Rank");
				country.setName("Country Name");
				country.setPopulation("Population");
				country.setDate("Years");
				country.setPercentage("Percentage");
				country.setSource("Source Url");
				country.setCapital("Capital");
			} 

			// generate data 
			writer.append(country.getRank());
			writer.append(',');
			writer.append('"');
			writer.append(country.getName());
			writer.append('"');
			writer.append(',');
			writer.append('"');
			writer.append(country.getCapital());
			writer.append('"');
			writer.append(',');
			writer.append('"');
			writer.append(country.getPopulation());
			writer.append('"');
			writer.append(',');
			writer.append('"');
			writer.append(country.getDate());
			writer.append('"');
			writer.append(',');
			writer.append('"');
			writer.append(country.getPercentage());
			writer.append('"');
			writer.append(',');
			writer.append(country.getSource());
			writer.append(',');
			writer.append('\n');
		}
		writer.flush();
		writer.close();
	}


	/*==============================================================================================================*/


	/**
	 * Function to create Text file
	 * @param countries
	 * @throws Exception
	 */
	public void handleOutputToTXT(List<CountryModel> countries) throws Exception{
		//write output file
		String outputFileName = txtOutputFilePath;

		// create object of file writer to write file
		FileWriter writer = new FileWriter(outputFileName);

		for(CountryModel country : countries) {

			// create tabular format txt
			writer.append(String.format("%-10s%-50s%-50s%-30s%-30s%-20s%-100s",country.getRank(), country.getName(),country.getCapital(), country.getPopulation(),
					country.getDate(),country.getPercentage(),country.getSource()));
			writer.append('\n');
		}
		writer.flush();
		writer.close();
	}


	/*==============================================================================================================*/

	/**
	 * Function to create Json File
	 * @param countries
	 * @throws Exception
	 */
	public void handleOutputToJSON(List<CountryModel> countries) throws Exception{
		//write output file
		String outputFileName = jsonOutputFilePath;

		// create object of file writer to write file
		FileWriter writer = new FileWriter(outputFileName);

		// create JSONObject
		JSONObject jObject = new JSONObject();
		try {
			//create JSONArray
			JSONArray jArray = new JSONArray();

			for (CountryModel country : countries) {
				// key and value pair
				JSONObject countryObject = new JSONObject();
				countryObject.put("rank", country.getRank());
				countryObject.put("name", country.getName());
				countryObject.put("capital", country.getCapital());
				countryObject.put("population", country.getPopulation());
				countryObject.put("date", country.getDate());
				countryObject.put("percentage", country.getPercentage());
				countryObject.put("source", country.getSource());
				jArray.put(countryObject);
			}
			// main JSONArray
			jObject.put("CountryDetail", jArray);

			// store json object in to file
			writer.write(jObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		writer.flush();
		writer.close();
	}



}
