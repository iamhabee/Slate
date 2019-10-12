package com.habee.slate.async;

import android.os.AsyncTask;

import com.habee.slate.model.SlateModels;
import com.habee.slate.persistence.SlateDao;

public class UpdateTask extends AsyncTask<SlateModels, Void, Void> {

    private SlateDao mDao;

    public UpdateTask(SlateDao dao) {
        this.mDao = dao;
    }

    @Override
    protected Void doInBackground(SlateModels... slateModels) {
        mDao.updateSlate(slateModels);
        return null;
    }
}
