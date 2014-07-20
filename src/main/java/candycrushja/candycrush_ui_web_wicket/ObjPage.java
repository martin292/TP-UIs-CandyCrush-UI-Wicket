package candycrushja.candycrush_ui_web_wicket;

import java.util.List;

import mundo.Jugador;
import mundo.Nivel;
import objetivos.Objetivo;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

import dificultad.Dificultad;
import dulces.Dulce;

public class ObjPage extends WebPage {

	// ************************************************************************
	// * Variables
	// ************************************************************************
	
	private static final long serialVersionUID = 1L;
	
	private Objetivo obj; //Modelo
	private Jugador usuario;
	private Nivel nivel;
	
	private ConfigNivelPage cnp;
	
	private String mensage = " ";
	
	// ************************************************************************
	// * Constructor
	// ************************************************************************
	
	public ObjPage(ConfigNivelPage cnp, Objetivo o, Nivel n, Jugador j){
		
		this.obj = o;
		this.usuario = j;
		this.nivel = n;		
		this.cnp = cnp;
		
		this.add(new Label("jugador", new PropertyModel<String>(this.usuario, "nombre")));
		
		Form<Objetivo> form = new Form<Objetivo>("formObjetivo", new CompoundPropertyModel<Objetivo>(this.obj));
		
		this.agregarSelector(form);
		form.add(new TextField<String>("cantidad"));
		
		this.crearBotones(form);
		
		PropertyModel<String> msg = new PropertyModel<String>(this, "mensage");
		form.add(new Label("mensage", msg));
		
		this.add(form);		
	}
	
	// ************************************************************************
	// * Metodos
	// ************************************************************************
	
	private void agregarSelector(Form<Objetivo> form) {
		form.add(new DropDownChoice<Dulce>("colores", new PropertyModel<Dulce>(this.obj, "color"), 
		crearListaDeDulces()));
	}
	protected LoadableDetachableModel<List<Dulce>> crearListaDeDulces(){
		return new LoadableDetachableModel<List<Dulce>>() {
			@Override
			protected List<Dulce> load() {
				return Dulce.getDulces();
			}
		};
	}
	
	private void crearBotones(Form<Objetivo> form){
		this.agregarBotonAceptarObj(form);
		//this.agregarBotonCancelarObj(form);
	}
	
	
	private void agregarBotonCancelarObj(Form<Objetivo> form) {
		Button boton = new Button("cancelar") {
	    	@Override
	    	public void onSubmit() {
	    		ObjPage.this.cancelar();
	    		
		    }
		};
		form.add(boton);
	}
	
	private void agregarBotonAceptarObj(Form<Objetivo> form) {
		Button boton = new Button("aceptar") {
	    	@Override
	    	public void onSubmit() {
	    		ObjPage.this.aceptar();
		    }
		};
		form.add(boton);		
	}
	
	private void cancelar(){
		if(!this.hayDatosInvalidos()){
			this.setResponsePage(this.cnp);
		}else{
			this.mensage = "Hay datos invalidos";
		}
	}
	
	private void aceptar(){
		if(!this.hayDatosInvalidos()){
			this.setResponsePage(new ConfigNivelPage(this.nivel, this.usuario, this.cnp.ep));
		}else{
			this.mensage = "Hay datos invalidos";
		}
	}
	
	private boolean hayDatosInvalidos(){
		return this.obj.getCantidad() < 3;
	}
	
}
