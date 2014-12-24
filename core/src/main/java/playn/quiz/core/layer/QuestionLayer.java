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
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.quiz.core.QuizContent;
import playn.quiz.core.util.QuizStyles;
import playn.quiz.core.util.ResourceBundle;
import react.UnitSlot;
import tripleplay.anim.Animation.One;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Icons;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Shim;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.Stylesheet;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.layout.TableLayout;
import static tripleplay.ui.layout.TableLayout.COL;

/**
 * The Layer which shows the 4 possibilities to answer.
 * 
 * @author Jonas Hoelzler
 * 
 */
public class QuestionLayer {

	private Root questionRoot;
	public Button[] buttons;
	private QuizContent quizContent;
	private Button selectedButton;
	private double startAnimationTime;
	private boolean answerCorrect;
	private boolean playResult;

	private ImageLayer canvasLayer;
	private String[] answers;
	private int i;
	private Label[] labelArray;

	public QuestionLayer(final QuizContent quizContent, Interface iface,
			ResourceBundle rB) {
		this.quizContent = quizContent;

		Styles buttonStyles = Styles.none();

		Label questionLabel = new Label("   " + rB.string("QUESTION"))
				.addStyles(Style.BACKGROUND.is(Background
						.image((quizContent.header))));
		questionLabel.addStyles(QuizStyles.fontStyleBoldWhiteLeft2);

		// quizContent.header

		Stylesheet rootSheet = Stylesheet.builder()
				.add(Button.class, buttonStyles).create();
		if (questionRoot != null) {
			questionRoot.destroyAll();
		}
		this.questionRoot = iface.createRoot(
				AxisLayout.vertical().offStretch(), rootSheet);
		questionRoot.setSize(500, 100);
		questionRoot.layer.setTranslation(180, 500);

		Group g = new Group(new AbsoluteLayout()).add(AbsoluteLayout.at(
				questionLabel, 0, 0, 495, 30));

		questionRoot.add(g);
		setUpButtons();

		this.answers = quizContent.getCurrentQuestion().getAnswers();
		setUpText(answers);
		


	}

