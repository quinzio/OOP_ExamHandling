package main;

import java.util.List;
import java.util.SortedMap;

import exams.ExamException;
import exams.ExamHandling;

public class Example {
	public static void main(String[] args) throws ExamException {
		ExamHandling eh = new ExamHandling();
		//R1
		eh.addStudyPlan("cs1", "oop", "data structures", "operating systems");
		eh.addStudyPlan("cs2", "oop", "graphical interfaces");
		try {eh.addStudyPlan("cs1", "oop");} //duplicated study plan
		catch(ExamException ex) {System.out.println(ex.getMessage());}
		eh.enrollStudent("jo","john", "cs1"); 
		eh.enrollStudent("my", "mary", "cs1");
		eh.enrollStudent("fk", "frank", "cs2"); 
		eh.enrollStudent("an","anne", "cs2");
		try {eh.enrollStudent("jo","jane", "cs1");} //duplicated student id
		catch(ExamException ex) {System.out.println(ex.getMessage());}
		try {eh.enrollStudent("bo","bob", "cs3");} //undefined study plan
		catch(ExamException ex) {System.out.println(ex.getMessage());}
		SortedMap<String, Long> numberOfStudentsForStudyPlan = eh.numberOfStudentsForStudyPlan();
		System.out.println(numberOfStudentsForStudyPlan);
		SortedMap<String, List<String>> studentsForStudyPlan = eh.studentsForStudyPlan();
		System.out.println(studentsForStudyPlan);
		
		//R2
		eh.openExam ("oop");
		try {eh.openExam ("oop");} //session already open
		catch(ExamException ex) {System.out.println(ex.getMessage());}
		eh.takeExam("jo", "oop"); 
		eh.takeExam("fk", "oop");
		try {eh.takeExam("my", "operating systems");} //no open session
		catch(ExamException ex) {System.out.println(ex.getMessage());}
		eh.openExam ("operating systems");
		try {eh.takeExam("an", "operating systems");} //course not in study plan
		catch(ExamException ex) {System.out.println(ex.getMessage());}
		List<String> studentsWhoTookTheExam = eh.studentsWhoTookTheExam ("oop");
		System.out.println(studentsWhoTookTheExam); // [fk, jo]
		System.out.println (eh.numberOfOpenExamSessions ()); //2
		eh.takeExam("my", "oop");
		
		//R3
		eh.giveGrade("oop", "jo", 27); 
		eh.giveGrade("oop", "fk", 29);
		eh.giveGrade("oop", "my", 29);
		try {eh.giveGrade("oop", "an", 25);} //exam not taken
		catch(ExamException ex) {System.out.println(ex.getMessage());}
		System.out.println(eh.studentsWhoTookTheExam ("oop")); //[fk, jo, my]
		eh.closeExam("oop");
		System.out.println(eh.studentsWhoTookTheExam ("oop")); //[]
		eh.takeExam("my", "operating systems");
		try {eh.closeExam("operating systems");} //missing grades
		catch(ExamException ex) {System.out.println(ex.getMessage());}
		System.out.println (eh.numberOfOpenExamSessions ()); //1
		
		//R4
		eh.takeExam("jo", "operating systems");
		eh.giveGrade("operating systems", "jo", 27);
		eh.openExam ("data structures");
		eh.takeExam("jo", "data structures");
		eh.giveGrade("data structures", "jo", 30);
		System.out.println (eh.gradesOfStudent("jo"));
		//{30=[data structures], 27=[oop, operating systems]}
		System.out.println (eh.gradesOfCourse("oop")); //{29=[fk, my], 27=[jo]}
		eh.giveGrade("operating systems", "my", 15);
		System.out.println (eh.failingGradesOfStudent("my"));
		System.out.println (eh.failingGradesOfCourse("operating systems"));
	}
}
