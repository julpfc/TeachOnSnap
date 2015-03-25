package com.julvez.pfc.teachonsnap.service.lesson.test.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.test.Answer;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.test.Question;
import com.julvez.pfc.teachonsnap.model.user.User;
import com.julvez.pfc.teachonsnap.repository.lesson.test.LessonTestRepository;
import com.julvez.pfc.teachonsnap.repository.lesson.test.LessonTestRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.lesson.test.LessonTestService;

public class LessonTestServiceImpl implements LessonTestService {

	private LessonTestRepository lessonTestRepository = LessonTestRepositoryFactory.getRepository();
	
	@Override
	public LessonTest getLessonTest(Lesson lesson) {
		LessonTest test = null;
		
		int idLessonTest = lessonTestRepository.getLessonTestID(lesson.getId());
		
		test = getLessonTest(idLessonTest);
			
		return test;
	}

	@Override
	public LessonTest getLessonTest(int idLessonTest) {
		LessonTest test = null;
		//TODO acabar control de errores
		if(idLessonTest>0){		
			test = lessonTestRepository.getLessonTest(idLessonTest);

			if(test != null){
				List<Integer> questionIDs = lessonTestRepository.getLessonTestQuestionIDs(idLessonTest);
				
				if(questionIDs!=null){
					List<Question> questions = new ArrayList<Question>();
							
					for(int questionID:questionIDs){
						Question question = getQuestion(questionID);						
						questions.add(question);			
					}
					test.setQuestions(questions);
				}
			}
		}
		return test;
	}

	@Override
	public void publish(int idLessonTest, User user) {
		LessonTest test = getLessonTest(idLessonTest);
						
		if(test!=null && test.isDraft() && user!=null && (user.isAdmin() || user.isAuthor())){
			lessonTestRepository.publish(idLessonTest, test.getIdLesson());			
		}		
	}

	@Override
	public void unpublish(int idLessonTest, User user) {
		LessonTest test = getLessonTest(idLessonTest);
		
		if(test!=null && !test.isDraft() && user!=null && (user.isAdmin() || user.isAuthor())){
			lessonTestRepository.unpublish(idLessonTest, test.getIdLesson());			
		}		
		
	}

	@Override
	public Question getQuestion(int idQuestion) {
		Question q = null;
		if(idQuestion>0){
			q = lessonTestRepository.getQuestion(idQuestion);
			
			List<Integer> answerIDs = lessonTestRepository.getQuestionAnswerIDs(idQuestion);
			List<Answer> answers = new ArrayList<Answer>();
			for(int answerID:answerIDs){
				Answer answer = lessonTestRepository.getAnswer(answerID);
				answers.add(answer);
			}
			q.setAnswers(answers);
		}
		return q;
	}

	@Override
	public void saveQuestion(Question question) {
		if(question != null && question.getId()>0){
			lessonTestRepository.saveQuestion(question.getId(), question.getText(),
					question.getPriority(), question.getIdLessonTest());
			for(Answer answer:question.getAnswers()){
				saveAnswer(answer);
			}
		}		
	}

	@Override
	public void saveAnswer(Answer answer) {
		if(answer!=null && answer.getId() >0){
			lessonTestRepository.saveAnswer(answer.getId(), answer.getText(), 
					answer.isCorrect(), answer.getReason(), answer.getIdQuestion(),
					getQuestion(answer.getIdQuestion()).getIdLessonTest());
		}		
	}

	@Override
	public Question createQuestion(Question question) {
		if(question!=null && question.isFullFilled()){
			int idQuestion = lessonTestRepository.createQuestion(question);
			
			if(idQuestion>0){
				lessonTestRepository.addLessonTestNumQuestions(question.getIdLessonTest());
				question = getQuestion(idQuestion);
			}
			else{
				question = null;
			}			
		}		
		return question;
	}


}
