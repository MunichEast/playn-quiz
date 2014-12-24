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
package playn.quiz.core.entities;

import static playn.core.PlayN.*;
import static playn.core.PlayN.graphics;

import playn.core.PlayN;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.util.Callback;
//import playn.core.ResourceCallback;
import playn.quiz.core.QuizContent;


public abstract class Entity {
	protected final ImageLayer layer;
	protected float x, y, angle;
	protected QuizContent quizContent;

	public Entity(final QuizContent quizContent, float px, float py,
			float pangle) {
		this.quizContent = quizContent;
		this.x = px;
		this.y = py;
		this.angle = pangle;
		layer = graphics().createImageLayer(getImage());
		initPreLoad(quizContent);
		getImage().addCallback(new Callback<Image>() {
			@Override
			public void onSuccess(Image image) {
				// since the image is loaded, we can use its width and height
				layer.setWidth(image.width());
				layer.setHeight(image.height());
				layer.setOrigin(image.width() / 2f, image.height() / 2f);
				layer.setScale(getWidth() / image.width(),
						getHeight() / image.height());
				layer.setTranslation(x, y);
				layer.setRotation(angle);
				initPostLoad(quizContent);
			}

			@Override
			public void onFailure(Throwable err) {
				PlayN.log().error("Error loading image: " + err.getMessage());
			}
		});
	}

	public void paint(float alpha) {
	}

	public void update(float delta) {
	}

	public void setPos(float x, float y) {
		layer.setTranslation(x, y);
	}

	public void setAngle(float a) {
		layer.setRotation(a);
	}

	abstract float getWidth();

	abstract float getHeight();

	public abstract Image getImage();

	protected static Image loadImage(String name) {
		return PlayN.assets().getImage("images/" + name);
	}

	/**
	 * Perform pre-image load initialization (e.g., attaching to PeaWorld
	 * layers).
	 * 
	 * @param peaWorld
	 */
	public abstract void initPreLoad(final QuizContent quizContent);

	/**
	 * Perform post-image load initialization (e.g., attaching to PeaWorld
	 * layers).
	 * 
	 * @param peaWorld
	 */
	public abstract void initPostLoad(final QuizContent quizContent);

	/**
	 * Shutsdown this elements
	 * 
	 * @param peaWorld
	 */
	public abstract void shutdown(final QuizContent quizContent);

}