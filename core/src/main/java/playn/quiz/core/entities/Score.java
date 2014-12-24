package playn.quiz.core.entities;

import static playn.core.PlayN.*;

public class Score {

	private int punkteAnzahl;
	private int joker;
	private int frage;

	public Score(int punkteAnzahl, int joker, int frage) {
		this.setPunkteAnzahl(punkteAnzahl);
		this.setJoker(joker);
		this.setFrage(frage);

	}

	public int getPunkteAnzahl() {
		return punkteAnzahl;
	}

	public void setPunkteAnzahl(int punkteAnzahl) {
		this.punkteAnzahl = punkteAnzahl;
	}

	public int getJoker() {
		return joker;
	}

	public void setJoker(int joker) {
		this.joker = joker;
	}

	public int getFrage() {
		return frage;
	}

	public void setFrage(int frage) {
		this.frage = frage;
	}

}
