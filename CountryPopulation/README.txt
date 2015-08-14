1) Generated Files:
   ---------------
	* Text File
	* JSON File
	* CSV File

2) Data-Fields:
  -------------
	* Rank
	* Name of the country
	* Name of the capital
	* Population
	* Years
	* Percentage (out of 100% of global population)
	* Source URL


3) Data fetching & Processing:
   --------------------------

 Capital Fetching is from:  https://en.wikipedia.org/wiki/List_of_national_capitals_by_population
   ------------------------
	* I am fetching only capital from this link since data is bit older one, many data is not synchronised and well ranked.
	
	* Actual data I am fetching from https://simple.wikipedia.org/wiki/List_of_countries_by_population
	
	* As this link has new data and all countries are well ranked based on the population.
	
	* I chose this link to get data because of ranking and total % population.
	
	* Once I get that country name I am appending capital name by matching respective country name. I have made sure not to duplicate it.
	
	
4) Steps to Run this project:
  --------------------------
	* Import this project as java project.
	* You may find 3 java source file ( CountryModel.java, WikiController.java, WikiRunner.java)
	* you have to run WikiRunner.java file
	* Once you run WikiRunner.java it will create 3 files (Country.csv, Country.txt, Country.json)


5) Technology Platform:
   -------------------
	* To get real time data from Wikipedia and to connect through Wikipedia I have used JSOUP API.
	* I have implemented code in Java.