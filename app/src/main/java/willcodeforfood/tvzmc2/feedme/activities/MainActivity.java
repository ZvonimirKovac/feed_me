package willcodeforfood.tvzmc2.feedme.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

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
        recyclerView.setAdapter(mAdapter);
    }
}
