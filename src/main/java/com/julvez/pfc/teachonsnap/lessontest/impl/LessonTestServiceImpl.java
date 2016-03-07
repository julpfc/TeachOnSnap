package com.julvez.pfc.teachonsnap.lessontest.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.lesson.model.Lesson;
import com.julvez.pfc.teachonsnap.lessontest.LessonTestService;
import com.julvez.pfc.teachonsnap.lessontest.model.Answer;
import com.julvez.pfc.teachonsnap.lessontest.model.LessonTest;
import com.julvez.pfc.teachonsnap.lessontest.model.Question;
import com.julvez.pfc.teachonsnap.lessontest.repository.LessonTestRepository;
import com.julvez.pfc.teachonsnap.url.URLService;

/**
 * Implementation of the LessonTestService interface, uses an internal {@link LessonTestRepository} 
 * to access/modify the tests related data.
 */
public class LessonTestServiceImpl implements LessonTestService {

	/** Repository than provides data access/modification */
	private LessonTestRepository lessonTestRepository;
	
	/** Provides the functionality to work with application's URLs */
	private URLService urlService;
	
	
	/**
	 * Constructor requires all parameters not to be null
	 * @param lessonTestRepository Repository than provides data access/modification
	 * @param urlService Provides the functionality to work with application's URLs
	 */
	public LessonTestServiceImpl(LessonTestRepository lessonTestRepository,
			URLService urlService) {
		if(lessonTestRepository == null || urlService == null){
			throw new IllegalArgumentException("Parameters cannot be null.");
		}
		this.lessonTestRepository = lessonTestRepository;
		this.urlService = urlService;
	}

	@Override
	public LessonTest getLessonTest(Lesson lesson) {
		LessonTest test = null;

		if(lesson != null){
			//get test id from lesson
			int idLessonTest = lessonTestRepository.getLessonTestID(lesson.getId());
			
			//get test from id
			test = getLessonTest(idLessonTest);
		}
			
		return test;
	}

	@Override
	public LessonTest getLessonTest(int idLessonTest) {
		LessonTest test = null;

		if(idLessonTest>0){		
			//get test from id
			test = lessonTestRepository.getLessonTest(idLessonTest);

			if(test != null){
				//get test's questions ids
				List<Integer> questionIDs = lessonTestRepository.getLessonTestQuestionIDs(idLessonTest);
				
				//get questions from ids
				if(questionIDs!=null){					
					List<Question> questions = new ArrayList<Question>();
							
					for(int questionID:questionIDs){
						Question question = getQuestion(questionID);						
						questions.add(question);			
					}
					test.setQuestions(questions);
				}
				//sets tests URLs
				test.setURLs(urlService.getLessonTestURL(), urlService.getLessonTestEditURL(), urlService.getLessonTestNewQuestionURL());
			}
		}
		return test;
	}

	@Override
	public void publish(int idLessonTest) {
		//get test from id
		LessonTest test = getLessonTest(idLessonTest);
		
		//publish test if not was previously published
		if(test!=null && test.isDraft()){
			lessonTestRepository.publish(idLessonTest, test.getIdLesson());			
		}		
	}

	@Override
	public void unpublish(int idLessonTest) {
		//get test from id
		LessonTest test = getLessonTest(idLessonTest);
		
		//unpublish test if was previously published
		if(test!=null && !test.isDraft()){
			lessonTestRepository.unpublish(idLessonTest, test.getIdLesson());			
		}		
		
	}

	@Override
	public Question getQuestion(int idQuestion) {
		Question q = null;
		if(idQuestion>0){
			//get question from id
			q = lessonTestRepository.getQuestion(idQuestion);
			
			if(q!=null){
				//get answers from question
				List<Integer> answerIDs = lessonTestRepository.getQuestionAnswerIDs(idQuestion);

				//get Answers from ids
				if(answerIDs != null){
					List<Answer> answers = new ArrayList<Answer>();
					for(int answerID:answerIDs){
						Answer answer = lessonTestRepository.getAnswer(answerID);
						answers.add(answer);
					}
					q.setAnswers(answers);
				}
				//set question URLs
				q.setURLs( urlService.getLessonTestEditQuestionURL(q.getIdLessonTest()));				
			}
		}
		return q;
	}

	@Override
	public void saveQuestion(Question question) {
		if(question != null && question.getId()>0){
			//save question
			lessonTestRepository.saveQuestion(question.getId(), question.getText(),
					question.getPriority(), question.getIdLessonTest());
			
			//save question's answers
			for(Answer answer:question.getAnswers()){
				saveAnswer(answer);
			}
		}		
	}

	@Override
	public LessonTest createQuestion(Question question) {
		LessonTest test = null;
		
		//create if the object is correctly fullfilled
		if(question!=null && question.isFullFilled()){
			
			//create question
			int idQuestion = lessonTestRepository.createQuestion(question);
			
			if(idQuestion>0){
				//get modified test
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
			//get test id from question
			int idLessonTest = question.getIdLessonTest();
			
			//get test from id
			LessonTest test = getLessonTest(idLessonTest);
			
			if(test!=null){
				//remove question
				lessonTestRepository.removeQuestion(test,question);
				
				//check if removed
				question = getQuestion(question.getId());			
			
				if(question==null){
					removed = true;	
					
					//unpublish test if no questions left
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
			//Remove test
			lessonTestRepository.removeLessonTest(test);
					
			//checks if test was removed
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
					
		if(question != null && newPriority >= 0){
			
			if(question.getPriority() != newPriority){
				
				//get test from question
				LessonTest test = getLessonTest(question.getIdLessonTest());
				
				if(test!=null){
					//get current position
					byte priority = question.getPriority();
					
					//update other relative positions
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
							//save question's new position
							q.setPriority((byte)newPriority);
							saveQuestion(q);
						}
					}
					
					//check if the question was moved
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
		
		//Not allow more than 10 answers per question
		if(lesson!=null && numAnswers>1 && numAnswers<10){
			
			//get test from lesson
			test = getLessonTest(lesson);
			
			if(test == null){
				//creates a new test
				int idLessonTest = lessonTestRepository.createLessonTest(lesson.getId(),multipleChoice, numAnswers);
				
				if(idLessonTest>0){
					//get test from id
					test = getLessonTest(idLessonTest);							
				}
				else{
					test = null;
				}			
			}
		}		
		return test;	
	}

	/**
	 * Persists changes of the answer
	 * @param answer to be persisted
	 */
	private void saveAnswer(Answer answer) {
		if(answer!=null && answer.getId() >0){
			//save answer
			lessonTestRepository.saveAnswer(answer.getId(), answer.getText(), 
					answer.isCorrect(), answer.getReason(), answer.getIdQuestion(),
					getQuestion(answer.getIdQuestion()).getIdLessonTest());
		}		
	}
}
