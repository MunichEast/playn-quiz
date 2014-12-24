package playn.quiz.core.entities;

import static playn.core.PlayN.*;
import playn.core.Image;
import playn.core.PlayN;
import playn.quiz.core.QuizContent;
import playn.quiz.core.util.QuizStyles;
import playn.quiz.core.util.ResourceBundle;
import pythagoras.f.Rectangle;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.layout.AxisLayout;

public class GameClock extends Entity {
	public static String TYPE = "Clock";
	private int clock = 90;
	private static final int CLOCK_MAX = 90;
	private double startTime;
	private Interface iface;
	private Root clockLayer;
	private Label clockLabel;
	private boolean running;
	private boolean initialized = false;

	public GameClock(QuizContent quizContent, ResourceBundle rB) {
		super(quizContent, 0, 0, 0);
		PlayN.log().info("Erstelle GameClock");

	}

	@Override
	public void update(float delta) {
		if (initialized){
			
		
			if (!quizContent.isOver()) {
	
				if (clock > 0) {
	
					if (running) {
						this.clock = CLOCK_MAX
								- (int) (PlayN.currentTime() - this.startTime)
								/ 1000;
						clockLayer.remove(clockLabel);
						clockLabel = new Label("" + clock)
								.setStyles(QuizStyles.FONT_STYLE_16_BLANK);
						clockLayer.add(clockLabel);
					}
	
				} else {
					quizContent.gameOver(false);
				}
			}
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

	@Override
	public void setPos(float x, float y) {
		this.x = x;
		this.y = y;
		layer.setTranslation(x, y);
	}

	@Override
	public Image getImage() {
		return image;
	}

	private static Image image = loadImage("38px-Star.png");

	@Override
	public void initPreLoad(QuizContent quizContent) {

	}

	@Override
	public void initPostLoad(QuizContent quizContent) {
		if (clockLayer == null) {
			init();
		}
		quizContent.getDynamicLayer().add(clockLayer.layer);
	}

	@Override
	public void shutdown(QuizContent quizContent) {
		quizContent.getDynamicLayer().remove(clockLayer.layer);
		quizContent.removeEntity(this);

	}

	public void init() {
		this.iface = quizContent.getIface();
		this.clock = CLOCK_MAX;
		this.startTime = PlayN.currentTime();

		this.clockLayer = iface.createRoot(AxisLayout.vertical().gap(10), null);
		clockLayer.setSize(130, 120);
		clockLayer.layer.setTranslation(800 - 140, 0);
		
		Label tmp = new Label(quizContent.quiz.rB.string("TIME") + ":")
		.setStyles(QuizStyles.FONT_STYLE_16_BLANK);
		clockLayer.add(tmp);
		
		
		
		this.clockLabel = new Label("--")
				.setStyles(QuizStyles.FONT_STYLE_16_BLANK);
		clockLayer.add(clockLabel);
		this.initialized  = true;
	}

	public void reset() {
		running = true;
		this.clock = CLOCK_MAX;
		this.startTime = PlayN.currentTime();
	}

	public void setVisible(boolean visible) {
		running = false;
		this.clock = CLOCK_MAX;
		this.startTime = PlayN.currentTime();
	}

}