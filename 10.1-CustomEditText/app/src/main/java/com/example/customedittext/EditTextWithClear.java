package com.example.customedittext;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class EditTextWithClear extends AppCompatEditText
        implements TextWatcher, View.OnTouchListener {
    Drawable mClearButtonImage;

    public EditTextWithClear(Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        Resources resources = getResources();
        mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_clear_opaque_24dp, null);
        // If the clear (X) button is tapped, clear the text.
        setOnTouchListener(this);
        //If the text changes, show or hide the clear (X) button.
        addTextChangedListener(this);
    }

    // TextWatcher interfaces
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        showClearButton();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    //View.OnTouchListener interfaces
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //
        Drawable[] drawables = getCompoundDrawablesRelative();
        if ((drawables[2] == null)) {
            return false;
        }
        float clearButtonStart; // Used for LTR languages
        float clearButtonEnd;  // Used for RTL languages
        //
        boolean isClearButtonClicked = false;

        /* trace the position of the touch i.e if is on the X ico */
        // Detect the touch in RTL or LTR layout direction.
        if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
            // If RTL, get the end of the button on the left side.
            clearButtonEnd = mClearButtonImage.getIntrinsicWidth() + getPaddingStart();
            // If the touch occurred before the end of the button,
            // set isClearButtonClicked to true.
            if (event.getX() < clearButtonEnd) {
                isClearButtonClicked = true;
            }
        } else {
            // Layout is LTR.
            // Get the start of the button on the right side.
            clearButtonStart = (getWidth() - getPaddingEnd()- mClearButtonImage.getIntrinsicWidth());
            // If the touch occurred after the start of the button,
            // set isClearButtonClicked to true.
            if (event.getX() > clearButtonStart) {
                isClearButtonClicked = true;
            }
        }

        // Check for actions if the button is tapped.
        if (isClearButtonClicked) {
            // Check for ACTION_DOWN (always occurs before ACTION_UP).
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                // Switch to the black version of clear button.
                mClearButtonImage =
                        ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_clear_black_24dp, null);
                showClearButton();
            }
            // Check for ACTION_UP.
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // Switch to the opaque version of clear button.
                mClearButtonImage =
                        ResourcesCompat.getDrawable(getResources(),
                                R.drawable.ic_clear_opaque_24dp, null);
                // Clear the text and hide the clear button.
                getText().clear();
                hideClearButton();
                return true;
            }
        }
        return false;
    }

    /**
     * Shows the clear (X) button.
     */
    private void showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,                      // Start of text.
                        null,               // Above text.
                        mClearButtonImage,  // place the drawable at End of text.
                        null);              // Below text.
    }

    /**
     * Hides the clear (X) button by not adding the drawable
     */
    private void hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds
                (null,             // Start of text.
                        null,      // Above text.
                        null,      // End of text.
                        null);     // Below text.
    }


}
