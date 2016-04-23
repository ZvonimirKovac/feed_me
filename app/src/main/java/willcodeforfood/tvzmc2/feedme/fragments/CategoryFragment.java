package willcodeforfood.tvzmc2.feedme.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;

import willcodeforfood.tvzmc2.feedme.R;
import willcodeforfood.tvzmc2.feedme.adapters.RecipeAdapter;
import willcodeforfood.tvzmc2.feedme.models.Recipe;

public class CategoryFragment extends Fragment {
    public static String CATEGORY_NAME = "willcodeforfood.tvzmc2.feedme.fragments.CATEGORY_NAME";

    private RecyclerView mRecyclerView;
    private TextView mTitleTextView;
    private Firebase mFirebaseRef;
    private RecipeAdapter mRecipeAdapter;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String categoryName) {
        CategoryFragment fragment = new CategoryFragment();

        Bundle args = new Bundle();
        args.putString(CATEGORY_NAME, categoryName);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.categorRecyclerView);
        mTitleTextView = (TextView) view.findViewById(R.id.categoryTitle);

        if(args != null) {
            mTitleTextView.setText(args.getString(CATEGORY_NAME));
        }

        initFirebase();
        initView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRecipeAdapter.cleanup();
    }

    private void initFirebase(){
        mFirebaseRef = new Firebase("https://feedmetvzmc2.firebaseio.com/recipesList/" +
                mTitleTextView.getText().toString());
    }

    private void initView(){
        mRecipeAdapter = new RecipeAdapter(
                mTitleTextView.getText().toString(),
                Recipe.class,
                R.layout.recipe_list_item_layout,
                RecipeAdapter.RecipeViewHolder.class,
                mFirebaseRef
        );
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRecipeAdapter);
    }
}
