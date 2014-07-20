package candycrushja.candycrush_ui_web_wicket;

import mundo.JuegoTerminadoException;
import partida.Celda;
import partida.PartidaGanadaException;
import partida.PartidaPerdidaException;
import partida.PartidaPersonalizadaGanadaException;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.Model;

import appModel.Fila;
import appModel.Log;
import appModel.PartidaAppModel;

public class PartidaPage extends PartidaGenericaPage {

	public PartidaPage(PartidaAppModel partida, MundoPage mundo) {
		super(partida, mundo);
		this.addMovimientosRestantes();
		this.addDebugs();
		this.addBoard();
		this.addLog();
	}
	
	protected void addMovimientosRestantes(){
		this.add(new Label("movimientosRestantes"));
	}

	public void addDebugs() {
		this.add(new Label("fila"));
		this.add(new Label("columna"));
		this.add(new Label("representacionVisual"));
	}

	protected void addBoard() {
		this.add(new PropertyListView<Fila>("filas") {
			@Override
			protected void populateItem(final ListItem<Fila> item) {
				agregarElementosDeFila(item);
			}
		});
	}

	protected void agregarElementosDeFila(ListItem<Fila> fila) {
		final PartidaAppModel partida = (PartidaAppModel) this.getDefaultModelObject();
		fila.add(new ListView<Celda>("celdas") {
			@Override
			protected void populateItem(final ListItem<Celda> listItem) {
				final Celda celda = listItem.getModelObject();
				final Link<Void> link = new Link<Void>("celda") {
					@Override
					public void onClick() {
						try {
							partida.elegirCelda(celda);
							// TODO
						} catch (PartidaPersonalizadaGanadaException e) {
							this.setResponsePage(new PartidaGanadaPersonalizadaPage(
									(PartidaAppModel) PartidaPage.this.getDefaultModelObject(), 
									PartidaPage.this.getMundo()));
						} catch (JuegoTerminadoException e) {
							this.setResponsePage(new PartidaGanadaPersonalizadaPage(
									(PartidaAppModel) PartidaPage.this.getDefaultModelObject(), 
									PartidaPage.this.getMundo()));
						} catch (PartidaGanadaException e) {
							this.setResponsePage(new PartidaGanadaPage(
									(PartidaAppModel) PartidaPage.this.getDefaultModelObject(), 
									PartidaPage.this.getMundo()));
						} catch (PartidaPerdidaException e) {
							this.setResponsePage(new PartidaPerdidaPage(
									(PartidaAppModel) PartidaPage.this.getDefaultModelObject(), 
									PartidaPage.this.getMundo()));
						}
					}
				};

				link.add(new Image("image", new Model<String>("images/"
						+ celda.getDulce() + ".png")));
				listItem.add(link);
			}
		});
	}
	
	protected void addLog(){
		this.add(new PropertyListView<Log>("logs") {
			@Override
			protected void populateItem(final ListItem<Log> item) {
				item.add(new Label("mensaje"));
			}
		});
	}

}
