package playn.quiz.core.util;

import static playn.core.PlayN.*;
import playn.core.Font;
import playn.core.PlayN;
import tripleplay.ui.Background;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;

public final class QuizStyles {

	public static final String fontType = "Verdana";

	public static final Font verdana10Bold = PlayN.graphics().createFont(
			fontType, Font.Style.BOLD, 10);

	public static final Font verdana12Bold = PlayN.graphics().createFont(
			fontType, Font.Style.BOLD, 12);

	public static final Font verdana13Bold = PlayN.graphics().createFont(
			fontType, Font.Style.BOLD, 13);

	public static final Font verdana14Bold = PlayN.graphics().createFont(
			fontType, Font.Style.BOLD, 14);

	public static final Font verdana14 = PlayN.graphics().createFont(fontType,
			Font.Style.PLAIN, 14);

	// Button titel of ranking
	public static final Styles fontStyleBoldWhite = Styles.make(
			Style.FONT.is(verdana14Bold), Style.COLOR.is(Color.WHITE));

	public static final Font verdana16Bold = PlayN.graphics().createFont(
			fontType, Font.Style.BOLD, 16);

	public static final Font verdana18Bold = PlayN.graphics().createFont(
			fontType, Font.Style.BOLD, 18);

	public static final Font verdana20Bold = PlayN.graphics().createFont(
			fontType, Font.Style.BOLD, 20);

	public static final Styles fontStyleBoldWhiteLeft = Styles.make(
			Style.FONT.is(verdana14Bold), Style.HALIGN.left,
			Style.COLOR.is(Color.WHITE));

	public static final Styles fontStyleBoldWhiteLeft2 = Styles.make(
			Style.FONT.is(verdana20Bold), Style.HALIGN.left,
			Style.COLOR.is(Color.WHITE));

	public static final Styles fontStyleBoldWhiteLeftInvisible = Styles.make(
			Style.FONT.is(verdana14Bold), Style.HALIGN.left,
			Style.COLOR.is(0x00000000));

	public static final Styles fontStylePlain = Styles.make(
			Style.FONT.is(verdana14), Style.COLOR.is(Color.WHITE));

	public static final Font verdana21Bold = PlayN.graphics().createFont(
			fontType, Font.Style.BOLD, 21);

	public static final Font verdana22Bold = PlayN.graphics().createFont(
			fontType, Font.Style.BOLD, 22);

	public static final Font verdana24Bold = PlayN.graphics().createFont(
			fontType, Font.Style.BOLD, 22);

	public static final Font verdana26Bold = PlayN.graphics().createFont(
			fontType, Font.Style.BOLD, 26);

	public static final Font verdana44Bold = PlayN.graphics().createFont(
			fontType, Font.Style.BOLD, 44);

	public static final Font verdana40Bold = PlayN.graphics().createFont(
			fontType, Font.Style.BOLD, 40);

	public static final Styles fontStyleQ = Styles.make(
			Style.FONT.is(verdana13Bold), Style.COLOR.is(Color.WHITE),
			Style.HALIGN.left,
			Style.BACKGROUND.is(Background.solid(Color.RED).inset(4)));

