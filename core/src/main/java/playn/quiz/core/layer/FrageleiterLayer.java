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
import playn.core.PlayN;
import playn.quiz.core.Quiz;
import playn.quiz.core.QuizContent;
import playn.quiz.core.util.QuizStyles;
import tripleplay.ui.Background;
import tripleplay.ui.Group;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.layout.AxisLayout;

public class FrageleiterLayer {

	private Root frageleiterRoot;
	private QuizContent quizContent;
	private int numQuestion = 0;

	Styles buttonStyles = Styles.none()
			.add(Style.BACKGROUND.is(Background.solid(0xFFFFFFFF)))
			.addSelected(Style.BACKGROUND.is(Background.solid(0xFFFFFFFF)));
	// private Label[] labelsFrageLeiter;
	private Group gewinnstufenGroup;
	private Quiz quiz;

	public FrageleiterLayer(final QuizContent quizContent, Interface iface,
			String[] gewinnstufen, Quiz quiz) {
		PlayN.log().info("Erstelle Frageleiter");
		this.quizContent = quizContent;
		this.quiz = quiz;

		this.frageleiterRoot = iface.createRoot(AxisLayout.vertical().gap(1),
				null);
		AxisLayout l = new AxisLayout.Vertical().gap(1);

		this.gewinnstufenGroup = new Group(l, Style.HALIGN.left);

		Label labelFrageleiter = new Label("1 - " + gewinnstufen[0] + " " + quiz.rB.string("PUNKTE") + ".")
				.setStyles(QuizStyles.fontStyleQ);
		// labelsFrageLeiter[0] = labelFrageleiter;
		gewinnstufenGroup.add(0, labelFrageleiter);
		for (int i = 1; i < 15; ++i) {
			labelFrageleiter = new Label("" + (i + 1) + " - " + gewinnstufen[i]
					+ " " + quiz.rB.string("PUNKTE") + ".")
					.setStyles(QuizStyles.fontStyle13);
			// labelsFrageLeiter[i] = labelFrageleiter;
			gewinnstufenGroup.add(0, labelFrageleiter);

		}
		frageleiterRoot.add(gewinnstufenGroup);
		frageleiterRoot.setSize(135, 300);
		frageleiterRoot.layer.setTranslation(800 - 135 - 7, 160);
		quizContent.getStaticLayerFront().add(frageleiterRoot.layer);
		PlayN.log().info("Frageleiter erstellt");
	}

	public void nextQuestion() {
		// labelsFrageLeiter[numQuestion].setStyles(fontStyle);
		gewinnstufenGroup.childAt(14 - numQuestion).setStyles(
				QuizStyles.fontStyle13);
		numQuestion++;
		// labelsFrageLeiter[numQuestion].setStyles(fontStyleQ);
		gewinnstufenGroup.childAt(14 - numQuestion).setStyles(
				QuizStyles.fontStyleQ);
	}

	public void removeLayers() {
		quizContent.getStaticLayerFront().remove(frageleiterRoot.layer);
	}
}