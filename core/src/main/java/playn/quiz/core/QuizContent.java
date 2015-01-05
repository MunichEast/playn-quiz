/**
 * Copyright 2011 The PlayN Authors
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

import java.util.ArrayList;
import java.util.List;

import playn.core.AssetWatcher;
import playn.core.CanvasImage;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.PlayN;
import playn.quiz.core.entities.Entity;
import playn.quiz.core.entities.GameClock;
import playn.quiz.core.entities.InfoText;
import playn.quiz.core.entities.Question;
import playn.quiz.core.entities.Score;
import playn.quiz.core.layer.FrageleiterLayer;
import playn.quiz.core.layer.JokerLayer;
import playn.quiz.core.layer.ProgressLayer;
import playn.quiz.core.layer.QuestionLayer;
import playn.quiz.core.layer.RatingLayer;
import playn.quiz.core.util.Color;
import playn.quiz.core.util.QuizStyles;
import tripleplay.anim.Animation.One;
import tripleplay.anim.Animator;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Shim;
import tripleplay.ui.layout.AxisLayout;

public class QuizContent extends Screen {

	private GroupLayer staticLayerBack;
	private GroupLayer dynamicLayer;
	private GroupLayer staticLayerFront;

	private int currentQuestion = -1;
	public List<Entity> entities = new ArrayList<Entity>(0);
	private List<Entity> entitiesToRemove = new ArrayList<Entity>(0);
	public Interface iface;
	public Question[] question;
	private final static int WIDTH = 800;
	private final static int HEIGHT = 600;
	private boolean started = false;

	public Quiz quiz;

	private Root vorspannLayer;
	private boolean gameOver;

	private Root begriffspaarRoot;

	private int bonus = 0;
	private boolean playVorspann;

	private boolean answerCorrect;

	private String sid;

	public GameClock gameClock;

	private QuestionLayer questionLayer;
	private ProgressLayer progressLayer;

	private RatingLayer ratingLayer;

	private double startAnimationTime;

	private final int[] gewinnstufen = new int[] { 50, 100, 200, 300, 500,
			1000, 2000, 4000, 8000, 16000, 32000, 64000, 125000, 250000, 500000 };
	private final String[] gewinnstufenString = new String[] { "50", "100",
			"200", "300", "500", "1.000", "2.000", "4.000", "8.000", "16.000",
			"32.000", "64.000", "125.000", "250.000", "500.000" };

	private FrageleiterLayer frageleiterLayer;

	private JokerLayer jokerLayer;

	private int cnt = 0;

	private GroupLayer imageGroup;

	public Animator anim;
	public Image imgStar;
	public Image imgBgPublikum;
	public Image imgBgGitter;
	public Image imgJokerPublikumGrey;
	public Image imgJokerPublikumGone;
	public Image img5050JokerGrey;
	public Image joker5050Gone;
	public Image imgJokerDeathGrey;
	public Image imgJokerDeathGone;
	public Image img5050Joker;
	public Image imgJokerPublikum;
	public Image imgJokerDeath;

	public Image header;
	public Image buttonA;
	public Image buttonB;
	public Image buttonC;
	public Image buttonD;

	public Image buttonAYellow;
	public Image buttonBYellow;
	public Image buttonCYellow;
	public Image buttonDYellow;

	public Image buttonAGreen;
	public Image buttonBGreen;
	public Image buttonCGreen;
	public Image buttonDGreen;

	public Image buttonARed;
	public Image buttonBRed;
	public Image buttonCRed;
	public Image buttonDRed;
	public CanvasImage canvasImage;
	private Root bonusLayerLabel;
	private Root bonusJokerLabel;
	public Root alertRoot;
	public Root bonusRoot;
	private Label nextQuestionLabel;
	private boolean animStarted = false;
	private ImageLayer imageLayer;

	public QuizContent(Quiz quiz) {
		this.quiz = quiz;

	}

	@Override
	public void init() {

		AssetWatcher assetWatcher = new AssetWatcher(
				new AssetWatcher.Listener() {
					@Override
					public void done() {
						startGame();
					}

					@Override
					public void error(Throwable e) {
						PlayN.log().error(
								"Error loading Quiz Game: " + e.getMessage());
					}
				});

		this.imgStar = PlayN.assets().getImage("images/38px-Star.png");
		this.imgBgPublikum = PlayN.assets().getImage("images/BgPublikum.png");
		this.imgBgGitter = PlayN.assets().getImage("images/BgGitter.png");

		this.imgJokerPublikumGrey = PlayN.assets().getImage(
				"buttons/JokerPublikumGrey.png");
		this.imgJokerPublikumGone = PlayN.assets().getImage(
				"buttons/JokerPublikumGone.png");
		this.img5050JokerGrey = PlayN.assets().getImage(
				"buttons/5050JokerGrey.png");
		this.joker5050Gone = PlayN.assets().getImage(
				"buttons/Joker5050Gone.png");
		this.imgJokerDeathGrey = PlayN.assets().getImage(
				"buttons/JokerDeathGrey.png");
		this.imgJokerDeathGone = PlayN.assets().getImage(
				"buttons/JokerDeathGone.png");
		this.img5050Joker = PlayN.assets().getImage("buttons/5050Joker.png");
		this.imgJokerPublikum = PlayN.assets().getImage(
				"buttons/JokerPublikum.png");
		this.imgJokerDeath = PlayN.assets().getImage("buttons/JokerDeath.png");

		this.header = PlayN.assets().getImage("buttons/HeaderEmpty.png");

		this.buttonA = PlayN.assets().getImage("buttons/ButtonA.png");
		this.buttonB = PlayN.assets().getImage("buttons/ButtonB.png");
		this.buttonC = PlayN.assets().getImage("buttons/ButtonC.png");
		this.buttonD = PlayN.assets().getImage("buttons/ButtonD.png");

		this.buttonARed = PlayN.assets().getImage("buttons/ButtonARed.png");
		this.buttonBRed = PlayN.assets().getImage("buttons/ButtonBRed.png");
		this.buttonCRed = PlayN.assets().getImage("buttons/ButtonCRed.png");
		this.buttonDRed = PlayN.assets().getImage("buttons/ButtonDRed.png");

		this.buttonAYellow = PlayN.assets().getImage(
				"buttons/ButtonAYellow.png");
		this.buttonBYellow = PlayN.assets().getImage(
				"buttons/ButtonBYellow.png");
		this.buttonCYellow = PlayN.assets().getImage(
				"buttons/ButtonCYellow.png");
		this.buttonDYellow = PlayN.assets().getImage(
				"buttons/ButtonDYellow.png");

		this.buttonAGreen = PlayN.assets().getImage("buttons/ButtonAGreen.png");
		this.buttonBGreen = PlayN.assets().getImage("buttons/ButtonBGreen.png");
		this.buttonCGreen = PlayN.assets().getImage("buttons/ButtonCGreen.png");
		this.buttonDGreen = PlayN.assets().getImage("buttons/ButtonDGreen.png");

		assetWatcher.add(imgStar);
		assetWatcher.add(imgBgPublikum);
		assetWatcher.add(imgBgGitter);
		assetWatcher.add(imgJokerPublikumGrey);
		assetWatcher.add(imgJokerPublikumGone);
		assetWatcher.add(img5050JokerGrey);
		assetWatcher.add(joker5050Gone);
		assetWatcher.add(imgJokerDeathGrey);
		assetWatcher.add(imgJokerDeathGone);
		assetWatcher.add(img5050Joker);
		assetWatcher.add(imgJokerPublikum);
		assetWatcher.add(imgJokerDeath);

		assetWatcher.add(header);

		assetWatcher.add(buttonA);
		assetWatcher.add(buttonB);
		assetWatcher.add(buttonC);
		assetWatcher.add(buttonD);

		assetWatcher.add(buttonARed);
		assetWatcher.add(buttonBRed);
		assetWatcher.add(buttonCRed);
		assetWatcher.add(buttonDRed);

		assetWatcher.add(buttonAYellow);
		assetWatcher.add(buttonBYellow);
		assetWatcher.add(buttonCYellow);
		assetWatcher.add(buttonDYellow);

		assetWatcher.add(buttonAGreen);
		assetWatcher.add(buttonBGreen);
		assetWatcher.add(buttonCGreen);
		assetWatcher.add(buttonDGreen);

		assetWatcher.start();

	}

	protected void startGame() {
		this.anim = new Animator();
		setStaticLayerBack(graphics().createGroupLayer());

		loadBackground();
		GroupLayer scaledLayer = graphics().createGroupLayer();
		graphics().rootLayer().add(scaledLayer);

		setDynamicLayer(graphics().createGroupLayer());
		setStaticLayerFront(graphics().createGroupLayer());

		// create our UI manager and configure it to process pointer events
		// iface = new Interface(null);
		// pointer().setListener(iface.plistener);
		iface = new Interface();

		// define our root stylesheet

		this.begriffspaarRoot = iface.createRoot(AxisLayout.horizontal(), null);
		getDynamicLayer().add(begriffspaarRoot.layer);
		/*
		 * create frageLeiterLayer
		 */

		this.frageleiterLayer = new FrageleiterLayer(this, iface,
				gewinnstufenString, quiz);

		scaledLayer.add(getStaticLayerBack());
		scaledLayer.add(getDynamicLayer());
		scaledLayer.add(getStaticLayerFront());

		Root bonusAnimRoot = iface.createRoot(AxisLayout.vertical().gap(5),
				null);

		bonusAnimRoot.setSize(300, 150);
		getDynamicLayer().add(bonusAnimRoot.layer);
		this.gameClock = new GameClock(this, quiz.rB);
		entities.add(gameClock);

		this.alertRoot = iface.createRoot(AxisLayout.vertical().gap(0), null);
		alertRoot.setSize(600, 310);
		alertRoot.layer.setTranslation(WIDTH / 2 - 600 / 2, 5);

		getStaticLayerFront().add(alertRoot.layer);

		this.bonusRoot = iface.createRoot(AxisLayout.vertical().gap(20), null);
		bonusRoot.setSize(300, 50);
		bonusRoot.layer.setTranslation(0, 0);
		getStaticLayerFront().add(bonusRoot.layer);

		// setUpEndGameButton();
		InfoText t1;
		InfoText t2;
		if (PlayN.random() < 0.5) {
			t1 = new InfoText(this, quiz.rB.string("RATE_IMAGES_1"), 0, 0);
			t2 = new InfoText(this, quiz.rB.string("RATE_IMAGES_2"), 0, 30);
		} else {
			t1 = new InfoText(this, quiz.rB.string("RATE_IMAGES_1"), 0, 0);
			t2 = new InfoText(this, quiz.rB.string("RATE_IMAGES_3"), 0, 30);
		}

		this.jokerLayer = new JokerLayer(this, iface);
		this.progressLayer = new ProgressLayer(this, iface);

		this.entities.add(t1);
		this.entities.add(t2);
		nextQuestion();
		this.started = true;

	}

	private void loadBackground() {
		// load and show our background
		int width = graphics().width(), height = graphics().height();
		ImageLayer bgLayer = PlayN.graphics().createImageLayer();
		this.canvasImage = PlayN.graphics().createImage(width, height);
		bgLayer.setImage(canvasImage);
		canvasImage.canvas().setFillColor(Color.BLACK);
		canvasImage.canvas().fillRect(0, 0, width, height);
		canvasImage.canvas().setFillColor(Color.WHITE);
		canvasImage.canvas().fillRect(1, 1, width - 2, height - 2);

		// joker box
		canvasImage.canvas().setFillColor(Color.BLUE);
		canvasImage.canvas().fillRect(25, 140, 100, 30);

		// bonus label box
		canvasImage.canvas().setFillColor(Color.BLUE);
		canvasImage.canvas().fillRect(25, 28, 100, 30);

		// bonus points box
		canvasImage.canvas().setFillColor(Color.BLUE);
		canvasImage.canvas().fillRect(15, 60, 120, 30);

		// time label box
		canvasImage.canvas().setFillColor(Color.BLUE);
		canvasImage.canvas().fillRect(675, 28, 100, 30);

		// time box
		canvasImage.canvas().setFillColor(Color.BLUE);
		canvasImage.canvas().fillRect(685, 60, 80, 30);

		getStaticLayerBack().add(bgLayer);

	}

	// private void setUpEndGameButton() {
	// Root endeRoot = iface.createRoot(AxisLayout.vertical().gap(3),
	// rootSheet);
	//
	// // Button endeButton = new Button();
	// // endeButton.setIcon(loadButton("Ende.png"));
	//
	// // endeButton.clicked().connect(new UnitSlot() {
	// // public void onEmit() {
	// // PlayN.log().info("Spiel freiwillig beenden");
	// //
	// // gameOver();
	// //
	// // }
	// // });
	// // endeRoot.add(endeButton);
	// // endeRoot.setSize(130, 65);
	// // endeRoot.layer.setTranslation(800 - 140, 600 - 90);
	// //
	// // getDynamicLayer().add(endeRoot.layer);
	// }

	public void addBonus(int addedBonus) {

		if (bonusLayerLabel != null) {
			bonusLayerLabel.destroyAll();
			bonusJokerLabel.destroyAll();
		}
		this.bonus = bonus + addedBonus;

		this.bonusLayerLabel = iface.createRoot(AxisLayout.vertical().gap(10),
				null);
		bonusLayerLabel.setSize(100, 120);
		bonusLayerLabel.layer.setTranslation(25, 0);
		bonusLayerLabel.add(new Label("Bonus:")
				.setStyles(QuizStyles.FONT_STYLE_16_BLANK));

		String label = "" + bonus + " " + quiz.rB.string("PUNKTE") + ".";

		bonusLayerLabel.add(new Label(label)
				.setStyles(QuizStyles.FONT_STYLE_16_BLANK));

		this.bonusJokerLabel = iface.createRoot(AxisLayout.horizontal(), null);
		bonusJokerLabel.setSize(100, 50);
		bonusJokerLabel.layer.setTranslation(25, 130);
		bonusJokerLabel.add(new Label("Joker:")
				.setStyles(QuizStyles.FONT_STYLE_16_BLANK));

		getDynamicLayer().add(bonusLayerLabel.layer);
		getDynamicLayer().add(bonusJokerLabel.layer);

	}

	public void playVorspannAnimation(boolean correctAnswer) {
		playVorspann = true;
		this.startAnimationTime = PlayN.currentTime();
		this.vorspannLayer = iface.createRoot(AxisLayout.vertical().gap(0),
				null);
		vorspannLayer.setSize(400, 200);
		vorspannLayer.layer.setTranslation(WIDTH / 2 - 400 / 2, 108);

		if (correctAnswer) {

			if (currentQuestion + 2 < 16) {
				this.nextQuestionLabel = new Label("Frage "
						+ (currentQuestion + 2))
						.setStyles(QuizStyles.FONT_STYLE_40_BLUE_INVISIBLE);
			} else {
				this.nextQuestionLabel = new Label(
						quiz.rB.string("UNGLAUBLICH"))
						.setStyles(QuizStyles.FONT_STYLE_40_BLUE_INVISIBLE);
			}
			vorspannLayer.add(nextQuestionLabel);

		} else {
			vorspannLayer.add(new Label("Game over!")
					.setStyles(QuizStyles.FONT_STYLE_40_RED));
		}
		String titel = question[currentQuestion].getTitel() + " ("
				+ question[currentQuestion].getDatierung() + ")";

		if (titel.length() > 50 && titel.contains(" ")) {
			String[] twoParts = seperateTitelInTwoParts(titel);
			vorspannLayer.add(new Shim(1, 45));
			vorspannLayer.add(new Label(twoParts[0])
					.setStyles(QuizStyles.FONT_STYLE_14_WB));
			vorspannLayer.add(new Label(twoParts[1])
					.setStyles(QuizStyles.FONT_STYLE_14_WB));
		} else {
			vorspannLayer.add(new Shim(1, 72));
			vorspannLayer.add(new Label(titel)
					.setStyles(QuizStyles.FONT_STYLE_14_WB));
		}

		if (!question[currentQuestion].getLocation().equals("")
				&& !(question[currentQuestion].getInstitution().length() > 30)) {
			vorspannLayer.add(new Label(question[currentQuestion]
					.getInstitution()
					+ ", "
					+ question[currentQuestion].getLocation())
					.setStyles(QuizStyles.FONT_STYLE_14_WB));
		} else {
			vorspannLayer.add(new Label(question[currentQuestion]
					.getInstitution()).setStyles(QuizStyles.FONT_STYLE_14_WB));
		}

		getStaticLayerFront().add(vorspannLayer.layer);

	}

	/*
	 * separate String
	 */
	public String[] seperateTitelInTwoParts(String titel) {

		if (titel.length() > 180) {
			titel = titel.substring(0, 180) + "...";
		}

		String[] split = titel.split(" ");
		int length = titel.length();

		String part1 = "";
		String part2 = "";
		for (int i = 0; i < split.length; ++i) {
			if (part1.length() < length / 2) {
				part1 += split[i] + " ";
			} else {
				part2 += split[i] + " ";
			}

		}

		return new String[] { part1, part2 };
	}

	public void gameOver(boolean answerCorrect) {
		PlayN.log().info("Game over!");
		gameOver = true;

		frageleiterLayer.removeLayers();
		getDynamicLayer().remove(questionLayer.getLayer());
		// jokerLayer.removeLayers();
		ratingLayer.removeLayers();
		getDynamicLayer().remove(begriffspaarRoot.layer);
		getDynamicLayer().remove(bonusLayerLabel.layer);
		getDynamicLayer().remove(bonusJokerLabel.layer);
		getDynamicLayer().remove(progressLayer.getLayer());
		getStaticLayerFront().remove(progressLayer.getPercentageCanvas());
		getStaticLayerBack().add(progressLayer.getRatingBar());
		getStaticLayerBack().remove(imageGroup);
		Score score;

		if (currentQuestion == 0) {
			score = new Score(bonus, jokerLayer.freigeschalten, 0);
		} else if (currentQuestion < 14) {
			addBonus(gewinnstufen[currentQuestion - 1]);
			score = new Score(bonus, jokerLayer.freigeschalten,
					gewinnstufen[currentQuestion - 1]);
		} else if (currentQuestion == 14) {
			if (answerCorrect) {
				addBonus(500000);
				score = new Score(bonus, jokerLayer.freigeschalten, 500000);
			} else {
				addBonus(250000);
				score = new Score(bonus, jokerLayer.freigeschalten, 250000);
			}

		} else {
			score = new Score(bonus, jokerLayer.freigeschalten, 0);
		}

		quiz.activateHighScoreScreen(score);

	}

	public void update(int delta) {
		clock.update(delta);
		if (started) {
			gameClock.update(delta);
			cnt += delta;
			if (iface != null) {
				iface.update(delta);
			}

			for (Entity e : entities) {
				e.update(delta);
			}

			for (Entity e : entitiesToRemove) {
				entities.remove(e);
			}
			if (questionLayer != null) {
				questionLayer.update(delta);
			}

			if (anim != null) {
				ratingLayer.animateTags();
				// Migration: update(cnt) -> paint(clock)
				anim.paint(clock);

			}

			playAnimations();
		}

	}

	private void playAnimations() {
		if (playVorspann) {

			if (PlayN.currentTime() - startAnimationTime > 2000) {
				if (!animStarted) {
					One animation = anim.tweenAlpha(imageLayer);
					animation.in(4000);
					animation.easeOut();
					animation.from(1);
					animation.to(0);
					animStarted = true;
				}

			}
			if (PlayN.currentTime() - startAnimationTime > 3000) {
				if (nextQuestionLabel != null)
					nextQuestionLabel.setStyles(QuizStyles.FONT_STYLE_40_BLUE);
			}

			if (PlayN.currentTime() - startAnimationTime > 4500) {

				playVorspann = false;
				getStaticLayerFront().remove(vorspannLayer.layer);
				if (answerCorrect == false) {
					gameOver(answerCorrect);
				} else if (currentQuestion == 14) {
					PlayN.log().info("Spielende!");
					gameOver(answerCorrect);
				} else {
					nextQuestion();
				}
			}

		}

	}

	public void paint(float alpha) {
		clock.paint(alpha);
		if (iface != null) {
			iface.paint(clock);
		}

	}

	public void setQuestions(Question[] questions) {
		this.question = questions;

	}

	public void nextQuestion() {
		animStarted = false;
		currentQuestion++;
		Image nextImage = question[currentQuestion].getImage();

		paintImage(nextImage);
		PlayN.log().info(
				"Correct Answer: "
						+ question[currentQuestion].getCorrectAnswer());

		if (currentQuestion != 0) {
			this.frageleiterLayer.nextQuestion();
		}

		if (ratingLayer != null) {
			ratingLayer.removeLayers();

		}

		this.ratingLayer = new RatingLayer(this, iface);

		if (questionLayer != null && questionLayer.getLayer() != null) {
			getDynamicLayer().remove(questionLayer.getLayer());
		}

		this.questionLayer = new QuestionLayer(this, iface, quiz.rB);
		questionLayer.setPos(150, 500);

		getDynamicLayer().add(questionLayer.getLayer());

		gameClock.reset();
		addBonus(0);
		getJokerLayer().setButtonsLocked(false);

	}

	private void paintImage(Image image) {

		paintImageWithShadow(image);

	}

	@Override
	public String name() {
		return "Quiz-Game";
	}

	@Override
	public void shutdown() {
		if (iface != null) {
			pointer().setListener(null);
			iface.destroyRoot(bonusLayerLabel);
			iface.destroyRoot(bonusJokerLabel);
			if (vorspannLayer != null)
				iface.destroyRoot(vorspannLayer);
			iface.destroyRoot(begriffspaarRoot);
			iface = null;
		}

		imageGroup.destroy();
		imageGroup = null;

		staticLayerBack.destroy();
		staticLayerBack = null;

		dynamicLayer.destroy();
		dynamicLayer = null;

		staticLayerFront.destroy();
		staticLayerFront = null;

	}

	public void removeEntity(Entity entity) {
		entitiesToRemove.add(entity);

	}

	public void setSessionID(String sid) {
		this.sid = sid;

	}

	public String getSessionID() {
		return sid;

	}

	public boolean isOver() {
		return gameOver;
	}

	public Interface getIface() {
		return this.iface;
	}

	public int getCurrentQuestionNumber() {

		return currentQuestion;
	}

	public Question getCurrentQuestion() {
		return question[currentQuestion];
	}

	public int getCurrentAmount() {
		return gewinnstufen[currentQuestion];
	}

	public QuestionLayer getQuestionLayer() {
		return this.questionLayer;
	}

	public RatingLayer getRatingLayer() {
		return ratingLayer;
	}

	public void setAnswerCorrect(boolean answerCorrect) {
		this.answerCorrect = answerCorrect;

	}

	public ProgressLayer getProgressLayer() {
		return this.progressLayer;
	}

	public JokerLayer getJokerLayer() {
		return this.jokerLayer;
	}

	// protected static Image loadButton(String name) {
	// return PlayN.assets().getImage("buttons/" + name);
	// }

	private void paintImageWithShadow(Image image) {

		this.imageLayer = graphics().createImageLayer(image);

		float imgHeight = imageLayer.height();
		float imgWidth = imageLayer.width();

		float innerWidth = 480f;
		float innerHeight = 290f;

		float outerWidth = innerWidth + 20f;
		float outerHeigth = innerHeight + 20f;

		// imageLayer.setScale(maxWidth / imgWidth);
		imageLayer.setScale(innerHeight / imgHeight);

		if (imageLayer.scaledWidth() > innerWidth) {
			imageLayer.setScale(innerWidth / imgWidth);
		}

		imageLayer.setTranslation(
				10 + (innerWidth - imageLayer.scaledWidth()) / 2,
				10 + (innerHeight - imageLayer.scaledHeight()) / 2);

		/*
		 * add background
		 */

		CanvasImage imBg = PlayN.graphics().createImage((int) outerWidth,
				(int) outerHeigth);
		imBg.canvas().setFillColor(Color.FRAME_COLOR);
		imBg.canvas().fillRect(0, 0, (int) outerWidth, (int) outerHeigth);

		imBg.canvas().setFillColor(Color.BORDER_CANVAS);
		imBg.canvas().fillRect(1, 1, (int) outerWidth - 2,
				(int) outerHeigth - 2);

		imBg.canvas().setFillColor(Color.BG_INNER_CANVAS);
		imBg.canvas().fillRect(10, 10, (int) innerWidth, (int) innerHeight);

		ImageLayer imageLayerBg = graphics().createImageLayer(imBg);

		this.imageGroup = PlayN.graphics().createGroupLayer();
		// setup shadow
		for (int i = 1; i <= 7; ++i) {
			CanvasImage boxShadow = PlayN.graphics().createImage(
					(int) innerWidth + 20, (int) innerHeight + 20);
			boxShadow.canvas().setFillColor(0x20888888);

			boxShadow.canvas().fillRect(0, 0, boxShadow.width(),
					boxShadow.height());
			ImageLayer boxShadowLayer = graphics().createImageLayer(boxShadow);
			boxShadowLayer.setTranslation(i, i);
			imageGroup.add(boxShadowLayer);
		}

		/*
		 * end setup background
		 */
		imageLayer.setAlpha(0);

		One animation = anim.tweenAlpha(imageLayer);
		animation.in(1000);
		animation.easeIn();
		animation.to(1);

		imageGroup.add(imageLayerBg);
		imageGroup.add(imageLayer);

		imageGroup.setTranslation(WIDTH / 2 - outerWidth / 2, 5);
		getStaticLayerBack().add(imageGroup);

	}

	public GroupLayer getDynamicLayer() {
		return dynamicLayer;
	}

	public void setDynamicLayer(GroupLayer dynamicLayer) {
		this.dynamicLayer = dynamicLayer;
	}

	public GroupLayer getStaticLayerBack() {
		return staticLayerBack;
	}

	public void setStaticLayerBack(GroupLayer staticLayerBack) {
		this.staticLayerBack = staticLayerBack;
	}

	public GroupLayer getStaticLayerFront() {
		return staticLayerFront;
	}

	private void setStaticLayerFront(GroupLayer staticLayerFront) {
		this.staticLayerFront = staticLayerFront;
	}

	public void zeigeDatierung() {
		InfoText t = new InfoText(this, quiz.rB.string("DATE_CREATED") + ": "
				+ this.getCurrentQuestion().getDatierung(), 0, 100);
		entities.add(t);

	}

}