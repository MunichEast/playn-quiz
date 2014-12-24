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
package playn.quiz.core.layer;

import static playn.core.PlayN.*;
import static playn.core.PlayN.graphics;
import static tripleplay.ui.layout.TableLayout.COL;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import playn.core.CanvasImage;
import playn.core.ImageLayer;
import playn.core.Json;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.quiz.core.QuizContent;
import playn.quiz.core.entities.BonusText;
import playn.quiz.core.entities.InfoText;
import playn.quiz.core.entities.RatingSlider;
import playn.quiz.core.util.Color;
import playn.quiz.core.util.QuizSimpleStyles;
import playn.quiz.core.util.QuizStyles;
import playn.quiz.core.util.ResourceBundle;
import react.UnitSlot;
import tripleplay.anim.Animation.One;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Element;
import tripleplay.ui.Group;
import tripleplay.ui.Icons;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Shim;
import tripleplay.ui.Slider;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.Stylesheet;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.layout.TableLayout;

/**
 * In this area the user can rate the picture using the stars.
 * 
 * @author Jonas Hoelzler
 * 
 */
public class RatingLayer {

	private boolean[] reihenfolge;
	// Rating Slider
	private RatingSlider slider0;
	private RatingSlider slider1;
	private RatingSlider slider2;
	private RatingSlider slider3;
	private RatingSlider slider4;
	private Root sliderRoot;

	protected static final String HINWEIS_TEXT = "Das Publikum wird stark von Ihrem Rating beeinflusst!";
	private ResourceBundle rB;
	private QuizContent quizContent;
	private Root begriffeLeft;
	private Root begriffeRight;
	private int numberSlidersDragged = 0;

	private List<Layer> activeLayer = new ArrayList<Layer>(0);

	private final int VOLLER_BONUS = 1;
	private final int HALBER_BONUS = 0;

	private boolean filledOut = false;
	private int[] answerRating;
	private boolean joker;
	private Root jokerRoot;
	private Group tagGroup;
	private int tagCounter;
	private double startSliderTime;
	private boolean[] clickedArray = new boolean[5];
	

