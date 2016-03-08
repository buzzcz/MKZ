package buzzcz.studentuvpomocnik;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public final class ParseXmls {

	private ParseXmls() {
	}

	public static ArrayList<Subject> parseTimetable(InputStream in) throws XmlPullParserException,
			IOException {
		ArrayList<Subject> timetable = new ArrayList<>();
		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(in, null);
		parser.nextTag();

		parser.require(XmlPullParser.START_TAG, null, "ns1:getRozvrhByStudentResponse");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) continue;
			String name = parser.getName();
			if (name.equals("rozvrh")) timetable = readTimetable(parser);
			else skip(parser);
		}
		return timetable;
	}

	private static ArrayList<Subject> readTimetable(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		ArrayList<Subject> timetable = new ArrayList<>();

		parser.require(XmlPullParser.START_TAG, null, "rozvrh");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) continue;
			String name = parser.getName();
			if (name.equals("rozvrhovaAkce")) timetable.add(readSubject(parser));
			else skip(parser);
		}
		return timetable;
	}

	private static Subject readSubject(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "rozvrhovaAkce");
		String subjName = "";
		String department = "";
		String shortcut = "";
		String statute = "";
		String teacher = "";
		String room = "";
		String type = "";
		String semester = "";
		int day = 7;
		String starts = "";
		String ends = "";
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) continue;
			String name = parser.getName();
			switch (name) {
				case "nazev":
					subjName = readTextFromTag(parser, "nazev");
					break;
				case "katedra":
					department = readTextFromTag(parser, "katedra");
					break;
				case "predmet":
					shortcut = readTextFromTag(parser, "predmet");
					break;
				case "statut":
					statute = readTextFromTag(parser, "statut");
					break;
				case "budova":
					room = readTextFromTag(parser, "budova");
					break;
				case "mistnost":
					room += readTextFromTag(parser, "mistnost");
					break;
				case "typAkce":
					type = readTextFromTag(parser, "typAkce");
					break;
				case "semestr":
					semester += readTextFromTag(parser, "semestr");
					break;
				case "denZkr":
					day = readDay(parser, "denZkr");
					break;
				case "hodinaSkutOd":
					starts = readTextFromTag(parser, "hodinaSkutOd");
					break;
				case "hodinaSkutDo":
					ends = readTextFromTag(parser, "hodinaSkutDo");
					break;
				case "vsichniUciteleJmenaTituly":
					teacher = readTextFromTag(parser, "vsichniUciteleJmenaTituly");
					break;
				default:
					skip(parser);
					break;
			}
		}
		return new Subject(subjName, department, shortcut, statute, teacher, room, type, semester,
				day, starts, ends);
	}

	public static Sylabus parseSylabus(InputStream in) throws XmlPullParserException,
			IOException {
		Sylabus sylabus = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
		parser.setInput(in, null);
		parser.nextTag();

		parser.require(XmlPullParser.START_TAG, null, "ns1:getPredmetInfoResponse");
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) continue;
			String name = parser.getName();
			if (name.equals("predmetInfo")) sylabus = readSylabus(parser);
			else skip(parser);
		}
		return sylabus;
	}

	private static Sylabus readSylabus(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "predmetInfo");
		String department = "";
		String shortcut = "";
		String subjName = "";
		String credits = "";
		String garants = "";
		String teachers = "";
		String excerciseTeachers = "";
		String seminarTeachers = "";
		String conditioningSubjects = "";
		String excludingSubjects = "";
		String isConditionToSubjects = "";
		String literature = "";
		String anotation = "";
		String typeOfExam = "";
		String creditBeforeExam = "";
		String examForm = "";
		String requirements = "";
		String overview = "";
		String prerequisitions = "";
		String gainKnowledge = "";
		String timeSeverity = "";
		String url = "";
		String languages = "";
		while (parser.next() != XmlPullParser.END_TAG) {
			if (parser.getEventType() != XmlPullParser.START_TAG) continue;
			String name = parser.getName();
			switch (name) {
				case "katedra":
					department = readTextFromTag(parser, "katedra");
					break;
				case "zkratka":
					shortcut = readTextFromTag(parser, "zkratka");
					break;
				case "nazevDlouhy":
					subjName = readTextFromTag(parser, "nazevDlouhy");
					break;
				case "kreditu":
					credits = readTextFromTag(parser, "kreditu");
					break;
				case "garanti":
					garants = readTextFromTag(parser, "garanti");
					break;
				case "prednasejici":
					teachers = readTextFromTag(parser, "prednasejici");
					break;
				case "cvicici":
					excerciseTeachers = readTextFromTag(parser, "cvicici");
					break;
				case "seminarici":
					seminarTeachers = readTextFromTag(parser, "seminarici");
					break;
				case "podminujiciPredmety":
					conditioningSubjects = readTextFromTag(parser, "podminujiciPredmety");
					break;
				case "vylucujiciPredmety":
					excludingSubjects = readTextFromTag(parser, "vylucujiciPredmety");
					break;
				case "podminujePredmety":
					isConditionToSubjects = readTextFromTag(parser, "podminujePredmety");
					break;
				case "literatura":
					literature = readTextFromTag(parser, "literatura");
					break;
				case "anotace":
					anotation = readTextFromTag(parser, "anotace");
					break;
				case "typZkousky":
					typeOfExam = readTextFromTag(parser, "typZkousky");
					break;
				case "maZapocetPredZk":
					creditBeforeExam = readTextFromTag(parser, "maZapocetPredZk");
					break;
				case "formaZkousky":
					examForm = readTextFromTag(parser, "formaZkousky");
					break;
				case "pozadavky":
					requirements = readTextFromTag(parser, "pozadavky");
					break;
				case "prehledLatky":
					overview = readTextFromTag(parser, "prehledLatky");
					break;
				case "predpoklady":
					prerequisitions = readTextFromTag(parser, "predpoklady");
					break;
				case "ziskaneZpusobilosti":
					gainKnowledge = readTextFromTag(parser, "ziskaneZpusobilosti");
					break;
				case "casovaNarocnost":
					timeSeverity = readTextFromTag(parser, "casovaNarocnost");
					break;
				case "predmetUrl":
					url = readTextFromTag(parser, "predmetUrl");
					break;
				case "vyucovaciJazyky":
					languages = readTextFromTag(parser, "vyucovaciJazyky");
					break;
				default:
					skip(parser);
					break;
			}
		}
		return new Sylabus(department, shortcut, subjName, credits, garants, teachers,
				excerciseTeachers, seminarTeachers, conditioningSubjects, excludingSubjects,
				isConditionToSubjects, literature, anotation, typeOfExam, creditBeforeExam,
				examForm, prerequisitions, overview, requirements, gainKnowledge, timeSeverity,
				url, languages);
	}

	private static String readTextFromTag(XmlPullParser parser, String tag) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, tag);
		String name = "";
		name = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, tag);
		return name;
	}

	private static int readDay(XmlPullParser parser, String tag) throws IOException,
			XmlPullParserException {
		String day = readTextFromTag(parser, tag);

		switch (day) {
			case "Po":
				return 0;
			case "Út":
				return 1;
			case "St":
				return 2;
			case "Čt":
				return 3;
			case "Pá":
				return 4;
			case "So":
				return 5;
			case "Ne":
				return 6;
			default:
				return 7;
		}
	}

	private static String readText(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		String result = "";
		if (parser.next() == XmlPullParser.TEXT) {
			result = parser.getText();
			parser.nextTag();
		}
		return result;
	}

	private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
		if (parser.getEventType() != XmlPullParser.START_TAG) {
			throw new IllegalStateException();
		}
		int depth = 1;
		while (depth != 0) {
			switch (parser.next()) {
				case XmlPullParser.END_TAG:
					depth--;
					break;
				case XmlPullParser.START_TAG:
					depth++;
					break;
			}
		}
	}
}
