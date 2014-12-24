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
import playn.core.GroupLayer;
import playn.quiz.core.entities.Question;
import playn.quiz.core.util.QuizSimpleStyles;
import tripleplay.ui.Background;
import tripleplay.ui.Group;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;

/**
 * A demo that displays a menu of the available demos.
 */
public class TestScreen extends Screen {

	private Interface iface;

	private Root root;

	private QuizContent quizContent;

	public TestScreen(QuizContent quizContent) {
		this.quizContent = quizContent;
	}

	@Override
	public String name() {
		return "Test";
	}

	@Override
	public void init() {
		
		iface = new Interface();

		GroupLayer layer = graphics().rootLayer();

		this.root = iface.createRoot(AxisLayout.horizontal().gap(15),
				QuizSimpleStyles.newSheet(), layer);
		root.setSize(300, 590);
		root.layer.setTranslation(250, 10);
		// root.add(f);

		
		// g.add(new Label("Hallo").addStyles(QuizStyles.fontStyle14));
		// g.layer.setTranslation(670, 680);

//		Button l = new Button("This Week");
//		l.clicked().connect(new UnitSlot() {
//			@Override
//			public void onEmit() {
//				System.out.println("Jello");
//			}
//		});
		Group g = new Group(AxisLayout.vertical());
		for(int i=11;i<15;++i){
			Question q = quizContent.question[i];
			for(String x : q.getAnswers()){
				g.add(new Label(x));
			}
		}
		root.add(g);
		
		Group g2 = new Group(AxisLayout.vertical());
		for(int i=6;i<10;++i){
			Question q = quizContent.question[i];
			for(String x : q.getAnswers()){
				g2.add(new Label(x));
			}
		}
		root.add(g2);
		
		Group g3 = new Group(AxisLayout.vertical());
		for(int i=0;i<5;++i){
			Question q = quizContent.question[i];
			for(String x : q.getAnswers()){
				g3.add(new Label(x));
			}
		}
		root.add(g3);
		
		Group g4 = new Group(AxisLayout.vertical().gap(15));
		for(int i=0;i<15;++i){
			Question q = quizContent.question[i];
				g4.add(new Label(q.getCorrectAnswer()));
		}
		root.add(g4);
		

		
		layer.add(root.layer);

	}

	@Override
	public void shutdown() {

	}

	@Override
	public void update(int delta) {
		clock.update(delta);
		if (iface != null) {
			// MIGRATION: Von Float -> int
			iface.update(delta);
		}

	}

	@Override
	public void paint(float alpha) {
		clock.paint(alpha);
		if (iface != null) {
			iface.paint(clock);
		}
	}

	protected Label label(String text, Background bg) {
		return new Label(text).addStyles(Style.HALIGN.center,
				Style.BACKGROUND.is(bg));
	}

}
