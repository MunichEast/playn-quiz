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
import playn.core.CanvasImage;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.quiz.core.QuizContent;
import playn.quiz.core.util.Color;
import tripleplay.anim.Animation.One;
import tripleplay.ui.Background;
import tripleplay.ui.Interface;
import tripleplay.ui.Root;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.layout.AxisLayout;

public class ProgressLayer {

	private QuizContent quizContent;

	private Root progressRoot;
	private float progressValue = 1;
	private ImageLayer percentageCanvas;

	private ImageLayer ratingBar;

	private final static int BAR_WIDTH = 100;
	private final static int Y_POS = 185;
	private final static int BAR_HEIGHT = 200;

	public ProgressLayer(final QuizContent quizContent, Interface iface) {
		this.quizContent = quizContent;

		Styles styles = Styles.none().add(
				Style.BACKGROUND.is(Background.solid(Color.BLUE)));

		// add a rectangle
		this.ratingBar = PlayN.graphics().createImageLayer();
		CanvasImage canvasImage = PlayN.graphics().createImage(BAR_WIDTH,
				BAR_HEIGHT);
		ratingBar.setImage(canvasImage);

		canvasImage.canvas().setFillColor(Color.BLACK);
		canvasImage.canvas().fillRect(0, 0, canvasImage.canvas().width(),
				canvasImage.canvas().height());
		ratingBar.setTranslation(75 - BAR_WIDTH / 2, Y_POS);

		quizContent.getStaticLayerBack().add(ratingBar);

		this.progressRoot = iface.createRoot(AxisLayout.horizontal(), null);
		progressRoot.setStyles(styles);
		this.progressRoot.setSize(BAR_WIDTH, 1);
		this.progressRoot.layer.setTranslation(75 - BAR_WIDTH / 2, Y_POS
				+ BAR_HEIGHT);

		// add a rectangle
		this.percentageCanvas = PlayN.graphics().createImageLayer();
		canvasImage = PlayN.graphics().createImage(BAR_WIDTH, BAR_HEIGHT);
		percentageCanvas.setImage(canvasImage);

		canvasImage.canvas().setFillColor(Color.WHITE);
		canvasImage.canvas().drawText("0%", 50, BAR_HEIGHT / 2);
		percentageCanvas.setTranslation(75 - BAR_WIDTH / 2 - 3, Y_POS);

		quizContent.getStaticLayerFront().add(percentageCanvas);

		quizContent.getDynamicLayer().add(progressRoot.layer);

	}

	public void addProgress(float i) {

		float fOld = this.progressValue;
		float fNew = this.progressValue + i;

		if (fNew >= 100) {
			quizContent.getJokerLayer().schalteBonusFrei();
		}

		fNew = fNew % 100;

		animateProgress(fOld, fNew);
		this.progressValue = fNew;

		quizContent.getStaticLayerFront().remove(percentageCanvas);
		this.percentageCanvas = PlayN.graphics().createImageLayer();
		CanvasImage canvasImage = PlayN.graphics().createImage(BAR_WIDTH,
				BAR_HEIGHT);
		percentageCanvas.setImage(canvasImage);
		canvasImage.canvas().setFillColor(Color.WHITE);
		canvasImage.canvas().drawText((int) fNew + "%", 50, BAR_HEIGHT / 2);
		percentageCanvas.setTranslation(75 - BAR_WIDTH / 2 - 3, Y_POS);
		quizContent.getStaticLayerFront().add(percentageCanvas);

	}

	private void animateProgress(float f_old, float f_new) {

		One progressAnimation = quizContent.anim
				.tweenScaleY(progressRoot.layer);
		One progressAnimation2 = quizContent.anim.tweenY(progressRoot.layer);

		progressAnimation.from((f_old * 2 % BAR_HEIGHT));
		progressAnimation.easeInOut();
		progressAnimation.to((f_new * 2 % BAR_HEIGHT));

		progressAnimation2.from((BAR_HEIGHT + Y_POS) - (f_old * 2));
		progressAnimation2.easeInOut();

		progressAnimation2.to((BAR_HEIGHT + Y_POS) - (f_new * 2));
	}

	public Layer getLayer() {
		return progressRoot.layer;
	}

	public Layer getPercentageCanvas() {
		return this.percentageCanvas;
	}

	public Layer getRatingBar() {
		return this.ratingBar;
	}

}