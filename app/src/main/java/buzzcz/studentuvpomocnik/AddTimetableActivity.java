package buzzcz.studentuvpomocnik;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class AddTimetableActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_timetable);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void addTimetableButtonAction(View v) throws InterruptedException {
		String personalNumber = ((EditText) findViewById(R.id.personalNumberEditText))
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
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						dowloadFile(dir, "timetable.xml", webPage + timetablePostfix);
						ArrayList<Subject> timetable = ParseXmls.parseTimetable(new
								FileInputStream(dir + File.separator + "timetable.xml"));
//						TODO download sylabus
						downloadSylabus(dir, webPage, timetable);

						Intent result = new Intent();
						editTimetable(timetable, result);
						setResult(RESULT_OK, result);
						dialog.dismiss();
						finish();
					} catch (IOException | XmlPullParserException e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
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
			dowloadFile(dir, "sylabus" + s.getDepartment() + "_" + s.getShortcut() + ".xml",
					webPage + sylabusPostfix);
		}
	}

	private void editTimetable(ArrayList<Subject> timetable, Intent result) {
		ArrayList<Subject> toRemove = new ArrayList<>();
		Calendar now = Calendar.getInstance();
		Calendar unor = Calendar.getInstance();
		unor.set(now.get(Calendar.YEAR), 1, 1);
		if (now.compareTo(unor) == -1) {
			for (Subject s : timetable) {
				if (s.getSemester().equals("LS")) toRemove.add(s);
			}
		} else {
			for (Subject s : timetable) {
				if (s.getSemester().equals("ZS")) toRemove.add(s);
			}
		}
		timetable.removeAll(toRemove);
		Collections.sort(timetable, new Comparator<Subject>() {
			@Override
			public int compare(Subject lhs, Subject rhs) {
				return lhs.getStarts().compareTo(rhs.getStarts());
			}
		});

		ArrayList<Subject> subjects;
		ArrayList<String> ch = new ArrayList<>();
		ch.add(getString(R.string.sylabus));
		ch.add(getString(R.string.terms));
		ch.add(getString(R.string.tasks));
		ch.add(getString(R.string.absences));
		for (int i = 0; i < 8; i++) {
			subjects = new ArrayList<>();
			for (Subject s : timetable) {
				if (s.getDay() == i) {
					subjects.add(s);
				}
			}
			for (Subject s : subjects) s.setItems(ch);
			result.putExtra("subjects" + i, subjects);
		}
	}
}
