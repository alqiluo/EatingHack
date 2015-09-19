package controllers;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Landing extends Controller {

	public static Result index() {
		return ok(Json.toJson("sup"));
	}
	
}
