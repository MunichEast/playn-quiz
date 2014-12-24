/**
 * Copyright 2011 The ForPlay Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package playn.quiz.core.entities;

import static playn.core.PlayN.*;
import static playn.core.PlayN.pointer;
import playn.core.AssetWatcher;
import playn.core.CanvasImage;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Json;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.quiz.core.Quiz;
import playn.quiz.core.Screen;
import playn.quiz.core.util.Color;
import playn.quiz.core.util.QuizSimpleStyles;
import playn.quiz.core.util.QuizStyles;
import react.UnitSlot;
import tripleplay.ui.Button;
import tripleplay.ui.Field;
import tripleplay.ui.Group;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Shim;
import tripleplay.ui.Stylesheet;
import tripleplay.ui.Stylesheet.Builder;
import tripleplay.ui.layout.AxisLayout;

/**
 * A demo that displays a menu of the available demos.
 */
public class HighScoreScreen extends Screen {

	private final Quiz quiz;
	private Interface iface;
	private GroupLayer layer;

	private Root evaluationRoot;

	private Field enterNameField;

	private Score score;
	private Image bgImage;
	private Group scoreGroup;
	private ImageLayer bgLayer;

	public HighScoreScreen(Quiz quiz) {
		this.quiz = quiz;
	}

	@Override
	public String name() {
		return "Highscore-Screen";
	}

	@Override
	public void init() {
		iface = new Interface();
		Builder builder = Stylesheet.builder();
		builder.add(Button.class, QuizStyles.FONT_STYLE_18);
		builder.add(Field.class, QuizStyles.FONTSTYLE17);
		builder.add(Label.class, QuizStyles.FONT_STYLE_18);
		layer = graphics().rootLayer();
		this.evaluationRoot = iface.createRoot(AxisLayout.vertical().gap(10),
				QuizSimpleStyles.newSheet());

		evaluationRoot.setSize(300, 590);
		evaluationRoot.layer.setTranslation(250, 10);

		// load and show our background
		int width = 800, height = 600;
		this.bgLayer = PlayN.graphics().createImageLayer();
		CanvasImage canvasImage = PlayN.graphics().createImage(width, height);
		canvasImage.canvas().setFillColor(Color.BLACK);
		canvasImage.canvas().fillRect(0, 0, width, height);
		canvasImage.canvas().setFillColor(Color.BGCOLOR);
		canvasImage.canvas().fillRect(1, 1, width - 2, height - 2);
		bgLayer.setImage(canvasImage);

		layer.add(bgLayer);

		AssetWatcher assetWatcher = new AssetWatcher(
				new AssetWatcher.Listener() {
					@Override
					public void done() {
						startHighscoreScreen();
					}

					@Override
					public void error(Throwable e) {
						PlayN.log().error(
								"Error loading Quiz Game: " + e.getMessage());
					}
				});
		String imageURLH = "images/BgHighScoreEval.png";
		this.bgImage = PlayN.assets().getImage(imageURLH);
		assetWatcher.add(bgImage);
		assetWatcher.start();

	}

	protected void startHighscoreScreen() {

		/*
		 * Bg for HighScore
		 */

		ImageLayer imageLayerH = graphics().createImageLayer(bgImage);
		imageLayerH.setScale(0.53f);
		imageLayerH.setTranslation(800 / 2 - imageLayerH.scaledWidth() / 2, 0);

		layer.add(imageLayerH);

		Label titel = new Label("Game Over");
		titel.setStyles(QuizStyles.FONTSTYLEHEADER_BIG);

		Label nameLabel = new Label("Name:");
		nameLabel.setStyles(QuizStyles.FONT_STYLE_HEADER_SMALL);
		evaluationRoot.add(titel);
		evaluationRoot.add(nameLabel);

		this.enterNameField = new Field("<Name>", QuizStyles.FONTSTYLE17);
		enterNameField.setPopupLabel("Name:");

		Group g = new Group(AxisLayout.vertical().offEqualize());
		g.add(new Shim(150, 1));
		g.add(enterNameField);

		evaluationRoot.add(g);

		evaluationRoot.add(scoreGroup);
		getHighScoreEvaluation();
		layer.add(evaluationRoot.layer);

	}

	@Override
	public void shutdown() {
		if (iface != null) {
			pointer().setListener(null);
			iface.destroyRoot(evaluationRoot);
			iface = null;
		}

		bgLayer.destroy();
		layer.destroy();
		layer = null;
	}

	@Override
	public void update(int delta) {
		clock.update(delta);
		if (iface != null) {
			iface.update(delta);
		}

	}

	@Override
	public void paint(float alpha) {
		clock.paint(alpha);
		if (iface != null) {
			iface.paint(clock);
		}
	}

