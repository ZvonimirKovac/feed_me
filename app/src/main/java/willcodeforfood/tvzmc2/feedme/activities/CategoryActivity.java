package willcodeforfood.tvzmc2.feedme.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.firebase.client.Firebase;

import willcodeforfood.tvzmc2.feedme.R;
import willcodeforfood.tvzmc2.feedme.adapters.CategoryAdapter;
import willcodeforfood.tvzmc2.feedme.adapters.RecipeAdapter;
import willcodeforfood.tvzmc2.feedme.models.Recipe;


public class CategoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Firebase mFirebaseRef;
    private RecipeAdapter mRecipeAdapter;
    private String categoryTitle;
    private TextView mTitleTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mRecyclerView = (RecyclerView) findViewById(R.id.categorRecyclerView);
        mTitleTextView = (TextView) findViewById(R.id.categoryTitle);

        categoryTitle = getIntent().getStringExtra(CategoryAdapter.CATEGORY_NAME);

        mTitleTextView.setText(categoryTitle);

        initFirebase();
        initView();

    }

    private void initFirebase(){
        mFirebaseRef = new Firebase("https://feedmetvzmc2.firebaseio.com/recipesList/" + categoryTitle);
    }

    private void initView(){
        mRecipeAdapter = new RecipeAdapter(
                Recipe.class,
                R.layout.recipe_list_item_layout,
                RecipeAdapter.RecipeViewHolder.class,
                mFirebaseRef
        );
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,1));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecipeAdapter);

    }

}
