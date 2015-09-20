package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import models.mysql.PlannedRecipe;
import models.mysql.UserModel;
import models.mysql.YummlyRecipe;

import org.joda.time.DateTime;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.avaje.ebean.Expr;
import com.fasterxml.jackson.databind.JsonNode;

public class Data extends Controller {
	
	public static Result frequentWeeklyRecipes() {
//		{
//			"user" : {
//				"email" : "test@gmail.com",
//				"sessionStr" : "yousuck"
//			},
//			"dateRange" : {
//				"start" : 1234,
//				"end" : 1456
//			}
//		}
		
		class Return {
			
			public List<YummlyRecipe> recipesInDescendingOrder;
			
			public List<Integer> recipesCountInDescendingOrder;
			
		};
		
		try {
			JsonNode json = request().body().asJson();
			
			JsonNode userJson = json.findPath("user");
			
			String email = userJson.get("email").textValue();
			String session = userJson.get("sessionStr").textValue();
			
			UserModel user = UserModel.find.where().eq("email", email).eq("session_str", session).findUnique();
			
			if(user == null) {
				return ok(Json.toJson("error: login failed"));
			}
			
			JsonNode dateRangeNode = json.get("dateRange");
			
			DateTime start = new DateTime(dateRangeNode.get("start").asLong());
			DateTime end = new DateTime(dateRangeNode.get("end").asLong());
			
			List<PlannedRecipe> calendarRecipes = PlannedRecipe.find.where().eq("user_id", user.id).and(Expr.ge("start", start), Expr.le("end", end)).findList();
			
			Map<Integer, Integer> recipeCount = new HashMap<Integer, Integer>();
			
			for(PlannedRecipe plannedRecipe : calendarRecipes) {
				if(recipeCount.containsKey(plannedRecipe.recipe.id)) {
					recipeCount.put(plannedRecipe.recipe.id, new Integer(recipeCount.get(plannedRecipe.recipe.id).intValue() + 1));
				} else {
					recipeCount.put(plannedRecipe.recipe.id, new Integer(1));
				}
			}
			
			List<Map.Entry<Integer, Integer>> list =
			        new LinkedList<>( recipeCount.entrySet() );
		    Collections.sort( list, new Comparator<Map.Entry<Integer, Integer>>()
		    {
		        @Override
		        public int compare( Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2 )
		        {
		            return (o2.getValue()).compareTo( o1.getValue() );
		        }
		    } );

		    Return _return = new Return();
		    _return.recipesInDescendingOrder = new ArrayList<YummlyRecipe>();
		    _return.recipesCountInDescendingOrder = new ArrayList<Integer>();
		    
		    for (Map.Entry<Integer, Integer> entry : list)
		    {
		    	_return.recipesInDescendingOrder.add(YummlyRecipe.find.where().eq("id", entry.getKey()).findUnique());
		    	_return.recipesCountInDescendingOrder.add(entry.getValue());
		    }
			
			return ok(Json.toJson(_return));
		}
		catch(Exception e) {
			System.out.println("readWeeklyRecipes Exception: " + e.getMessage());
			return ok(Json.toJson("invalid request format"));
		}
	}
	
}
