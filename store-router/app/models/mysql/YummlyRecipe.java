package models.mysql;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.db.ebean.Model;

@Entity(name="yummly_recipe")
@Table
public class YummlyRecipe extends Model {
	
	private static final long serialVersionUID = 1L;

	@Id
	public int id;
	
	@Column(name="yummly_id")
	public String yummlyRecipeId;
	
	public String name;
	
	@Column(name="image_url")
	public String imageURL;

	@Column(name="cook_time")
	public int cookTime;
	
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name="YummlyIngredient")
	public List<YummlyIngredient> ingredients;
	
    public static Model.Finder<String, YummlyRecipe> find = new Model.Finder<String, YummlyRecipe>(String.class, YummlyRecipe.class);
    
}
