package willcodeforfood.tvzmc2.feedme.fragments;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;

import willcodeforfood.tvzmc2.feedme.R;
import willcodeforfood.tvzmc2.feedme.adapters.CategoryAdapter;
import willcodeforfood.tvzmc2.feedme.models.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private CategoryAdapter mAdapter;
    private Firebase mFirebaseRef;

    public MainFragment() {

    }

    public static Fragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        initFirebase();
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    private void initFirebase() {
        mFirebaseRef = new Firebase(getString(R.string.firebase_categories_url));
    }

    private void initView() {
        mAdapter = new CategoryAdapter(
                Category.class,
                R.layout.category_item,
                CategoryAdapter.CategoryViewHolder.class,
                mFirebaseRef);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(4));
        mRecyclerView.setAdapter(mAdapter);
    }

    private class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            int childPosition = parent.getChildLayoutPosition(view);
            int lastChildPosition = parent.getAdapter().getItemCount() - 1;
            boolean isLeft = childPosition % 2 == 0;

            // is top item
            if (childPosition == 0 || childPosition == 1) {
                if(isLeft)
                    outRect.right = space;
                else
                    outRect.left = space;

                outRect.top = 0;
                outRect.bottom = space;
            }
            // is bottom item
            else if (childPosition == lastChildPosition || childPosition == lastChildPosition -1) {
                if(isLeft)
                    outRect.right = space;
                else
                    outRect.left = space;

                outRect.top = space;
                outRect.bottom = 0;
            }
            // is positioned left in the grid
            else if(isLeft) {
                outRect.right = space;
                outRect.top = space;
                outRect.bottom = space;
            }
            // is positioned right in the grid
            else  {
                outRect.left = space;
                outRect.top = space;
                outRect.bottom = space;
            }
        }
    }
}
