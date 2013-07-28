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
	
	public static void openVoting(Long competition, Long team){
		Competition c = find.ref(competition);
		//close voting for all other teams in competition
		for(Team t : c.teams){
			if(t.votingOpen){
				Team tm = Team.find.ref(t.id);
				tm.votingOpen = false;
				tm.save();
			}
		}
		//open voting for the new team
		Team open = Team.find.ref(team);
		open.votingOpen = true;
		open.save();
		
	}
	
	public static void closeVoting(Long competition){
		Competition c = find.ref(competition);
		//close voting for all other teams in competition
		for(Team t : c.teams){
			if(t.votingOpen){
				Team tm = Team.find.ref(t.id);
				tm.votingOpen = false;
				tm.save();
			}
		}
	}
	
	public static void castVote(Long competition, Vote vote){
		Competition c = find.ref(competition);
		for(Team t : c.teams){
			if(t.votingOpen){
				Team tm = Team.find.ref(t.id);
				Vote v = Vote.find.ref(vote.id);
				v.team = tm;
				tm.votes.add(v);
				tm.save();
				v.save();
				System.out.println("Vote cast for team: " + tm.teamName + " vote value: " + v.vote);
			}
		}
	}
	
	public static List<Team> getTeams(Long competition){
		Competition c = find.ref(competition);
		return c.teams;
	}
	
	public static List<Competition> findAll() {
        return find.all();
    }
	
}