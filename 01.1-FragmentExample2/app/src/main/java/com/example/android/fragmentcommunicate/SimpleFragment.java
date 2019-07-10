package com.example.android.fragmentcommunicate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;


public class SimpleFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    private static final int YES = 0;
    private static final int NO = 1;
    private RadioGroup radioGroup;
    private View rootView;

    public SimpleFragment() {
        // Required empty public constructor
    }

    public static SimpleFragment newInstance() {
        return new SimpleFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        rootView =inflater.inflate(R.layout.fragment_simple, container, false);
        radioGroup = rootView.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(this);

        // Return the View for the fragment's UI.
        return rootView;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        MainActivity activity=(MainActivity)radioGroup.getContext();
        //Log.e(this.getClass().getSimpleName(),radioGroup.getContext().getClass().toString());

        View radioButton = radioGroup.findViewById(checkedId);
        int index = radioGroup.indexOfChild(radioButton);
        TextView textView =
                rootView.findViewById(R.id.fragment_header);
        switch (index) {
            case YES: // User chose "Yes."
                textView.setText(R.string.yes_message);
                break;
            case NO: // User chose "No."
                textView.setText(R.string.no_message);
                break;
            default: // No choice made.
                // Do nothing.
                break;
        }
    }
}