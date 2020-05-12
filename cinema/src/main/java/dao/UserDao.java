package dao;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UserDao {
	
	
	
	private final String path = "/user.json";
	
	public UserDao() {
		
	}
	
	public String getPasswordEncrypted(String passwordToHash) {
        String generatedPassword = null;
        try {
            
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            md.update(passwordToHash.getBytes());
             
            byte[] bytes = md.digest();
            
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i< bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
	}
	
	public void addUser(String username, String password, String role){
		password = getPasswordEncrypted(password);
		System.out.println(username + " " + password);
		JSONObject obj = new JSONObject();
		obj.put("username", username);
		obj.put("password", password);
		obj.put("role", role);
		
		JSONArray users = getUsersJSON();
		
		users.add(obj);
		
		persistUsers(users);
	}
	
	private void persistUsers(JSONArray users){
		JSONObject obj = new JSONObject();
		obj.put("users", users);
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
	
	private JSONArray getUsersJSON() {
		try{
			JSONObject obj = new JSONObject();
			JSONParser parser = new JSONParser();
			FileReader fileReader = new FileReader(path);
			obj = (JSONObject)parser.parse(fileReader);
			fileReader.close();
			JSONArray users = (JSONArray)obj.get("users");
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
	
	private JSONObject getUser(String username) {
		JSONArray users = getUsersJSON();
		Iterator<JSONObject> iterator = users.iterator();
		while(iterator.hasNext()) {
			JSONObject obj = iterator.next();
			if(((String)obj.get("username")).equals(username)){
				return obj;
			}
		}
		return null;
	}
	
	public String getRole(String username) {
		
		JSONObject obj = getUser(username);
		
		if(obj == null)
			return null;
		
		return (String)obj.get("role");
	}
	
	public String getPassword(String username) {
		
		JSONObject obj = getUser(username);
		
		if(obj == null)
			return null;
		
		return (String)obj.get("password");
	}
	
}
