package playn.quiz.core.entities;

import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.PlayN;
import react.UnitSlot;
import react.Value;
import tripleplay.ui.Icon;
import tripleplay.ui.Icons;
import tripleplay.ui.Slider;
import tripleplay.ui.Slider.Range;

public class RatingSlider extends Slider{

	private Image imgStar;


	public RatingSlider (float value, float min, float max, Image imgStar) {
        super(value,min,max);
        this.imgStar = imgStar;
        setTextThumb();
    }
	
	
	private void setTextThumb() {
		CanvasImage image = PlayN.graphics().createImage(38, 38);
		image.canvas().drawImage(imgStar, 0, 0);
		

		
		int v = value.get().intValue();
		if (v < 0) {
			v = 0;
		} else if (v > 100) {
			v = 100;
		}
		String x = "";
		if (v < 10) {
			x = "  " + v;
		} else if (v == 100) {
			x = "" + v;
		} else {
			x = " " + v;
		}
		image.canvas().setFillColor(0xFFFFFF00);
		image.canvas().fillCircle(18, 18, 6);
		image.canvas().setFillColor(0xFF000000);
		image.canvas().drawText(x, 8, 23);

		this.addStyles(Slider.THUMB_IMAGE.is(Icons.loader(image, 38, 38)));
	}
	
    protected void setValueFromPointer (float x) {
        Range r = range.get();
        float width = _thumbRange;
        x = Math.min(width,  x - _thumbLeft);
        float pos = Math.max(x, 0) / width * r.range;
        if (_increment != null) {
            float i = _increment;
            pos = i * Math.round(pos / i);
        }
        value.update(r.min + pos);
        setTextThumb();
    }
	
}
