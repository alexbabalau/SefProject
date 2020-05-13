package dao;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtils {
	public static JSONArray getEntriesJSON(String path) {
		try{
			JSONObject obj = new JSONObject();
			JSONParser parser = new JSONParser();
			
			FileReader fileReader = new FileReader(path);
			
			obj = (JSONObject)parser.parse(fileReader);
			
			fileReader.close();
			JSONArray users = (JSONArray)obj.get("entries");
			return users;
		}
		catch(ParseException e) {
			return new JSONArray();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void persistEntries(JSONArray entries, String path){
		JSONObject obj = new JSONObject();
		obj.put("entries", entries);
		try {
			FileWriter file = new FileWriter(path);
			
			file.write(obj.toJSONString());
			
			file.flush();
			file.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
