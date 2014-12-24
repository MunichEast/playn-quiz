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
import playn.core.CanvasImage;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.PlayN;
import playn.quiz.core.util.Color;
import tripleplay.anim.Animation.One;
import tripleplay.anim.Animator;
import tripleplay.ui.Interface;
import tripleplay.ui.Root;
import tripleplay.ui.layout.AxisLayout;

/**
 * The Intro-"Video"
 */
public class IntroScreen extends Screen {

	private final Quiz quiz;

	private Interface iface;
	private GroupLayer layer;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private ImageLayer imageLayer;
	private int cnt = 0;

	public Animator anim;

	private Root root;

	private Image image;

	public IntroScreen(Quiz quiz) {
		this.quiz = quiz;
	}

	@Override
	public String name() {
		return "Intro";
	}

	@Override
	public void init() {

		iface = new Interface();

		this.root = iface.createRoot(AxisLayout.horizontal(), null);

		// are images loaded?
		AssetWatcher assetWatcher = new AssetWatcher(
				new AssetWatcher.Listener() {
					@Override
					public void done() {
						startIntro();
					}

					@Override
					public void error(Throwable e) {
						PlayN.log().error(
								"Error loading Quiz Game: " + e.getMessage());
					}
				});

		// load and show our background
		int width = graphics().width(), height = graphics().height();
		ImageLayer bgLayer = PlayN.graphics().createImageLayer();
		CanvasImage canvasImage = PlayN.graphics().createImage(width, height);
		bgLayer.setImage(canvasImage);
		canvasImage.canvas().setFillColor(Color.BLACK);
		canvasImage.canvas().fillRect(0, 0, width, height);
		layer = graphics().rootLayer();
		layer.add(bgLayer);

		// ImageLayer imageLayer =
		// graphics().createImageLayer(PlayN.assetManager().getImage("images/logo.png"));
		String imageURL = "images/Artigo-Logo2.png";

		this.image = PlayN.assets().getImage(imageURL);

		assetWatcher.add(image);
		root.setSize(500, 300);

		assetWatcher.start();

	}

	protected void startIntro() {
		this.anim = new Animator();
		this.imageLayer = graphics().createImageLayer(image);
		root.layer.add(imageLayer);
		root.layer.setTranslation(WIDTH / 2 - imageLayer.width() / 2, HEIGHT
				/ 2 - imageLayer.height() / 2);
		layer.add(root.layer);
		root.layer.setAlpha(0);
		One animation = anim.tweenAlpha(root.layer);
		animation.in(2000);
		animation.easeIn();
		animation.to(1);
		anim.delay(4000).then().action(new Runnable() {
			public void run() {
				One animation = anim.tweenAlpha(root.layer);
				animation.from(1);
				animation.in(2000);
				animation.easeOut();
				animation.to(0);
			}
		});
		anim.delay(6000).then().action(new Runnable() {
			public void run() {
				quiz.activateMenu();

			}
		});

	}

	@Override
	public void shutdown() {
		if (iface != null) {
			pointer().setListener(null);
			iface.destroyRoot(root);
			iface = null;
		}

		imageLayer.destroy();
		layer.destroy();
		layer = null;

	}

	@Override
	public void update(int delta) {
		clock.update(delta);
		cnt += delta;
		if (iface != null) {
			iface.update(delta);
			
		}

		if (anim != null) {
			//Migration update(cnt) -> paint(clock)
			anim.paint(clock);
			

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
