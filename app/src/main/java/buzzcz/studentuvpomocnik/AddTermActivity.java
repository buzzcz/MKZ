package buzzcz.studentuvpomocnik;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;

public class AddTermActivity extends AppCompatActivity {

	private String personalNumber;
	private String subjectName;
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

		if (term != null) {
			((EditText) findViewById(R.id.titleEditText)).setText(term.getTitle());
			((EditText) findViewById(R.id.descriptionEditText)).setText(term.getDescription());
		}
	}

	public void addTerm(View v) throws ParseException {
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
