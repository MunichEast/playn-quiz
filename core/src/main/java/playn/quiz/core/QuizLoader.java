package playn.quiz.core;

import static playn.core.PlayN.*;
import java.util.HashMap;

import playn.core.AssetWatcher;
import playn.core.Json;
import playn.core.PlayN;
//import playn.core.ResourceCallback;
import playn.core.util.Callback;
import playn.quiz.core.entities.Question;

public class QuizLoader {

	public static void CreateWorld(Quiz quiz,

	final Callback<QuizContent> callback) {
		final QuizContent quizContent = new QuizContent(quiz);

		PlayN.net().get(quizContent.connectStringQuiz, new Callback<String>() {
			@Override
			public void onSuccess(String resource) {
				PlayN.log().info("Got answer from server"); // create an
															// asset
															// watcher
															// that will
															// call our
															// callback
															// when all
															// assets
				// are loaded
				AssetWatcher assetWatcher = new AssetWatcher(
						new AssetWatcher.Listener() {
							@Override
							public void done() {
								callback.onSuccess(quizContent);
							}

							@Override
							public void error(Throwable e) {
								callback.onFailure(e);
								// explainer.showRetryButton();
							}
						});

				// parse

				PlayN.log().info("Read input");
				Json.Object document = PlayN.json().parse(resource);

				String sid = document.getString("SID");
				quizContent.setSessionID(sid);

				Question[] questions = new Question[15];
				// parse the entities, adding each asset
				// to the asset watcher
				Json.Array jsonEntities = document.getArray("Array");
				for (int i = 0; i < jsonEntities.length(); i++) {
					Json.Object jsonEntity = jsonEntities.getObject(i);
					// String artist = jsonEntity.getString("Artist");
					String title = jsonEntity.getString("Title");
					String teaser = jsonEntity.getString("Teaser");
					String datierung = jsonEntity.getString("Datierung");
					String institution = jsonEntity.getString("Institution");
					String location = jsonEntity.getString("Location");
					
					//int numTags = jsonEntity.getInt("NumTags");
					
//					HashMap<String,Integer> tagList = new HashMap<String,Integer>();
//					for(int jj=0;jj<numTags;++jj){
//						tagList.put(jsonEntity.getString("Tag"+jj), jsonEntity.getInt("TagNum"+jj));
//					}
					
							
					String answers[] = new String[4];
					answers[0] = jsonEntity.getString("A");
					answers[1] = jsonEntity.getString("B");
					answers[2] = jsonEntity.getString("C");
					answers[3] = jsonEntity.getString("D");
					Integer[] deaths = new Integer[4];
					deaths[0] = jsonEntity.getInt("DA");
					deaths[1] = jsonEntity.getInt("DB");
					deaths[2] = jsonEntity.getInt("DC");
					deaths[3] = jsonEntity.getInt("DD");

					String correctAnswer = jsonEntity
							.getString("CorrectAnswer");
					String imageURL = jsonEntity.getString("URL");

					imageURL = quizContent.imageString + imageURL;
				

					Question question = new Question(i, imageURL,
							correctAnswer, answers, deaths, title, teaser, datierung, institution, location);
					// Question question = new
					// Question(i,
					// "","A","A","B","C","D");

					if (question != null) {
						assetWatcher.add(question.getImage());
						questions[i] = question;
					}
				}

				quizContent.setQuestions(questions);
				// start the watcher (it will call the
				// callback when everything is
				// loaded)
				assetWatcher.start();

			}

			@Override
			public void onFailure(Throwable cause) {
				PlayN.log().error(
						"Error in Quizloader.java while loading images");
				cause.fillInStackTrace();
			}
		});

	}
}
