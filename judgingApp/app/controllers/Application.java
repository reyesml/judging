package controllers;

import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import models.*;

public class Application extends Controller {
	
	static Form<Competition> compForm = Form.form(Competition.class);
  
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
  
    
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
}
