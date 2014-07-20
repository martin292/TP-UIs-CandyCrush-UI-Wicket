package candycrushja.candycrush_ui_web_wicket;

import mundo.Jugador;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import appModel.MundoAppModel;
import appModel.PartidaAppModel;

public class PartidaGanadaPage extends PartidaTerminadaPage {

	public PartidaGanadaPage(PartidaAppModel partida, MundoPage mundo){
		super(partida, mundo);
		this.addSiguienteNivel();
	}
	
	public void addSiguienteNivel(){
		final MundoPage mundoPage = this.getMundo();
		final MundoAppModel mundoAppModel = (MundoAppModel) mundoPage.getDefaultModelObject();
		final Jugador jugador = mundoAppModel.getJugador();
		this.add(new Link("siguienteNivel") {
			public void onClick() {
				PartidaAppModel nuevaPartida = new PartidaAppModel(
						jugador.ultimoNivelDisponible(), jugador.getDificultadSeleccionada());
				this.setResponsePage(new PartidaPage(nuevaPartida, mundoPage));
			}
		});
	}
}
