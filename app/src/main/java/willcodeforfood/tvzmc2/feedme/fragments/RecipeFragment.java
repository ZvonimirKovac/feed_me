package willcodeforfood.tvzmc2.feedme.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;
import com.firebase.client.utilities.Base64;

import java.io.IOException;
import java.util.List;

import willcodeforfood.tvzmc2.feedme.R;
import willcodeforfood.tvzmc2.feedme.models.Recipe;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment {
    public static final String RECIPE_ARG = "willcodeforfood.tvzmc2.feedme.fragments.RECIPE_ARG";

    private Firebase mFirebase;
    private Recipe mRecipe;

    private TextView mTextViewName;
    private ImageView mRecipeImage;
    private LinearLayout mIngredientsHolder;
    private LinearLayout mInstructionsHolder;

    public RecipeFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Recipe recipe) {
        Fragment fragment = new RecipeFragment();

        Bundle args = new Bundle();
        args.putSerializable(RECIPE_ARG, recipe);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        mRecipe = (Recipe) getArguments().getSerializable(RECIPE_ARG);

        mTextViewName = (TextView) view.findViewById(R.id.recipeName);
        mRecipeImage = (ImageView) view.findViewById(R.id.recipeScreenImage);
        mIngredientsHolder = (LinearLayout) view.findViewById(R.id.ingredientsHolder);
        mInstructionsHolder = (LinearLayout) view.findViewById(R.id.instructionsHolder);

        initFirebase();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_recipe, menu);
    }

    private void initFirebase() {
        mFirebase = new Firebase("https://feedmetvzmc2.firebaseio.com/recipesDetails/" +
                mRecipe.getCategory() + "/" + mRecipe.getRecipeName());
        mFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Recipe tempRecipe = dataSnapshot.getValue(Recipe.class);
                mRecipe.setIngredients(tempRecipe.getIngredients());
                mRecipe.setInstructions(tempRecipe.getInstructions());

                initView();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void initView() {
        try {
            Glide.with(getContext())
                    .load(Base64.decode(mRecipe.getBase64EncodedImage()))
                    .crossFade()
                    .placeholder(R.drawable.placeholder_category)
                    .into(mRecipeImage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        mTextViewName.setText(mRecipe.getRecipeName());

        for (String ingredient : mRecipe.getIngredients()) {
            TextView tv = new TextView(getContext());
            tv.setText(ingredient);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 8, 4, 8);
            tv.setLayoutParams(params);
            tv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ingredient_rounded_background));
            tv.setTextColor(ContextCompat.getColor(getContext(), R.color.recipe_screen_text_color));
            tv.setTypeface(Typeface.DEFAULT_BOLD);

            mIngredientsHolder.addView(tv);
        }

        for (String instruction : mRecipe.getInstructions()) {
            TextView tv = new TextView(getContext());
            tv.setText(instruction);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 8, 4, 8);
            tv.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.instruction_rounded_background));
            tv.setLayoutParams(params);
            tv.setTextColor(ContextCompat.getColor(getContext(), R.color.recipe_screen_text_color));
            tv.setTypeface(Typeface.DEFAULT_BOLD);

            mInstructionsHolder.addView(tv);
        }
    }
}
