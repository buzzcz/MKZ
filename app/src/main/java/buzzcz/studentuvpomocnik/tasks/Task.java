package buzzcz.studentuvpomocnik.tasks;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class representing task
 * <p>
 * Created by Jaroslav Klaus
 */
public class Task implements Parcelable {
	/**
	 * Task's id
	 */
	private final int id;
	/**
	 * Student's personal number
	 */
	private final String personalNumber;
	/**
	 * Name of the subject which task is this
	 */
	private final String subject;
	/**
	 * Due date of the task
	 */
	private final String date;
	/**
	 * Due time of the task
	 */
	private final String time;
	/**
	 * Task's title
	 */
	private final String title;
	/**
	 * Task's description
	 */
	private final String description;

	/**
	 * Constructor for creating tasks
	 *
	 * @param id             Task's id
	 * @param personalNumber Student's personal number
	 * @param subject        Name of the subject which task is this
	 * @param date           Due date of the task
	 * @param time           Due time of the task
	 * @param title          Task's title
	 * @param description    Task's description
	 */
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

	/**
	 * Constructor for creating task from parcel
	 *
	 * @param in parcel to create task from
	 */
	private Task(Parcel in) {
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

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
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
