package com.julvez.pfc.teachonsnap.service.lesson.test.impl;

import java.util.ArrayList;
import java.util.List;

import com.julvez.pfc.teachonsnap.model.lesson.test.Answer;
import com.julvez.pfc.teachonsnap.model.lesson.test.LessonTest;
import com.julvez.pfc.teachonsnap.model.lesson.test.Question;
import com.julvez.pfc.teachonsnap.repository.lesson.test.LessonTestRepository;
import com.julvez.pfc.teachonsnap.repository.lesson.test.LessonTestRepositoryFactory;
import com.julvez.pfc.teachonsnap.service.lesson.test.LessonTestService;

public class LessonTestServiceImpl implements LessonTestService {

	private LessonTestRepository lessonTestRepository = LessonTestRepositoryFactory.getRepository();
	
	@Override
	public LessonTest getLessonTest(int idLessonTest) {
		LessonTest test = lessonTestRepository.getLessonTest(idLessonTest);
		
		List<Integer> questionIDs = lessonTestRepository.getLessonTestQuestionIDs(idLessonTest);
		List<Question> questions = new ArrayList<Question>();
				
		for(int questionID:questionIDs){
			Question question = lessonTestRepository.getQuestion(questionID);
			List<Integer> answerIDs = lessonTestRepository.getQuestionAnswerIDs(questionID);
			List<Answer> answers = new ArrayList<Answer>();
			for(int answerID:answerIDs){
				Answer answer = lessonTestRepository.getAnswer(answerID);
				answers.add(answer);
			}
			question.setAnswers(answers);
			questions.add(question);			
		}
		test.setQuestions(questions);
		
		return test;
	}


}