package buzzcz.studentuvpomocnik.timetables;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Class representing a subject
 * <p>
 * Created by Jaroslav Klaus
 */
public class Subject implements Parcelable {
	/**
	 * Subject's full name
	 */
	private final String name;
	/**
	 * Subject's department
	 */
	private final String department;
	/**
	 * Short name of the subject
	 */
	private final String shortcut;
	/**
	 * Subject's statue
	 */
	private final String statute;
	/**
	 * Subject's teachers
	 */
	private final String teacher;
	/**
	 * Room in which subject is taught
	 */
	private final String room;
	/**
	 * Type of subject
	 */
	private final String type;
	/**
	 * Semester in which is the subject taught
	 */
	private final String semester;
	/**
	 * Day on which is the subject taught
	 */
	private final int day;
	/**
	 * Time when the subject starts
	 */
	private final String starts;
	/**
	 * Time when the subject ends
	 */
	private final String ends;
	/**
	 * Items that are needed for expandable list view
	 */
	private ArrayList<String> items;

	/**
	 * Constructor for creating subjects
	 *
	 * @param name       Subject's full name
	 * @param department Subject's department
	 * @param shortcut   Short name of the subject
	 * @param statute    Subject's statue
	 * @param teacher    Subject's teachers
	 * @param room       Room in which subject is taught
	 * @param type       Type of subject
	 * @param semester   Semester in which is the subject taught
	 * @param day        Day on which is the subject taught
	 * @param starts     Time when the subject starts
	 * @param ends       Time when the subject ends
	 */
	public Subject(String name, String department, String shortcut, String statute, String
			teacher, String room, String type, String semester, int day, String starts, String
			               ends) {
		this.name = name;
		this.department = department;
		this.shortcut = shortcut;
		this.statute = statute;
		this.teacher = teacher;
		this.room = room;
		this.type = type;
		this.semester = semester;
		this.day = day;
		this.starts = starts;
		this.ends = ends;
	}

	/**
	 * Constructor for creating subject from parcel
	 *
	 * @param in parcel to create subject from
	 */
	private Subject(Parcel in) {
		name = in.readString();
		department = in.readString();
		shortcut = in.readString();
		statute = in.readString();
		teacher = in.readString();
		room = in.readString();
		type = in.readString();
		semester = in.readString();
		day = in.readInt();
		starts = in.readString();
		ends = in.readString();
		items = in.createStringArrayList();
	}

	public static final Creator<Subject> CREATOR = new Creator<Subject>() {
		@Override
		public Subject createFromParcel(Parcel in) {
			return new Subject(in);
		}

		@Override
		public Subject[] newArray(int size) {
			return new Subject[size];
		}
	};

	@Override
	public String toString() {
		return "Subject{" +
				"name='" + name + '\'' +
				", department='" + department + '\'' +
				", shortcut='" + shortcut + '\'' +
				", statute='" + statute + '\'' +
				", teacher='" + teacher + '\'' +
				", room='" + room + '\'' +
				", type='" + type + '\'' +
				", semester='" + semester + '\'' +
				", day=" + day +
				", starts='" + starts + '\'' +
				", ends='" + ends + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public String getDepartment() {
		return department;
	}

	public String getShortcut() {
		return shortcut;
	}

	public String getTeacher() {
		return teacher;
	}

	public String getRoom() {
		return room;
	}

	public String getType() {
		return type;
	}

	private String getSemester() {
		return semester;
	}

	public int getDay() {
		return day;
	}

	public String getStarts() {
		return starts;
	}

	public String getEnds() {
		return ends;
	}

	public ArrayList<String> getItems() {
		return items;
	}

	public void setItems(ArrayList<String> items) {
		this.items = items;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(department);
		dest.writeString(shortcut);
		dest.writeString(statute);
		dest.writeString(teacher);
		dest.writeString(room);
		dest.writeString(type);
		dest.writeString(semester);
		dest.writeInt(day);
		dest.writeString(starts);
		dest.writeString(ends);
		dest.writeStringList(items);
	}

	/**
	 * Removes subjects that are from other than selected semester from the list. Then the list is
	 * sorted by the time when the subject starts
	 *
	 * @param timetable list of subjects
	 * @param semester  selected semester
	 */
	public static void sortTimetable(ArrayList<Subject> timetable, String semester) {
		ArrayList<Subject> toRemove = new ArrayList<>();
		if (semester.equals("ZS")) {
			for (Subject s : timetable) {
				if (s.getSemester().equals("LS") || s.getType().equals("Zkouška")) toRemove.add(s);
			}
		} else {
			for (Subject s : timetable) {
				if (s.getSemester().equals("ZS") || s.getType().equals("Zkouška")) toRemove.add(s);
			}
		}
		timetable.removeAll(toRemove);
		Collections.sort(timetable, new Comparator<Subject>() {
			@Override
			public int compare(Subject lhs, Subject rhs) {
				return lhs.getStarts().compareTo(rhs.getStarts());
			}
		});
	}
}
