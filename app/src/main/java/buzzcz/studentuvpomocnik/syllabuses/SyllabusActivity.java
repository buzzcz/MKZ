package buzzcz.studentuvpomocnik.syllabuses;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import buzzcz.studentuvpomocnik.R;
import buzzcz.studentuvpomocnik.tools.ParseXmls;

/**
 * Activity showing syllabus of a subject
 */
public class SyllabusActivity extends AppCompatActivity {

	private String personalNumber, subjectName;
	private Syllabus syllabus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_syllabus);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Show wait dialog, parse syllabus XML and show it
		final ProgressDialog dialog = ProgressDialog.show(this, getString(R.string
				.loading_dialog_title), getString(R.string.loading_dialog_text));
		personalNumber = getIntent().getStringExtra("personalNumber");
		subjectName = getIntent().getStringExtra("subject");
		setTitle(getTitle() + " " + subjectName);
		subjectName = subjectName.replace("/", "_");

		new AsyncTask<Object, Object, Object>() {
			@Override
			protected Object doInBackground(Object... params) {
				String dir = getFilesDir().getAbsolutePath() + File.separator + personalNumber;
				try {
					syllabus = ParseXmls.parseSyllabus(new FileInputStream(dir + File.separator +
							"syllabus_" + subjectName + ".xml"));
					setViews();
				} catch (XmlPullParserException | IOException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(Object o) {
				dialog.dismiss();
			}
		}.execute();
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

	/**
	 * Sets text of views from the loaded syllabus
	 */
	private void setViews() {
		TextView textView = (TextView) findViewById(R.id.shortcutContentTextView);
		textView.setText(syllabus.getDepartment() + "/" + syllabus.getShortcut());

		textView = (TextView) findViewById(R.id.nameContentTextView);
		textView.setText(syllabus.getName());

		textView = (TextView) findViewById(R.id.creditsContentTextView);
		textView.setText(syllabus.getCredits());

		textView = (TextView) findViewById(R.id.garantsContentTextView);
		textView.setText(syllabus.getGarants());

		textView = (TextView) findViewById(R.id.teachersContentTextView);
		textView.setText(syllabus.getTeachers());

		textView = (TextView) findViewById(R.id.excerciseTeachersContentTextView);
		textView.setText(syllabus.getExerciseTeachers());

		textView = (TextView) findViewById(R.id.seminarTeachersContentTextView);
		textView.setText(syllabus.getSeminarTeachers());

		textView = (TextView) findViewById(R.id.conditioningSubjectsContentTextView);
		textView.setText(syllabus.getConditioningSubjects());

		textView = (TextView) findViewById(R.id.excludingSubjectsContentTextView);
		textView.setText(syllabus.getExcludingSubjects());

		textView = (TextView) findViewById(R.id.isConditionToSubjectsContentTextView);
		textView.setText(syllabus.getIsConditionToSubjects());

		textView = (TextView) findViewById(R.id.literatureContentTextView);
		textView.setText(syllabus.getLiterature());

		textView = (TextView) findViewById(R.id.anotationContentTextView);
		textView.setText(syllabus.getAnnotation());

		textView = (TextView) findViewById(R.id.typeOfExamContentTextView);
		textView.setText(syllabus.getTypeOfExam());

		textView = (TextView) findViewById(R.id.creditBeforeExamContentTextView);
		textView.setText(syllabus.getCreditBeforeExam());

		textView = (TextView) findViewById(R.id.examFormContentTextView);
		textView.setText(syllabus.getExamForm());

		textView = (TextView) findViewById(R.id.requirementsContentTextView);
		textView.setText(syllabus.getRequirements());

		textView = (TextView) findViewById(R.id.overviewContentTextView);
		textView.setText(syllabus.getOverview());

		textView = (TextView) findViewById(R.id.prerequisitionsContentTextView);
		textView.setText(syllabus.getPrerequisites());

		textView = (TextView) findViewById(R.id.gainKnowledgeContentTextView);
		textView.setText(syllabus.getGainKnowledge());

		textView = (TextView) findViewById(R.id.timeSeverityContentTextView);
		textView.setText(syllabus.getTimeSeverity());

		textView = (TextView) findViewById(R.id.urlContentTextView);
		textView.setText(syllabus.getUrl());

		textView = (TextView) findViewById(R.id.languagesContentTextView);
		textView.setText(syllabus.getLanguages());
	}

}