	public RatingLayer(final QuizContent quizContent, Interface iface) {

		this.quizContent = quizContent;
		this.rB = quizContent.quiz.rB;
		Stylesheet rootSheet = Stylesheet.builder().add(Button.class).create();
		this.sliderRoot = iface.createRoot(AxisLayout.horizontal(), rootSheet);

		this.begriffeLeft = iface
				.createRoot(AxisLayout.horizontal(), rootSheet);

		this.begriffeRight = iface.createRoot(AxisLayout.horizontal(),
				rootSheet);

		
		slider0 = new RatingSlider(50, 0, 100, quizContent.imgStar);
		slider0.addStyles(Slider.BAR_BACKGROUND.is(Background.roundRect(0xFFFFFF00, 2).inset(1)),Slider.BAR_WIDTH.is(233f));
		slider1 = new RatingSlider(50, 0, 100, quizContent.imgStar);
		slider1.addStyles(Slider.BAR_BACKGROUND.is(Background.roundRect(0xFFFFFF00, 2).inset(1)),Slider.BAR_WIDTH.is(233f));
		slider2 = new RatingSlider(50, 0, 100, quizContent.imgStar);
		slider2.addStyles(Slider.BAR_BACKGROUND.is(Background.roundRect(0xFFFFFF00, 2).inset(1)),Slider.BAR_WIDTH.is(233f));
		slider3 = new RatingSlider(50, 0, 100, quizContent.imgStar);
		slider3.addStyles(Slider.BAR_BACKGROUND.is(Background.roundRect(0xFFFFFF00, 2).inset(1)),Slider.BAR_WIDTH.is(233f));
		slider4 = new RatingSlider(50, 0, 100, quizContent.imgStar);
		slider4.addStyles(Slider.BAR_BACKGROUND.is(Background.roundRect(0xFFFFFF00, 2).inset(1)),Slider.BAR_WIDTH.is(233f));

		
		UnitSlot unitSlot0 = new UnitSlot() {
			public void onEmit() {
				if(clickedArray[0] == false){
					clickedArray[0] = true;
					incNumberSlidersDragged();
				}
			}
		};
		slider0.clicked().connect(unitSlot0);
		
		UnitSlot unitSlot1 = new UnitSlot() {
			public void onEmit() {
				if(clickedArray[1] == false){
					clickedArray[1] = true;
					incNumberSlidersDragged();
				}
			}
		};
		slider1.clicked().connect(unitSlot1);
		
		UnitSlot unitSlot2 = new UnitSlot() {
			public void onEmit() {
				if(clickedArray[2] == false){
					clickedArray[2] = true;
					incNumberSlidersDragged();
				}
			}
		};
		slider2.clicked().connect(unitSlot2);
		
		UnitSlot unitSlot3 = new UnitSlot() {
			public void onEmit() {
				if(clickedArray[3] == false){
					clickedArray[3] = true;
					incNumberSlidersDragged();
				}
			}
		};
		slider3.clicked().connect(unitSlot3);
		
		UnitSlot unitSlot4 = new UnitSlot() {
			public void onEmit() {
				if(clickedArray[4] == false){
					clickedArray[4] = true;
					incNumberSlidersDragged();
				}
			}
		};
		slider4.clicked().connect(unitSlot4);
		
		
		AxisLayout l2 = new AxisLayout.Vertical().gap(1);
		Group sliderGroup = new Group(l2);
		sliderGroup.add(slider0);
		sliderGroup.add(slider1);
		sliderGroup.add(slider2);
		sliderGroup.add(slider3);
		sliderGroup.add(slider4);

		AxisLayout l = new AxisLayout.Vertical().gap(17);

		Group leftTerm = new Group(l, Style.HALIGN.right);
		Group rightTerm = new Group(l, Style.HALIGN.left);

		this.reihenfolge = new boolean[5];
		for (int i = 0; i < 5; ++i) {
			if (PlayN.random() < 0.5) {
				reihenfolge[i] = false;
			} else {
				reihenfolge[i] = true;
			}
		}
		
		String[][] BEGRIFFSPAARE = new String[][]{ { rB.string("LINEAR"), rB.string("MALERISCH") },
			{ rB.string("FLAECHE"), rB.string("TIEFE") }, { rB.string("GESCHLOSSEN"), rB.string("OFFEN") },
			{ rB.string("VIELHEIT"), rB.string("EINHEIT") }, { rB.string("KLARHEIT"), rB.string("UNKLARHEIT") } };

		if (reihenfolge[0]) {

			Label tmlLabel = new Label(BEGRIFFSPAARE[0][0]);
			tmlLabel.setStyles(QuizStyles.labelStyleLeft);

			leftTerm.add(tmlLabel);

			Label tmlLabel2 = new Label(BEGRIFFSPAARE[0][1]);
			tmlLabel2.setStyles(QuizStyles.labelStyleRight);
			rightTerm.add(tmlLabel2);

		} else {
			Label tmlLabel = new Label(BEGRIFFSPAARE[0][1]);
			tmlLabel.setStyles(QuizStyles.labelStyleLeft);

			leftTerm.add(tmlLabel);

			Label tmlLabel2 = new Label(BEGRIFFSPAARE[0][0]);
			tmlLabel2.setStyles(QuizStyles.labelStyleRight);
			rightTerm.add(tmlLabel2);
		}

		if (reihenfolge[1]) {
			Label tmlLabel = new Label(BEGRIFFSPAARE[1][0]);
			tmlLabel.setStyles(QuizStyles.labelStyleLeft);

			leftTerm.add(tmlLabel);

			Label tmlLabel2 = new Label(BEGRIFFSPAARE[1][1]);
			tmlLabel2.setStyles(QuizStyles.labelStyleRight);
			rightTerm.add(tmlLabel2);
		} else {
			Label tmlLabel = new Label(BEGRIFFSPAARE[1][1]);
			tmlLabel.setStyles(QuizStyles.labelStyleLeft);

			leftTerm.add(tmlLabel);

			Label tmlLabel2 = new Label(BEGRIFFSPAARE[1][0]);
			tmlLabel2.setStyles(QuizStyles.labelStyleRight);
			rightTerm.add(tmlLabel2);
		}

		if (reihenfolge[2]) {
			Label tmlLabel = new Label(BEGRIFFSPAARE[2][0]);
			tmlLabel.setStyles(QuizStyles.labelStyleLeft);

			leftTerm.add(tmlLabel);

			Label tmlLabel2 = new Label(BEGRIFFSPAARE[2][1]);
			tmlLabel2.setStyles(QuizStyles.labelStyleRight);
			rightTerm.add(tmlLabel2);
		} else {
			Label tmlLabel = new Label(BEGRIFFSPAARE[2][1]);
			tmlLabel.setStyles(QuizStyles.labelStyleLeft);

			leftTerm.add(tmlLabel);

			Label tmlLabel2 = new Label(BEGRIFFSPAARE[2][0]);
			tmlLabel2.setStyles(QuizStyles.labelStyleRight);
			rightTerm.add(tmlLabel2);
		}

		if (reihenfolge[3]) {
			Label tmlLabel = new Label(BEGRIFFSPAARE[3][0]);
			tmlLabel.setStyles(QuizStyles.labelStyleLeft);

			leftTerm.add(tmlLabel);

			Label tmlLabel2 = new Label(BEGRIFFSPAARE[3][1]);
			tmlLabel2.setStyles(QuizStyles.labelStyleRight);
			rightTerm.add(tmlLabel2);
		} else {
			Label tmlLabel = new Label(BEGRIFFSPAARE[3][1]);
			tmlLabel.setStyles(QuizStyles.labelStyleLeft);

			leftTerm.add(tmlLabel);

			Label tmlLabel2 = new Label(BEGRIFFSPAARE[3][0]);
			tmlLabel2.setStyles(QuizStyles.labelStyleRight);
			rightTerm.add(tmlLabel2);
		}

		if (reihenfolge[4]) {
			Label tmlLabel = new Label(BEGRIFFSPAARE[4][0]);
			tmlLabel.setStyles(QuizStyles.labelStyleLeft);

			leftTerm.add(tmlLabel);

			Label tmlLabel2 = new Label(BEGRIFFSPAARE[4][1]);
			tmlLabel2.setStyles(QuizStyles.labelStyleRight);
			rightTerm.add(tmlLabel2);
		} else {
			Label tmlLabel = new Label(BEGRIFFSPAARE[4][1]);
			tmlLabel.setStyles(QuizStyles.labelStyleLeft);

			leftTerm.add(tmlLabel);

			Label tmlLabel2 = new Label(BEGRIFFSPAARE[4][0]);
			tmlLabel2.setStyles(QuizStyles.labelStyleRight);
			rightTerm.add(tmlLabel2);
		}

		// sliderGroup

		// Placeholder
		// Styles invisible = Styles.make(Style.HALIGN.center,
		// Style.COLOR.is(0x000000),
		// Style.BACKGROUND.is(Background.solid(0x000000, 5)));
		// leftTerm.add(new Label("Unklarheit und Bewegtheit", invisible));
		// rightTerm.add(new Label("Unklarheit und Bewegtheit", invisible));
		leftTerm.add(new Shim(125, 1));
		rightTerm.add(new Shim(125, 1));
		// sliderGroup.add(new Shim(250,1));

		sliderRoot.add(sliderGroup);
		begriffeLeft.add(leftTerm);
		begriffeRight.add(rightTerm);

		sliderRoot.setSize(250, 200);

		begriffeLeft.setSize(150, 200);
		begriffeRight.setSize(150, 200);

		setPos(800 / 2, 300);
		activeLayer.add(sliderRoot.layer);
		activeLayer.add(begriffeLeft.layer);
		activeLayer.add(begriffeRight.layer);

		for (Layer l1 : activeLayer) {
			quizContent.getDynamicLayer().add(l1);
		}

	}