	public void enterScore(Score score) {
		this.score = score;
		this.scoreGroup = new Group(AxisLayout.horizontal());

		Group gesamt = new Group(AxisLayout.vertical().offStretch());
		Group joker = new Group(AxisLayout.vertical().offStretch());
		Group frage = new Group(AxisLayout.vertical().offStretch());

		gesamt.add(new Label(quiz.rB.string("OVERALL"))
				.setStyles(QuizStyles.FONT_STYLE_HEADER_SMALL_WO_INSET));
		gesamt.add(new Shim(100, 1));
		joker.add(new Label(quiz.rB.string("JOKER"))
				.setStyles(QuizStyles.FONT_STYLE_HEADER_SMALL_WO_INSET));
		joker.add(new Shim(30, 1));
		frage.add(new Label(quiz.rB.string("FRAGENSTUFE"))
				.setStyles(QuizStyles.FONT_STYLE_HEADER_SMALL_WO_INSET));
		frage.add(new Shim(100, 1));

		int punktezahl = score.getPunkteAnzahl();
		gesamt.add(new Label(punktezahl + " " + quiz.rB.string("PUNKTE")+".")
				.setStyles(QuizStyles.FONT_STYLE_18));

		int jokerEl = score.getJoker();
		joker.add(new Label(jokerEl + "x").setStyles(QuizStyles.FONT_STYLE_18));

		int frageString = score.getFrage();
		frage.add(new Label(frageString + " " + quiz.rB.string("PUNKTE")+".")
				.setStyles(QuizStyles.FONT_STYLE_18));

		scoreGroup.add(gesamt);
		scoreGroup.add(joker);
		scoreGroup.add(frage);

	}

	public void submitScore() {

		Json.Writer w = PlayN.json().newWriter();
		w.object();
		w.value("Action", "Store");
		w.value("Name", enterNameField.text.get());
		w.value("PunkteAnzahl", score.getPunkteAnzahl());

		w.value("Joker", score.getJoker());
		w.value("Frage", score.getFrage());

		w.end();

		PlayN.net().post(connectStringHighScore, w.write(),
				new Callback<String>() {

					@Override
					public void onSuccess(String result) {

						quiz.activateMenu();
					}

					@Override
					public void onFailure(Throwable err) {
						quiz.activateMenu();
					}
				});

	}

	public void getHighScoreEvaluation() {

		Json.Writer w = PlayN.json().newWriter();
		w.object();
		w.value("Action", "GetEvaluation");
		w.value("PunkteAnzahl", score.getPunkteAnzahl());
		w.value("Joker", score.getJoker());
		w.value("Frage", score.getFrage());

		w.end();

		PlayN.net().post(connectStringHighScore, w.write(),
				new Callback<String>() {

					@Override
					public void onSuccess(String result) {
						Json.Object document = PlayN.json().parse(result);
						int placeAllTime = document.getInt("placeAllTime");
						int placeThisWeek = document.getInt("placeThisWeek");

						Label weekLabel = new Label(quiz.rB.string("THIS_WEEK")+":")
								.setStyles(QuizStyles.FONT_STYLE_18);
						;
						Label placeThisWeekLabel = new Label(placeThisWeek
								+ ".").setStyles(QuizStyles.FONT_STYLE_18);
						Label allTimeLabel = new Label("All-Time:")
								.setStyles(QuizStyles.FONT_STYLE_18);
						;
						Label placeAllTimeLabel = new Label(placeAllTime + ".")
								.setStyles(QuizStyles.FONT_STYLE_18);
						;
						evaluationRoot.add(new Label(quiz.rB.string("PLATZ"))
								.setStyles(QuizStyles.FONT_STYLE_HEADER_SMALL));
						evaluationRoot.add(weekLabel);
						evaluationRoot.add(placeThisWeekLabel);
						evaluationRoot.add(allTimeLabel);
						evaluationRoot.add(placeAllTimeLabel);
						Button b = new Button(" OK ");
						// b.addStyles(Styles.none().add(
						// Style.BACKGROUND.is(Background
						// .solid(0xAAFFFFFF))));
						// b.addStyles(QuizStyles.FONTSTYLEBUTTON);
						b.clicked().connect(new UnitSlot() {
							public void onEmit() {

								submitScore();

							}
						});
						evaluationRoot.add(b);

						// evaluationRoot
						// .add(new Label(
						// "Beim Gewinn handelt es sich natürlich um Spielgeld!")
						// .setStyles(QuizStyles.fontStyle10));

					}

					@Override
					public void onFailure(Throwable err) {
						quiz.activateMenu();
					}
				});

	}

	// public Keyboard.Listener keyboardListener() {
	// return keyListener;
	// }
}
