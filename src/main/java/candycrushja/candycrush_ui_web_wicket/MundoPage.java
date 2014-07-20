package candycrushja.candycrush_ui_web_wicket;

import java.util.List;

import mundo.Jugador;
import mundo.NivelParaJugar;
import partida.Partida;

import appModel.MundoAppModel;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

public class MundoPage extends WebPage {

	protected static final long serialVersionUID = 1L;
	
	protected final HomePage home;
	
	public MundoPage(HomePage home, Jugador jugador){
		this.setDefaultModel(new CompoundPropertyModel<MundoAppModel>(new MundoAppModel(jugador)));
		this.home = home;
		this.addProfile();
		Form<Partida> nivelesForm = new Form<Partida>("nivelesForm");
		this.add(nivelesForm);
		this.agregarNiveles(nivelesForm);
		this.agregarAccionesDeNivel(nivelesForm);
		this.addActions();
		this.agregarDificultades();
	}

	public void addProfile(){
		this.add(new Label("jugador.nombre"));
		this.add(new Label("jugador.puntaje"));
		this.add(new Label("jugador.dificultadSeleccionada"));
	}
	
	public void agregarNiveles(Form<Partida> form){
		MundoAppModel appModel = (MundoAppModel) this.getDefaultModelObject();
		form.add(new DropDownChoice<NivelParaJugar>("niveles", 
                new PropertyModel<NivelParaJugar>(appModel, "nivelSeleccionado"),
                this.crearListaNiveles()));
		form.add(new Label("feedback"));
	}
	
	protected LoadableDetachableModel<List<NivelParaJugar>> crearListaNiveles(){
		final MundoAppModel appModel = (MundoAppModel) this.getDefaultModelObject();
		return new LoadableDetachableModel<List<NivelParaJugar>>() {
			@Override
			protected List<NivelParaJugar> load() {
				return appModel.nivelesDisponibles();
			}
		};
	}
	
	public void agregarAccionesDeNivel(Form<Partida> form){
		final MundoAppModel appModel = (MundoAppModel) this.getDefaultModelObject();
		
		form.add(new Button("jugarNivel") {
			@Override
			public void onSubmit(){
				if(appModel.nivelSeleccionado()){
					this.setResponsePage(new PartidaPage(appModel.armarPartida(), MundoPage.this));
				}
			}
		});
	}
	
	public void addActions(){
		final MundoAppModel appModel = (MundoAppModel) this.getDefaultModelObject();
		
		this.add(new Link("abrirEditor"){
			public void onClick(){
				EditorPage editor = new EditorPage(MundoPage.this, appModel.getJugador());
				this.setResponsePage(editor);
			}
		});
		
		this.add(new Button("reiniciarJuego") {
			@Override
			public void onSubmit(){
				appModel.reiniciarJuego();
			}
		});
		
		this.add(new Link("salir"){
			public void onClick(){
				this.setResponsePage(MundoPage.this.getHome());
			}
		});
	}
	
	public void agregarDificultades(){
		final MundoAppModel appModel = (MundoAppModel) this.getDefaultModelObject();
		
		this.add(new Link("facil"){
			public void onClick(){
				appModel.seleccionarDificultadFacil();
				this.setResponsePage(MundoPage.this);
			}
		});
		
		this.add(new Link("medio"){
			public void onClick(){
				appModel.seleccionarDificultadMedia();
				this.setResponsePage(MundoPage.this);
			}
		});
		
		this.add(new Link("dificil"){
			public void onClick(){
				appModel.seleccionarDificultadDificil();
				this.setResponsePage(MundoPage.this);
			}
		});
	}

	public HomePage getHome() {return home;}

}
