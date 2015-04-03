package com.julvez.pfc.teachonsnap.service.lesson.test.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.Lesson;
import com.julvez.pfc.teachonsnap.model.lesson.test.Answer;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.test.Question;
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
	public void publish(int idLessonTest) {
		LessonTest test = getLessonTest(idLessonTest);
						
		if(test!=null && test.isDraft()){
			lessonTestRepository.publish(idLessonTest, test.getIdLesson());			
		}		
	}

	@Override
	public void unpublish(int idLessonTest) {
		LessonTest test = getLessonTest(idLessonTest);
		
		if(test!=null && !test.isDraft()){
			lessonTestRepository.unpublish(idLessonTest, test.getIdLesson());			
		}		
		
	}

	@Override
	public Question getQuestion(int idQuestion) {
		Question q = null;
		if(idQuestion>0){
			q = lessonTestRepository.getQuestion(idQuestion);
			
			if(q!=null){
				List<Integer> answerIDs = lessonTestRepository.getQuestionAnswerIDs(idQuestion);
				List<Answer> answers = new ArrayList<Answer>();
				for(int answerID:answerIDs){
					Answer answer = lessonTestRepository.getAnswer(answerID);
					answers.add(answer);
				}
				q.setAnswers(answers);
			}
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
	public LessonTest createQuestion(Question question) {
		LessonTest test = null;
		
		if(question!=null && question.isFullFilled()){
			int idQuestion = lessonTestRepository.createQuestion(question);
			
			if(idQuestion>0){
				question = getQuestion(idQuestion);
				test = getLessonTest(question.getIdLessonTest());		
			}
			else{
				test = null;
			}			
		}		
		return test;
	}

	@Override
	public boolean removeQuestion(Question question) {
		boolean removed = false;
		
		if(question!=null){
			int idLessonTest = question.getIdLessonTest();
			
			LessonTest test = getLessonTest(idLessonTest);
			
			if(test!=null){
				lessonTestRepository.removeQuestion(test,question);
				
				question = getQuestion(question.getId());			
			
				if(question==null){
					removed = true;	
					
					test = getLessonTest(idLessonTest);
					if(test.getNumQuestions()==0){
						unpublish(idLessonTest);
					}
				}
			}
		}
		return removed;
	}

	@Override
	public boolean removeLessonTest(LessonTest test) {
		boolean removed = false;
		
		if(test!=null){
			if(test.isDraft()){
				publish(test.getId());
			}
			lessonTestRepository.removeLessonTest(test);
					
			test = getLessonTest(test.getId());			
				
			if(test==null){
				removed = true;					
			}			
		}
		return removed;
	}

	@Override
	public boolean moveQuestion(Question question, int newPriority) {
		boolean moved = false;
					
		if(question!=null && newPriority>=0){
			
			if(question.getPriority()!=newPriority){
				
				LessonTest test = getLessonTest(question.getIdLessonTest());
				
				if(test!=null){
					byte priority = question.getPriority();
					
					for(Question q:test.getQuestions()){
						if(q.getId()!=question.getId()){
							if(q.getPriority()>priority && q.getPriority()<=newPriority){
								q.setPriority((byte)(q.getPriority()-1));
								saveQuestion(q);
							}
							else if(q.getPriority()>=newPriority && q.getPriority()<priority){
								q.setPriority((byte)(q.getPriority()+1));							
								saveQuestion(q);
							}						
						}
						else{
							q.setPriority((byte)newPriority);
							saveQuestion(q);
						}
					}
					
					question = getQuestion(question.getId());
						
					if(question!=null && question.getPriority()==newPriority){
						moved = true;					
					}
				}
			}
			else{
				moved = true;
			}
		}
		return moved;
	}

	@Override
	public LessonTest createLessonTest(Lesson lesson, boolean multipleChoice, int numAnswers) {
		LessonTest test = null;
		
		if(lesson!=null && numAnswers>1 && numAnswers<10){
			test = getLessonTest(lesson);
			
			if(test == null){
				int idLessonTest = lessonTestRepository.createLessonTest(lesson.getId(),multipleChoice, numAnswers);
				
				if(idLessonTest>0){
					test = getLessonTest(idLessonTest);							
				}
				else{
					test = null;
				}			
			}
		}		
		return test;	
	}


}
