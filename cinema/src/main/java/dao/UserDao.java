package dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
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
	
	
	
	private String path = "users5.json";
	private File myFile;
	public static UserDao instance = null;
	private UserDao() {
		String currentDir = System.getProperty("user.home");
		path = currentDir + System.getProperty("file.separator") + path;
		myFile = new File(path);
		if(myFile.exists())
			myFile.delete();
		System.out.println(myFile.getAbsolutePath());
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
	
	
	
	public void addUser(User user, boolean isEncrypted){
		String password = user.getPassword();
		if(!isEncrypted)
			password = PasswordUtils.getPasswordEncrypted(password);
		System.out.println(user.getUsername() + " " + password);
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
