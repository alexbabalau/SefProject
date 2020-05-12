package dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.Admin;
import model.Manager;
import model.Regular;
import model.User;

public class UserDao {
	
	
	
	private final String path = "users5.json";
	private File myFile;
	public static UserDao instance = null;
	private UserDao() {
		myFile = new File(path);
		try {
			myFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static UserDao getInstance() {
		if(instance == null) {
			instance = new UserDao();
		}
		return instance;
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
	
	public void addUser(User user){
		String password = getPasswordEncrypted(user.getPassword());
		
		JSONObject obj = new JSONObject();
		obj.put("username", user.getUsername());
		obj.put("password", password);
		obj.put("role", user.getRole());
		obj.put("phone", user.getPhone());
		obj.put("email", user.getEmail());
		obj.put("name", user.getName());
		obj.put("cinema", user.getCinema());
		JSONArray users = JSONUtils.getEntriesJSON(path);
		
		users.add(obj);
		
		JSONUtils.persistEntries(users, path);
	}
	
	public boolean existUser(String username) {
		JSONArray users = JSONUtils.getEntriesJSON(path);
		Iterator<JSONObject> iterator = users.iterator();
		
		while(iterator.hasNext()) {
			JSONObject next = iterator.next();
			
			if(next.get("username").equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
	
	public User selectByUsername(String username) {
		JSONObject user = getUser(username);
		if(((String)user.get("role")).equals("admin")) {
			return new Admin((String)user.get("username"), (String)user.get("password"), (String)user.get("phone"), (String)user.get("name"), (String)user.get("email"));
		}
		if(((String)user.get("role")).equals("manager")) {
			return new Manager((String)user.get("username"), (String)user.get("password"), (String)user.get("phone"), (String)user.get("name"), (String)user.get("email"), (String)user.get("cinema"));
		}
		if(((String)user.get("role")).equals("regular")) {
			return new Regular((String)user.get("username"), (String)user.get("password"), (String)user.get("phone"), (String)user.get("name"), (String)user.get("email"));
		}
		return null;
	}
	
	private JSONObject getUser(String username) {
		JSONArray users = JSONUtils.getEntriesJSON(path);
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
	
	public void close() {
		myFile.delete();
	}
	
}
