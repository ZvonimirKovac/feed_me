package willcodeforfood.tvzmc2.feedme.activities;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.client.Firebase;

import willcodeforfood.tvzmc2.feedme.R;
import willcodeforfood.tvzmc2.feedme.adapters.CategoryAdapter;
import willcodeforfood.tvzmc2.feedme.models.Category;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Firebase mFirebaseRef;
    private CategoryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        initFirebase();
        initView();
    }

    private void initFirebase() {
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        mFirebaseRef = new Firebase(getString(R.string.firebase_categories_url));
    }

    private void initView() {
        mAdapter = new CategoryAdapter(
                Category.class,
                R.layout.category_item,
                CategoryAdapter.CategoryViewHolder.class,
                mFirebaseRef);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(4));
        recyclerView.setAdapter(mAdapter);
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
