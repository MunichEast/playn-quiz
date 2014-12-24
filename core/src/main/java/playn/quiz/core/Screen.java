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
import playn.core.Keyboard;
import playn.core.PlayN;
import playn.core.util.Clock;

/**
 * Demonstrates a particular PlayN feature or set of features. This interface is
 * kind of like {@link Game} except that it has lifecycle methods for cleaning
 * up as well as initializing. Demos also all share a predefined update rate (25
 * fps).
 * 
 * The connect-Strings are for the connection to the Server
 */
public abstract class Screen {

	// Migration
	public static final int UPDATE_RATE = 25;
	public final Clock.Source clock = new Clock.Source(UPDATE_RATE);

	// for HTML5 we must work with ../../../artigo,
	// for Java without /artigo...
	// for contacting the online system with https://artigo.org/seam...
	// public final String connectStringQuiz =
	// "../../../artigo/seam/resource/quiz";
	public final String connectStringQuiz = "/artigo/seam/resource/quiz";
	public final String connectStringQuiz2 = "/artigo/seam/resource/rating";
	public final String connectStringHighScore = "/artigo/seam/resource/highscore";
	public final String imageString = "/artigo/seam/resource/image/";
	public final String connectStringLocale = "/artigo/seam/resource/locale";

	/**
	 * Returns the name of this demo.
	 */
	public abstract String name();

	/**
	 * Initializes this demo. Here is where listeners should be wired up and
	 * resources loaded.
	 */
	public abstract void init();

	/**
	 * Shuts down this demo. Listeners should be cleared and resources
	 * destroyed.
	 */
	public abstract void shutdown();

	/**
	 * Called every update tick while this demo is active.
	 * 
	 * @param delta
	 *            the amount of time that has elapsed since the last update
	 *            call.
	 */
	public void update(int delta) {
	}

	/**
	 * Called while this demo is active, to paint the demo.
	 * 
	 * @param alpha
	 *            a value in the range [0,1) that represents the fraction of the
	 *            update tick that has elapsed since the last call to update.
	 */
	public void paint(float alpha) {
	}

	/**
	 * Because the quiz uses a few keys to move between demos, a demo must not
	 * register a keyboard listener directly, but instead return its listener
	 * from this method. This allows the quiz to intercept the keys it needs and
	 * to pass on other key events to the demo.
	 */
	public Keyboard.Listener keyboardListener() {
		return null;
	}

}