	private void setUpButtons() {

		this.buttons = new Button[4];
		UnitSlot[] unitSlot = new UnitSlot[4];

		this.buttons[0] = new Button("").addStyles(Style.BACKGROUND
				.is(Background.image((quizContent.buttonA))));

		buttons[0].addStyles(QuizStyles.fontStyleBoldWhiteLeft);

		this.buttons[1] = new Button("").addStyles(Style.BACKGROUND
				.is(Background.image((quizContent.buttonB))));

		buttons[1].addStyles(QuizStyles.fontStyleBoldWhiteLeft);

		this.buttons[2] = new Button("").addStyles(Style.BACKGROUND
				.is(Background.image((quizContent.buttonC))));

		buttons[2].addStyles(QuizStyles.fontStyleBoldWhiteLeft);

		this.buttons[3] = new Button("").addStyles(Style.BACKGROUND
				.is(Background.image((quizContent.buttonD))));

		buttons[3].addStyles(QuizStyles.fontStyleBoldWhiteLeft);

		unitSlot[0] = new UnitSlot() {
			public void onEmit() {
				PlayN.log().info("Antwort A");
				quizContent.gameClock.setVisible(false);
				evaluateAnswer(answers[0], buttons[0], 0);
			}
		};

		unitSlot[1] = new UnitSlot() {
			public void onEmit() {
				PlayN.log().info("Antwort B");
				quizContent.gameClock.setVisible(false);
				evaluateAnswer(answers[1], buttons[1], 1);
			}
		};

		unitSlot[2] = new UnitSlot() {
			public void onEmit() {
				PlayN.log().info("AntwortC");
				quizContent.gameClock.setVisible(false);
				evaluateAnswer(answers[2], buttons[2], 2);
			}
		};

		unitSlot[3] = new UnitSlot() {
			public void onEmit() {
				PlayN.log().info("Antwort D");
				quizContent.gameClock.setVisible(false);
				evaluateAnswer(answers[3], buttons[3], 3);
			}
		};

		buttons[0].clicked().connect(unitSlot[0]);
		buttons[1].clicked().connect(unitSlot[1]);
		buttons[2].clicked().connect(unitSlot[2]);
		buttons[3].clicked().connect(unitSlot[3]);

		// Group groupAB = new Group(AxisLayout.horizontal().offEqualize());
		// Group groupCD = new Group(AxisLayout.horizontal().offEqualize());
		// groupAB.add(buttons[0]);
		// groupAB.add(buttons[1]);
		// groupCD.add(buttons[2]);
		// groupCD.add(buttons[3]);

		Group group = new Group(new TableLayout(COL, COL).gaps(1, 1)).add(
				new Group(new AbsoluteLayout()).add(AbsoluteLayout.at(
						buttons[0], 0, 0, 243, 30)), new Group(
						new AbsoluteLayout()).add(AbsoluteLayout.at(buttons[1],
						0, 0, 243, 30)), new Group(new AbsoluteLayout())
						.add(AbsoluteLayout.at(buttons[2], 0, 0, 243, 30)),
				new Group(new AbsoluteLayout()).add(AbsoluteLayout.at(
						buttons[3], 0, 0, 243, 30)));

		 buttons[0].layer.setAlpha(0);
		 buttons[1].layer.setAlpha(0);
		 buttons[2].layer.setAlpha(0);
		 buttons[3].layer.setAlpha(0);
		
		 One animation = quizContent.anim.tweenAlpha(buttons[0].layer);

		 animation.in(500);
		 animation.from(0);
		 animation.easeIn();
		 animation.to(1);
		
		 quizContent.anim.delay(300).then().action(new Runnable() {
		 public void run() {
		 One animation = quizContent.anim.tweenAlpha(buttons[1].layer);
		 animation.in(500);
		 animation.from(0);
		 animation.easeIn();
		 animation.to(1);
		 }
		 });
		
		 quizContent.anim.delay(600).then().action(new Runnable() {
		 public void run() {
		 One animation = quizContent.anim.tweenAlpha(buttons[2].layer);
		 animation.in(500);
		 animation.from(0);
		 animation.easeIn();
		 animation.to(1);
		 }
		 });
		
		 quizContent.anim.delay(900).then().action(new Runnable() {
		 public void run() {
		 One animation = quizContent.anim.tweenAlpha(buttons[3].layer);
		 animation.in(500);
		 animation.from(0);
		 animation.easeIn();
		 animation.to(1);
		 }
		 });
		questionRoot.add(group);

	}

	private void setUpText(final String[] texts) {

		buttons[0].text.update("       " + texts[0]);
		buttons[1].text.update("       " + texts[1]);
		buttons[2].text.update("       " + texts[2]);
		buttons[3].text.update("       " + texts[3]);

		// this.labelArray = new Label[4];
		// labelArray[0] = new Label(texts[0])
		// .setStyles(QuizStyles.fontStyleBoldWhiteLeft);
		// labelArray[1] = new Label(texts[1])
		// .setStyles(QuizStyles.fontStyleBoldWhiteLeft);
		// labelArray[2] = new Label(texts[2])
		// .setStyles(QuizStyles.fontStyleBoldWhiteLeft);
		// labelArray[3] = new Label(texts[3])
		// .setStyles(QuizStyles.fontStyleBoldWhiteLeft);
		//
		// for (int i = 0; i < 4; ++i) {
		// if (!buttons[i].isEnabled()) {
		// labelArray[i]
		// .setStyles(QuizStyles.fontStyleBoldWhiteLeftInvisible);
		// }
		// }

	}

	public Layer getLayer() {
		return questionRoot.layer;
	}

	public void setPos(int x, int y) {

		questionRoot.layer.setTranslation(x, y);

	}

	public void startAnimation(boolean answerCorrect, Button button, int i) {
		this.i = i;
		this.startAnimationTime = PlayN.currentTime();
		this.answerCorrect = answerCorrect;
		this.selectedButton = button;
		this.playResult = true;
	}

