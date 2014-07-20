package candycrushja.candycrush_ui_web_wicket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import repos.MundoFactory;

import mundo.Jugador;

public class HomePageModel implements Serializable{

	// ************************************************************************
	// * Variables
	// ************************************************************************
	
	private static final long serialVersionUID = 1L;
	
	private List<Jugador> jugadores = new ArrayList<Jugador>();
	private Jugador jugador;
	private String nombre = "Jorge";
	private String message = "Ingrese su nombre";
	
	// ************************************************************************
	// * Accessors
	// ************************************************************************	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	// ************************************************************************
	// * Constructor
	// ************************************************************************	

	public HomePageModel(){
				
		this.jugadores = MundoFactory.getInstance().getJugadores();
		this.jugador = null;
		
	}
	
	// ************************************************************************
	// * Metodos
	// ************************************************************************
	
	public void logearse(HomePage hp){
		if(this.elUsuarioExiste(this.getNombre())){
            this.jugador = this.jugadorSeleccionado(this.getNombre());
            hp.setResponsePage(new MundoPage(hp, this.jugador));
		}else{                        
			this.setMessage("El usuario no existe");
		}
	}
	
	public void registrarse(HomePage hp){
		if(this.elUsuarioExiste(this.getNombre())){
			this.setMessage("El usuario ya existe");
		}else{
			Jugador j = new Jugador(this.getNombre());
			this.jugadores.add(j);
			this.jugador = j;
			hp.setResponsePage(new MundoPage(hp, this.jugador));
		}
	}
	
	
	//Metodos auxiliares-------------------------------------------
	
	private Boolean elUsuarioExiste(String nom){
        return this.buscarUsuario(nom);
	}
	private Boolean buscarUsuario(String nom) {
        return MundoFactory.getInstance().buscarUsuario(nom);
	}
	
	private Jugador jugadorSeleccionado(String nom) {
        return MundoFactory.getInstance().retJugador(nom);
	}
	
	
}
