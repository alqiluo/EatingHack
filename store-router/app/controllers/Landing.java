package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import models.mysql.YummlyIngredient;
import models.mysql.YummlyRecipe;
import models.proxies.yummly.RecipeSearch;
import play.libs.F.Promise;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Landing extends Controller {

	public static Result index() {
//		RecipeSearch search = new RecipeSearch();
//		Promise<String> response = search.perform("soup");
//		
//		String result = response.get(1000);

		String result = "{\"criteria\":{\"terms\":null,\"allowedIngredients\":null,\"excludedIngredients\":null},\"matches\":[{\"imageUrlsBySize\":{\"90\":\"http://lh3.googleusercontent.com/_8N4VX0kUU-ew2uSKQeA5Ux3o4FltN8BJjI3EeVfvW8xvCcuure0lhbDXujbI3CHUWpFl5n6PNNgp0EZmR_cqQ=s90-c\"},\"sourceDisplayName\":\"Vitamix\",\"ingredients\":[\"onions\",\"olive oil\",\"sweet potatoes\",\"apples\",\"carrots\",\"vegetable bouillon cube\",\"water\"],\"id\":\"Sweet-Potato-Soup-1301102\",\"smallImageUrls\":[\"http://lh3.googleusercontent.com/_3ixBW7-0iJfacK96oNPHshVTBtgBvhNlLWBIM3sF_JOAgYLE1I19g6XAXLPWdQCft4SrSUk0R7YdR9Qfjq3=s90\"],\"recipeName\":\"Sweet Potato Soup\",\"totalTimeInSeconds\":null,\"attributes\":{},\"flavors\":{\"piquant\":0.0,\"meaty\":0.16666666666666666,\"bitter\":0.16666666666666666,\"sweet\":0.16666666666666666,\"sour\":0.16666666666666666,\"salty\":0.16666666666666666},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"http://lh3.googleusercontent.com/kXInHcVCtk-hG9yJ8gWoDXDPBskPcQnew7HiaUdcmBwUqQX7yrp0hdKk0IJx5izzPFm6fSOxd9djukp4dJmrmQ=s90-c\"},\"sourceDisplayName\":\"Totallythebomb.com\",\"ingredients\":[\"butternut squash\",\"chicken broth\",\"onions\",\"minced garlic\",\"salted butter\",\"heavy whipping cream\",\"pumpkin pie spice\",\"salt\",\"brown sugar\",\"dried cranberries\",\"butternut squash soup\"],\"id\":\"Super-Delicious-Butternut-Squash-Soup-1300997\",\"smallImageUrls\":[\"http://lh3.googleusercontent.com/atSUVUl95sPKWgm9KifV7ZabzxJPIZTjwO5AKO3-e1nB-jOB-CABjf2CbPGPvLVHTGE802cqyMmUgaj5AQWw=s90\"],\"recipeName\":\"Super Delicious Butternut Squash Soup\",\"totalTimeInSeconds\":3600,\"attributes\":{\"course\":[\"Soups\"],\"holiday\":[]},\"flavors\":{\"piquant\":0.0,\"meaty\":0.16666666666666666,\"bitter\":0.16666666666666666,\"sweet\":0.5,\"sour\":0.5,\"salty\":0.16666666666666666},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"http://lh3.googleusercontent.com/lusXQbLEGcsvTbAEowSr_cowYbVKpUQz76kYNkwhif0OIg6-T3lPYOY4D-rWIYDVP_uPNJ3C4BcMamuU4hVe1JY=s90-c\"},\"sourceDisplayName\":\"The Pioneer Woman\",\"ingredients\":[\"meat\",\"kidney beans\",\"pinto beans\",\"black beans\",\"diced tomatoes\",\"corn\",\"rotelle\",\"salt\",\"pepper\",\"Velveeta\"],\"id\":\"7-Can-Soup-1300455\",\"smallImageUrls\":[\"http://lh3.googleusercontent.com/rLvvOanYYBitiqCH6lQQtzJ0itc55IT_oXHYWx0sTPHUEVfz87j8_IUTKMKpkJIk_2z0SNuU0uHwVC9qN1z-2fw=s90\"],\"recipeName\":\"7-Can Soup\",\"totalTimeInSeconds\":2100,\"attributes\":{\"course\":[\"Soups\"]},\"flavors\":{\"piquant\":0.0,\"meaty\":0.16666666666666666,\"bitter\":0.8333333333333334,\"sweet\":0.16666666666666666,\"sour\":0.5,\"salty\":0.6666666666666666},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"http://lh3.googleusercontent.com/vbfCBddvWBNx61rViqp3M0pu7FF8YCZgtsrD8-kxp1f3vBZoW1lAnuZuQ4KhdCbHxkJcWIFGrjj1S_XEfbMGw7s=s90-c\"},\"sourceDisplayName\":\"The Dabblist\",\"ingredients\":[\"kale\",\"vegetable stock\",\"purple onion\",\"garlic\",\"avocado\",\"yellow miso\",\"olive oil\",\"lemon\",\"pepper\",\"sea salt\"],\"id\":\"Super-Immunity-Soup-1298215\",\"smallImageUrls\":[\"http://lh3.googleusercontent.com/m2KM8MezG4Lej6t98_jW35HlXJXrBZuODXjAzVuUjk03MbVQUr0QfDrtDpeWwoXbm9UqCZuV4K-nY0Zo-PJz=s90\"],\"recipeName\":\"Super Immunity Soup\",\"totalTimeInSeconds\":900,\"attributes\":{\"course\":[\"Soups\"]},\"flavors\":{\"piquant\":0.0,\"meaty\":0.16666666666666666,\"bitter\":0.8333333333333334,\"sweet\":0.16666666666666666,\"sour\":0.8333333333333334,\"salty\":0.6666666666666666},\"rating\":3},{\"imageUrlsBySize\":{\"90\":\"http://lh3.googleusercontent.com/3Theag2osDVTDCFMbc4yeaZ47mOk_9KK-H2Ti5IRQQoR0zLSfw719f-_ITwzR8owdV6VskpfZzL8ExCr62j5eg=s90-c\"},\"sourceDisplayName\":\"Our Table for Seven\",\"ingredients\":[\"chicken broth\",\"evaporated milk\",\"dried minced onion\",\"mustard\",\"salt\",\"shredded cheddar cheese\",\"shredded asiago cheese\",\"Velveeta\",\"frozen broccoli\"],\"id\":\"_Crock-Pot_-Three-Cheese-and-Broccoli-Soup-1296672\",\"smallImageUrls\":[\"http://lh3.googleusercontent.com/wq7wY768IvzIBpXXpl-pg01KIhFqXcztG9WY4sBIQX90xpKk569k7x17YjqEqtSQdveSR1d7yzlydxVassaH=s90\"],\"recipeName\":\"{Crock Pot} Three Cheese and Broccoli Soup\",\"totalTimeInSeconds\":14400,\"attributes\":{\"course\":[\"Soups\"]},\"flavors\":{\"piquant\":0.16666666666666666,\"meaty\":0.3333333333333333,\"bitter\":0.6666666666666666,\"sweet\":0.16666666666666666,\"sour\":0.16666666666666666,\"salty\":0.8333333333333334},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"http://lh3.googleusercontent.com/cVS-IBLabAYacZ0NyduKjqKZ9ISz-V7SF0l5fKj7FagHrM68awREOsIg5aCC0QbEU8RLDOSnEWMFin7YIppFj44=s90-c\"},\"sourceDisplayName\":\"Flavorite\",\"ingredients\":[\"hash brown\",\"chicken broth\",\"cream of chicken soup\",\"onions\",\"ground pepper\",\"cream cheese\"],\"id\":\"Crockpot-Creamy-Potato-Soup-1293663\",\"smallImageUrls\":[\"http://lh3.googleusercontent.com/Oxr6Q0lIMUTo3uPHelWkWgMuTRRbNXDzgZL0UXZSKVJ2ovI5kj27xMf97mktl5-pcVjWWp_d7lWm6qGdldm_NQ=s90\"],\"recipeName\":\"Crockpot Creamy Potato Soup\",\"totalTimeInSeconds\":3000,\"attributes\":{\"course\":[\"Soups\"]},\"flavors\":{\"piquant\":0.16666666666666666,\"meaty\":0.16666666666666666,\"bitter\":0.16666666666666666,\"sweet\":0.16666666666666666,\"sour\":0.3333333333333333,\"salty\":0.5},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"http://lh3.googleusercontent.com/dwb-ecl9WWTO1WZB6DO5UluQWQ6S7pWWb2NgC4BXkCRMJw9kq1UHRJumlAjTyCdpWBSjuVnty5mi6wcTjdbs23w=s90-c\"},\"sourceDisplayName\":\"Recipes That Crock\",\"ingredients\":[\"milk\",\"cream of chicken soup\",\"chicken\",\"sour cream\",\"hot pepper sauce\",\"green onions\",\"shredded Monterey Jack cheese\"],\"id\":\"Buffalo-Chicken-Soup-_Crock-Pot_-1300905\",\"smallImageUrls\":[\"http://lh3.googleusercontent.com/cHjlTVvE_1qX0Q9fX9dbVcRx08dW3fnGsYeD4ktqjgWILMV4Sm-yfc0wcmF_IGDI6A23MPJ5JI7NKEs_soCqTLo=s90\"],\"recipeName\":\"Buffalo Chicken Soup {Crock Pot}\",\"totalTimeInSeconds\":15000,\"attributes\":{\"course\":[\"Soups\"]},\"flavors\":{\"piquant\":0.8333333333333334,\"meaty\":0.16666666666666666,\"bitter\":0.3333333333333333,\"sweet\":0.16666666666666666,\"sour\":0.16666666666666666,\"salty\":0.5},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"http://lh3.googleusercontent.com/Iv4_qPeVRwik8DuHKlORTFEy1baCvQV_ZbyXI1whj3fsdEUomqNJu7sAXu7FxkbXuWfP1pAeP7lhZCfTE4oc=s90-c\"},\"sourceDisplayName\":\"Mama Loves Food\",\"ingredients\":[\"dried split peas\",\"chicken stock\",\"smoked ham hocks\",\"finely chopped onion\",\"minced garlic\",\"carrots\",\"diced ham\"],\"id\":\"Slow-Cooker-Split-Pea-Soup_-1299801\",\"smallImageUrls\":[\"http://lh3.googleusercontent.com/cghg8Bkf6Ds0itKxXXhHS74OjNGaH0ZCHDTL0_foCAJRT81ukFpbGqAcxgy2H6ABLD3Xg_PSWaJPgX4j7lD8=s90\"],\"recipeName\":\"Slow Cooker Split Pea Soup.\",\"totalTimeInSeconds\":1500,\"attributes\":{\"course\":[\"Soups\"]},\"flavors\":null,\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"http://lh3.googleusercontent.com/Dbigg2LnypSWIIHVqLhFPLrpxuPIJlBfUd592YJ8YmyVcEv1W7JNkUpNvWnhHKhitB8d4Io2wPnenc9Gh6qn=s90-c\"},\"sourceDisplayName\":\"Little Spice Jar\",\"ingredients\":[\"salted butter\",\"white onion\",\"garlic\",\"dried thyme\",\"all-purpose flour\",\"chicken broth\",\"nutmeg\",\"bay leaves\",\"milk\",\"half & half\",\"broccoli\",\"carrots\",\"cheddar cheese\",\"parmesan cheese\",\"salt\",\"pepper\"],\"id\":\"Creamy-Broccoli-Cheese-Soup-1300129\",\"smallImageUrls\":[\"http://lh3.googleusercontent.com/uhDze6-I4k589YJHT08UPyOMWkSmINlVKt5e2Skc_55mxMsprV0xAMzjTdJwOC2sijoT7TZzq_4paYH9zqsgxtk=s90\"],\"recipeName\":\"ˆCreamy Broccoli Cheese Soup\",\"totalTimeInSeconds\":1800,\"attributes\":{\"course\":[\"Soups\"]},\"flavors\":{\"piquant\":0.0,\"meaty\":0.16666666666666666,\"bitter\":0.16666666666666666,\"sweet\":0.16666666666666666,\"sour\":0.8333333333333334,\"salty\":0.16666666666666666},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"http://lh3.googleusercontent.com/uuxBu5koAzzyezfXrBeKS714t2k4MGCDZ5Oa7iQ2dwrHRVqBFUrA-fAg6uszcKxbJC_pVZWj7r4-h_JHW4D0jEk=s90-c\"},\"sourceDisplayName\":\"Well Plated\",\"ingredients\":[\"great northern beans\",\"diced tomatoes\",\"mexicorn\",\"water\",\"chili powder\",\"parsley flakes\",\"ground cumin\",\"dried oregano\",\"garlic powder\",\"onion powder\",\"kosher salt\",\"ground pepper\",\"tortilla chips\",\"sour cream\",\"grating cheese\",\"nonfat greek yogurt\",\"chopped cilantro\",\"avocado\"],\"id\":\"Easy-Taco-Soup-1300316\",\"smallImageUrls\":[\"http://lh3.googleusercontent.com/sWvabfbByKabaGJ_bE4kmFFW1eC4Yzx_gAmdCGnPwqjcwSjrNStiUnPiP6MNNG70JlZJoCkQ3aWyO0uXpFeg=s90\"],\"recipeName\":\"Easy Taco Soup\",\"totalTimeInSeconds\":600,\"attributes\":{\"course\":[\"Soups\"]},\"flavors\":null,\"rating\":4}],\"facetCounts\":{},\"totalMatchCount\":103605,\"attribution\":{\"html\":\"Recipe search powered by <a href='http://www.yummly.com/recipes'><img alt='Yummly' src='http://static.yummly.com/api-logo.png'/></a>\",\"url\":\"http://www.yummly.com/recipes/\",\"text\":\"Recipe search powered by Yummly\",\"logo\":\"http://static.yummly.com/api-logo.png\"}}";

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
		
		return ok(Json.toJson(result));
	}
	
}
