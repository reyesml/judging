package models;

import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;



@Entity
public class Vote extends Model{
	
	@Id
	public Long id;
	
	public int vote = 0;
	
	public Date date;
	
	public Vote(int vote){
		this.date = new Date();
		this.vote = vote;
	}
	
	//Query stuff
	public static Model.Finder<Long,Vote> find = new Model.Finder(Long.class, Vote.class);
	
	public static Vote create(int theVote) {
        Vote vote = new Vote(theVote);
        vote.save();
        return vote;
    }
	
	
}