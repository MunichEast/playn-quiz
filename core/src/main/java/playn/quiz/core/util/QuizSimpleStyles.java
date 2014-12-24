//
// Triple Play - utilities for use in PlayN-based games
// Copyright (c) 2011, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/tripleplay/blob/master/LICENSE

package playn.quiz.core.util;

import static playn.core.PlayN.*;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.CheckBox;
import tripleplay.ui.Field;
import tripleplay.ui.Label;
import tripleplay.ui.Style;
import tripleplay.ui.Stylesheet;
import tripleplay.ui.ToggleButton;

/**
 * Provides a simple style sheet that is useful for development and testing.
 */
public class QuizSimpleStyles {
	/**
	 * Creates and returns a simple default stylesheet.
	 */
	public static Stylesheet newSheet() {
		return newSheetBuilder().create();
	}

	/**
	 * Creates and returns a stylesheet builder configured with some useful
	 * default styles. The caller can augment the sheet with additional styles
	 * and call {@code create}.
	 */
	public static Stylesheet.Builder newSheetBuilder() {
		int bgColor = Color.BLUE, ulColor = Color.WHITE, brColor = Color.SHADOW;
		Background butBg = Background.roundRect(bgColor, 5, ulColor, 2).inset(
				5, 6, 2, 6);
		Background butSelBg = Background.roundRect(bgColor, 5, brColor, 2)
				.inset(6, 5, 1, 7);
		return Stylesheet
				.builder()
				.add(Label.class, QuizStyles.fontStyle12)
				.add(Button.class, Style.BACKGROUND.is(butBg),
						Style.FONT.is(QuizStyles.verdana14Bold),
						Style.FONT.COLOR.is(Color.WHITE))
				.add(Button.class, Style.Mode.SELECTED,
						Style.BACKGROUND.is(butSelBg),
						Style.FONT.is(QuizStyles.verdana14Bold),
						Style.FONT.COLOR.is(Color.WHITE))
				.add(ToggleButton.class, Style.BACKGROUND.is(butBg))
				.add(ToggleButton.class, Style.Mode.SELECTED,
						Style.BACKGROUND.is(butSelBg))
				.add(CheckBox.class,
						Style.BACKGROUND.is(Background.roundRect(bgColor, 5,
								ulColor, 2).inset(3, 2, 0, 3)))
				.add(CheckBox.class,
						Style.Mode.SELECTED,
						Style.BACKGROUND.is(Background.roundRect(bgColor, 5,
								brColor, 2).inset(3, 2, 0, 3)))
				.
				// flip ul and br to make Field appear recessed
				add(Field.class,
						Style.BACKGROUND.is(Background.beveled(0xFFFFFFFF,
								brColor, ulColor).inset(5)))
				.add(Field.class,
						Style.Mode.DISABLED,
						Style.BACKGROUND.is(Background.beveled(0xFFCCCCCC,
								brColor, ulColor).inset(5)));
	}
}
