package com.julvez.pfc.teachonsnap.service.lesson.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.manager.string.StringManager;
import com.julvez.pfc.teachonsnap.manager.string.StringManagerFactory;
import com.julvez.pfc.teachonsnap.model.lesson.Answer;
import com.julvez.pfc.teachonsnap.model.lesson.CloudTag;
import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.Link;
import com.julvez.pfc.teachonsnap.model.lesson.Question;
import com.julvez.pfc.teachonsnap.model.lesson.Tag;
import com.julvez.pfc.teachonsnap.repository.lesson.LessonRepository;
import com.julvez.pfc.teachonsnap.repository.lesson.LessonRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.lang.LangService;
import com.julvez.pfc.teachonsnap.service.lang.LangServiceFactory;
import com.julvez.pfc.teachonsnap.service.lesson.LessonService;
import com.julvez.pfc.teachonsnap.service.user.UserService;
import com.julvez.pfc.teachonsnap.service.user.UserServiceFactory;

public class LessonServiceImpl implements LessonService{

	private LessonRepository lessonRepository = LessonRepositoryFactory.getRepository();	
	private UserService userService = UserServiceFactory.getService();
	private LangService langService = LangServiceFactory.getService();
	private StringManager stringManager = StringManagerFactory.getManager();
	
	
	@Override
	public List<Lesson> getLessonsFromTag(String tag,int firstResult) {
		
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		List<Integer> ids = lessonRepository.getLessonIDsFromTag(tag,firstResult);
		
		for(int id:ids){
			lessons.add(getLesson(id));
		}
		
		return lessons;
	}

	@Override
	public Lesson getLessonFromURI(String lessonURI) {
		Lesson lesson = null;

		int id = lessonRepository.getLessonIDFromURI(lessonURI);
		
		lesson= getLesson(id);
		
		return lesson;
	}

	@Override
	public List<Tag> getLessonTags(int idLesson) {
		
		List<Tag> tags = new ArrayList<Tag>();
		
		List<Integer> ids = lessonRepository.getLessonTagIDs(idLesson);
		
		for(int id:ids){
			tags.add(lessonRepository.getTag(id));
		}		
		return tags;
	}

	@Override
	public List<Lesson> getLinkedLessons(int idLesson) {
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		List<Integer> ids = lessonRepository.getLinkedLessonIDs(idLesson);
		
		for(int id:ids){
			lessons.add(getLesson(id));
		}
		
		return lessons;
	}

	@Override
	public List<Link> getMoreInfoLinks(int idLesson) {
		List<Link> links = new ArrayList<Link>();
		
		List<Integer> ids = lessonRepository.getMoreInfoLinkIDs(idLesson);
		
		for(int id:ids){
			links.add(lessonRepository.getLink(id));
			}
		
		return links;	
	}

	@Override
	public List<Link> getSourceLinks(int idLesson) {
		List<Link> links = new ArrayList<Link>();
		
		List<Integer> ids = lessonRepository.getSourceLinkIDs(idLesson);
		
		for(int id:ids){
			links.add(lessonRepository.getLink(id));
			}
		
		return links;
	}

	@Override
	public List<CloudTag> getCloudTags() {
		List<CloudTag> cloudTags = new ArrayList<CloudTag>();
		
		List<Object[]> result= lessonRepository.getCloudTags();
				
		int max=0;
		int min=0;
		for(Object ids[]:result){
			int aux= ((BigInteger)ids[1]).intValue();
			if(aux>max) max=aux;
			if(min==0 || aux<min) min=aux;
			
			cloudTags.add(new CloudTag(lessonRepository.getTag(((Integer)ids[0]).intValue()),((BigInteger)ids[1]).shortValue()));
		}
		
		return getCloudTagListNormalized(cloudTags,max,min);
	}
	
	private List<CloudTag> getCloudTagListNormalized(List<CloudTag> cloudTags,int max, int min){
		final int weightLevel = 6;
		// Normalizamos los pesos
		for(CloudTag cloudTag:cloudTags){
			short weight = (short) (Math.floor(((double)((weightLevel-1) * (cloudTag.getWeight() - min)))/(double)(max-min)) + 1);
			cloudTag.setWeight(weight);
		}				
		return cloudTags;
	}
	

