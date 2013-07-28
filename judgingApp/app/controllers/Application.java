package controllers;

import java.util.List;

import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import models.*;

public class Application extends Controller {
	
	static Form<Competition> compForm = Form.form(Competition.class);
	static Form<Team> teamForm = Form.form(Team.class);
	
    public static Result index() {
    	List<Competition> c = Competition.findAll();
        return ok(index.render(c));
    }
  
    /**
     * Competition
     * @return
     */
    public static Result competitions(){
    	return ok(views.html.competitions.render(Competition.findAll(), compForm));
    }
    
    public static Result newCompetition(){
    	Form<Competition> filledForm = compForm.bindFromRequest();
    	if(filledForm.hasErrors()) {
    		return badRequest(
    				views.html.competitions.render(Competition.findAll(), filledForm)
    				);
    	} else {
    		Competition.create(filledForm.get());
    		return redirect(routes.Application.competitions());  
    	}
    }
    
    public static Result viewCompetition(Long compId){
    	Competition c = Competition.find.ref(compId);
    	List<Team> teams = Team.findAll();
    	
    	return ok(views.html.compDetails.render(c, teams));
    }
    
    public static Result enrollTeam(Long compId){
    	Competition c = Competition.find.ref(compId);
    	DynamicForm requestData = Form.form().bindFromRequest();
    	String teamIdString = requestData.get("teamId");
    	
    	Long teamId = Long.parseLong(teamIdString);
    	//Team t = Team.find.ref(teamId);
    	System.out.println("Team id: " + teamId);
    	Competition.addTeam(compId, teamId);
    	
    	List<Team> teams = Team.findAll();
    	
    	System.out.println("Teams competing: " + c.teams.size());
    	return ok(views.html.compDetails.render(c, teams));
    }
    
    public static Result openVoting(Long compId, Long teamId){
    	Competition c = Competition.find.ref(compId);
    	Team t = Team.find.ref(teamId);
    	List<Team> teams = Team.findAll();
    	Competition.openVoting(compId, teamId);
    	
    	return ok(views.html.compDetails.render(c, teams));
    }
    
    public static Result closeVoting(Long compId){
    	
    	Competition c = Competition.find.ref(compId);
    	List<Team> teams = Team.findAll();
    	Competition.closeVoting(compId);
    	return ok(views.html.compDetails.render(c, teams));
    	
    }
    
    
    
    public static Result castVote(Long compId){
    	Competition c = Competition.find.ref(compId);
    	DynamicForm requestData = Form.form().bindFromRequest();
    	String voteResult = requestData.get("vote");
    	if(voteResult != null){
	    	Vote v = Vote.create(0);
	    	if(voteResult.equalsIgnoreCase("upvote")){
	    		v.vote = 1;
	    	}else if(voteResult.equalsIgnoreCase("downvote")){
	    		v.vote = -1;
	    	}
	    	v.save();
	    	Competition.castVote(compId, v);
    	}
    	return ok(views.html.vote.render(c));	
    }
    
    public static Result teamInfo(Long teamId){
    	Team t = Team.find.ref(teamId);
    	return ok(views.html.teamDetails.render(t));
    }
    
    public static Result results(Long compId){
    	Competition c = Competition.find.ref(compId);
    	return ok(views.html.compResults.render(c));
    }
    
    
    /**
     * Team
     * @return
     */
    public static Result teams(){
    	return ok(views.html.teams.render(Team.findAll(), teamForm));
    }
    
    public static Result newTeam(){
    	Form<Team> filledForm = teamForm.bindFromRequest();
    	if(filledForm.hasErrors()) {
    		return badRequest(
    				views.html.teams.render(Team.findAll(), filledForm)
    				);
    	} else {
    		Team.create(filledForm.get());
    		return redirect(routes.Application.teams());  
    	}
    }
    
    
}
