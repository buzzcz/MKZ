package buzzcz.studentuvpomocnik;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AddTimetableActivity extends AppCompatActivity {

	private ProgressDialog pDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_timetable);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}

	public void addTimetableButtonAction(View v) {
		String personalNumber = ((EditText) findViewById(R.id.personalNumberEditText)).getText()
				.toString().toUpperCase();
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
//		    TODO run in background, wait for it and return filename
			String file = school + "_" + personalNumber + "_timetable.xml";
				try {
					URL url = new URL("https://stag-ws." + school + "" +
							".cz/ws/services/rest/rozvrhy/getRozvrhByStudent?osCislo=" +
							personalNumber);
					URLConnection connection = url.openConnection();
					connection.connect();
					InputStream input = new BufferedInputStream(url.openStream());
					FileOutputStream output = openFileOutput(file, Context.MODE_PRIVATE);
					byte data[] = new byte[1024];
					while (input.read(data) != -1) {
						output.write(data);
					}
					output.flush();
					output.close();
					input.close();
				} catch (java.io.IOException e) {
					e.printStackTrace();
				}

			Intent result = new Intent();
			result.putExtra("personalNumber", personalNumber);
			setResult(RESULT_OK, result);
			finish();
		} else {
			Toast.makeText(this, getString(R.string.error_no_personal_number), Toast.LENGTH_LONG)
					.show();
		}
	}
}