package com.habee.slate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.habee.slate.adapter.SlateRecyclerAdapter;
import com.habee.slate.model.SlateModels;
import com.habee.slate.persistence.SlateRepository;
import com.habee.slate.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SlateRecyclerAdapter.onSlateListener, View.OnClickListener {

    private static final String TAG = "MainActivity";

//    widget var declaration
    private RecyclerView mRecyclerView;


//    other var declaration
    private ArrayList<SlateModels> mSlateModels = new ArrayList<>();
    private SlateRecyclerAdapter mSlateRecyslerAdpter;
    private SlateRepository mSlateRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSlateRepository = new SlateRepository(this);
        findViewById(R.id.fab).setOnClickListener(this);
//        get the recyclerview id
        mRecyclerView = findViewById(R.id.recyclerView);

//        initial the recyclerview list
        initRecycler();

//        initialize fake slate list in the view
//        fakeSlates();
        retrieveSlate();

//        set Action Toolbar
        setSupportActionBar((Toolbar)findViewById(R.id.slate_toolbar));
        setTitle("Slate");
    }

    public void retrieveSlate(){
        mSlateRepository.retrieveSlate().observe(this, new Observer<List<SlateModels>>() {
            @Override
            public void onChanged(List<SlateModels> slateModels) {
                if (mSlateModels.size() > 0){
                    mSlateModels.clear();
                }
                if (slateModels != null){
                    mSlateModels.addAll(slateModels);
                }
                mSlateRecyslerAdpter.notifyDataSetChanged();
            }
        });
    }

//    public void fakeSlates(){
//        for (int i= 0; i < 100; i++){
//            SlateModels slateModels = new SlateModels();
//            slateModels.setTitle("Title #" + i);
//            slateModels.setContent("content #" + i);
//            slateModels.setTimestamp("jan " + i);
//            mSlateModels.add(slateModels);
//        }
//        mSlateRecyslerAdpter.notifyDataSetChanged();
//    }

    public void initRecycler(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(mRecyclerView);
        mSlateRecyslerAdpter = new SlateRecyclerAdapter(mSlateModels, this);
        mRecyclerView.setAdapter(mSlateRecyslerAdpter);
    }


    @Override
    public void onSlateClick(int position) {
        Intent i = new Intent(this, SlateNoteActivity.class);
        i.putExtra("Selected slate", mSlateModels.get(position));
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, SlateNoteActivity.class);
        startActivity(intent);
    }

    private void deleteSlate(SlateModels slate){
        mSlateModels.remove(slate);
        mSlateRecyslerAdpter.notifyDataSetChanged();
        mSlateRepository.deleteSlate(slate);
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteSlate(mSlateModels.get(viewHolder.getAdapterPosition()));
        }
    };

}
