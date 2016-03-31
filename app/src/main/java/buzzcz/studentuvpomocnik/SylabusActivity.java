package buzzcz.studentuvpomocnik;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SylabusActivity extends AppCompatActivity {

	private String personalNumber, subjectName;
	private Sylabus sylabus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sylabus);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		final ProgressDialog dialog = ProgressDialog.show(this, getString(R.string
				.loading_dialog_title), getString(R.string.loading_dialog_text));
		personalNumber = getIntent().getStringExtra("personalNumber");
		subjectName = getIntent().getStringExtra("subject");
		subjectName = subjectName.replace("/", "_");

		new Thread(new Runnable() {
			@Override
			public void run() {
				String dir = getFilesDir().getAbsolutePath() + File.separator + personalNumber;
				try {
					sylabus = ParseXmls.parseSylabus(new FileInputStream(dir + File.separator +
							"sylabus_" + subjectName + ".xml"));
					setViews();
					dialog.dismiss();
				} catch (XmlPullParserException | IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
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

	private void setViews() {
		TextView textView = (TextView) findViewById(R.id.shortcutContentTextView);
		textView.setText(sylabus.getDepartment() + "/" + sylabus.getShortcut());

		textView = (TextView) findViewById(R.id.nameContentTextView);
		textView.setText(sylabus.getName());

		textView = (TextView) findViewById(R.id.creditsContentTextView);
		textView.setText(sylabus.getCredits());

		textView = (TextView) findViewById(R.id.garantsContentTextView);
		textView.setText(sylabus.getGarants());

		textView = (TextView) findViewById(R.id.teachersContentTextView);
		textView.setText(sylabus.getTeachers());

		textView = (TextView) findViewById(R.id.excerciseTeachersContentTextView);
		textView.setText(sylabus.getExcerciseTeachers());

		textView = (TextView) findViewById(R.id.seminarTeachersContentTextView);
		textView.setText(sylabus.getSeminarTeachers());

		textView = (TextView) findViewById(R.id.conditioningSubjectsContentTextView);
		textView.setText(sylabus.getConditioningSubjects());

		textView = (TextView) findViewById(R.id.excludingSubjectsContentTextView);
		textView.setText(sylabus.getExcludingSubjects());

		textView = (TextView) findViewById(R.id.isConditionToSubjectsContentTextView);
		textView.setText(sylabus.getIsConditionToSubjects());

		textView = (TextView) findViewById(R.id.literatureContentTextView);
		textView.setText(sylabus.getLiterature());

		textView = (TextView) findViewById(R.id.anotationContentTextView);
		textView.setText(sylabus.getAnotation());

		textView = (TextView) findViewById(R.id.typeOfExamContentTextView);
		textView.setText(sylabus.getTypeOfExam());

		textView = (TextView) findViewById(R.id.creditBeforeExamContentTextView);
		textView.setText(sylabus.getCreditBeforeExam());

		textView = (TextView) findViewById(R.id.examFormContentTextView);
		textView.setText(sylabus.getExamForm());

		textView = (TextView) findViewById(R.id.requirementsContentTextView);
		textView.setText(sylabus.getRequirements());

		textView = (TextView) findViewById(R.id.overviewContentTextView);
		textView.setText(sylabus.getOverview());

		textView = (TextView) findViewById(R.id.prerequisitionsContentTextView);
		textView.setText(sylabus.getPrerequisitions());

		textView = (TextView) findViewById(R.id.gainKnowledgeContentTextView);
		textView.setText(sylabus.getGainKnowledge());

		textView = (TextView) findViewById(R.id.timeSeverityContentTextView);
		textView.setText(sylabus.getTimeSeverity());

		textView = (TextView) findViewById(R.id.urlContentTextView);
		textView.setText(sylabus.getUrl());

		textView = (TextView) findViewById(R.id.languagesContentTextView);
		textView.setText(sylabus.getLanguages());
	}

}