	@Override
	public Lesson getLesson(int idLesson) {
		Lesson lesson = lessonRepository.getLesson(idLesson);
		lesson.setAuthor(userService.getUser(lesson.getIdUser()));
		lesson.setLanguage(langService.getLanguage(lesson.getIdLanguage()));
		return lesson;
	}

	@Override
	public List<Lesson> getLessonsFromAuthor(String author,int firstResult) {
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		List<Integer> ids = lessonRepository.getLessonIDsFromAuthor(author,firstResult);
		
		for(int id:ids){
			lessons.add(getLesson(id));
		}
		
		return lessons;
	}

	@Override
	public LessonTest getLessonTest(int idLessonTest) {
		LessonTest test = lessonRepository.getLessonTest(idLessonTest);
		
		List<Integer> questionIDs = lessonRepository.getLessonTestQuestionIDs(idLessonTest);
		List<Question> questions = new ArrayList<Question>();
				
		for(int questionID:questionIDs){
			Question question = lessonRepository.getQuestion(questionID);
			List<Integer> answerIDs = lessonRepository.getQuestionAnswerIDs(questionID);
			List<Answer> answers = new ArrayList<Answer>();
			for(int answerID:answerIDs){
				Answer answer = lessonRepository.getAnswer(answerID);
				answers.add(answer);
			}
			question.setAnswers(answers);
			questions.add(question);			
		}
		test.setQuestions(questions);
		
		return test;
	}

	@Override
	public List<Lesson> getLastLessons(int firstResult) {
		List<Lesson> lessons = new ArrayList<Lesson>();
		
		List<Integer> ids = lessonRepository.getLastLessonIDs(firstResult);
		
		for(int id:ids){
			lessons.add(getLesson(id));
		}
		
		return lessons;
	}

	@Override
	public List<CloudTag> getAuthorCloudTags() {
		List<CloudTag> cloudTags = new ArrayList<CloudTag>();
		
		List<Object[]> result= lessonRepository.getAuthorCloudTags();
				
		int max=0;
		int min=0;
		for(Object ids[]:result){
			int aux= ((BigInteger)ids[1]).intValue();
			if(aux>max) max=aux;
			if(min==0 || aux<min) min=aux;
			
			cloudTags.add(new CloudTag(userService.getUser(((Short)ids[0]).intValue()),((BigInteger)ids[1]).shortValue()));
		}
		
		return getCloudTagListNormalized(cloudTags,max,min);
	}

	@Override
	public Lesson createLesson(Lesson newLesson) {
		Lesson ret = null;
		if(newLesson!=null){
			newLesson.setURIname(stringManager.generateURIname(newLesson.getTitle()));
			//TODO controlar duplicate keys, title, uriname,...
			int idLesson = lessonRepository.createLesson(newLesson);
			newLesson.setId(idLesson);
			if(!stringManager.isEmpty(newLesson.getText()))
				saveLessonText(newLesson, newLesson.getText());
			ret = getLesson(idLesson);
		}
		return ret;
	}

	@Override
	public Lesson saveLessonText(Lesson lesson, String newText) {
		Lesson ret = null;
		if(lesson!=null && lesson.getId()>0 && newText!=null){
			lessonRepository.saveLessonText(lesson.getId(),newText);
			lesson.setText(newText);
			ret = lesson;
		}
		return ret;
	}

	@Override
	public Lesson addLessonTags(Lesson lesson, List<String> tags) {
		ArrayList<Integer> tagIDs = new ArrayList<Integer>();
		Lesson ret = null;
		
		if(lesson!=null && lesson.getId()>0 && tags!=null){

			for(String tag:tags){
				int tagID = 0;
				tagID = lessonRepository.getTagID(tag);
				if(tagID>0){
					tagIDs.add(tagID);
				}
				else{
					tagID = lessonRepository.createTag(tag);	
					if(tagID>0){
						tagIDs.add(tagID);
					}
				}
			}
			if(tags.size()>0){
				lessonRepository.addLessonTags(lesson.getId(), tagIDs);
			}
			ret = lesson;
		}
		return ret;
	}
	

}
