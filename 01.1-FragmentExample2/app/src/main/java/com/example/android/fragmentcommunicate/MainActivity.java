/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.fragmentcommunicate;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * https://codelabs.developers.google.com/codelabs/advanced-android-training-fragment-communication/#2
 *
 *  displayFragment() associates fragment layout to Activity layout
 *  closeFragment() removes fragment
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final String STATE_FRAGMENT = "state_of_fragment";
    private Button mButton;
    private boolean isFragmentDisplayed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        if (savedInstanceState != null) {
            isFragmentDisplayed =
                    savedInstanceState.getBoolean(STATE_FRAGMENT);
            if (isFragmentDisplayed) {
                // If the fragment is displayed, change button to "close".
                mButton.setText(R.string.close);
            }
        }
        //
        mButton = findViewById(R.id.open_button);
        mButton.setOnClickListener(this);
    }

    public void displayFragment() {
        SimpleFragment simpleFragment = SimpleFragment.newInstance();
        // Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Add the SimpleFragment.
        fragmentTransaction=fragmentTransaction.add(R.id.fragment_container,simpleFragment);
        fragmentTransaction=fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        // Update the Button text.
        mButton.setText(R.string.close);
        // Set boolean flag to indicate fragment is open.
        isFragmentDisplayed = true;
    }

    public void closeFragment() {
        // Get the FragmentManager.
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check to see if the fragment is already showing.
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager
                .findFragmentById(R.id.fragment_container);
        if (simpleFragment != null) {
            // Create and commit the transaction to remove the fragment.
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        // Update the Button text.
        mButton.setText(R.string.open);
        // Set boolean flag to indicate fragment is closed.
        isFragmentDisplayed = false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.open_button) {
            if (!isFragmentDisplayed) {
                displayFragment();
            } else {
                closeFragment();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed);
        super.onSaveInstanceState(savedInstanceState);
    }
}