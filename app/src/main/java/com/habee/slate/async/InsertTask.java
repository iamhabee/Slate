package com.habee.slate.async;

import android.os.AsyncTask;

import com.habee.slate.model.SlateModels;
import com.habee.slate.persistence.SlateDao;

public class InsertTask extends AsyncTask<SlateModels, Void, Void> {

    private SlateDao mDao;

    public InsertTask(SlateDao dao) {
        this.mDao = dao;
    }

    @Override
    protected Void doInBackground(SlateModels... slateModels) {
        mDao.insertSlate(slateModels);
        return null;
    }
}
