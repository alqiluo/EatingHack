package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.mysql.UserModel;
import models.mysql.YummlyIngredient;
import models.mysql.YummlyRecipe;
import models.proxies.yummly.RecipeSearch;
import play.Play;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

public class Recipe extends Controller {

	public static Result searchInternalRecipe() {
//		{
//			"user" : {
//				"email" : "asdf@gmail.com"
//				"sessionStr" : "asldfj"
//			},
//			"search" : "soup"
//		}
		
		String searchText;
		
		try {
			JsonNode json = request().body().asJson();
			
			JsonNode userJson = json.findPath("user");
			
			String email = userJson.get("email").textValue();
			String session = userJson.get("sessionStr").textValue();
			
			searchText = json.get("search").asText();
			
			UserModel user = UserModel.find.where().eq("email", email).eq("session_str", session).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: login failed"));
			}
		}
		catch(Exception e) {
			System.out.println("searchInternalRecipe Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
		
		if(searchText.length() < 3) {
			return ok(Json.toJson("API requires search text length >= 3"));
		}
		
		List<YummlyRecipe> recipes = YummlyRecipe.find.where().contains("name", searchText).findList();
		
		return ok(Json.toJson(recipes));
	}
	
	public static Result searchExternalRecipe() {
//		{
//			"user" : {
//				"email" : "asdf@gmail.com"
//				"sessionStr" : "asldfj"
//			},
//			"search" : "soup"
//		}
	
		String searchText;

		try {
			JsonNode json = request().body().asJson();
			
			JsonNode userJson = json.findPath("user");
			
			String email = userJson.get("email").textValue();
			String session = userJson.get("sessionStr").textValue();
			
			searchText = json.get("search").asText();
			
			UserModel user = UserModel.find.where().eq("email", email).eq("session_str", session).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: login failed"));
			}
		}
		catch(Exception e) {
			System.out.println("searchExternalRecipe Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
		
		if(searchText.length() < 3) {
			return ok(Json.toJson("API requires search text length >= 3"));
		}
		
		RecipeSearch search = new RecipeSearch();
		Promise<String> response = search.perform(searchText);
		
		String result = response.get(Play.application().configuration().getInt("HTTP.TIMEOUT"));

		JsonNode json = Json.parse(result);

		JsonNode matches = json.get("matches");
		
		if(matches.isArray()) {
			for(JsonNode match : matches) {
				
				String yummlyRecipeId = match.get("id").asText();
				
				if(YummlyRecipe.find.where().eq("yummly_id", yummlyRecipeId).findUnique() != null) {
					continue;
				}
				
				YummlyRecipe recipe = new YummlyRecipe();
				recipe.yummlyRecipeId = yummlyRecipeId;
				recipe.name = match.get("recipeName").asText();
				recipe.cookTime = match.get("totalTimeInSeconds").asInt();
				if(match.get("imageUrlsBySize").has("90")) {
					recipe.imageURL = match.get("imageUrlsBySize").get("90").asText();
				}
				else {
					recipe.imageURL = match.get("smallImageUrls").get(0).asText();
				}
				
				JsonNode ingredients = match.get("ingredients");
				
				if(ingredients.isArray()) {
					for(JsonNode ingredient : ingredients) {
						String ingredientName = ingredient.asText();
						
						if(YummlyIngredient.find.where().eq("name", ingredientName).findUnique() != null) {
							continue;
						}
						
						YummlyIngredient yummlyIngredient = new YummlyIngredient();
						yummlyIngredient.name = ingredientName;
						recipe.ingredients.add(yummlyIngredient);
					}
				}
				
				recipe.save();
			}
		}
		
		List<YummlyRecipe> recipes = YummlyRecipe.find.where().contains("name", searchText).findList();
		
		return ok(Json.toJson(recipes));
	}
	
	public static Result getRecipeIngredients() {
//		{
//			"user" : {
//				"email" : "asdf@gmail.com",
//				"sessionStr" : "aswldfjlx"
//			},
//			"recipes" : [
//				{
//					"id": 123,
//					"multiplier" : 1 
//				},
//				{
//					"id": 124,
//					"multiplier" : 2 
//				},
//				{
//					"id": 121,
//					"multiplier" : 1 
//				}
//			]
//		}
		
		Map<String, Integer> netIngredientMap = new HashMap<String, Integer>();
		
		try {
			JsonNode json = request().body().asJson();
			
			JsonNode userJson = json.findPath("user");
			
			String email = userJson.get("email").textValue();
			String session = userJson.get("sessionStr").textValue();
			
			UserModel user = UserModel.find.where().eq("email", email).eq("session_str", session).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: login failed"));
			}
			
			JsonNode recipesNode = json.get("recipes");
			
			if(recipesNode.isArray()) {
				for(JsonNode recipeNode : recipesNode) {
					YummlyRecipe recipe = YummlyRecipe.find.where().eq("id", recipeNode.get("id").asInt()).findUnique();
					for(YummlyIngredient ingredient : recipe.ingredients) {
						if(netIngredientMap.containsKey(ingredient.name)) {
							netIngredientMap.put(ingredient.name, new Integer(netIngredientMap.get(ingredient.name) + recipeNode.get("multiplier").asInt()));
						} else {
							netIngredientMap.put(ingredient.name, new Integer(0));
						}
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("getRecipeIngredients Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
		
		return ok(Json.toJson(netIngredientMap));
	}
	
}