	public void removeLayers() {
		for (Layer l : activeLayer) {
			quizContent.getDynamicLayer().remove(l);
		}
		activeLayer = new ArrayList<Layer>(0);
	}

	public void setPos(int x, int y) {
		begriffeLeft.layer.setTranslation(x - 150 - 250 / 2, y + 20);

		sliderRoot.layer.setTranslation(x - 250 / 2, y + 10);

		begriffeRight.layer.setTranslation(x + 250 / 2, y + 20);
	}

	/**
	 * 
	 * @param fillOutTime 
	 * @param joker
	 *            True if joker is clicked
	 */
	protected void sendeRating(double fillOutTimeMs) {
		PlayN.log().info("Sende HTML POST Request");

		Json.Writer w = PlayN.json().newWriter();

		w.object();
		w.value("SID", quizContent.getSessionID());
		w.value("QuestionNumber", quizContent.getCurrentQuestionNumber());

		if (reihenfolge[0]) {
			PlayN.log().info(
					"LinearVsMalerisch: " + slider0.value.get().intValue());
			w.value("LinearVsMalerisch", slider0.value.get().intValue());
		} else {
			PlayN.log().info(
					"LinearVsMalerisch: "
							+ (100 - slider0.value.get().intValue()));
			w.value("LinearVsMalerisch", 100 - slider0.value.get().intValue());
		}

		if (reihenfolge[1]) {
			PlayN.log().info("FlaecheVsTiefe: " + slider1.value.get());
			w.value("FlaecheVsTiefe", slider1.value.get().intValue());
		} else {
			PlayN.log()
					.info("FlaecheVsTiefe: "
							+ (100 - slider1.value.get().intValue()));
			w.value("FlaecheVsTiefe", 100 - slider1.value.get().intValue());
		}

		if (reihenfolge[2]) {
			PlayN.log().info(
					"GeschlossenVsOffen: " + slider2.value.get().intValue());
			w.value("GeschlossenVsOffen", slider2.value.get().intValue());
		} else {
			PlayN.log().info(
					"GeschlossenVsOffen: "
							+ (100 - slider2.value.get().intValue()));
			w.value("GeschlossenVsOffen", 100 - slider2.value.get().intValue());
		}

		if (reihenfolge[3]) {
			PlayN.log().info(
					"VielheitVsEinheit: " + slider3.value.get().intValue());
			w.value("VielheitVsEinheit", slider3.value.get().intValue());
		} else {
			PlayN.log().info(
					"VielheitVsEinheit: "
							+ (100 - slider3.value.get().intValue()));
			w.value("VielheitVsEinheit", 100 - slider3.value.get().intValue());
		}

		if (reihenfolge[4]) {
			PlayN.log().info(
					"KlarheitVsUnklarheitUndBewegtheit: "
							+ slider4.value.get().intValue());
			w.value("KlarheitVsUnklarheitUndBewegtheit", slider4.value.get()
					.intValue());
		} else {
			PlayN.log().info(
					"KlarheitVsUnklarheitUndBewegtheit: "
							+ (100 - slider4.value.get().intValue()));
			w.value("KlarheitVsUnklarheitUndBewegtheit", 100 - slider4.value
					.get().intValue());
		}
		
		PlayN.log().info("Time for filling out: "+ fillOutTimeMs);
		w.value("FillOutTimeMs", fillOutTimeMs);

		w.end();

		String post = "";
		if (joker) {
			post = quizContent.connectStringQuiz2;
		} else {
			post = quizContent.connectStringQuiz2;
		}

		PlayN.net().post(post, w.write(), new Callback<String>() {

			@Override
			public void onSuccess(String rating) {
				// parse
				Json.Object document = PlayN.json().parse(rating);
				answerRating = new int[4];
				answerRating[0] = document.getInt("A");
				answerRating[1] = document.getInt("B");
				answerRating[2] = document.getInt("C");
				answerRating[3] = document.getInt("D");
				try {
					increaseCorrectAnswerRating();
				} catch (Exception e) {
					e.printStackTrace();
				}

				int bonus = document.getInt("Bonus");
				// zeigeBonus(bonus);
				filledOut = true;

				zeigeBonus(bonus);

			}

			/*
			 * hack: the Publikumsjoker does not really help: So we just add
			 * some percentages to the correct answer to make it help ;))
			 */
			private void increaseCorrectAnswerRating() {

				double[] newanswerRating = new double[4];

				int nmbr = -1;
				int y = 0;

				for (int i = 0; i < 4; ++i) {
					if (quizContent.getCurrentQuestion().getAnswers()[i]
							.equals(quizContent.getCurrentQuestion()
									.getCorrectAnswer())) {
						nmbr = i;
					} else {
						if (answerRating[i] > 6) {
							newanswerRating[i] = answerRating[i] - 6;
							y += 6;
						}

					}
				}

				newanswerRating[nmbr] = answerRating[nmbr] + y;

				double denom = newanswerRating[0] + newanswerRating[1]
						+ newanswerRating[2] + newanswerRating[3];

				for (int i = 0; i < 4; ++i) {
					answerRating[i] = (int) ((newanswerRating[i] / denom) * 100);
				}

			}

			@Override
			public void onFailure(Throwable err) {
				// callback.error(err);
			}
		});
	}

