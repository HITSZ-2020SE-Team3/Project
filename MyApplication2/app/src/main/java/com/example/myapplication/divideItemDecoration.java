package com.example.myapplication;
//这是用于画下划线的划线类，用recyclerView的一个类实现，由于时间原因，暂时先直接在布局中加上分割线使用（牺牲一些性能）
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class divideItemDecoration extends RecyclerView.ItemDecoration {
    public divideItemDecoration() {
        super();
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
