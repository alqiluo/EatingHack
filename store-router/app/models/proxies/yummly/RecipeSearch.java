package models.proxies.yummly;

import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSResponse;

public class RecipeSearch extends BaseYummly {
	
	private String url = "http://api.yummly.com/v1/api/recipes?" + getAPPCrediential() + "&q=";

	public Promise<String> perform(String text) {
		System.out.println(url + text);
		
		Promise<String> promise = WS.url(url + text).get().map(
			    new Function<WSResponse, String>() {
			        public String apply(WSResponse response) {
			            String result = response.getBody();
			            return result;
			        }
			    }
			);
		return promise;
	}
	
}