	public static final Styles fontStyle13 = Styles.make(
			Style.FONT.is(verdana13Bold), Style.HALIGN.left,
			Style.COLOR.is(Color.WHITE),
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(4)));

	public static final Styles fontStyle14 = Styles.make(
			Style.FONT.is(verdana14Bold), Style.HALIGN.left,
			Style.COLOR.is(Color.WHITE),
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(4)));

	public static final Styles fontStyleTag1 = Styles.make(
			Style.FONT.is(verdana10Bold), Style.HALIGN.left,
			Style.COLOR.is(Color.WHITE),
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(1)));

	public static final Styles fontStyleTag2 = Styles.make(
			Style.FONT.is(verdana14Bold), Style.HALIGN.left,
			Style.COLOR.is(Color.WHITE),
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(1)));

	public static final Styles fontStyleTag3 = Styles.make(
			Style.FONT.is(verdana18Bold), Style.HALIGN.left,
			Style.COLOR.is(Color.WHITE),
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(1)));

	public static final Styles fontStyleTag4 = Styles.make(
			Style.FONT.is(verdana22Bold), Style.HALIGN.left,
			Style.COLOR.is(Color.WHITE),
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(1)));

	public static final Styles fontStyleTag5 = Styles.make(
			Style.FONT.is(verdana26Bold), Style.HALIGN.left,
			Style.COLOR.is(Color.WHITE),
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(1)));

	public static final Styles fontStyle12 = Styles.make(
			Style.FONT.is(verdana12Bold), Style.COLOR.is(Color.WHITE));

	public static final Styles fontStyle10 = Styles.make(
			Style.FONT.is(verdana10Bold), Style.COLOR.is(Color.WHITE));

	public static final Styles labelStyleLeft = Styles.make(
			Style.FONT.is(verdana12Bold), Style.HALIGN.center,
			Style.COLOR.is(0xFFFFFFFF),
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(2)));

	public static final Styles labelStyleRight = Styles.make(
			Style.FONT.is(verdana12Bold), Style.HALIGN.center,
			Style.COLOR.is(Color.WHITE),
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(2)));

	public static final Styles fontStyleUeberschrift = Styles.make(Style.FONT
			.is(verdana16Bold));

	public static final Styles FONTSTYLE17 = Styles.make(
			Style.FONT.is(verdana18Bold), Style.HALIGN.center,
			Style.FONT.HALIGN.center,
			Style.BACKGROUND.is(Background.solid(0xAAFFFFFF).inset(4)),
			Style.COLOR.is(Color.WHITE));
	public static final Styles FONT_STYLE_16 = Styles.make(
			Style.FONT.is(verdana16Bold), Style.HALIGN.center,
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(4)),
			Style.COLOR.is(Color.WHITE));

	public static final Styles FONT_STYLE_16_ROUND = Styles.make(Style.FONT
			.is(verdana16Bold), Style.HALIGN.center, Style.BACKGROUND
			.is(Background.bordered(Color.BLUE, Color.WHITE, 3).inset(4)),
			Style.COLOR.is(Color.WHITE));

	public static final Styles FONT_STYLE_18 = Styles.make(
			Style.FONT.is(verdana18Bold), Style.HALIGN.center,
			Style.BACKGROUND.is(Background.blank()),
			Style.COLOR.is(Color.WHITE));

	public static final Styles FONT_STYLE_16_INSET = Styles.make(
			Style.FONT.is(verdana16Bold), Style.HALIGN.center,
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(4)),
			Style.COLOR.is(Color.WHITE));
	
	public static final Styles FONT_STYLE_16_BLANK = Styles.make(
			Style.FONT.is(verdana16Bold), Style.HALIGN.center,
			Style.BACKGROUND.is(Background.blank()),
			Style.COLOR.is(Color.WHITE));

	public static final Styles FONT_STYLE_16_INSET2 = Styles.make(
			Style.FONT.is(verdana16Bold), Style.HALIGN.center,
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(4)),
			Style.COLOR.is(Color.WHITE));

	public static final Styles FONT_STYLE_16_INSET2_OLD = Styles.make(
			Style.FONT.is(verdana16Bold), Style.HALIGN.center,
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(4)),
			Style.COLOR.is(Color.WHITE));

	public static final Styles FONT_STYLE_HEADER_SMALL = Styles.make(
			Style.FONT.is(verdana24Bold),
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(4)),
			Style.COLOR.is(Color.WHITE));

	public static final Styles FONT_STYLE_HEADER_SMALL_WO_INSET = Styles.make(
			Style.FONT.is(verdana20Bold),
			Style.BACKGROUND.is(Background.solid(Color.BLUE)),
			Style.COLOR.is(Color.WHITE));

	public static final Styles fontStyleError = Styles.make(
			Style.FONT.is(verdana22Bold), Style.COLOR.is(Color.RED));

	public static final Styles FONTSTYLEBUTTON = Styles.make(
			Style.FONT.is(verdana26Bold), Style.HALIGN.center,
			Style.FONT.HALIGN.center,
			Style.BACKGROUND.is(Background.solid(0xAAFFFFFF).inset(4)),
			Style.COLOR.is(Color.BLUE));

	public static final Styles EXPLAINER_HEADER_STYLE = Styles.make(
			Style.FONT.is(PlayN.graphics().createFont(fontType,
					Font.Style.BOLD, 30)), Style.COLOR.is(Color.WHITE),
			Style.HALIGN.right, Style.BACKGROUND.is(Background.blank()));

	public static final Styles LABEL_STYLE_STICHPUNKT = Styles.make(Style.FONT
			.is(PlayN.graphics().createFont(fontType, Font.Style.PLAIN, 12)),Style.COLOR.is(Color.WHITE),Style.HALIGN.right,Style.BACKGROUND.is(Background.blank()));

	public static final Styles LABEL_STYLE_STICHPUNKT_HEADER = Styles.make(
			Style.FONT.is(PlayN.graphics().createFont(fontType,
					Font.Style.BOLD, 20)), Style.COLOR.is(Color.WHITE),
			Style.HALIGN.center, Style.BACKGROUND.is(Background.blank()));

	public static final Styles FONT_STYLE_40_GREEN = Styles.make(
			Style.FONT.is(verdana40Bold), Style.HALIGN.center,
			Style.COLOR.is(Color.GREEN));

	public static final Styles FONT_STYLE_40_BLUE = Styles.make(
			Style.FONT.is(verdana40Bold), Style.HALIGN.center,
			Style.COLOR.is(Color.BLUE));

	public static final Styles FONT_STYLE_40_BLUE_INVISIBLE = Styles.make(
			Style.FONT.is(verdana40Bold), Style.HALIGN.center,
			Style.COLOR.is(0x00000000));

	public static final Styles FONT_STYLE_24_GREEN = Styles.make(
			Style.FONT.is(verdana24Bold), Style.HALIGN.center,
			Style.COLOR.is(Color.GREEN));

	// Grüne HinweisTexte
	public static final Styles FONT_STYLE_14_WB = Styles.make(
			Style.FONT.is(verdana14Bold), Style.HALIGN.right,
			Style.BACKGROUND.is(Background.solid(Color.BLACK).inset(6)),
			Style.COLOR.is(Color.WHITE));

	public static final Styles FONT_STYLE_40_RED = Styles.make(
			Style.FONT.is(verdana40Bold), Style.HALIGN.center,
			Style.COLOR.is(Color.RED));

	public static final Styles FONTSTYLEHEADER_BIG = Styles.make(
			Style.FONT.is(verdana44Bold),
			Style.BACKGROUND.is(Background.solid(Color.BLUE).inset(4)),
			Style.COLOR.is(Color.WHITE));

	// public static final Styles buttonStyles = Styles
	// .none()
	// .add(Style.BACKGROUND.is(Background.solid(Color.WHITE).inset(5, 6, 2,
	// 6)))
	// .addSelected(
	// Style.BACKGROUND.is(Background.solid(Color.WHITE).inset(5, 6, 2, 6)));

}
