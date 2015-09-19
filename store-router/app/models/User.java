package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.base.DatedModel;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class User extends DatedModel {
	
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
	
    public static Model.Finder<String, User> find = new Model.Finder<String, User>(String.class, User.class);

}
