package com.habee.slate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.habee.slate.model.SlateModels;
import com.habee.slate.persistence.SlateRepository;
import com.habee.slate.util.Utility;

public class SlateNoteActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener,
        TextWatcher {

    private static final String TAG = "SlateNoteActivity";
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    private LineEditText mLineEditText;
    private EditText mEditText;
    private TextView mTextView;
    private RelativeLayout mCheckContainer, mBackContanier;
    private ImageButton mCheckBtn, mBackBtn;

    private SlateModels mInitialSlate;
    private SlateModels mFinalSlate;
    private boolean isNewSlate;
    private GestureDetector mGestureDetector;
    private int mode;
    private SlateRepository mSlateRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slate_note);

        mLineEditText = findViewById(R.id.editText);
        mEditText = findViewById(R.id.toolbar_editView);
        mTextView = findViewById(R.id.toolbar_textView);
        mCheckBtn = findViewById(R.id.check);
        mBackBtn = findViewById(R.id.back_arrow);
        mCheckContainer = findViewById(R.id.check_container);
        mBackContanier = findViewById(R.id.back_arrow_container);
        mSlateRepository = new SlateRepository(this);
        if (getIncomingIntent()){
//            edit text mode
            enableEditMode();
            enableContentInteraction();
            setNewSlateProperties();

        }else{
//            view text mode
            setSlateProperties();

        }
        setListener();
    }


    public void saveChanges(){
        if (isNewSlate){
            saveNewSlate();
        }else {
            saveUpdateSlate();
        }
    }
    public void saveUpdateSlate(){
        mSlateRepository.updateSlate(mFinalSlate);
    }
    public void saveNewSlate(){
        mSlateRepository.insertSlate(mFinalSlate);
    }
    private void disableContentInteraction(){
        mLineEditText.setKeyListener(null);
        mLineEditText.setFocusable(false);
        mLineEditText.setFocusableInTouchMode(false);
        mLineEditText.setCursorVisible(false);
        mLineEditText.clearFocus();
    }

    private void enableContentInteraction(){
        mLineEditText.setKeyListener(new EditText(this).getKeyListener());
        mLineEditText.setFocusable(true);
        mLineEditText.setFocusableInTouchMode(true);
        mLineEditText.setCursorVisible(true);
        mLineEditText.requestFocus();
    }
    private boolean getIncomingIntent(){
        if (getIntent().hasExtra("Selected slate")){
            mInitialSlate = getIntent().getParcelableExtra("Selected slate");
            mFinalSlate = getIntent().getParcelableExtra("Selected slate");
            Log.d(TAG, "getIncomingIntent: ");
            mode = EDIT_MODE_DISABLED;
            isNewSlate = false;
            return false;
        }else{
            mode = EDIT_MODE_ENABLED;
            isNewSlate = true;
            return true;
        }


    }

    private void enableEditMode(){
        mBackContanier.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        mTextView.setVisibility(View.GONE);
        mEditText.setVisibility(View.VISIBLE);
        mode = EDIT_MODE_ENABLED;
        enableContentInteraction();
    }

    private void disableEditMode(){
        mBackContanier.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mTextView.setVisibility(View.VISIBLE);
        mEditText.setVisibility(View.GONE);
        mode = EDIT_MODE_DISABLED;
        disableContentInteraction();

        String temp = mLineEditText.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");
        if (temp.length() > 0){
            mFinalSlate.setTitle(mEditText.getText().toString());
            mFinalSlate.setContent(mLineEditText.getText().toString());

            String timeStamp = Utility.getCurrentTimeStamp();
            mFinalSlate.setTimestamp(timeStamp);
            if (!mFinalSlate.getTitle().equals(mInitialSlate.getTitle())||
                    !mFinalSlate.getContent().equals(mInitialSlate.getContent())){
                saveChanges();
            }
        }
    }

    private void setSlateProperties(){
        mTextView.setText(mInitialSlate.getTitle());
        mEditText.setText(mInitialSlate.getTitle());
        mLineEditText.setText(mInitialSlate.getContent());

    }

    private void setNewSlateProperties(){
        mTextView.setText("Slate Title");
        mEditText.setText("Slate Title");

        mInitialSlate = new SlateModels();
        mFinalSlate = new SlateModels();
        mInitialSlate.setTitle("Slate Title");
        mFinalSlate.setTitle("Slate Title");
    }

    private void setListener(){
        mLineEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);
        mTextView.setOnClickListener(this);
        mCheckBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
        mEditText.addTextChangedListener(this);
    }

    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if(view == null){
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        enableEditMode();
        Log.d(TAG, "onDoubleTap: ready to edit slate");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.check:{
                hideSoftKeyboard();
                disableEditMode();
                break;
            }
            case R.id.toolbar_textView:{
                enableEditMode();
                mEditText.requestFocus();
                mEditText.setSelection(mEditText.length());
                break;
            }
            case R.id.back_arrow:{
                finish();
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mode == EDIT_MODE_ENABLED){
            onClick(mCheckBtn);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mode = savedInstanceState.getInt("mode");
        if (mode == EDIT_MODE_ENABLED){
            enableEditMode();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mEditText.setText(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
