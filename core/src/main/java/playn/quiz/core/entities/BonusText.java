package playn.quiz.core.entities;

import static playn.core.PlayN.*;
import playn.core.Image;
import playn.quiz.core.QuizContent;
import playn.quiz.core.util.Color;
import playn.quiz.core.util.QuizStyles;
import tripleplay.ui.Label;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;

public class BonusText extends Entity {
	public static String TYPE = "BonusText";
	private int cnt = 0;
	private final int VOLLER_BONUS = 1;
	private final int HALBER_BONUS = 0;
	private final int KEIN_BONUS = -1;
	private Label l;

	public BonusText(QuizContent quizContent, int bonus, int x, int y) {
		super(quizContent, x, y, 0);

		Styles font_style_bonus = null;
		String text = "";

		switch (bonus) {
		case VOLLER_BONUS:

			font_style_bonus = Styles.make(
					Style.FONT.is(QuizStyles.verdana20Bold),
					Style.HALIGN.center, Style.COLOR.is(Color.GREEN));

			text = quizContent.quiz.rB.string("MEGA_BONUS") + ": "
					+ quizContent.getCurrentAmount() * 2 + " "
					+ quizContent.quiz.rB.string("PUNKTE") + ".!";
			break;
		case HALBER_BONUS:
			font_style_bonus = Styles.make(
					Style.FONT.is(QuizStyles.verdana20Bold),
					Style.HALIGN.center, Style.COLOR.is(Color.GREEN));
			text = quizContent.quiz.rB.string("FULL_BONUS") + ": "
					+ quizContent.getCurrentAmount() / 2 + " "
					+ quizContent.quiz.rB.string("PUNKTE") + ".!";
			break;
		case KEIN_BONUS:
			font_style_bonus = Styles.make(
					Style.FONT.is(QuizStyles.verdana20Bold),
					Style.HALIGN.center, Style.COLOR.is(Color.RED));
			text = quizContent.quiz.rB.string("HALF_BONUS") + "!";
			break;

		}

		this.l = new Label(text).setStyles(font_style_bonus);

		setPos(x, y);
		quizContent.bonusRoot.add(l);
	}

	@Override
	public void update(float delta) {

		cnt++;
		if (cnt > 75) {
			shutdown(quizContent);
		}

		y -= delta * getVelocity();
		quizContent.bonusRoot.layer.setTranslation(x, y);

	}

	@Override
	float getWidth() {
		return image.width() / 4;
	}

	@Override
	float getHeight() {
		return image.height() / 4;
	}

	float getVelocity() {
		return 0.04f;
	}

	@Override
	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
		quizContent.bonusRoot.layer.setTranslation(x, y);

	}

	@Override
	public Image getImage() {
		return image;
	}

	// Dummy
	private static Image image = loadImage("38px-Star.png");

	@Override
	public void initPreLoad(QuizContent quizContent) {

	}

	@Override
	public void initPostLoad(QuizContent quizContent) {

	}

	@Override
	public void shutdown(QuizContent quizContent) {
		quizContent.bonusRoot.remove(l);
		quizContent.removeEntity(this);

	}

}