package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

import com.avaje.ebean.*;
import com.avaje.ebean.annotation.PrivateOwned;



@Entity
public class Competition extends Model{
	
	@Id
	public Long id;
	
	public String name;
	
	//@ManyToMany
	@OneToMany(cascade = CascadeType.ALL)
	public List<Team> teams = new ArrayList<Team>();
	
	@OneToMany(cascade = CascadeType.ALL)
	public List<Vote> votes = new ArrayList<Vote>();
	
	public Competition(String name){
		this.name = name;
	}
	
	
	
	//Query stuff
    public static Model.Finder<Long,Competition> find = new Model.Finder(Long.class, Competition.class);

	public static void create(Competition competition){
		competition.save();
	}
	
	public static void addTeam(Long competition, Long team){
		//add a team to the specified competition.
		Competition c = find.ref(competition);
		Team t = Team.find.ref(team);
		
		//associations must be handled manually on a OneToMany/ManyToOne relation
		c.teams.add(t);
		t.competition = c;
		System.out.println("adding team: " + t.teamName + " to competition: " + c.name);
		t.save();
		c.save();
		
		
		
	}
	
	public static List<Team> getTeams(Long competition){
		Competition c = find.ref(competition);
		return c.teams;
	}
	
	public static List<Competition> findAll() {
        return find.all();
    }
	
}