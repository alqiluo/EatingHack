package models.proxies.yummly;

import play.Play;

public abstract class BaseYummly {
	
	private String APP_ID = Play.application().configuration().getString("YUMMLY.APP_ID");
	
	private String APP_KEY = Play.application().configuration().getString("YUMMLY.APP_KEY");

	protected String getAPPCrediential() {
		return "_app_id=" + APP_ID + "&_app_key=" + APP_KEY;
	}
	
}
