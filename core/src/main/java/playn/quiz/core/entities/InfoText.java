package playn.quiz.core.entities;

import static playn.core.PlayN.*;
import playn.core.Image;
import playn.quiz.core.QuizContent;
import playn.quiz.core.util.QuizStyles;
import tripleplay.ui.Label;

public class InfoText extends Entity {
	public static String TYPE = "InfoText";
	private int cnt = 0;
	private Label l;

	public InfoText(QuizContent quizContent, String msg, int x, int y) {
		super(quizContent, x, y, 0);

		if(y < 90){
			this.l = new Label(msg).setStyles(QuizStyles.FONT_STYLE_24_GREEN);
		}else{
			this.l = new Label(msg).setStyles(QuizStyles.FONT_STYLE_14_WB);
		}
		setPos(x,y);
		quizContent.alertRoot.add(l);
		
	}

	@Override
	public void update(float delta) {

		cnt++;
		if (cnt > 200) {
			shutdown(quizContent);
		}

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
		// g.layer.setTranslation(x, y);

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
		quizContent.alertRoot.remove(l);
		quizContent.removeEntity(this);

	}

}