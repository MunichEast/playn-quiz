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
package playn.quiz.core;

import static playn.core.PlayN.*;
import static playn.core.PlayN.graphics;
import static playn.core.PlayN.pointer;
import playn.core.AssetWatcher;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Json;
import playn.core.Json.Object;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.quiz.core.util.Color;
import playn.quiz.core.util.QuizSimpleStyles;
import playn.quiz.core.util.QuizStyles;
import react.UnitSlot;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Icon;
import tripleplay.ui.Icons;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Shim;
import tripleplay.ui.Slider;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.layout.AxisLayout;

/**
 * A demo that displays a menu of the available demos. TODO: inefficient
 * implementation in highscore root
 */
public class Menu extends Screen {
	private Object document;

	private final Quiz quiz;

	private Interface iface;
	private GroupLayer layer;
	// up and down
	private int sliderValue = 0;

	private Button switcherButton;
	private Label header;
	private Root playButtonRoot;

	private Button buttonPlay;

	private ImageLayer imageLayerH;

	private Image buttonPlayImage;

	private Image imageLogo;

	private Image slider;

	private Root rootHighscore;

	private Image imageUp;

	private Image imageDown;

	public Menu(Quiz quiz) {
		this.quiz = quiz;
	}

	@Override
	public String name() {
		return "Menu";
	}

	@Override
	public void init() {

		layer = graphics().createGroupLayer();

		int width = graphics().width(), height = graphics().height();
		ImageLayer bgLayer = PlayN.graphics().createImageLayer();
		CanvasImage canvasImage = PlayN.graphics().createImage(width, height);
		canvasImage.canvas().setFillColor(Color.BLACK);
		canvasImage.canvas().fillRect(0, 0, width, height);
		canvasImage.canvas().setFillColor(Color.BGCOLOR);
		canvasImage.canvas().fillRect(1, 1, width - 2, height - 2);
		canvasImage.canvas().setFillColor(Color.RED);
		canvasImage.canvas().drawText("V.2.2-LIVE", (float) (width - 80),
				(float) (height - 7));
		bgLayer.setImage(canvasImage);
		layer.add(bgLayer);
		graphics().rootLayer().add(layer);

		iface = new Interface();

		if(quiz.rB == null){
			determineLanguage();
		}else{
			loadAssets();
		}
		
		

	}

	public void loadAssets() {
		// Prepare images
				AssetWatcher assetWatcher = new AssetWatcher(
						new AssetWatcher.Listener() {
							@Override
							public void done() {
								startMenu();
							}

							@Override
							public void error(Throwable e) {
								PlayN.log().error(
										"Error loading Quiz Game: " + e.getMessage());
							}
						});
				this.slider = PlayN.assets().getImage("images/Slider.png");
				this.buttonPlayImage = PlayN.assets()
						.getImage("buttons/ButtonPlay.png");
				this.imageUp = PlayN.assets().getImage("images/Up.png");
				this.imageDown = PlayN.assets().getImage("images/Down.png");
				assetWatcher.add(imageUp);
				assetWatcher.add(buttonPlayImage);
				assetWatcher.add(slider);

				/*
				 * Bg for HighScore
				 */

				String imageURLH = "images/BgHighScore.png";
				Image image = PlayN.assets().getImage(imageURLH);
				this.imageLayerH = graphics().createImageLayer(image);
				assetWatcher.add(image);

				String imageURL = "images/Artigo-LogoWide.png";
				this.imageLogo = PlayN.assets().getImage(imageURL);

				assetWatcher.add(imageLogo);

				assetWatcher.start();
		
	}

	protected void determineLanguage() {
		PlayN.net().get(connectStringLocale, new Callback<String>() {

			@Override
			public void onSuccess(String jsonString) {

				if (jsonString.equals("")) {
					PlayN.log()
							.error("Verbindungsfehler: Leerer Antwortstring");
					onFailure(null);
				} else {
					// parse
					Object documentLocale = PlayN.json().parse(jsonString);
					quiz.setLanguage(documentLocale.getString("Locale"), Menu.this);
					
				}

			}

			@Override
			public void onFailure(Throwable err) {
				PlayN.log().error("Verbindungsfehler");

				playButtonRoot = iface.createRoot(
						AxisLayout.vertical().gap(15), null);
				playButtonRoot.setSize(graphics().width(), graphics().height());
				Label l = new Label(
						"Entschuldigung. Keine Verbindung zum Server vorhanden.")
						.setStyles(QuizStyles.fontStyleError);
				Label l2 = new Label(
						"Bitte spielen Sie doch ein anderes ARTigo-Spiel!")
						.setStyles(QuizStyles.fontStyleError);
				playButtonRoot.add(l);
				playButtonRoot.add(l2);
				playButtonRoot.layer.setTranslation(0, 0);
				layer.add(playButtonRoot.layer);

			}
		});

	}

