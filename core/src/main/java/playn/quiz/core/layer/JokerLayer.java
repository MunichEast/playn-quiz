/**
5050.jpg * Copyright 2011 The PlayN Authors
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
import playn.quiz.core.QuizContent;
import playn.quiz.core.entities.InfoText;
import playn.quiz.core.util.Color;
import playn.quiz.core.util.QuizStyles;
import playn.quiz.core.util.ResourceBundle;
import react.UnitSlot;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Icons;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.layout.AxisLayout;

public class JokerLayer {

	private Root jokerRoot;
	private QuizContent quizContent;

	private Button labelPublikumsjoker;
	private Button label5050;
	private Button labelDatierung;
	public int freigeschalten = 0;
	private boolean locked;

	public JokerLayer(final QuizContent quizContent, Interface iface) {
		this.quizContent = quizContent;
		this.jokerRoot = iface.createRoot(AxisLayout.vertical().gap(3), null);
		AxisLayout l = new AxisLayout.Vertical().gap(3);
		Group jokerGroup = new Group(l, Style.HALIGN.left);

		this.labelPublikumsjoker = new Button(
				(Icons.image(quizContent.imgJokerPublikumGrey)));

		labelPublikumsjoker.clicked().connect(new UnitSlot() {
			public void onEmit() {
				if (locked) {
					return;
				}
				// PlayN.log().info("Publikumsjoker");

				if (!quizContent.getRatingLayer().isFilledOut()) {
					// PlayN.log().info("Erst ausfüllen!");
					InfoText t = new InfoText(quizContent, quizContent.quiz.rB
							.string("RATE_FIRST"), 0, 0);
					quizContent.entities.add(t);
				} else {
					quizContent.getRatingLayer().zeigePublikumsjoker();
					labelPublikumsjoker.icon.update(Icons
							.image(quizContent.imgJokerPublikumGone));
					labelPublikumsjoker.setEnabled(false);
				}

			}
		});

		jokerGroup.add(labelPublikumsjoker);

		this.label5050 = new Button(Icons.image(quizContent.img5050JokerGrey));

		label5050.clicked().connect(new UnitSlot() {
			public void onEmit() {
				if (locked) {
					return;
				}
				// //PlayN.log().info("Joker 5050");
				quizContent.getQuestionLayer().set5050();
				label5050.icon.update(Icons.image(quizContent.joker5050Gone));
				label5050.setEnabled(false);

			}
		});

		jokerGroup.add(label5050);

		this.labelDatierung = new Button(
				Icons.image(quizContent.imgJokerDeathGrey));
		labelDatierung.clicked().connect(new UnitSlot() {
			public void onEmit() {
				if (locked) {
					return;
				}
				// PlayN.log().info("Datierung");
				quizContent.zeigeDatierung();
				labelDatierung.icon.update(Icons
						.image(quizContent.imgJokerDeathGone));
				labelDatierung.setEnabled(false);
			}
		});

		jokerGroup.add(labelDatierung);

		Label placeholder = new Label("Bonus:").setStyles(Styles.make(
				Style.FONT.is(QuizStyles.verdana16Bold), Style.HALIGN.center,
				Style.BACKGROUND.is(Background.blank())));

		// jokerGroup.add(placeholder);

		jokerRoot.add(0, jokerGroup);
		jokerRoot.setSize(130, 400);
		jokerRoot.layer.setTranslation(15, 290);

		label5050.setEnabled(false);
		labelDatierung.setEnabled(false);
		labelPublikumsjoker.setEnabled(false);

		quizContent.getDynamicLayer().add(jokerRoot.layer);
	}

	public void removeLayers() {
		if (jokerRoot.layer != null) {
			quizContent.getDynamicLayer().remove(jokerRoot.layer);
		}

	}

	public void schalteBonusFrei() {

		if (label5050.isEnabled() && labelPublikumsjoker.isEnabled()
				&& labelDatierung.isEnabled()) {
			return;
		}

		boolean done = false;
		while (!done) {
			double x = PlayN.random();
			if (x < 0.33) {
				if (!label5050.isEnabled()) {
					InfoText t = new InfoText(quizContent,
							quizContent.quiz.rB.string("NEW_JOKER_AVAILABLE"),
							0, 0);
					quizContent.entities.add(t);
					label5050.setEnabled(true);
					label5050.icon
							.update(Icons.image(quizContent.img5050Joker));
					done = true;
				}

			} else if (x < 0.66) {
				if (!labelPublikumsjoker.isEnabled()) {
					InfoText t = new InfoText(quizContent,
							quizContent.quiz.rB.string("NEW_JOKER_AVAILABLE"),
							0, 0);
					quizContent.entities.add(t);
					labelPublikumsjoker.setEnabled(true);
					labelPublikumsjoker.icon.update(Icons
							.image(quizContent.imgJokerPublikum));
					done = true;
				}

			} else if (x < 1.01) {
				if (!labelDatierung.isEnabled()) {
					InfoText t = new InfoText(quizContent,
							quizContent.quiz.rB.string("NEW_JOKER_AVAILABLE"),
							0, 0);
					quizContent.entities.add(t);
					labelDatierung.setEnabled(true);
					labelDatierung.icon.update(Icons
							.image(quizContent.imgJokerDeath));
					done = true;
				}

			}
		}

		freigeschalten++;
	}

	public void setButtonsLocked(boolean locked) {
		this.locked = locked;
	}

}