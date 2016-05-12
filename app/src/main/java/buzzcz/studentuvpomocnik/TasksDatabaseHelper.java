package buzzcz.studentuvpomocnik;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TasksDatabaseHelper extends SQLiteOpenHelper {

	public TasksDatabaseHelper(Context context) {
		super(context, "tasks", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE tasks (_id INTEGER PRIMARY KEY AUTOINCREMENT, personalNumber " +
				"TEXT, subject TEXT, time DATE, title TEXT, description TEXT);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS tasks");
		onCreate(db);
	}
}