	protected void startMenu() {

		this.header = new Label(quiz.rB.string("ALL_TIME_RECORDS"));

		this.playButtonRoot = iface.createRoot(AxisLayout.vertical().gap(15),
				null);
		playButtonRoot.setSize(200, 200);
		playButtonRoot.layer.setTranslation(300, 120);

		this.buttonPlay = new Button(Icons.image(buttonPlayImage));

		playButtonRoot.add(buttonPlay);
		buttonPlay.clicked().connect(new UnitSlot() {
			@Override
			public void onEmit() {
				quiz.activateExplainer();
			}
		});

		layer.add(playButtonRoot.layer);
		ImageLayer imageLayer = graphics().createImageLayer(imageLogo);
		imageLayer.setScale(0.4f);

		imageLayer.setTranslation(800 / 2 - imageLayer.scaledWidth() / 2, 10);

		layer.add(imageLayer);

		this.switcherButton = new Button(quiz.rB.string("ALL_TIME"));
		switcherButton.clicked().connect(new UnitSlot() {
			@Override
			public void onEmit() {
				getEintraege();
			}
		});

		getEintraege();
	}

	public void postload() {

		imageLayerH.setScale(0.425f);
		imageLayerH
				.setTranslation(800 / 2 - imageLayerH.scaledWidth() / 2, 290);

		layer.add(imageLayerH);

		paintSlider();

		writeOverallHighscore();
	}

	private void paintSlider() {

		// //HighscoreSlider
		// this.sliderBar = new HighscoreSlider(0, 0, 100).addStyles(
		// HighscoreSlider.THUMB_IMAGE.is(Icons.loader(slider,38,38)),
		// HighscoreSlider.BAR_BACKGROUND.is(Background.roundRect(0xFFFFFFFF,
		// 9)),
		// HighscoreSlider.BAR_HEIGHT.is(100f),
		// HighscoreSlider.BAR_WIDTH.is(5f)
		//
		//
		// );

		// sliderBar.value.connect(new UnitSlot() {
		// @Override
		// public void onEmit() {
		// writeOverallHighscore();
		// }
		// });

	}

