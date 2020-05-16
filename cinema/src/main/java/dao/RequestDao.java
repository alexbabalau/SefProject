package dao;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Manager;

public class RequestDao {
	private String path = "requests.json";
	private File myFile;
	private static RequestDao instance;
	private RequestDao() {
		String currentDir = System.getProperty("user.home");
		path = currentDir + System.getProperty("file.separator") + path;
		myFile = new File(path);
		if(myFile.exists())
			myFile.delete();
		try {
			myFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static RequestDao getInstance() {
		if(instance == null) {
			instance = new RequestDao();
		}
		return instance;
	}
	
	public void close() {
		myFile.delete();
		instance = null;
	}
	
	public void addRequest(Manager manager) {
		JSONObject obj = new JSONObject();
		obj.put("username", manager.getUsername());
		obj.put("password", PasswordUtils.getPasswordEncrypted(manager.getPassword()));
		obj.put("phone", manager.getPhone());
		obj.put("email", manager.getEmail());
		obj.put("name", manager.getName());
		obj.put("cinema", manager.getCinema());
		JSONArray requests = JSONUtils.getEntriesJSON(path);
		
		requests.add(obj);
		
		JSONUtils.persistEntries(requests, path);
	}
	
	private Manager getManagerFromJSON(JSONObject obj) {
		return new Manager((String)obj.get("username"), (String)obj.get("password"), (String)obj.get("phone"), (String)obj.get("name"), (String)obj.get("email"), (String)obj.get("cinema"));
	}
	
	public Manager findRequest(String username) {
		JSONArray requests = JSONUtils.getEntriesJSON(path);
		for(int i = 0; i < requests.size(); i++) {
			JSONObject obj = (JSONObject) requests.get(i);
			if(username.equals((String)obj.get("username"))) {
				return getManagerFromJSON(obj);
			}
		}
		return null;
	}
	
	public List<Manager> getRequests(){
		List<Manager> requestList = new ArrayList<>();
		
		JSONArray requests = JSONUtils.getEntriesJSON(path);
		
		for(int i = 0; i < requests.size(); i++) {
			requestList.add(getManagerFromJSON((JSONObject)requests.get(i)));
		}
		
		return requestList;
	}
	
	public void deleteRequest(String username) {
		JSONArray requests = JSONUtils.getEntriesJSON(path);
		requests.removeIf(obj -> username.equals((String)((JSONObject)obj).get("username")));
		JSONUtils.persistEntries(requests, path);
	}
	
}
