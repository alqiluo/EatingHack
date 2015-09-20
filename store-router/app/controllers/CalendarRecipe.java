package controllers;

import java.util.List;

import models.mysql.PlannedRecipe;
import models.mysql.UserModel;
import models.mysql.YummlyRecipe;

import org.joda.time.DateTime;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Expr;
import com.fasterxml.jackson.databind.JsonNode;

public class CalendarRecipe extends Controller {
	
	public static Result createWeeklyRecipes() {
//		{
//			"user" : {
//				"email" : "asdf@gmail.com",
//				"sessionStr" : "aswldfjlx"
//			},
//			"calendarRecipes" : [
//				{
//					"recipeId": 123,
//					"multiplier" : 3,
//					"start" : 1234124019
//					"end" : 1284113412
//				},
//				{
//					"recipeId": 123,
//					"multiplier" : 3,
//					"start" : 1234124019
//					"end" : 1284113412
//				}
//			]
//		}
	
		try {
			JsonNode json = request().body().asJson();
			
			JsonNode userJson = json.findPath("user");
			
			String email = userJson.get("email").textValue();
			String session = userJson.get("sessionStr").textValue();
			
			UserModel user = UserModel.find.where().eq("email", email).eq("session_str", session).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: login failed"));
			}
			
			JsonNode calendarRecipesNode = json.get("calendarRecipes");
			
			if(calendarRecipesNode.isArray()) {
				for(JsonNode calendarRecipeNode : calendarRecipesNode) {
					YummlyRecipe recipe = YummlyRecipe.find.where().eq("id", calendarRecipeNode.get("recipeId").asInt()).findUnique();
					
					PlannedRecipe plannedRecipe = new PlannedRecipe();
					plannedRecipe.user = user;
					plannedRecipe.recipe = recipe;
					plannedRecipe.start = new DateTime(calendarRecipeNode.get("start").asInt());
					plannedRecipe.end = new DateTime(calendarRecipeNode.get("end").asInt());
					plannedRecipe.multiplier = calendarRecipeNode.get("multiplier").asInt();
					
					plannedRecipe.save();
				}
			}			
			return ok(Json.toJson("saved"));
		}
		catch(Exception e) {
			System.out.println("createWeeklyRecipes Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
	
	public static Result readWeeklyRecipes() {
//		{
//			"user" : {
//				"email" : "asdf@gmail.com",
//				"sessionStr" : "aswldfjlx"
//			},
//			"calendarRecipes" : {
//				"start" : 1235123,
//				"end" : 2341313
//			}
//		}
	
		try {
			JsonNode json = request().body().asJson();
			
			JsonNode userJson = json.findPath("user");
			
			String email = userJson.get("email").textValue();
			String session = userJson.get("sessionStr").textValue();
			
			UserModel user = UserModel.find.where().eq("email", email).eq("session_str", session).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: login failed"));
			}
			
			JsonNode calendarRecipesNode = json.get("calendarRecipes");
			
			DateTime start = new DateTime(calendarRecipesNode.get("start").asInt());
			DateTime end = new DateTime(calendarRecipesNode.get("end").asInt());
			
			List<PlannedRecipe> calendarRecipes = PlannedRecipe.find.where().eq("user_id", user.id).and(Expr.ge("start", start), Expr.le("end", end)).findList();
			
			return ok(Json.toJson(calendarRecipes));
		}
		catch(Exception e) {
			System.out.println("readWeeklyRecipes Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
	
	public static Result updateWeeklyRecipes() {
//		{
//			"user" : {
//				"email" : "asdf@gmail.com",
//				"sessionStr" : "aswldfjlx"
//			},
//			"calendarRecipes" : [
//				{
//					"id" : 123,
//					"start" : 1235123,
//					"end" : 2341313
//					"multiplier" : 12
//				},
//				{
//					"id" : 123,
//					"start" : 1235123,
//					"end" : 2341313
//					"multiplier" : 12
//				}
//			]
//		}
	
		try {
			JsonNode json = request().body().asJson();
			
			JsonNode userJson = json.findPath("user");
			
			String email = userJson.get("email").textValue();
			String session = userJson.get("sessionStr").textValue();
			
			UserModel user = UserModel.find.where().eq("email", email).eq("session_str", session).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: login failed"));
			}
			
			JsonNode calendarRecipesNode = json.get("calendarRecipes");
			
			if(calendarRecipesNode.isArray()) {
				for(JsonNode calendarRecipeNode : calendarRecipesNode) {
					PlannedRecipe plannedRecipe = PlannedRecipe.find.where().eq("id", calendarRecipeNode.get("id").asInt()).findUnique();
					plannedRecipe.start = new DateTime(calendarRecipeNode.get("start").asInt());
					plannedRecipe.end = new DateTime(calendarRecipeNode.get("end").asInt());
					plannedRecipe.multiplier = calendarRecipeNode.get("multiplier").asInt();
					
					plannedRecipe.update();
				}
			}			
			return ok(Json.toJson("updated"));
		}
		catch(Exception e) {
			System.out.println("updateWeeklyRecipes Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
	
	public static Result deleteWeeklyRecipes() {
//		{
//			"user" : {
//				"email" : "asdf@gmail.com",
//				"sessionStr" : "aswldfjlx"
//			},
//			"calendarRecipeIds" : [
//				123,
//				124,
//				125
//			]
//		}
	
		try {
			JsonNode json = request().body().asJson();
			
			JsonNode userJson = json.findPath("user");
			
			String email = userJson.get("email").textValue();
			String session = userJson.get("sessionStr").textValue();
			
			UserModel user = UserModel.find.where().eq("email", email).eq("session_str", session).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: login failed"));
			}
			
			JsonNode calendarRecipeIdsNode = json.get("calendarRecipeIds");
			
			if(calendarRecipeIdsNode.isArray()) {
				for(JsonNode calendarRecipeIdNode : calendarRecipeIdsNode) {
					PlannedRecipe plannedRecipe = PlannedRecipe.find.where().eq("id", calendarRecipeIdNode.asInt()).findUnique();
	
					plannedRecipe.delete();
				}
			}			
			return ok(Json.toJson("deleted"));
		}
		catch(Exception e) {
			System.out.println("deleteWeeklyRecipes Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
}
