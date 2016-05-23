package buzzcz.studentuvpomocnik.terms;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import buzzcz.studentuvpomocnik.R;

/**
 * Activity for adding or editing terms
 * <p>
 * Created by Jaroslav Klaus
 */
public class AddTermActivity extends AppCompatActivity {

	/**
	 * Student's personal number
	 */
	private String personalNumber;
	/**
	 * Name of the subject which task is being added/edited
	 */
	private String subjectName;
	/**
	 * Term that is being edited
	 */
	private Term term;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_term);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		personalNumber = getIntent().getStringExtra("personalNumber");
		subjectName = getIntent().getStringExtra("subject");
		term = getIntent().getParcelableExtra("term");
		setTitle(getTitle() + " " + subjectName);

		// If term is passed, set the values
		if (term != null) {
			((EditText) findViewById(R.id.titleEditText)).setText(term.getTitle());
			((EditText) findViewById(R.id.descriptionEditText)).setText(term.getDescription());
		}
	}

	/**
	 * Adds or updates term into database
	 *
	 * @param v view that called this method
	 */
	public void addTerm(View v) {
		if (checkInput()) {
			if (term == null) {
				SQLiteDatabase db = (new TermsDatabaseHelper(this)).getWritableDatabase();
				ContentValues cv = new ContentValues();
				cv.put("personalNumber", personalNumber);
				cv.put("subject", subjectName);
				cv.put("title", ((EditText) findViewById(R.id.titleEditText)).getText().toString
						());
				cv.put("description", ((EditText) findViewById(R.id.descriptionEditText)).getText
						().toString());
				db.insert("terms", null, cv);
				db.close();
			} else {
				SQLiteDatabase db = (new TermsDatabaseHelper(this)).getWritableDatabase();
				ContentValues cv = new ContentValues();
				cv.put("title", ((EditText) findViewById(R.id.titleEditText)).getText().toString
						());
				cv.put("description", ((EditText) findViewById(R.id.descriptionEditText)).getText
						().toString());
				db.update("terms", cv, "_id = " + term.getId(), null);
				db.close();
			}
			onBackPressed();
		}
	}

	/**
	 * Checks if title is set. If not, alert is shown
	 *
	 * @return <code>true</code> if title is ok, <code>false</code> otherwise
	 */
	private boolean checkInput() {
		View v = findViewById(R.id.titleEditText);
		String s = ((EditText) v).getText().toString();
		if (s.trim().isEmpty()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
			builder.setTitle(R.string.error_no_term_title).setNeutralButton(android.R.string.ok,
					null);
			builder.create().show();
			return false;
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
