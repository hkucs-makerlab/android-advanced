package com.example.android.fragmentexample;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;


public class SimpleFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    private static final int YES = 0;
    private static final int NO = 1;
    private static final int NONE = 2;

    private RadioGroup radioGroup;
    private View rootView;
    public int mRadioButtonChoice = NONE;
    OnFragmentInteractionListener mListener;
    private static final String CHOICE = "choice";

    public SimpleFragment() {
        // Required empty public constructor
    }

    // create an instance, save the state 'choice' to fragment argument'
    public static SimpleFragment newInstance(int choice) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(CHOICE, choice);
        fragment.setArguments(arguments);
        return fragment;
    }

    // is called by fragmentManager to create the views from layout
    //      and set the event listener to radioGroup view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        rootView =inflater.inflate(R.layout.fragment_simple, container, false);
        radioGroup = rootView.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(this);

        if (getArguments().containsKey(CHOICE)) {
            // A choice was made, so get the choice.
            mRadioButtonChoice = getArguments().getInt(CHOICE);
            // Check the radio button choice.
            if (mRadioButtonChoice != NONE) {
                radioGroup.check(radioGroup.getChildAt(mRadioButtonChoice).getId());
            }
        }
        // Return the View for the fragment's UI.
        return rootView;
    }

    @Override // is called when an radioButton is selected
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
                mRadioButtonChoice = YES;
                mListener.onRadioButtonChoice(YES);
                break;
            case NO: // User chose "No."
                textView.setText(R.string.no_message);
                mRadioButtonChoice = NO;
                mListener.onRadioButtonChoice(NO);
                break;
            default: // No choice made.
                mRadioButtonChoice = NONE;
                mListener.onRadioButtonChoice(NONE);
                break;
        }
    }

    // is called as soon as the Fragment is associated with the Activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // context is MainActivity implemented OnFragmentInteractionListener
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + getResources().getString(R.string.exception_message));
        }
    }

    interface OnFragmentInteractionListener {
        void onRadioButtonChoice(int choice);
    }
}
