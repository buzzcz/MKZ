package buzzcz.studentuvpomocnik.terms;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class representing term
 * <p>
 * Created by Jaroslav Klaus
 */
public class Term implements Parcelable {

	/**
	 * Term's id
	 */
	private final int id;
	/**
	 * Student's personal number
	 */
	private final String personalNumber;
	/**
	 * Name of the subject which term is this
	 */
	private final String subject;
	/**
	 * Term's title
	 */
	private final String title;
	/**
	 * Term's description
	 */
	private final String description;

	/**
	 * Constructor for creating terms
	 *
	 * @param id             Term's id
	 * @param personalNumber Student's personal number
	 * @param subject        Name of the subject which term is this
	 * @param title          Term's title
	 * @param description    Term's description
	 */
	public Term(int id, String personalNumber, String subject, String
			title, String
			            description) {
		this.id = id;
		this.personalNumber = personalNumber;
		this.subject = subject;
		this.title = title;
		this.description = description;
	}

	/**
	 * Constructor for creating term from parcel
	 *
	 * @param in parcel to create term from
	 */
	private Term(Parcel in) {
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
		dest.writeString(title);
		dest.writeString(description);
	}
}
