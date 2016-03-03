package buzzcz.studentuvpomocnik;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Subject implements Parcelable {
	private String name;
	private String department;
	private String shortcut;
	private String statute;
	private String teacher;
	private String room;
	private String type;
	private String semester;
	private int day;
	private String starts;
	private String ends;
	ArrayList<String> items;

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

	protected Subject(Parcel in) {
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

	public void setName(String name) {
		this.name = name;
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

	public String getStatute() {
		return statute;
	}

	public void setStatute(String statute) {
		this.statute = statute;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getStarts() {
		return starts;
	}

	public void setStarts(String starts) {
		this.starts = starts;
	}

	public String getEnds() {
		return ends;
	}

	public void setEnds(String ends) {
		this.ends = ends;
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
}
