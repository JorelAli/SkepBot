package io.github.skepter.skepbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONArray;
import org.json.JSONObject;

public class WebsiteGetters {

	public static String shortAnswersWolframAlpha(String query) throws IOException {
		StringBuilder result = new StringBuilder();
		String urlStr = "http://api.wolframalpha.com/v1/result?appid=" + SkepBot.WOLFRAM_ALPHA_ID + "&i=" + URLEncoder.encode(query, "UTF-8") + "&units=metric";
		SkepBot.log("Response sent to Wolfram Alpha");
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
		if(conn.getResponseCode() == 501) {
			return "I couldn't understand that, sorry";
		}		
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		
		String output = result.toString();
		if(output.matches("\\d+\\/\\d+")) {
			double a = Double.parseDouble(output.split("\\/")[0]);
			double b = Double.parseDouble(output.split("\\/")[1]);
			double resultDouble = a / b;
			DecimalFormat format = new DecimalFormat("#.00");
			
			if(resultDouble > Long.MAX_VALUE) {
				return "Number is too large to compute";
			} else {
				return format.format(resultDouble) + " (2 decimal places)";
			}
		}
		
		if(output.contains("I can help you to compute.")) {
			return "I can " + SkepBot.functions.get(ThreadLocalRandom.current().nextInt(0, SkepBot.functions.size()));
		}
		
		return output;
	}

	public static String getDadJoke() throws IOException {
		StringBuilder result = new StringBuilder();
		URL url = new URL("https://icanhazdadjoke.com/");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "text/plain");
	    conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString();
	}
	
	public static String getInsult() throws IOException {
		StringBuilder result = new StringBuilder();
		URL url = new URL("https://insult.mattbas.org/api/insult.txt");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
	    conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString();
	}
	
	public static String getPoshInsult() throws IOException {
		StringBuilder result = new StringBuilder();
		URL url = new URL("http://quandyfactory.com/insult/json");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
	    conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return new JSONObject(result.toString()).getString("insult");
	}
		
	public static String getMumJoke() throws IOException {
		StringBuilder result = new StringBuilder();
		URL url = new URL("http://api.yomomma.info/");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
	    conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString().substring(9, result.toString().length() - 2);
	}
	
	public static String getRandomFact() throws IOException {
		StringBuilder result = new StringBuilder();
		URL url = new URL("http://randomfactgenerator.net/");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
	    conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		
		String raw = result.toString();
		String output = raw.substring(raw.indexOf("<div id='z'>"), raw.indexOf("<br/>", raw.indexOf("<div id='z'>")));
		return output.substring(12);
	}

	public static String getChuckNorrisJoke() throws IOException {
		StringBuilder result = new StringBuilder();
		URL url = new URL("http://api.icndb.com/jokes/random/");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		String raw = result.toString();
		String joke = raw.substring(raw.indexOf("joke\": \"") + 8, raw.indexOf("\", \"cate"));
		return joke;
	}
	
	public static String[] getDefinition(String wordToDefine) throws IOException {
		StringBuilder result = new StringBuilder();
		URL url = new URL("https://owlbot.info/api/v1/dictionary/" + wordToDefine + "?format=json");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
	    conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		
		
		JSONArray arr = new JSONArray(result.toString());
		
		if(arr.length() == 0) {
			return new String[] {"I have no clue what " + wordToDefine + " means. Sorry!"};
		}
		
		String[] output = new String[arr.length()];
		
		for(int i = 0; i < arr.length(); i++) {
			output[i] = arr.getJSONObject(i).getString("type") + ": "+ arr.getJSONObject(i).getString("defenition");
		}
		
		return output;
	}

	public static String getWouldYouRather() throws IOException {
		StringBuilder result = new StringBuilder();
		URL url = new URL("https://www.rrrather.com/botapi");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();		
		String raw = result.toString();
		JSONObject ob = new JSONObject(raw);
		if(ob.getBoolean("nsfw")) {
			return getWouldYouRather();
		} else {
			return ob.get("title") + ": " + ob.get("choicea") + " OR " + ob.get("choiceb");
		}
	}
	
	public static String getNumberFact(String mainMsgLC) throws IOException {
		String number = mainMsgLC.replaceAll("\\D", "");
		StringBuilder result = new StringBuilder();
		URL url = new URL("http://numbersapi.com/" + number + "?notfound=floor");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();

		String resultantNumber = result.toString().replaceAll("\\D", "");
		if (!number.equals(resultantNumber)) {
			return "I couldn't find any interesting info about that number";
		} else {
			return result.toString();
		}
	}
	
	public static String randomNoun() throws IOException {
		String urlStr = "http://www.wordgenerator.net/application/p.php?" + "type=1" + "&id=nouns" + "&id2=null" + "&id3=null" + "&spaceflag=false";
		
		StringBuilder result = new StringBuilder();
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
        conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		rd.close();
		return result.toString().split(",")[0];
	}

}
