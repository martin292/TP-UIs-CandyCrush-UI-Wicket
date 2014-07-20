package candycrushja.candycrush_ui_web_wicket;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import mundo.Jugador;
import mundo.Nivel;
import mundo.NivelParaJugar;

import objetivos.Regular;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import appModel.PartidaAppModel;

import dificultad.Dificultad;

import repos.MundoFactory;

public class EditorPage extends WebPage {

	// ************************************************************************
	// * Variables
	// ************************************************************************
	
	private static final long serialVersionUID = 1L;
	
	private Jugador usuario;//Modelo
	public MundoPage mp;
	
	private List<Nivel> niveles = new ArrayList<Nivel>();
	
	// ************************************************************************
	// * Constructor
	// ************************************************************************
	
	public EditorPage(MundoPage mp, Jugador usuario){

		this.usuario = usuario;
		this.mp = mp;
		this.niveles = usuario.getNivelesPropios();
		
		this.add(new Label("jugador", new PropertyModel<String>(this.usuario, "nombre")));
		
		Form<Jugador> form = new Form<Jugador>("formEditor", new CompoundPropertyModel<Jugador>(this.usuario));
		this.agregarGrillaNiveles(form);
		this.agregarBotones(form);
		
		this.add(form);
	}

	
	// ************************************************************************
	// * Metodos
	// ************************************************************************
	
	private void agregarBotones(Form<Jugador> form) {
		Button agregar = new Button("agregar"){
	    	@Override
	    	public void onSubmit() {
	    		EditorPage.this.agregarNivel();
		    }
		};
		form.add(agregar);
		
		Button salir = new Button("salir"){
			@Override
	    	public void onSubmit() {				
				EditorPage.this.salirDelEditor();
		    }
		};
		form.add(salir);
	}

	private void agregarGrillaNiveles(Form<Jugador> form) {
		form.add(new PropertyListView<Nivel>("nivelesPropios") {
			@Override
			protected void populateItem(final ListItem<Nivel> item) {
				item.add(new Label("nombre"));
						
				item.add(new Button("editar") {
					@Override
					public void onSubmit() {
						EditorPage.this.editar(item.getModelObject());
					}
				});
				item.add(new Button("borrar") {
					@Override
					public void onSubmit() {
						EditorPage.this.borrar(item.getModelObject());
					}
				});
				item.add(new Button("jugar") {
					@Override
					public void onSubmit() {
						EditorPage.this.jugar(item.getModelObject());
					}
				});
			}
		});
	}

	//Metodos auxiliares**********************************************************************

	private void agregarNivel(){
		Nivel nuevoNivel = new Nivel();
		
		Point p = new Point(3, 3);
		
		nuevoNivel.setNombre("NuevoNivel");
		nuevoNivel.setDimensionTablero(p);
		nuevoNivel.setMovimientosDisponibles(100);
		nuevoNivel.agregarObjetivo(new Regular(100, 100));
		
		this.usuario.agregarNivel(nuevoNivel);
				
		this.setResponsePage(new ConfigNivelPage(nuevoNivel, this.usuario, this));		
	}
	
	private void salirDelEditor(){
		this.setResponsePage(this.mp);
	}
	
	private void editar(Nivel n){
		this.setResponsePage(new ConfigNivelPage(n, this.usuario, this));
	}
	
	private void borrar(Nivel n){
		this.usuario.eliminarNivelPropio(n.getNombre());
		this.setResponsePage(new EditorPage(this.mp, this.usuario));
	}
	
	private void jugar(Nivel n){
		//TODO 
		NivelParaJugar nivel = new NivelParaJugar(this.usuario, n, true);
		PartidaAppModel p = new PartidaAppModel(nivel, this.usuario.getDificultadSeleccionada());
		
		this.setResponsePage(new PartidaPage(p, this.mp));		
	}
	
}
