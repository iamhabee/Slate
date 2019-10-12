package com.habee.slate.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.habee.slate.async.DeleteTask;
import com.habee.slate.async.InsertTask;
import com.habee.slate.model.SlateModels;

import java.util.List;

public class SlateRepository {
    private SlateDatabase mSlateDatabase;

    public SlateRepository(Context context) {
        mSlateDatabase = SlateDatabase.getInstance(context);
    }

    public void insertSlate(SlateModels slate){
        new InsertTask(mSlateDatabase.getSlateDao()).execute(slate);
    }
    public void updateSlate(SlateModels slate){
        new UpdateTask(mSlateDatabase.getSlateDao()).execute(slate);
    }
    public void deleteSlate(SlateModels slate){
        new DeleteTask(mSlateDatabase.getSlateDao()).execute(slate);
    }
    public LiveData<List<SlateModels>> retrieveSlate(){
        return mSlateDatabase.getSlateDao().getSlate();
    }

}