	private void writeOverallHighscore() {

		// design Highscore
		// create our demo interface

		this.rootHighscore = iface.createRoot(AxisLayout.vertical().gap(5),
				QuizSimpleStyles.newSheet(), layer);
		rootHighscore.setSize(600, 250);
		rootHighscore.addStyles(Styles.make(Style.BACKGROUND.is(Background
				.solid(Color.BLUE))));
		// root3.addStyles(Styles.make(Style.COLOR.is(0xFFFFFFFF)));
		rootHighscore.layer.setTranslation(100, 325);

		layer.add(rootHighscore.layer);

		Group window = new Group(AxisLayout.horizontal().offStretch());

		Group subwindow = new Group(AxisLayout.vertical().offStretch());
		subwindow.add(header);

		Group records = new Group(AxisLayout.horizontal().offStretch());

		Group platz = new Group(AxisLayout.vertical().offStretch());

		Group name = new Group(AxisLayout.vertical().offStretch());

		Group gesamt = new Group(AxisLayout.vertical().offStretch());
		Group joker = new Group(AxisLayout.vertical().offStretch());
		Group fragenstufe = new Group(AxisLayout.vertical().offStretch());
		records.add(platz, name, gesamt, joker, fragenstufe);
		platz.add(new Label(quiz.rB.string("PLATZ"))
				.setStyles(QuizStyles.fontStyleBoldWhite));
		platz.add(new Shim(30, 1));
		name.add(new Label(quiz.rB.string("NAME"))
				.setStyles(QuizStyles.fontStyleBoldWhite));
		name.add(new Shim(150, 1));
		gesamt.add(new Label(quiz.rB.string("OVERALL"))
				.setStyles(QuizStyles.fontStyleBoldWhite));
		gesamt.add(new Shim(100, 1));
		joker.add(new Label(quiz.rB.string("JOKER"))
				.setStyles(QuizStyles.fontStyleBoldWhite));
		joker.add(new Shim(30, 1));
		fragenstufe.add(new Label(quiz.rB.string("FRAGENSTUFE"))
				.setStyles(QuizStyles.fontStyleBoldWhite));
		fragenstufe.add(new Shim(100, 1));

		int anzahl = document.getInt("Anzahl");
		// setting the start value

		int start = (int) ((sliderValue / 100f) * (anzahl - 10));

		for (int i = start; i < start + 10; ++i) {
			Object score = document.getObject("Platz" + i);
			if (score != null) {
				platz.add(new Label((i + 1) + "."));

				String nameEl = score.getString("Name");
				if (nameEl.length() > 16) {
					nameEl = nameEl.substring(0, 15);
				}

				if (nameEl.equals("")) {
					name.add(new Label("-"));
				} else {
					name.add(new Label(nameEl));
				}

				int punktezahl = score.getInt("PunkteAnzahl");
				gesamt.add(new Label(punktezahl + " Pkt."));

				int jokerEl = score.getInt("Joker");
				joker.add(new Label(jokerEl + "x"));

				int frage = score.getInt("Frage");
				fragenstufe.add(new Label(frage + " Pkt."));
			}

		}

		subwindow.add(records);

		Group buttonGroup = new Group(AxisLayout.vertical().offStretch());
		buttonGroup.add(new Shim(100, 200));
		buttonGroup.add(switcherButton);
		window.add(buttonGroup);
		window.add(subwindow);

		rootHighscore.add(window);

		Group sliderGroup = new Group(AxisLayout.vertical().offStretch());
		sliderGroup.add(new Shim(1, 40));

		Button buttonUp = new Button(Icons.image(imageUp));

		UnitSlot unitSlotUp = new UnitSlot() {
			public void onEmit() {
				if(sliderValue - 10 > 0){
					sliderValue = sliderValue - 10;
					writeOverallHighscore();
				}
				
			}
		};

		buttonUp.clicked().connect(unitSlotUp);

		Button buttonDown = new Button(Icons.image(imageDown));

		UnitSlot unitSlotDown = new UnitSlot() {
			public void onEmit() {
				if (sliderValue + 10 <= 100){
					sliderValue = sliderValue + 10;
					writeOverallHighscore();
				}
				
			}
		};

		buttonDown.clicked().connect(unitSlotDown);

		sliderGroup.add(buttonUp);
		sliderGroup.add(new Shim(1, 50));
		sliderGroup.add(buttonDown);
		window.add(sliderGroup);

	}

	private void getEintraege() {
		if (rootHighscore != null)
			rootHighscore.destroyAll();
		paintSlider();
		String mode = switcherButton.text.get();

		if (mode.equals("All Time")) {
			header = new Label("All Time Records")
					.setStyles(QuizStyles.fontStyleUeberschrift);
			switcherButton = new Button(quiz.rB.string("THIS_WEEK"))
					.setStyles(QuizStyles.fontStyleBoldWhite);

			switcherButton.clicked().connect(new UnitSlot() {
				@Override
				public void onEmit() {
					getEintraege();
				}
			});
		} else {
			header = new Label("This Week Records")
					.setStyles(QuizStyles.fontStyleUeberschrift);
			switcherButton = new Button(quiz.rB.string("ALL_TIME"))
					.setStyles(QuizStyles.fontStyleBoldWhite);

			switcherButton.clicked().connect(new UnitSlot() {
				@Override
				public void onEmit() {
					getEintraege();
				}
			});
		}

		Json.Writer w = PlayN.json().newWriter();
		w.object();
		w.value("Action", "GetHighscores");
		w.value("HighScoreMode", mode);
		w.end();

		PlayN.net().post(connectStringHighScore, w.write(),
				new Callback<String>() {

					@Override
					public void onSuccess(String rating) {

						if (rating.equals("")) {
							PlayN.log().error(
									"Verbindungsfehler: Leerer Antwortstring");
							onFailure(null);
						} else {
							// parse
							
							document = PlayN.json().parse(rating);
							// parse the entities, adding each asset to the
							// asset
							// watcher
							postload();
						}

					}

					@Override
					public void onFailure(Throwable err) {
						PlayN.log().error("Verbindungsfehler");

					}
				});

	}

	@Override
	public void shutdown() {
		if (iface != null) {
			pointer().setListener(null);
			iface.destroyRoot(playButtonRoot);
			iface = null;
		}

		imageLayerH.destroy();

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

}
