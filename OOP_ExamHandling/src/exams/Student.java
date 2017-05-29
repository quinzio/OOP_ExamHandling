package exams;

public class Student {
	private String name;
	private String id;
	private StudyPlan studyplan;
	
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
	
	

}