	protected void setBalken(int[] answerRating) {

		this.jokerRoot = quizContent.iface.createRoot(AxisLayout.vertical(),
				null);
		jokerRoot.setSize(350, 315);
		jokerRoot.layer.setTranslation(223, 260);

		Group g = new Group(new TableLayout(COL, COL, COL, COL).gaps(115, 3),
				Styles.none());

		int j = 0;
		for (int i : answerRating) {

			String percentageText = "";

			if (i < 10) {
				percentageText = "  " + i + "%";
			} else if (i < 100) {
				percentageText = " " + i + "%";
			} else {
				percentageText = i + "%";
			}

			Label l = new Label(percentageText)
					.setStyles(QuizStyles.fontStylePlain);
			g.add(l);
			// Root root =
			// quizContent.iface.createRoot(AxisLayout.vertical().gap(20),
			// null);
			// root.setSize(50, 50);
			// root.layer.setTranslation(340 + j * 32, 340);

			// activeLayer.add(root.layer);
			// quizContent.getDynamicLayer().add(root.layer);

			// add a rectangle
			if (i > 0) {

				float height = 1.15f * (float) i;

				ImageLayer ratingBar = PlayN.graphics().createImageLayer();
				CanvasImage canvasImage2 = PlayN.graphics().createImage(30,
						(int) height);
				ratingBar.setImage(canvasImage2);

				canvasImage2.canvas().setFillColor(Color.RED2);
				canvasImage2.canvas().fillRect(0, 0,
						canvasImage2.canvas().width(),
						canvasImage2.canvas().height());
				ratingBar.setTranslation(333 + j * 33, 477 - height);

				activeLayer.add(ratingBar);
				quizContent.getDynamicLayer().add(ratingBar);
			}

			// add A B C D
			Root rootLetter = quizContent.iface.createRoot(AxisLayout
					.vertical().gap(20), null);
			rootLetter.setSize(50, 50);
			rootLetter.layer.setTranslation(150, 500);

			// ratingText.setTranslation(340 + j * 33, 475);

			j++;

		}

		for (int i = 0; i < 4; ++i) {
			String letter = "";
			if (i == 0) {
				letter = "A";
			} else if (i == 1) {
				letter = "B";
			} else if (i == 2) {
				letter = "C";
			} else {
				letter = "D";
			}

			Label l2 = new Label(letter).setStyles(QuizStyles.fontStylePlain);
			g.add(l2);
		}

		jokerRoot.add(g);
		activeLayer.add(jokerRoot.layer);
		quizContent.getDynamicLayer().add(jokerRoot.layer);

	}

