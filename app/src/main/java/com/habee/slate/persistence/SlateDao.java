package com.habee.slate.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.habee.slate.model.SlateModels;

import java.util.List;

import static android.icu.text.MessagePattern.ArgType.SELECT;

@Dao

public interface SlateDao {

    @Insert
    long[] insertSlate(SlateModels... slateModels);

    @Query("SELECT * FROM slate")
    LiveData<List<SlateModels>> getSlate();


    @Delete
    int deleteSlate(SlateModels... slateModels);

    @Update
    int update(SlateModels... slateModels);
}
