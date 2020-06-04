package com.example.taopiao.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taopiao.mvp.fragment.CinemaFragment;

import java.util.List;
import java.util.Objects;

public class SpinnerAdapter2 extends ArrayAdapter<String> {
    private List<String> list;
    public SpinnerAdapter2(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.list=objects;
    }

    @Nullable
    @Override
    public String getItem(int position) {
//        return super.getItem(position);
        return list.get(position);
    }
}
