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
        return ok(index.render("Your new application is ready."));
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
    	return TODO;
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
