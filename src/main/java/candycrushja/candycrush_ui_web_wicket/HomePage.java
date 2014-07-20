package candycrushja.candycrush_ui_web_wicket;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;


public class HomePage extends WebPage {
	
	// ************************************************************************
	// * Variables
	// ************************************************************************
	
	private static final long serialVersionUID = 1L;
	
	private HomePageModel modelo = new HomePageModel();


	// ************************************************************************
	// * Constructor
	// ************************************************************************
	
	public HomePage() {
		
		Form<HomePageModel> form = new Form<HomePageModel>("homePageform", new CompoundPropertyModel<HomePageModel>(this.modelo));
		
		form.add(new TextField<String>("nombre"));

		this.agregarBotonLogearse(form);
		this.agregarBotonRegistrarse(form);
		
		PropertyModel<String> msg = new PropertyModel<String>(this.modelo, "message");
		form.add(new Label("feedback", msg));
		
		this.add(form);		
    }
	
	
	// ************************************************************************
	// * Metodos
	// ************************************************************************
		
	public void agregarBotonRegistrarse(Form<HomePageModel> f){
	    Button boton = new Button("registrarse") {
	    	@Override
	    	public void onSubmit() {
	    		HomePage.this.modelo.registrarse(HomePage.this);
		    }
		};
		f.add(boton);
	}
	public void agregarBotonLogearse(Form<HomePageModel> f){
		Button boton = new Button("logearse") {
			@Override
	    	public void onSubmit() {
				HomePage.this.modelo.logearse(HomePage.this);
		    }
		};
		f.add(boton);
	}
		
}
