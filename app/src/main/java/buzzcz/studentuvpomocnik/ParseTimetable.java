package buzzcz.studentuvpomocnik;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public final class ParseTimetable {

	private ParseTimetable() {
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
					subjName = readName(parser);
					break;
				case "katedra":
					department = readDepartment(parser);
					break;
				case "predmet":
					shortcut = readShortcut(parser);
					break;
				case "statut":
					statute = readStatute(parser);
					break;
				case "budova":
					room = readBuilding(parser);
					break;
				case "mistnost":
					room += readRoom(parser);
					break;
				case "typAkce":
					type = readType(parser);
					break;
				case "semestr":
					semester += readSemester(parser);
					break;
				case "denZkr":
					day = readDay(parser);
					break;
				case "hodinaSkutOd":
					starts = readStarts(parser);
					break;
				case "hodinaSkutDo":
					ends = readEnds(parser);
					break;
				case "vsichniUciteleJmenaTituly":
					teacher = readTeacher(parser);
					break;
				default:
					skip(parser);
					break;
			}
		}
		return new Subject(subjName, department, shortcut, statute, teacher, room, type, semester,
				day, starts, ends);
	}

	private static String readName(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "nazev");
		String name = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "nazev");
		return name;
	}

	private static String readDepartment(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "katedra");
		String department = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "katedra");
		return department;
	}

	private static String readShortcut(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "predmet");
		String shortcut = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "predmet");
		return shortcut;
	}

	private static String readStatute(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "statut");
		String statute = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "statut");
		return statute;
	}

	private static String readTeacher(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "vsichniUciteleJmenaTituly");
		String teacher = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "vsichniUciteleJmenaTituly");
		return teacher;
	}

	private static String readBuilding(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "budova");
		String building = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "budova");
		return building;
	}

	private static String readRoom(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "mistnost");
		String room = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "mistnost");
		return room;
	}

	private static String readType(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "typAkce");
		String type = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "typAkce");
		return type;
	}

	private static String readSemester(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "semestr");
		String semester = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "semestr");
		return semester;
	}

	private static int readDay(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "denZkr");
		String day = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "denZkr");

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

	private static String readStarts(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "hodinaSkutOd");
		String starts = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "hodinaSkutOd");
		return starts;
	}

	private static String readEnds(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		parser.require(XmlPullParser.START_TAG, null, "hodinaSkutDo");
		String ends = readText(parser);
		parser.require(XmlPullParser.END_TAG, null, "hodinaSkutDo");
		return ends;
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
