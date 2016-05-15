package buzzcz.studentuvpomocnik;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

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
		Spinner schoolSpinner = (Spinner) findViewById(R.id.schoolSpinner);
		schoolSpinner.setAdapter(new SchoolInSpinnerAdapter(this, R.layout.school_in_spinner,
				getResources().getStringArray(R.array.schools)));
	}

	private class SchoolInSpinnerAdapter extends ArrayAdapter<String> {
		public SchoolInSpinnerAdapter(Context context, int resource, String[] schools) {
			super(context, resource, schools);
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getCustomView(position, convertView, parent);
		}

		public View getCustomView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getLayoutInflater();
			View row = inflater.inflate(R.layout.school_in_spinner, parent, false);
			TextView label = (TextView) row.findViewById(R.id.schoolInSpinner);
			label.setText(getResources().getStringArray(R.array.schools)[position]);
			ImageView icon = (ImageView) row.findViewById(R.id.schoolIcon);
			switch (position) {
				case 0:
					icon.setImageResource(R.drawable.zcu);
					break;
				case 1:
					icon.setImageResource(R.drawable.jcu);
					break;
				case 2:
					icon.setImageResource(R.drawable.avu);
					break;
				case 3:
					icon.setImageResource(R.drawable.mvso);
					break;
				case 4:
					icon.setImageResource(R.drawable.slu);
					break;
				case 5:
					icon.setImageResource(R.drawable.tul);
					break;
				case 6:
					icon.setImageResource(R.drawable.uhk);
					break;
				case 7:
					icon.setImageResource(R.drawable.ujep);
					break;
				case 8:
					icon.setImageResource(R.drawable.upce);
					break;
				case 9:
					icon.setImageResource(R.drawable.upol);
					break;
				case 10:
					icon.setImageResource(R.drawable.utb);
					break;
				case 11:
					icon.setImageResource(R.drawable.vfu);
					break;
				case 12:
					icon.setImageResource(R.drawable.voscb);
					break;
				case 13:
					icon.setImageResource(R.drawable.vsss);
					break;
				case 14:
					icon.setImageResource(R.drawable.vske);
					break;
				case 15:
					icon.setImageResource(R.drawable.vsrr);
					break;
			}
			return row;
		}
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

	public void addTimetableButtonAction(final View v) throws InterruptedException {
		String personalNumber = ((EditText) findViewById(R.id.personalNumberEditText)).getText()
				.toString().toUpperCase();
		if (!personalNumber.trim().isEmpty()) {
			final ProgressDialog dialog = ProgressDialog.show(this, getString(R.string
					.downloading_dialog_title), getString(R.string.downloading_dialog_text));
			String prefix = "stag-ws";
			String school = "";
			switch (((Spinner) findViewById(R.id.schoolSpinner)).getSelectedItemPosition()) {
				case 0:
					school = "zcu";
					break;
				case 1:
					school = "jcu";
					break;
				case 2:
					school = "avu";
					prefix = "stag";
					break;
				case 3:
					school = "zcu";
					prefix = "stag-mvso";
					break;
				case 4:
					school = "slu";
					break;
				case 5:
					school = "tul";
					break;
				case 6:
					school = "uhk";
					prefix = "stagws";
					break;
				case 7:
					school = "ujep";
					prefix = "ws";
					break;
				case 8:
					school = "upce";
					break;
				case 9:
					school = "upol";
					prefix = "stagservices";
					break;
				case 10:
					school = "utb";
					break;
				case 11:
					school = "vfu";
					prefix = "stagweb";
					break;
				case 12:
					school = "zcu";
					prefix = "stag-voscb";
					break;
				case 13:
					school = "zcu";
					prefix = "stag-vsss";
					break;
				case 14:
					school = "zcu";
					prefix = "stag-vske";
					break;
				case 15:
					school = "zcu";
					prefix = "stag-vsrr";
					break;
			}
			final String webPage = "https://" + prefix + "." + school + "" + "" +
					".cz/ws/services/rest/";
			final String timetablePostfix = "rozvrhy/getRozvrhByStudent?osCislo=" + personalNumber;
			final String finalPersonalNumber = prefix + File.separator + school + File.separator +
					personalNumber;
			final String dir = getFilesDir().getAbsolutePath() + File.separator +
					finalPersonalNumber;
			new AsyncTask<Object, Object, Integer>() {

				@Override
				protected Integer doInBackground(Object... params) {
					try {
						NetworkInfo info = ((ConnectivityManager) getSystemService(Context
								.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
						if (info != null && info.isConnected()) {
							dowloadFile(dir, "timetable.xml", webPage + timetablePostfix);
							ArrayList<Subject> timetable = ParseXmls.parseTimetable(new
									FileInputStream(dir + File.separator + "timetable.xml"));
							if (timetable.isEmpty()) {
								File f = new File(dir, "timetable.xml");
								if (f.exists()) f.delete();
								cancel(false);
								return -2;
							}
							downloadSylabus(dir, webPage, timetable);
							Intent result = new Intent();
							editTimetable(timetable, result);
							result.putExtra("personalNumber", finalPersonalNumber);
							setResult(RESULT_OK, result);

							SharedPreferences settings = getSharedPreferences("timetables",
									MODE_PRIVATE);
							SharedPreferences.Editor editor = settings.edit();
							String timetables = settings.getString("timetables", null);
							if (timetables != null && !timetables.contains(finalPersonalNumber)) {
								timetables = !timetables.equals("null") ?
										timetables + "," + finalPersonalNumber : finalPersonalNumber;

								editor.remove("timetables").putString("timetables", timetables);
								editor.apply();
								settings = PreferenceManager.getDefaultSharedPreferences
										(getApplicationContext());
								editor = settings.edit();
								editor.remove("personalNumber").putString("personalNumber",
										finalPersonalNumber);
								editor.apply();
							}
						} else {
							cancel(false);
							return -1;
						}
					} catch (IOException | XmlPullParserException e) {
						e.printStackTrace();
					}
					return 1;
				}

				@Override
				protected void onPostExecute(Integer o) {
					super.onPostExecute(o);
					dialog.dismiss();
					finish();
				}

				@Override
				protected void onCancelled(Integer result) {
					super.onCancelled(result);
					dialog.dismiss();
					if (result == -1) {
						AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
						builder.setMessage(R.string.error_no_connection).setNeutralButton
								(android.R.string.ok, null);
						builder.create().show();
					} else if (result == -2) {
						AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
						builder.setMessage(R.string.error_wrong_personal_number).setNeutralButton
								(android.R.string.ok, null);
						builder.create().show();
					}
				}
			}.execute();
		} else {
			AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
			builder.setMessage(R.string.error_no_personal_number).setNeutralButton
					(android.R.string.ok, null);
			builder.create().show();
		}
	}

	private void dowloadFile(String dir, String file, String webPage) throws IOException {
		URL url = new URL(webPage);
		URLConnection connection = url.openConnection();
		connection.connect();
		InputStream input = new BufferedInputStream(url.openStream());
		File f = new File(dir, file);
		if (f.exists()) f.delete();
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
		Subject.sortTimetable(timetable, getIntent().getStringExtra("semester"));
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