	public void incNumberSlidersDragged() {
		numberSlidersDragged++;
		if(numberSlidersDragged == 1){
			//start Counter
			this.startSliderTime = PlayN.currentTime();
		}
		
		
		if (numberSlidersDragged == 5) {
			double fillOutTimeMs = PlayN.currentTime() - startSliderTime;
			sendeRating(fillOutTimeMs);
			//zeigeTags();
		}

		quizContent.getProgressLayer().addProgress(3);

	}

	private void zeigeBonus(int bonus) {

		if (quizContent.getCurrentQuestionNumber() > 4) {
			quizContent.getQuestionLayer().zeigeTodestage();
		}

		String text = "";
		// show teaser if availible
		if (!quizContent.getCurrentQuestion().getTeaser().equals("")) {
			text = quizContent.getCurrentQuestion().getTeaser();
			
			if (text.length() > 50 && text.contains(" ")) {
				String[] twoParts = quizContent.seperateTitelInTwoParts(text);

				InfoText t1 = new InfoText(quizContent, twoParts[0], 0, 100);
				InfoText t2 = new InfoText(quizContent, twoParts[1], 0, 130);
				quizContent.entities.add(t1);
				quizContent.entities.add(t2);

			} else {
				InfoText t = new InfoText(quizContent, text, 0, 100);
				quizContent.entities.add(t);

			}
		}
		// end show teaser
		

		BonusText bonusText = new BonusText(quizContent, bonus, 250, 310);
		quizContent.entities.add(bonusText);

		if (bonus == VOLLER_BONUS) {
			quizContent.getProgressLayer().addProgress(40);
			quizContent.addBonus(quizContent.getCurrentAmount() * 2);
		} else if (bonus == HALBER_BONUS) {
			quizContent.getProgressLayer().addProgress(25);
			quizContent.addBonus(quizContent.getCurrentAmount());
		} else {
			quizContent.getProgressLayer().addProgress(15);
			quizContent.addBonus(quizContent.getCurrentAmount() / 2);
		}

	}

