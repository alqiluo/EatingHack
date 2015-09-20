package controllers;

import java.util.Random;

import models.mysql.UserModel;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

public class User extends Controller {
	
	public static Result register() {
	//	{
	//		"register" : {
	//			"email" : "test@gmail.com",
	//			"password" : "yousuck",
	//			"udid" : "wut"
	//		}
	//	}
	//	
	//	{
	//		"email" : "test@gmail.com",
	//		"udid" : "wut",
	//		"sessionStr" : "lasjdflkxjlj"
	//	}
		try {

			
			JsonNode json = request().body().asJson();
			JsonNode registrationJson = json.findPath("register");
			
			String email = registrationJson.get("email").textValue();
			String hashedPassword = registrationJson.get("password").textValue();
			String udid = registrationJson.get("udid").textValue();

			UserModel user = UserModel.find.where().eq("email", email).findUnique();
			
			if(user != null) {
	    		return ok(Json.toJson("error: email not available"));
			}
			
			user = new UserModel();
	    	user.email = email;
	    	user.hashedPassword = hashedPassword;
	    	user.verified = false;
	    	user.udid = udid;
	    	user.sessionStr = getRandomHexString(255);
	    	user.save();
	    	user = UserModel.find.where().eq("id", user.id).findUnique();
	    	
	    	user.hashedPassword = null;
	    	
	    	return ok(Json.toJson(user));
		}
		catch(Exception e) {
			System.out.println("register Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
	
	private static String getRandomHexString(int numchars){
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.toString().substring(0, numchars);
    }

	public static Result login() {
		//	{
		//		"login" : {
		//			"email" : "test@gmail.com",
		//			"password" : "yousuck",
		//			"udid" : "wut"
		//		}
		//	}
		//	
		//	{
		//		"email" : "test@gmail.com",
		//		"udid" : "wut",
		//		"sessionStr" : "lasjdflkxjlj"
		//	}
		
		try {
			JsonNode json = request().body().asJson();
			
			JsonNode loginJson = json.findPath("login");
			
			String email = loginJson.get("email").textValue();
			String hashedPassword = loginJson.get("password").textValue();
			String udid = loginJson.get("udid").textValue();
			
			UserModel user = UserModel.find.where().eq("email", email).eq("hashedPassword", hashedPassword).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: login failed"));
			}
			
			user.udid = udid;
			user.sessionStr = getRandomHexString(255);
			user.update();
			
			user.hashedPassword = null;
			
			return ok(Json.toJson(user));
		}
		catch(Exception e) {
			System.out.println("login Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
	
}
