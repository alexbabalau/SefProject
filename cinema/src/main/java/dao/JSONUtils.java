package dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtils {
	public static JSONArray getEntriesJSON(String path) {
		FileReader fileReader = null;
		try{
			JSONObject obj = new JSONObject();
			JSONParser parser = new JSONParser();
			
			fileReader = new FileReader(path);
			
			obj = (JSONObject)parser.parse(fileReader);
			
			fileReader.close();
			
			JSONArray users = (JSONArray)obj.get("entries");
			return users;
		}
		catch(ParseException e) {
			if(fileReader != null)
				try {
					fileReader.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		FileWriter file = null;
		try {
			
			file = new FileWriter(path);
			file.write(obj.toJSONString());
			
			file.flush();
			file.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
