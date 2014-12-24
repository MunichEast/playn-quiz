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
import playn.core.Game;
import playn.core.PlayN;
import playn.core.util.Callback;
import playn.quiz.core.entities.HighScoreScreen;
import playn.quiz.core.entities.Score;
import playn.quiz.core.util.ResourceBundle;
//import playn.core.ResourceCallback;

import static playn.core.PlayN.*;

/**
 * The main entry point for the quiz game. Migration: Extends Defaults
 */
public class Quiz extends Game.Default {

	private Screen activeDemo;
	private Screen quizContent;
	public ResourceBundle rB;

	boolean contentLoaded = false;
	protected Menu menuScreen;

	// Migration
	public Quiz() {
		super(Screen.UPDATE_RATE);
	}

	public void activateScreen(Screen demo) {
		if (activeDemo != null) {
			activeDemo.shutdown();
		}
		activeDemo = demo;
		PlayN.log().info("Starting " + demo.name());
		activeDemo.init();

	}

	@Override
	public void init() {

		// Migration: Set size gibt es nicht mehr
		// wird bei Java bzw. Html festgelegt
		// PlayN.graphics().setSize(800, 600);

		Screen intro = new IntroScreen(this);
		activateScreen(intro);

		// For Tests

		// Menu menuScreen = new Menu(this);
		// activateScreen(menuScreen);

		// Screen testScreen = new TestScreen(this);
		// activateScreen(testScreen);
		// activateExplainer();
		// activateHighScoreScreen(new Score(0,0,0));

	}

	@Override
	public void update(int delta) {
		activeDemo.update(delta);
	}

	@Override
	public void paint(float alpha) {
		activeDemo.paint(alpha);
	}

	public void activateMenu() {
		this.menuScreen = new Menu(this);
		activateScreen(menuScreen);
	}

	public void activateExplainer() {
		final ExplainerScreen explainer = new ExplainerScreen(this);
		activateScreen(explainer);

		QuizLoader.CreateWorld(this, new Callback<QuizContent>() {
			@Override
			public void onSuccess(QuizContent resource) {
				quizContent = resource;
				contentLoaded = true;
				explainer.showStartButton();

			}

			@Override
			public void onFailure(Throwable err) {
				PlayN.log().error(
						"Error loading Quiz Game: " + err.getMessage());
				explainer.showStartButton();
			}
		});

	}

	public void activateQuizContent() {
		activateScreen(quizContent);
	}

	public boolean isQuizLoaded() {
		return contentLoaded;
	}

	public void activateHighScoreScreen(Score score) {
		HighScoreScreen highscoreScreen = new HighScoreScreen(this);
		highscoreScreen.enterScore(score);
		activateScreen(highscoreScreen);
		quizContent = null;

	}

	/**
	 * set Language to en oder de
	 * 
	 * @param menu
	 * @param string
	 */
	public void setLanguage(String locale, Menu menu) {

		if (locale == null) {
			rB = new ResourceBundle("Label_de.properties", menu);
		} else {
			if (locale.equals("en")) {
				rB = new ResourceBundle("Label_en.properties", menu);
			} else {
				rB = new ResourceBundle("Label_de.properties", menu);
			}
		}

	}

}
