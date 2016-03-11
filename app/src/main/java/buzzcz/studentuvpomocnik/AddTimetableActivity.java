package buzzcz.studentuvpomocnik;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class AddTimetableActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_timetable);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

	public void addTimetableButtonAction(View v) throws InterruptedException {
		final String personalNumber = ((EditText) findViewById(R.id.personalNumberEditText))
				.getText()
				.toString().toUpperCase();
		String prefix = "stag-ws";
		String school = "";
		switch (((Spinner) findViewById(R.id.schoolSpinner)).getSelectedItemPosition()) {
			case 0:
				school = "zcu";
				break;
			case 1:
				school = "jcu";
				break;
		}

		if (!personalNumber.trim().isEmpty()) {
			final ProgressDialog dialog = ProgressDialog.show(this, getString(R.string
					.downloading_dialog_title), getString(R.string.downloading_dialog_text));
			final String dir = getFilesDir().getAbsolutePath() + File.separator + school + File
					.separator + personalNumber;
			final String webPage = "https://" + prefix + "." + school + "" + "" +
					".cz/ws/services/rest/";
			final String timetablePostfix = "rozvrhy/getRozvrhByStudent?osCislo=" + personalNumber;
			final String finalSchool = school;
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						dowloadFile(dir, "timetable.xml", webPage + timetablePostfix);
						ArrayList<Subject> timetable = ParseXmls.parseTimetable(new
								FileInputStream(dir + File.separator + "timetable.xml"));
						downloadSylabus(dir, webPage, timetable);

						Intent result = new Intent();
						editTimetable(timetable, result);
						result.putExtra("school", finalSchool);
						result.putExtra("personalNumber", personalNumber);
						setResult(RESULT_OK, result);
						dialog.dismiss();
						finish();
					} catch (IOException | XmlPullParserException e) {
						e.printStackTrace();
					}
				}
			}).start();
		} else {
			Toast.makeText(this, getString(R.string.error_no_personal_number), Toast.LENGTH_LONG)
					.show();
		}
	}

	private void dowloadFile(String dir, String file, String webPage) throws IOException {
		URL url = new URL(webPage);
		URLConnection connection = url.openConnection();
		connection.connect();
		InputStream input = new BufferedInputStream(url.openStream());
		File f = new File(dir, file);
		if (f.exists())
			f.delete();
		f.getParentFile().mkdirs();
		FileOutputStream output = new FileOutputStream(f);
		byte data[] = new byte[1024];
		int n;
		while ((n = input.read(data)) != -1) {
			output.write(data, 0, n);
		}
		output.flush();
		output.close();
		input.close();
	}

	private void downloadSylabus(String dir, String webPage, ArrayList<Subject> timetable) throws
			IOException {
		for (Subject s : timetable) {
			String sylabusPostfix = "predmety/getPredmetInfo?katedra=" + s.getDepartment() +
					"&zkratka=" + s.getShortcut();
			dowloadFile(dir, "sylabus_" + s.getDepartment() + "_" + s.getShortcut() + ".xml",
					webPage + sylabusPostfix);
		}
	}

	private void editTimetable(ArrayList<Subject> timetable, Intent result) {
		Subject.sortTimetable(timetable);

		ArrayList<String> ch = new ArrayList<>();
		ch.add("");
		for (Subject s : timetable) s.setItems(ch);

		ArrayList<Subject> subjects;
		for (int i = 0; i < 8; i++) {
			subjects = new ArrayList<>();
			for (Subject s : timetable) {
				if (s.getDay() == i) {
					subjects.add(s);
				}
			}
			result.putExtra("subjects" + i, subjects);
		}
	}
}
