package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

import com.avaje.ebean.*;
import com.avaje.ebean.annotation.PrivateOwned;



@Entity
public class Team extends Model{
	
	@Id
	public Long id;
	
	public String teamName;
	
	public String project;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	public List<Vote> votes = new ArrayList<Vote>();
	
	//@ManyToMany
	//public List<Competition> competition = new ArrayList<Competition>();
	@ManyToOne
	public Competition competition;
	
	public Team(String teamName, String project){
		this.teamName = teamName;
		this.project = project;
	}
	
	
	//Query stuff
	
    public static Model.Finder<Long,Team> find = new Model.Finder(Long.class, Team.class);
    
    public static List<Team> findAll() {
        return find.all();
    }
    
    public static void create(Team newTeam){
    	newTeam.save();
    }
    
    public static void create(String teamName, String project){
    	Team t = new Team(teamName, project);
    	t.save();
    }

	public static void addVote(Long team, Long vote) {
        Team t = Team.find.ref(team);
        t.votes.add(
            Vote.find.ref(vote)
        );
        t.save();
    }
	
	public static List<Vote> getVotesForTeam(Long id){
		Team t = Team.find.ref(id);
		return t.votes;
	}
	
}