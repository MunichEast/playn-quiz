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
import static playn.core.PlayN.assets;

import java.util.HashMap;

import playn.core.Image;

public class Question {

	Image image;
	private String imageURL;
	private String correctAnswer;
	private String[] answers;
	private Integer[] deaths;
	private String titel;
	private String teaser;
	private String institution;
	private String datierung;
	private String location;
	private HashMap<String,Integer> tagList;

	public Question(int questionNumber, String imageURL, String correctAnswer,
			String[] answers, Integer[] deaths, String title, String teaser, String datierung, String institution, String location, HashMap<String,Integer> tagList) {
		this.imageURL = imageURL;
		image = assets().getImage(getImageName());

		this.setCorrectAnswer(correctAnswer);
		this.answers = answers;
		this.deaths = deaths;
		this.titel = title;
		this.setTeaser(teaser);
		this.setInstitution(institution);
		this.setDatierung(datierung);
		this.setLocation(location);
		this.setTagList(tagList);
	}

	private String getImageName() {
		return imageURL;
	}

	public Image getImage() {
		return image;
	}

	public boolean validateAnswer(String answer) {
		if (answer.equals(getCorrectAnswer())) {
			return true;
		}
		return false;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}
	
	public int getCorrectAnswerNum() {
		for(int i=0;i<4;++i){
			if(correctAnswer.equals(answers[i])){
				return i;
			}
		}
		return -1;
		
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String[] getAnswers() {
		return this.answers;
	}

	public Integer[] getDeaths() {
		return deaths;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getTeaser() {
		return teaser;
	}

	public void setTeaser(String teaser) {
		this.teaser = teaser;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getDatierung() {
		return datierung;
	}

	public void setDatierung(String datierung) {
		this.datierung = datierung;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public HashMap<String,Integer> getTagList() {
		return tagList;
	}

	public void setTagList(HashMap<String,Integer> tagList) {
		this.tagList = tagList;
	}


}