	public void playAnimation() {
		if (playResult) {
			if (PlayN.currentTime() - startAnimationTime < 700) {
				return;
			} else if ((((int) (PlayN.currentTime() - startAnimationTime) / 400) % 3) == 0) {
				if (answerCorrect) {
					if (i == 0) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonAYellow))));

					} else if (i == 1) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonAYellow))));
					} else if (i == 2) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonCYellow))));
					} else if (i == 3) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonDYellow))));
					}

				} else {
					if (i == 0) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonAYellow))));
					} else if (i == 1) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonBYellow))));
					} else if (i == 2) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonCYellow))));
					} else if (i == 3) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonDYellow))));
					}
				}

			} else {

				if (answerCorrect) {

					if (i == 0) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonAGreen))));
					} else if (i == 1) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonBGreen))));
					} else if (i == 2) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonCGreen))));
					} else if (i == 3) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonDGreen))));
					}

				} else {

					// set correct button green
					int correctButtonPos = quizContent.getCurrentQuestion()
							.getCorrectAnswerNum();

					if (correctButtonPos == 0) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonAGreen))));
					} else if (correctButtonPos == 1) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonBGreen))));
					} else if (correctButtonPos == 2) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonCGreen))));
					} else if (correctButtonPos == 3) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonDGreen))));
					}

					// end set correct Button green

					if (i == 0) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonARed))));
					} else if (i == 1) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonBRed))));
					} else if (i == 2) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonCRed))));
					} else if (i == 3) {
						selectedButton.addStyles(Style.BACKGROUND.is(Background
								.image((quizContent.buttonDRed))));
					}
				}
			}

			if (PlayN.currentTime() - startAnimationTime > 3000) {
				playResult = false;
				quizContent.playVorspannAnimation(answerCorrect);
			}
		}

	}

	public void evaluateAnswer(String answer, Button button, int i) {

		quizContent.getJokerLayer().setButtonsLocked(true);

		for (Button b : buttons) {
			b.setEnabled(false);
		}

		this.answerCorrect = quizContent.getCurrentQuestion().validateAnswer(
				answer);
		quizContent.setAnswerCorrect(answerCorrect);
		if (i == 0) {
			button.addStyles(Style.BACKGROUND.is(Background
					.image((quizContent.buttonAYellow))));
		} else if (i == 1) {
			button.addStyles(Style.BACKGROUND.is(Background
					.image((quizContent.buttonBYellow))));
		} else if (i == 2) {
			button.addStyles(Style.BACKGROUND.is(Background
					.image((quizContent.buttonCYellow))));
		} else if (i == 3) {
			button.addStyles(Style.BACKGROUND.is(Background
					.image((quizContent.buttonDYellow))));
		}

		PlayN.log().info("" + answerCorrect);

		startAnimation(answerCorrect, button, i);
		// quizContent.getRatingLayer().startAnimation(answerCorrect);

	}

	public void update(float delta) {

		playAnimation();

	}

	/**
	 * sets 50/50-Joker Removes 2 wrong answers
	 */
	public void set5050() {


		// String[] texts = new String[4];
		//
		// for (int k = 0; k < 4; ++k) {
		// texts[k] = answers[k];
		// }
		//
		int i = 0;
		// String cleared = "";
		while (i != 2) {
			float f = PlayN.random();

			Button b;
			String answer;
			int j = 0;
			if (f < 0.25f) {
				j = 0;
			} else if (f < 0.50) {
				j = 1;
			} else if (f < 0.75) {
				j = 2;
			} else {
				j = 3;
			}

			b = buttons[j];
			//label = labelArray[j];
			answer = answers[j];

			if (b.isEnabled()
					&& !answer.equals(quizContent.getCurrentQuestion()
							.getCorrectAnswer())) {
				clearAnswer(b);
				// texts[j] = " ";
				i++;
				// cleared = answer;
			}

		}

	}

	private void clearAnswer(Button button) {
		button.setEnabled(false);
		button.setStyles(QuizStyles.fontStyleBoldWhiteLeftInvisible);

	}

	public void zeigeTodestage() {
		// quizContent.getStaticLayerFront().remove(canvasLayer);
		// PlayN.graphics().createFont("Verdana", Font.Style.BOLD, 12);

		Integer[] deaths = quizContent.getCurrentQuestion().getDeaths();

		String[] texts = new String[4];

		for (int i = 0; i < 4; ++i) {
			if (answers[i].length() > 15) {
				String[] tmp = answers[i].split(" ");
				int z = tmp.length - 1;
				texts[i] = tmp[z];
			} else {
				texts[i] = answers[i];
			}

			if (deaths[i] > 2012) {
				texts[i] += " (lebend)";
			} else {
				texts[i] += " (†" + deaths[i] + ")";
			}

		}
		setUpText(texts);

	}

	public Layer getCanvasLayer() {
		return this.canvasLayer;
	}
}