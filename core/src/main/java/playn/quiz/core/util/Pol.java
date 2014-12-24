package playn.quiz.core.util;

import static playn.core.PlayN.*;
import java.util.*;

public class Pol {

	private String name;
	private String imgName;
	private List<String> stichpunkte = new ArrayList<String>();

	public Pol(String name, String imgName) {
		this.name = name;
		this.imgName = imgName;
	}

	public List<String> getStichpunkte() {
		return stichpunkte;
	}

	public void setStichpunkt(List<String> stichpunkte) {
		this.stichpunkte = stichpunkte;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addStichpunkt(String stichpunkt) {
		if(stichpunkt.equals(" ") || stichpunkt.equals("")){
			
		}else{
			stichpunkte.add(stichpunkt);
		}
		

	}

	public String getImage() {

		return "woelfflinImages/" + imgName;

	}

}
