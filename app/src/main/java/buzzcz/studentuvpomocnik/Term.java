package buzzcz.studentuvpomocnik;

import android.os.Parcel;
import android.os.Parcelable;

public class Term implements Parcelable {
	private int id;
	private String personalNumber;
	private String subject;
	private String title;
	private String description;

	public Term(int id, String personalNumber, String subject, String
			title, String
			            description) {
		this.id = id;
		this.personalNumber = personalNumber;
		this.subject = subject;
		this.title = title;
		this.description = description;
	}

	protected Term(Parcel in) {
		id = in.readInt();
		personalNumber = in.readString();
		subject = in.readString();
		title = in.readString();
		description = in.readString();
	}

	public static final Creator<Term> CREATOR = new Creator<Term>() {
		@Override
		public Term createFromParcel(Parcel in) {
			return new Term(in);
		}

		@Override
		public Term[] newArray(int size) {
			return new Term[size];
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
		dest.writeString(title);
		dest.writeString(description);
	}
}
