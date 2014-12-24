package playn.quiz.core.util;

import static playn.core.PlayN.*;
import playn.core.PlayN;

public class Begriffssystematik {

	boolean initialized = false;

	private static Pol[][] pol;

	public static Pol[] getBegriffspaar(int r, ResourceBundle rB) {
		init(rB);
		
		Pol[] tmp = pol[r];

		// vertausche evtl. Seiten
		if (PlayN.random() < 0.5f) {
			Pol[] tmp2 = new Pol[2];
			tmp2[0] = tmp[1];
			tmp2[1] = tmp[0];
			return tmp2;
		}

		return tmp;

	}

	private static void init(ResourceBundle rB) {

		pol = new Pol[5][2];

		/*
		 * linear vs. malerisch
		 */
		Pol linear = new Pol(rB.string("LINEAR"),"Linear.jpg");
		linear.addStichpunkt(rB.string("LINEAR_S1"));
		linear.addStichpunkt(rB.string("LINEAR_S2"));
		linear.addStichpunkt(rB.string("LINEAR_S3"));

		pol[0][0] = linear;

		Pol malerisch = new Pol(rB.string("MALERISCH"), "Malerisch.jpg");
		malerisch.addStichpunkt(rB.string("MALERISCH_S1"));
		malerisch.addStichpunkt(rB.string("MALERISCH_S2"));
		malerisch.addStichpunkt(rB.string("MALERISCH_S3"));

		pol[0][1] = malerisch;

		/*
		 * Flaeche vs. Tiefe
		 */

		Pol flaeche = new Pol(rB.string("FLAECHE"),"Flaeche.jpg");
		flaeche.addStichpunkt(rB.string("FLAECHE_S1"));
		flaeche.addStichpunkt(rB.string("FLAECHE_S2"));

		pol[1][0] = flaeche;

		Pol tiefe = new Pol(rB.string("TIEFE"),"Tiefe.jpg");
		tiefe.addStichpunkt(rB.string("TIEFE_S1"));
		tiefe.addStichpunkt(rB.string("TIEFE_S2"));
		tiefe.addStichpunkt(rB.string("TIEFE_S3"));

		pol[1][1] = tiefe;

		/*
		 * Geschlossen vs. offene Form
		 */

		Pol geschlossen = new Pol(rB.string("GESCHLOSSEN"),"Geschlossene Form.jpg");
		geschlossen.addStichpunkt(rB.string("GESCHLOSSEN_S1"));
		geschlossen.addStichpunkt(rB.string("GESCHLOSSEN_S2"));
		geschlossen.addStichpunkt(rB.string("GESCHLOSSEN_S3"));
		geschlossen.addStichpunkt(rB.string("GESCHLOSSEN_S4"));

		pol[2][0] = geschlossen;

		Pol offen = new Pol(rB.string("OFFEN"),"Offene Form.jpg");
		offen.addStichpunkt(rB.string("OFFEN_S1"));
		offen.addStichpunkt(rB.string("OFFEN_S2"));
		offen.addStichpunkt(rB.string("OFFEN_S3"));
		offen.addStichpunkt(rB.string("OFFEN_S4"));

		pol[2][1] = offen;

		/*
		 * Vielheit vs. Einheit
		 */

		Pol vielheit = new Pol(rB.string("VIELHEIT"), "Vielheit.jpg");
		vielheit.addStichpunkt(rB.string("VIELHEIT_S1"));
		vielheit.addStichpunkt(rB.string("VIELHEIT_S2"));
		pol[3][0] = vielheit;

		Pol einheit = new Pol(rB.string("EINHEIT"), "Einheit.jpg");
		einheit.addStichpunkt(rB.string("EINHEIT_S1"));
		einheit.addStichpunkt(rB.string("EINHEIT_S2"));

		pol[3][1] = einheit;

		/*
		 * Klarheit vs. Unklarheit
		 */

		Pol klarheit = new Pol(rB.string("KLARHEIT"), "Klarheit.jpg");
		klarheit.addStichpunkt(rB.string("KLARHEIT_S1"));
		klarheit.addStichpunkt(rB.string("KLARHEIT_S2"));
		pol[4][0] = klarheit;

		Pol unklarheit = new Pol(rB.string("UNKLARHEIT"), "Unklarheit.jpg");
		unklarheit.addStichpunkt(rB.string("UNKLARHEIT_S1"));
		unklarheit.addStichpunkt(rB.string("UNKLARHEIT_S2"));

		pol[4][1] = unklarheit;

	}

}
