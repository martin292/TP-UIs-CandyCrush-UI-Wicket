package candycrushja.candycrush_ui_web_wicket;

import java.util.ArrayList;
import java.util.List;

import mundo.Jugador;
import mundo.Nivel;
import objetivos.ExplosionesPorColor;
import objetivos.GrandesExplosiones;
import objetivos.Objetivo;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

import dulces.Dulce;

public class ConfigNivelPage extends WebPage {
	
	// ************************************************************************
	// * Variables
	// ************************************************************************

	private static final long serialVersionUID = 1L;
	
	private Jugador usuario;
	private Nivel nivel;//Modelo
	public EditorPage ep;
	
	private String mensage = " ";

	
	// ************************************************************************
	// * Constructor
	// ************************************************************************
	
	public ConfigNivelPage(Nivel lvl, Jugador user, EditorPage editorPage) {
		
		this.iniciar(lvl, user, editorPage);
		
		this.add(new Label("jugador", new PropertyModel<String>(this.usuario, "nombre")));
		
		Form<Nivel> form = new Form<Nivel>("formNivel", new CompoundPropertyModel<Nivel>(this.nivel));
		
		this.agregarCamposDelNivel(form);
		
		this.agregarGrillaObjetivos(form);
		
		this.agregarBotonGExp(form);
		this.agregarBotonExpC(form);
		
		this.agregarBotonAceptarNivel(form);
		//this.agregarBotonCancelarNivel(form);
		
		PropertyModel<String> msg = new PropertyModel<String>(this, "mensage");
		form.add(new Label("mensage", msg));
		
		this.add(form);
	}

	// ************************************************************************
	// * Metodos
	// ************************************************************************
	
	private void iniciar(Nivel lvl, Jugador user, EditorPage editorPage){
		this.usuario = user;
		this.nivel = lvl;
		this.ep = editorPage;
	}
		
	private void agregarBotonCancelarNivel(Form<Nivel> form) {
		Button boton = new Button("cancelarNivel") {
	    	@Override
	    	public void onSubmit() {
	    			ConfigNivelPage.this.cancelarNivel();
		    }
		};
		form.add(boton);		
	}

	private void agregarBotonAceptarNivel(Form<Nivel> form) {
		Button boton = new Button("aceptarNivel") {
	    	@Override
	    	public void onSubmit() {
	    		ConfigNivelPage.this.aceptarNivel();
		    }
		};
		form.add(boton);		
	}


	private void agregarBotonExpC(Form<Nivel> form) {
		Button boton = new Button("expCol") {
	    	@Override
	    	public void onSubmit() {
	    		ConfigNivelPage.this.agregarExplosionPorColor();
		    }
		};
		form.add(boton);
	}

	private void agregarBotonGExp(Form<Nivel> form) {
		Button boton = new Button("gExp") {
	    	@Override
	    	public void onSubmit() {
	    		ConfigNivelPage.this.agregarGranExplosion();
		    }
		};
		form.add(boton);		
	}

	private void agregarCamposDelNivel(Form<Nivel> form) {
		form.add(new TextField<String>("nombre"));
		form.add(new TextField<String>("filas"));
		form.add(new TextField<String>("columnas"));
		form.add(new TextField<String>("movimientosDisponibles"));		
	}


	private void agregarGrillaObjetivos(Form<Nivel> form) {
		form.add(new PropertyListView<Objetivo>("objetivos"){
			@Override
			protected void populateItem(final ListItem<Objetivo> item) {
				item.add(new Label("nombre"));
						
				item.add(new Button("editar") {
					@Override
					public void onSubmit() {
						//editar el obj
						ConfigNivelPage.this.editarObj(item.getModelObject());
					}
				});
				item.add(new Button("borrar") {
					@Override
					public void onSubmit() {
						//borrar el obj
						ConfigNivelPage.this.borrarObj(item.getModelObject());
					}
				});
			}
		});		
	}
	
	//Metodos auxiliares************************************************************

	private void aceptarNivel(){
		if(!hayDatosInvalidos()){
			this.setResponsePage(new EditorPage(this.ep.mp, this.usuario));
		}else{
			this.mensage = "Hay datos invalidos";
		}
	}
	
	private void cancelarNivel(){
		if(!hayDatosInvalidos()){
			this.setResponsePage(this.ep);
		}else{
			this.mensage = "Hay datos invalidos";
		}
	}
	
	private void agregarExplosionPorColor(){ 
		ExplosionesPorColor obj = new ExplosionesPorColor(Dulce.getRojo(), 3);
		
		this.nivel.agregarObjetivo(obj);
		
		this.setResponsePage(new ObjPage(this, obj, this.nivel, this.usuario));
	}
	
	private void agregarGranExplosion(){
		GrandesExplosiones obj = new GrandesExplosiones(Dulce.getRojo(), 3);
		
		this.nivel.agregarObjetivo(obj);
		
		this.setResponsePage(new ObjPage(this, obj, this.nivel, this.usuario));
	}
	
	private void editarObj(Objetivo obj){
		this.setResponsePage(new ObjPage(this, obj, this.nivel, this.usuario));
	}
	
	private void borrarObj(Objetivo obj){
		this.nivel.eliminarObjetivo(obj.getNombre());
		this.setResponsePage(new ConfigNivelPage(this.nivel, this.usuario, this.ep));
	}
	
	private boolean hayDatosInvalidos(){
		return (this.nivel.getFilas() < 2) || (this.nivel.getColumnas() < 2);
	}
		
}
