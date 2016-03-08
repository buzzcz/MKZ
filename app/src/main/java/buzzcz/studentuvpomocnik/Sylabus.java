package buzzcz.studentuvpomocnik;

public class Sylabus {
	private String department;
	private String shortcut;
	private String name;
	private String credits;
	private String garants;
	private String teachers;
	private String excerciseTeachers;
	private String seminarTeachers;
	private String conditioningSubjects;
	private String excludingSubjects;
	private String isConditionToSubjects;
	private String literature;
	private String anotation;
	private String typeOfExam;
	private String creditBeforeExam;
	private String examForm;
	private String requirements;
	private String overview;
	private String prerequisitions;
	private String gainKnowledge;
	private String timeSeverity;
	private String url;
	private String languages;

	public Sylabus(String department, String shortcut, String name, String credits, String
			garants, String teachers, String excerciseTeachers, String seminarTeachers, String
			               conditioningSubjects, String excludingSubjects, String
			isConditionToSubjects, String
			               literature, String anotation, String typeOfExam, String
			creditBeforeExam, String
			               examForm, String prerequisitions, String overview, String requirements,
	               String
			               gainKnowledge, String timeSeverity, String url, String languages) {
		this.department = department;
		this.shortcut = shortcut;
		this.name = name;
		this.credits = credits;
		this.garants = garants;
		this.teachers = teachers;
		this.excerciseTeachers = excerciseTeachers;
		this.seminarTeachers = seminarTeachers;
		this.conditioningSubjects = conditioningSubjects;
		this.excludingSubjects = excludingSubjects;
		this.isConditionToSubjects = isConditionToSubjects;
		this.literature = literature;
		this.anotation = anotation;
		this.typeOfExam = typeOfExam;
		this.creditBeforeExam = creditBeforeExam;
		this.examForm = examForm;
		this.prerequisitions = prerequisitions;
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

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getShortcut() {
		return shortcut;
	}

	public void setShortcut(String shortcut) {
		this.shortcut = shortcut;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCredits() {
		return credits;
	}

	public void setCredits(String credits) {
		this.credits = credits;
	}

	public String getGarants() {
		return garants;
	}

	public void setGarants(String garants) {
		this.garants = garants;
	}

	public String getTeachers() {
		return teachers;
	}

	public void setTeachers(String teachers) {
		this.teachers = teachers;
	}

	public String getExcerciseTeachers() {
		return excerciseTeachers;
	}

	public void setExcerciseTeachers(String excerciseTeachers) {
		this.excerciseTeachers = excerciseTeachers;
	}

	public String getSeminarTeachers() {
		return seminarTeachers;
	}

	public void setSeminarTeachers(String seminarTeachers) {
		this.seminarTeachers = seminarTeachers;
	}

	public String getConditioningSubjects() {
		return conditioningSubjects;
	}

	public void setConditioningSubjects(String conditioningSubjects) {
		this.conditioningSubjects = conditioningSubjects;
	}

	public String getExcludingSubjects() {
		return excludingSubjects;
	}

	public void setExcludingSubjects(String excludingSubjects) {
		this.excludingSubjects = excludingSubjects;
	}

	public String getIsConditionToSubjects() {
		return isConditionToSubjects;
	}

	public void setIsConditionToSubjects(String isConditionToSubjects) {
		this.isConditionToSubjects = isConditionToSubjects;
	}

	public String getLiterature() {
		return literature;
	}

	public void setLiterature(String literature) {
		this.literature = literature;
	}

	public String getAnotation() {
		return anotation;
	}

	public void setAnotation(String anotation) {
		this.anotation = anotation;
	}

	public String getTypeOfExam() {
		return typeOfExam;
	}

	public void setTypeOfExam(String typeOfExam) {
		this.typeOfExam = typeOfExam;
	}

	public String getCreditBeforeExam() {
		return creditBeforeExam;
	}

	public void setCreditBeforeExam(String creditBeforeExam) {
		this.creditBeforeExam = creditBeforeExam;
	}

	public String getExamForm() {
		return examForm;
	}

	public void setExamForm(String examForm) {
		this.examForm = examForm;
	}

	public String getPrerequisitions() {
		return prerequisitions;
	}

	public void setPrerequisitions(String prerequisitions) {
		this.prerequisitions = prerequisitions;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public String getGainKnowledge() {
		return gainKnowledge;
	}

	public void setGainKnowledge(String gainKnowledge) {
		this.gainKnowledge = gainKnowledge;
	}

	public String getTimeSeverity() {
		return timeSeverity;
	}

	public void setTimeSeverity(String timeSeverity) {
		this.timeSeverity = timeSeverity;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLanguages() {
		return languages;
	}

	public void setLanguages(String languages) {
		this.languages = languages;
	}
}