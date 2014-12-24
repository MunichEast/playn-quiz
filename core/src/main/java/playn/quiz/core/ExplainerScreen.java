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
 * the License.s
 */
package playn.quiz.core;

import static playn.core.PlayN.*;
import playn.core.AssetWatcher;
import playn.core.CanvasImage;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.PlayN;
import playn.quiz.core.util.Begriffssystematik;
import playn.quiz.core.util.Color;
import playn.quiz.core.util.Pol;
import playn.quiz.core.util.QuizSimpleStyles;
import playn.quiz.core.util.QuizStyles;
import react.UnitSlot;
import tripleplay.anim.Animation.One;
import tripleplay.anim.Animator;
import tripleplay.ui.Button;
import tripleplay.ui.Field;
import tripleplay.ui.Group;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Styles;
import tripleplay.ui.layout.AxisLayout;

/**
 * A demo that displays a menu of the available demos.
 */
public class ExplainerScreen extends Screen {

	private Quiz quiz;

	private Interface iface;
	private GroupLayer layer;

	private int cnt2;
	private Field loadingText;
	private boolean buttonLoaded = false;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	private Styles labelStyleHeader;
	private int cnt = 0;
	private Styles labelStyleStichpunkt;

	private Image image1;

	private Image image2;

	private Pol[] pole;

	private Animator anim;

	private boolean started = false;

	private ImageLayer bgLayer;
	private Root loaderRoot;
	// muss zerstört werden
	private Root descriptionRootLeft;
	private Root descriptionRootRight;

	private GroupLayer imageGroupLeft;

	private GroupLayer imageGroupRight;

	public int nextInt = 0;

	private int[] r;

	private Button nextButton;

	public ExplainerScreen(Quiz quiz) {
		this.quiz = quiz;
	}

	@Override
	public String name() {
		return "ExplainerScreen";
	}

	@Override
	public void init() {

		ermittleZufallsreiheinfolge();

		buttonLoaded = false;

		iface = new Interface();
		layer = graphics().rootLayer();

		loadBackground();

		loadLoadingHeader();

		nextExplainer();

		Root nextRoot = iface.createRoot(AxisLayout.vertical(),
				QuizSimpleStyles.newSheet(), layer);
		nextRoot.setSize(150, 50);
		nextRoot.layer.setTranslation(
				(WIDTH / 2 - nextRoot.size().width() / 2) + 250, 550);

		// Next-Button
		this.nextButton = new Button("Next (" + (nextInt + 1) + "/5)");

		// continueButton.addStyles(Styles.make(Style.FONT.is(QuizStyles.verdana20Bold),
		// Style.BACKGROUND.is(Background
		// .solid(Color.BLUE))));
		nextButton.setEnabled(true);
		nextButton.clicked().connect(new UnitSlot() {
			public void onEmit() {
				nextInt++;

				if (nextInt < 5) {
					cleanUpOldExplainer();
					nextExplainer();
				}
				if (nextInt == 4) {
					nextButton.setEnabled(false);
					nextButton.setVisible(false);

				}

			}

		});
		nextRoot.add(nextButton);
		layer.add(nextRoot.layer);

	}

	private void ermittleZufallsreiheinfolge() {
		this.r = new int[5];
		r[0] = -1;
		r[1] = -1;
		r[2] = -1;
		r[3] = -1;
		r[4] = -1;

		for (int i = 0; i < 5; ++i) {
			r[i] = (int) (PlayN.random() * 5f);
			for (int j = 0; j < i; ++j) {
				if (r[i] == r[j]) {
					i--;
					break;
				}
			}
		}

	}

	private void loadLoadingHeader() {

		this.labelStyleHeader = QuizStyles.LABEL_STYLE_STICHPUNKT_HEADER;
		this.labelStyleStichpunkt = QuizStyles.LABEL_STYLE_STICHPUNKT;
		Styles loader = QuizStyles.EXPLAINER_HEADER_STYLE;
		this.loadingText = new Field(quiz.rB.string("PLEASE_WAIT") + ".  !",
				loader);

		this.loaderRoot = iface.createRoot(AxisLayout.horizontal(),
				QuizSimpleStyles.newSheet(), layer);

		loaderRoot.setSize(200, 80);
		loaderRoot.layer.setTranslation(300, 0);

		loaderRoot.add(loadingText);

		layer.add(loaderRoot.layer);

	}

