package playn.quiz.core.util;

import static playn.core.PlayN.*;
import static playn.core.PlayN.assets;

import java.util.HashMap;

import playn.core.PlayN;
import playn.core.util.Callback;
import playn.quiz.core.Menu;

/**
 * Eigene Implementierung von ResourceBundle
 * 
 * @author Jonas
 * 
 */
public class ResourceBundle {

	HashMap<String, String> hashMap = new HashMap<String, String>();
	private Menu menu;

	/**
	 * Erstelle mit "Label_de.properties" oder "Label_en.properties
	 * 
	 * @param lang
	 * @param menu
	 * @param helloGame
	 */
	public ResourceBundle(String lang, final Menu menu) {
		this.menu = menu;

		assets().getText(lang, new Callback<String>() {
			@Override
			public void onSuccess(String text) {
				String[] lines = text.split("\\r?\\n");

				for (String line : lines) {
					loadProperties(line);
				}
				menu.loadAssets();
			}

			private void loadProperties(String line) {
				String[] tmp = line.split("=");
				hashMap.put(tmp[0], tmp[1]);
			}

			@Override
			public void onFailure(Throwable err) {
				log().error("Error loading Sprachdatei", err);
			}
		});

	}

	public String string(String identifier) {
		String result = hashMap.get(identifier);
		if (result == null) {
			PlayN.log().error("Bad Identifier: " + identifier);
		}
		return result;
	}

}
