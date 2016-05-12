package buzzcz.studentuvpomocnik;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
	private int id;
	private String personalNumber;
	private String subject;
	private String date;
	private String time;
	private String title;
	private String description;

	public Task(int id, String personalNumber, String subject, String date, String time, String
			title, String
			description) {
		this.id = id;
		this.personalNumber = personalNumber;
		this.subject = subject;
		this.date = date;
		this.time = time;
		this.title = title;
		this.description = description;
	}

	protected Task(Parcel in) {
		id = in.readInt();
		personalNumber = in.readString();
		subject = in.readString();
		date = in.readString();
		time = in.readString();
		title = in.readString();
		description = in.readString();
	}

	public static final Creator<Task> CREATOR = new Creator<Task>() {
		@Override
		public Task createFromParcel(Parcel in) {
			return new Task(in);
		}

		@Override
		public Task[] newArray(int size) {
			return new Task[size];
		}
	};

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPersonalNumber() {
		return personalNumber;
	}

	public void setPersonalNumber(String personalNumber) {
		this.personalNumber = personalNumber;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(personalNumber);
		dest.writeString(subject);
		dest.writeString(date);
		dest.writeString(time);
		dest.writeString(title);
		dest.writeString(description);
	}
}
