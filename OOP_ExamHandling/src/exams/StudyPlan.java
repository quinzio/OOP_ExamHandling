package exams;

import java.util.Map;
import java.util.TreeMap;

public class StudyPlan {
	private String name;
	private Map<String, Course> courses = new TreeMap<>();
	private Map<String, Student> students = new TreeMap<>();
	
	public StudyPlan(String name, Map<String, Course> courses) {
		this.name = name;
		if (courses != null)
			this.courses = courses;
	}

	public String getName() {
		return name;
	}

	public Map<String, Course> getCourses() {
		return courses;
	}
	
	public void addCourse(Course course) {
		courses.put(course.getName(), course);
	}
	
	public void addStudent(Student student) {
		students.put(student.getId(), student);
	}

	public Map<String, Student> getStudents() {
		return students;
	}

}
