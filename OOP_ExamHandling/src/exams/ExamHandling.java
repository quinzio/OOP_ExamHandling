package exams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ExamHandling {
	private Map<String, StudyPlan> studyPlans = new TreeMap<>();
	private Map<String, Course> coursesGlob = new TreeMap<>();
	private Map<String, Student> students = new TreeMap<>();

	/**
	 * Il metodo addStudyPlan (String name, String... courses) aggiunge un piano
	 * di studio; il primo parametro è il nome del piano di studio e i parametri
	 * successivi sono i nomi dei corsi che lo compongono. Un corso può far
	 * parte di più piani di studio. Lancia un'eccezione se il nome del piano è
	 * duplicato.
	 * 
	 * @throws ExamException
	 **/
	public void addStudyPlan(String name, String... courses)
			throws ExamException {
		if (studyPlans.containsKey(name))
			throw new ExamException();
		StudyPlan studyPlan = new StudyPlan(name, null);
		for (String c : courses) {
			if (coursesGlob.containsKey(c))
				throw new ExamException();
			Course course = new Course(c);
			studyPlan.addCourse(course);
			coursesGlob.put(c, course);
		}
	}

	/**
	 * Il metodo enrollStudent (String studentId, String studentName, String
	 * studyPlan) registra uno studente con identificativo e nome, e associa
	 * allo studente un piano di studi. Lancia un'eccezione se l'identificativo
	 * è duplicato o se il piano non è definito.
	 * 
	 * @throws ExamException
	 * 
	 **/
	public void enrollStudent(String studentId, String studentName,
			String studyPlan) throws ExamException {

		if (students.containsKey(studentId))
			throw new ExamException();
		Student stud = new Student(studentName, studentId,
				studyPlans.get(studyPlan));
		studyPlans.get(studyPlan).addStudent(stud);
		students.put(studentId, stud);
		for (Course crs : studyPlans.get(studyPlan).getCourses().values()) {
			crs.addStudent(students.get(studentId));
		}
	}
	/**
	 * 
	 * Il metodo numberOfStudentsForStudyPlan dà il n. di studenti per ogni
	 * piano di studio; i piani sono ordinati alfabeticamente per nome.
	 * SortedMap<String, Long> numberOfStudentsForStudyPlan =
	 * eh.numberOfStudentsForStudyPlan();
	 */
	public SortedMap<String, Long> numberOfStudentsForStudyPlan() {
		return studyPlans.values().stream()
				.collect(Collectors.toMap(StudyPlan::getName,
						s -> Long.valueOf(s.getStudents().size()), (m1, m2) -> {
							return m1;
						}, TreeMap::new));
	}

	/**
	 * 
	 * Il metodo studentsForStudyPlan dà la lista ordinata degli id degli
	 * studenti per ogni piano di studio; i piani sono ordinati alfabeticamente
	 * per nome.
	 * 
	 * // * SortedMap<String, List<String>> studentsForStudyPlan =
	 * eh.studentsForStudyPlan();
	 */
	public SortedMap<String, List<String>> studentsForStudyPlan() {
		return students.values().stream().collect(Collectors.groupingBy(
				stud -> stud.getStudyplan().getName(), TreeMap::new,
				Collectors.mapping(Student::getId, Collectors.toList())));

	}
	/**
	 * 
	 * Il metodo openExam (String courseName) apre una sessione d’esame per il
	 * corso indicato. Lancia un'eccezione se il corso non è definito o se c’è
	 * già una sessione aperta.
	 * 
	 * @throws ExamException
	 * 
	 */
	public void openExam(String courseName) throws ExamException {
		if (!coursesGlob.containsKey(courseName))
			throw new ExamException();
		if (coursesGlob.get(courseName).isExamOpen())
			throw new ExamException();

		coursesGlob.get(courseName).setExamOpen(true);
	}

	/***
	 * 
	 * Uno studente dà un esame con il metodo takeExam (String studentId, String
	 * course). Il metodo lancia un'eccezione se il corso non è definito o non è
	 * nel piano di studi dello studente, la sessione d’esame non è aperta, lo
	 * studente ha già eseguito takeExam nella sessione oppure ha già un voto
	 * sufficiente (>= 18) per il corso.
	 * 
	 * @throws ExamException
	 * 
	 **/
	public void takeExam(String studentId, String course) throws ExamException {
		if (!coursesGlob.containsKey(course))
			throw new ExamException();
		if (!students.get(studentId).getStudyplan().getCourses()
				.containsKey(course))
			throw new ExamException();
		if (!coursesGlob.get(course).isExamOpen())
			throw new ExamException();
		if (coursesGlob.get(course).getGrades().containsKey(studentId))
			throw new ExamException();

		coursesGlob.get(course).getGrades().put(studentId, null);
	}

	/**
	 * Il metodo studentsWhoTookTheExam (String courseName) dà l’elenco ordinato
	 * degli id degli studenti che hanno eseguito takeExam nella sessione
	 * corrente. Lancia un'eccezione se il corso non è definito.
	 * 
	 **/
	public List<String> studentsWhoTookTheExam(String courseName) {
		if (coursesGlob.get(courseName).isExamClose()) return null;
		List<String> ls = new ArrayList<>(
				coursesGlob.get(courseName).getGrades().keySet());
		ls.sort(Comparator.naturalOrder());
		return ls;
	}

	/***
	 * 
	 * Il metodo giveGrade (String courseName, String studentId, int grade)
	 * permette di dare un voto allo studente. Il metodo lancia un'eccezione se
	 * lo studente non ha dato l’esame (cioè non ha eseguito takeExam), ha già
	 * un voto sufficiente (>= 18) per l'esame, il voto non è compreso tra 12 e
	 * 30 (estremi inclusi). L'esame è superato se il voto è sufficiente,
	 * altrimenti è fallito. Un voto <18 è detto failing grade.
	 * 
	 * @throws ExamException
	 * 
	 **/

	public void giveGrade(String courseName, String studentId, int grade)
			throws ExamException {
		if (grade < 12 | grade > 30)
			throw new ExamException();
		if (!coursesGlob.get(courseName).getGrades().containsKey(studentId))
			throw new ExamException();
		if (coursesGlob.get(courseName).getGrades().get(studentId) >= 18)
			throw new ExamException();
		coursesGlob.get(courseName).getGrades().put(studentId, grade);
	}
	/***
	 * Il metodo closeExam (String courseName) chiude la sessione d'esame.
	 * Lancia un’eccezione se la sessione non è aperta per il corso o se non
	 * tutti gli studenti che hanno dato l’esame hanno ricevuto un voto. Dopo la
	 * chiusura di una sessione d'esame, la chiamata del metodo
	 * studentsWhoTookTheExam dà una lista vuota (cioè la lista degli studenti
	 * che hanno dato l’esame nella sessione ormai chiusa è vuota).
	 * 
	 * @throws ExamException
	 **/
	public void closeExam(String courseName) throws ExamException {
		Course course = coursesGlob.get(courseName);
		if (!course.isExamOpen())
			throw new ExamException();
		if (course.getGrades().values().contains(null)) 
			throw new ExamException();
		course.setExamClose(true);
		
	}

}
