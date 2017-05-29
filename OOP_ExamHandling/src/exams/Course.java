package exams;

import java.util.Map;
import java.util.TreeMap;

public class Course {
	private String name;
	private boolean examOpen = false;
	private boolean examClose = false;
	private Map<String, Integer> grades = new TreeMap<>();
	private Map<String, Student> students = new TreeMap<>();

	public Course(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public boolean isExamOpen() {
		return examOpen;
	}

	public void setExamOpen(boolean examOpen) {
		this.examOpen = examOpen;
	}

	public void addGrade(String id, int grade) {
		grades.put(id, grade);
	}

	public Map<String, Integer> getGrades() {
		return grades;
	}

	public Map<String, Student> getStudent() {
		return students;
	}
	
	public void addStudent(Student student) {
		students.put(student.getName(), student);
	}

	public boolean isExamClose() {
		return examClose;
	}

	public void setExamClose(boolean examClose) {
		this.examClose = examClose;
	}

}
