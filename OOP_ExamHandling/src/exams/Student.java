package exams;

import java.util.Map;
import java.util.TreeMap;

public class Student {
	private String name;
	private String id;
	private StudyPlan studyplan;
	private Map<String, Integer> grades = new TreeMap<>();
	
	public Student(String name, String id, StudyPlan studyplan) {
		this.name = name;
		this.id = id;
		this.studyplan = studyplan;
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	public StudyPlan getStudyplan() {
		return studyplan;
	}

	public void addGrade(String courseName, int grade) {
		grades.put(courseName, grade);
	}

	public Map<String, Integer> getGrades() {
		return grades;
	}
	
	

}
