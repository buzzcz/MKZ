package buzzcz.studentuvpomocnik.syllabuses;

/**
 * Class representing subject's syllabus
 * <p>
 * Created by Jaroslav Klaus
 */
public class Syllabus {
	/**
	 * Department of subject
	 */
	private final String department;
	/**
	 * Subject's shortcut
	 */
	private final String shortcut;
	/**
	 * Subject's full name
	 */
	private final String name;
	/**
	 * Credits for absolving this subject
	 */
	private final String credits;
	/**
	 * Subject's garants
	 */
	private final String garants;
	/**
	 * Subject's teachers
	 */
	private final String teachers;
	/**
	 * Subject's exercise teachers
	 */
	private final String exerciseTeachers;
	/**
	 * Subject's seminar teachers
	 */
	private final String seminarTeachers;
	/**
	 * Conditioning subjects of this subject
	 */
	private final String conditioningSubjects;
	/**
	 * Excluding subjects of this subject
	 */
	private final String excludingSubjects;
	/**
	 * Subjects which conditioning subject is this subject
	 */
	private final String isConditionToSubjects;
	/**
	 * Literature recommended for this subject
	 */
	private final String literature;
	/**
	 * Subject's annotation
	 */
	private final String annotation;
	/**
	 * How is this subject ended
	 */
	private final String typeOfExam;
	/**
	 * If you need to pass a credit test before exam
	 */
	private final String creditBeforeExam;
	/**
	 * Form of exam
	 */
	private final String examForm;
	/**
	 * Subject's requirements
	 */
	private final String requirements;
	/**
	 * Overview of the discussed matter
	 */
	private final String overview;
	/**
	 * Subject's prerequisites
	 */
	private final String prerequisites;
	/**
	 * What knowledge you gain from this subject
	 */
	private final String gainKnowledge;
	/**
	 * How much time does this subject consumes
	 */
	private final String timeSeverity;
	/**
	 * URL for subject's website
	 */
	private final String url;
	/**
	 * In what languages is the subject taught
	 */
	private final String languages;

	/**
	 * Constructor for syllabus
	 *
	 * @param department            Department of subject
	 * @param shortcut              Subject's shortcut
	 * @param name                  Subject's full name
	 * @param credits               Credits for absolving this subject
	 * @param garants               Subject's garants
	 * @param teachers              Subject's teachers
	 * @param exerciseTeachers      Subject's exercise teachers
	 * @param seminarTeachers       Subject's seminar teachers
	 * @param conditioningSubjects  Conditioning subjects of this subject
	 * @param excludingSubjects     Excluding subjects of this subject
	 * @param isConditionToSubjects Subjects which conditioning subject is this subject
	 * @param literature            Literature recommended for this subject
	 * @param annotation            Subject's annotation
	 * @param typeOfExam            How is this subject ended
	 * @param creditBeforeExam      If you need to pass a credit test before exam
	 * @param examForm              Form of exam
	 * @param prerequisites         Subject's prerequisites
	 * @param overview              Overview of the discussed matter
	 * @param requirements          Subject's requirements
	 * @param gainKnowledge         What knowledge you gain from this subject
	 * @param timeSeverity          How much time does this subject consumes
	 * @param url                   URL for subject's website
	 * @param languages             In what languages is the subject taught
	 */
	public Syllabus(String department, String shortcut, String name, String credits, String
			garants, String teachers, String exerciseTeachers, String seminarTeachers, String
			                conditioningSubjects, String excludingSubjects, String
			                isConditionToSubjects, String
			                literature, String annotation, String typeOfExam, String
			                creditBeforeExam, String
			                examForm, String prerequisites, String overview, String requirements,
	                String
			                gainKnowledge, String timeSeverity, String url, String languages) {
		this.department = department;
		this.shortcut = shortcut;
		this.name = name;
		this.credits = credits;
		this.garants = garants;
		this.teachers = teachers;
		this.exerciseTeachers = exerciseTeachers;
		this.seminarTeachers = seminarTeachers;
		this.conditioningSubjects = conditioningSubjects;
		this.excludingSubjects = excludingSubjects;
		this.isConditionToSubjects = isConditionToSubjects;
		this.literature = literature;
		this.annotation = annotation;
		this.typeOfExam = typeOfExam;
		this.creditBeforeExam = creditBeforeExam;
		this.examForm = examForm;
		this.prerequisites = prerequisites;
		this.overview = overview;
		this.requirements = requirements;
		this.gainKnowledge = gainKnowledge;
		this.timeSeverity = timeSeverity;
		this.url = url;
		this.languages = languages;
	}

	public String getDepartment() {
		return department;
	}

	public String getShortcut() {
		return shortcut;
	}

	public String getName() {
		return name;
	}

	public String getCredits() {
		return credits;
	}

	public String getGarants() {
		return garants;
	}

	public String getTeachers() {
		return teachers;
	}

	public String getExerciseTeachers() {
		return exerciseTeachers;
	}

	public String getSeminarTeachers() {
		return seminarTeachers;
	}

	public String getConditioningSubjects() {
		return conditioningSubjects;
	}

	public String getExcludingSubjects() {
		return excludingSubjects;
	}

	public String getIsConditionToSubjects() {
		return isConditionToSubjects;
	}

	public String getLiterature() {
		return literature;
	}

	public String getAnnotation() {
		return annotation;
	}

	public String getTypeOfExam() {
		return typeOfExam;
	}

	public String getCreditBeforeExam() {
		return creditBeforeExam;
	}

	public String getExamForm() {
		return examForm;
	}

	public String getPrerequisites() {
		return prerequisites;
	}

	public String getOverview() {
		return overview;
	}

	public String getRequirements() {
		return requirements;
	}

	public String getGainKnowledge() {
		return gainKnowledge;
	}

	public String getTimeSeverity() {
		return timeSeverity;
	}

	public String getUrl() {
		return url;
	}

	public String getLanguages() {
		return languages;
	}

}