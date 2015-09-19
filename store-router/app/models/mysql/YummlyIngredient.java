package models.mysql;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity(name="yummly_ingredient")
@Table
public class YummlyIngredient extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public int id;
	
	public String name;
	
    public static Model.Finder<String, YummlyIngredient> find = new Model.Finder<String, YummlyIngredient>(String.class, YummlyIngredient.class);

}