	private void loadBackground() {
		// load and show our background
		this.bgLayer = PlayN.graphics().createImageLayer();
		CanvasImage canvasImage = PlayN.graphics().createImage(WIDTH, HEIGHT);
		bgLayer.setImage(canvasImage);

		canvasImage.canvas().setFillColor(Color.BLACK);
		canvasImage.canvas().fillRect(0, 0, WIDTH, HEIGHT);
		canvasImage.canvas().setFillColor(Color.BGCOLOR);
		canvasImage.canvas().fillRect(1, 1, WIDTH - 2, HEIGHT - 2);
		// upper box
		canvasImage.canvas().setFillColor(Color.BLUE);
		canvasImage.canvas().fillRect(20, 10, 760, 50);
		// left box
		canvasImage.canvas().setFillColor(Color.BLUE);
		canvasImage.canvas().fillRect(40, 380, 320, 170);
		// right box
		canvasImage.canvas().setFillColor(Color.BLUE);
		canvasImage.canvas().fillRect(440, 380, 320, 170);
		
		CanvasImage canvasImageHeader = PlayN.graphics().createImage(WIDTH, HEIGHT);

		layer.add(bgLayer);

	}

	protected void showExplainer() {
		AxisLayout l = new AxisLayout.Vertical().gap(5);

		this.imageGroupLeft = paintImageWithShadow("left", image1);
		layer.add(imageGroupLeft);
		this.imageGroupRight = paintImageWithShadow("right", image2);
		layer.add(imageGroupRight);

		this.descriptionRootLeft = iface.createRoot(AxisLayout.horizontal(),
				QuizSimpleStyles.newSheet(), layer);
		Group leftText = new Group(l);
		leftText.add(new Label(pole[0].getName()).setStyles(labelStyleHeader));
		for (String pString : pole[0].getStichpunkte()) {
			leftText.add(new Field(pString, labelStyleStichpunkt));
		}

		descriptionRootLeft.setSize(WIDTH / 2, 120);
		descriptionRootLeft.layer.setTranslation(0, 400);

		descriptionRootLeft.add(leftText);

		layer.add(descriptionRootLeft.layer);

		Group rightText = new Group(l);
		rightText.add(new Label(pole[1].getName()).setStyles(labelStyleHeader));
		for (String r : pole[1].getStichpunkte()) {
			rightText.add(new Field(r, labelStyleStichpunkt));
		}

		this.descriptionRootRight = iface.createRoot(AxisLayout.horizontal(),
				QuizSimpleStyles.newSheet(), layer);

		descriptionRootRight.setSize(WIDTH / 2, 120);
		descriptionRootRight.layer.setTranslation(WIDTH / 2, 400);

		descriptionRootRight.add(rightText);

		layer.add(descriptionRootRight.layer);

		this.started = true;
	}

	protected void nextExplainer() {

		this.pole = Begriffssystematik.getBegriffspaar(r[nextInt], quiz.rB);

		String imageURL1 = pole[0].getImage();

		this.image1 = PlayN.assets().getImage(imageURL1);

		String imageURL2 = pole[1].getImage();

		this.image2 = PlayN.assets().getImage(imageURL2);

		AssetWatcher assetWatcher = new AssetWatcher(
				new AssetWatcher.Listener() {
					@Override
					public void done() {
						showExplainer();

					}

					@Override
					public void error(Throwable e) {
						PlayN.log().error(
								"Error loading Quiz Game: " + e.getMessage());
						// showRetryButton();
					}

				});

		assetWatcher.add(image1);
		assetWatcher.add(image2);

		assetWatcher.start();

		createNextButton();

	}

