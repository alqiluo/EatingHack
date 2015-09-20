package models.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import models.mysql.base.DatedModel;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity(name="user")
@Table
public class UserModel extends DatedModel {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Required
	public int id;
	
	public String email;

	@Column(name="hashed_password")
	public String hashedPassword;
	
	public Boolean verified;
	
	@Column(name="session_str")
	public String sessionStr;
	
	@Column(name="udid")
	public String udid;
	
    public static Model.Finder<String, UserModel> find = new Model.Finder<String, UserModel>(String.class, UserModel.class);

}
