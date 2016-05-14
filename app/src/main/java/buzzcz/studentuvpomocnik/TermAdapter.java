package buzzcz.studentuvpomocnik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TermAdapter extends ArrayAdapter<Term> {

	private Context context;
	private ArrayList<Term> terms;

	public TermAdapter(Context context, int resource, List<Term> objects) {
		super(context, -1, objects);
		this.context = context;
		terms = (ArrayList<Term>) objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Term term = terms.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
					.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.term_item, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.titleTextView);
		textView.setText(term.getTitle());

		textView = (TextView) convertView.findViewById(R.id.descriptionTextView);
		textView.setText(term.getDescription());

		return convertView;
	}
}
