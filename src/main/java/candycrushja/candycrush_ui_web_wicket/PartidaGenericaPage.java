package candycrushja.candycrush_ui_web_wicket;

import objetivos.Objetivo;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;

import appModel.PartidaAppModel;

public class PartidaGenericaPage extends WebPage {
	
	protected static final long serialVersionUID = 1L;

	protected MundoPage mundo;
	
	public PartidaGenericaPage(PartidaAppModel partida, MundoPage mundo) {
		this.mundo = mundo;
		this.setDefaultModel(new CompoundPropertyModel<PartidaAppModel>(partida));
		this.addProfile();
		this.addActions();
		this.addObjetives();
	}
	
	public void addProfile() {
		this.add(new Label("puntosAlcanzados"));
		this.add(new Label("dificultad"));
		this.add(new Label("nivelParaJugar"));
	}
	
	public void addActions() {
		this.add(new Link("reiniciar") {
			public void onClick() {
				PartidaAppModel partida = (PartidaAppModel) PartidaGenericaPage.this
						.getDefaultModelObject();
				partida.reiniciar();
				this.setResponsePage(new PartidaPage(partida, PartidaGenericaPage.this
						.getMundo()));
			}
		});

		this.add(new Link("salir") {
			public void onClick() {
				this.setResponsePage(PartidaGenericaPage.this.getMundo());
			}
		});
	}

	protected void addObjetives() {
		this.add(new PropertyListView<Objetivo>("objetivos") {
			@Override
			protected void populateItem(final ListItem<Objetivo> item) {
				item.add(new Label("objetivoACumplir"));
				item.add(new Label("estado"));
			}
		});
	}
	
	public MundoPage getMundo(){return mundo;}
	public void setMundo(MundoPage mundo){this.mundo = mundo;}

}