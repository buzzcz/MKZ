package buzzcz.studentuvpomocnik.terms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class for handling database of terms
 * <p>
 * Created by Jaroslav Klaus
 */
class TermsDatabaseHelper extends SQLiteOpenHelper {

	/**
	 * Constructor for creating helper
	 *
	 * @param context context passed to the helper
	 */
	public TermsDatabaseHelper(Context context) {
		super(context, "terms", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE terms (_id INTEGER PRIMARY KEY AUTOINCREMENT, personalNumber " +
				"TEXT, subject TEXT, title TEXT, description TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS terms");
		onCreate(db);
	}
}