	public void zeigePublikumsjoker() {
		removeLayers();

		ImageLayer imageLayer = graphics().createImageLayer(
				quizContent.imgBgPublikum);
		imageLayer.setScale(0.55f);

		imageLayer.setTranslation(800 / 2 - imageLayer.scaledWidth() / 2, 315);

		activeLayer.add(imageLayer);
		quizContent.getDynamicLayer().add(imageLayer);

		imageLayer = graphics().createImageLayer(quizContent.imgBgGitter);
		imageLayer.setScale(0.92f);

		imageLayer.setTranslation((800 / 2 - imageLayer.scaledWidth() / 2) - 2,
				360);

		activeLayer.add(imageLayer);

		quizContent.getDynamicLayer().add(imageLayer);

		setBalken(answerRating);

	}

	/**
	 * currently tags are not shown!
	 */
	public void zeigeTags() {
		removeLayers();

		Root tagRoot = quizContent.iface.createRoot(AxisLayout.horizontal(),
				QuizSimpleStyles.newSheet());
		tagRoot.setSize(400, 200);
		tagRoot.layer.setTranslation(200, 313);

		this.tagGroup = new Group(new AbsoluteLayout(), Styles.none());
		tagCounter = 0;

		
		for (Entry<String, Integer> tagEntry : quizContent.getCurrentQuestion().getTagList().entrySet()) {
			int x = (int) (PlayN.random() * 370);
			int y = (int) (PlayN.random() * 160);

			Label l = new Label(tagEntry.getKey().toUpperCase());
			
			int num = tagEntry.getValue();
			if(num <=1){
				l.setStyles(QuizStyles.fontStyleTag1);
			}else if (num <=3){
				l.setStyles(QuizStyles.fontStyleTag2);
			}else if (num <=7){
				l.setStyles(QuizStyles.fontStyleTag3);
			}else if (num <=20){
				l.setStyles(QuizStyles.fontStyleTag4);
			}else{
				l.setStyles(QuizStyles.fontStyleTag5);
			}

			l.layer.setAlpha(0);

			tagGroup.add(AbsoluteLayout.at(l, x, y));
		}
		tagRoot.add(tagGroup);

		activeLayer.add(tagRoot.layer);

		quizContent.getDynamicLayer().add(tagRoot.layer);

	}

	public void animateTags() {
		if (tagGroup != null) {
			if (tagCounter < 8) {
				int childCount = tagGroup.childCount();
				if(childCount == 0){
					return;
				}
				
				int x = (int) (PlayN.random() * childCount);
				final Layer labelLayer = tagGroup.childAt(x).layer;
				Element el = tagGroup.childAt(x);
				tagGroup.removeAt(x);
				tagGroup.add(el);
				
				tagCounter++;
				One animation = quizContent.anim.tweenAlpha(labelLayer);
				float time = 1000 + PlayN.random() * 4000;
			
				animation.in(time);
				animation.easeIn();
				animation.from(0);
				animation.to(1);
				quizContent.anim.delay(time + 6000 + PlayN.random() * 20000).then().action(new Runnable() {
					public void run() {
						One animation = quizContent.anim.tweenAlpha(labelLayer);
						animation.from(1);
						animation.in(1000);
						animation.easeOut();
						animation.to(0);
						tagCounter--;
					}
				});

			}

		}

	}

	public boolean isFilledOut() {
		return filledOut;
	}
}