	private void cleanUpOldExplainer() {

		descriptionRootLeft.destroy();
		descriptionRootRight.destroy();
		imageGroupLeft.destroy();
		imageGroupRight.destroy();
	}

	private void createNextButton() {
		if (nextInt != 0)
			nextButton.text.update("Next (" + (nextInt + 1) + "/5)");
	}

	private GroupLayer paintImageWithShadow(String side, Image image) {

		ImageLayer imageLayer = graphics().createImageLayer(image);

		float imgHeight = imageLayer.height();
		float imgWidth = imageLayer.width();

		float innerWidth = 370f;
		float innerHeight = 280f;

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

		GroupLayer imageGroup = PlayN.graphics().createGroupLayer();
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

		imageGroup.add(imageLayerBg);
		imageGroup.add(imageLayer);

		if (side.equals("left")) {
			imageGroup.setTranslation(5, 70);
		} else {
			imageGroup.setTranslation(WIDTH / 2 + 5, 70);
		}

		return imageGroup;

	}

	@Override
	public void shutdown() {
		if (iface != null) {
			pointer().setListener(null);
			iface.destroyRoot(loaderRoot);
			iface = null;
		}

		layer.destroy();
		layer = null;
	}

	@Override
	public void update(int delta) {
		clock.update(delta);
		if (iface != null) {
			iface.update(delta);
		}

		if (started) {
			if (!buttonLoaded) {
				paintLoader();
			}
		}

		if (anim != null) {
			cnt += delta;
			// Migration: update(cnt) -> paint(clock)
			anim.paint(clock);

			if (cnt > 2000) {
				loadingText.setVisible(false);
			}

		}

	}

	private void loadButton() {
		final Button continueButton = new Button(" "
				+ quiz.rB.string("STARTEN") + "... ");
		// continueButton.addStyles(Styles.make(Style.FONT.is(QuizStyles.verdana20Bold),
		// Style.BACKGROUND.is(Background
		// .solid(Color.BLUE))));
		continueButton.setEnabled(true);
		continueButton.clicked().connect(new UnitSlot() {
			public void onEmit() {
				continueButton.setEnabled(false);
				quiz.activateQuizContent();
			}

		});
		Root startRoot = iface.createRoot(AxisLayout.vertical(),
				QuizSimpleStyles.newSheet(), layer);
		startRoot.setSize(150, 50);
		startRoot.layer.setTranslation(
				(WIDTH / 2 - startRoot.size().width() / 2) + 350, 550);

		layer.add(startRoot.layer);

		startRoot.add(continueButton);

	}

	private void paintLoader() {
		Styles loader = QuizStyles.EXPLAINER_HEADER_STYLE;

		if ((cnt2 / 25) % 3 == 1) {
			loaderRoot.remove(loadingText);
			this.loadingText = new Field(
					quiz.rB.string("PLEASE_WAIT") + ".  !", loader);
			loaderRoot.add(loadingText);
			cnt2++;
		} else if ((cnt2 / 25) % 3 == 2) {
			loaderRoot.remove(loadingText);
			this.loadingText = new Field(
					quiz.rB.string("PLEASE_WAIT") + ".. !", loader);
			loaderRoot.add(loadingText);
			cnt2++;
		} else {
			loaderRoot.remove(loadingText);
			this.loadingText = new Field(
					quiz.rB.string("PLEASE_WAIT") + "...!", loader);
			loaderRoot.add(loadingText);
			cnt2++;
		}
	}

	@Override
	public void paint(float alpha) {
		clock.paint(alpha);
		if (iface != null) {
			iface.paint(clock);
		}
	}

	public void showStartButton() {
		buttonLoaded = true;

		fadeOutLoadingText();

		loadButton();
	}

	private void fadeOutLoadingText() {
		// loadingText.setVisible(false);
		this.anim = new Animator();
		One animation = anim.tweenAlpha(loadingText.layer);
		animation.from(1);
		animation.in(2000);
		animation.easeOut();
		animation.to(0);
	}
}
