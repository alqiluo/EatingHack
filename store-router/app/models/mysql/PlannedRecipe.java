package models.mysql;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.joda.time.DateTime;

import play.data.format.Formats;
import play.db.ebean.Model;

@Entity(name="planned_recipe")
@Table
public class PlannedRecipe extends Model {

	private static final long serialVersionUID = 1L;

	@Id
	public int id;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName="id")
	public UserModel user;
	
	@ManyToOne
	@JoinColumn(name="recipe_id", referencedColumnName="id")
	public YummlyRecipe recipe;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public DateTime start;
	
	@Formats.DateTime(pattern="yyyy-MM-dd HH:mm:ss")
	public DateTime end;
	
	public int multiplier;
	
    public static Model.Finder<String, PlannedRecipe> find = new Model.Finder<String, PlannedRecipe>(String.class, PlannedRecipe.class);
	
}
