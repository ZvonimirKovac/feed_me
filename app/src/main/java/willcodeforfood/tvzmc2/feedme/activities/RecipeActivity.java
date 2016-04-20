package willcodeforfood.tvzmc2.feedme.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import willcodeforfood.tvzmc2.feedme.adapters.RecipeAdapter;
import willcodeforfood.tvzmc2.feedme.models.Recipe;

public class RecipeActivity extends AppCompatActivity {
    private Firebase mFirebase;
    private Recipe recipe;

    private ImageView mRecipeImage;
    private ImageButton mButtonFavorites;
    private LinearLayout mIngredientsHolder;
    private LinearLayout mInstructionsHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipe = (Recipe) getIntent().getSerializableExtra(RecipeAdapter.SELECTED_RECIPE);

        mRecipeImage = (ImageView) findViewById(R.id.recipeScreenImage);
        mButtonFavorites = (ImageButton) findViewById(R.id.favoritesButton);
        mIngredientsHolder = (LinearLayout) findViewById(R.id.ingredientsHolder);
        mInstructionsHolder = (LinearLayout) findViewById(R.id.instructionsHolder);

        initFirebase();
    }

    private void initFirebase() {
        mFirebase = new Firebase("https://feedmetvzmc2.firebaseio.com/recipesDetails/" +
                recipe.getCategory() + "/" + recipe.getRecipeName());
        mFirebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> indicator = new GenericTypeIndicator<List<String>>() {
                };
                Recipe tempRecipe = dataSnapshot.getValue(Recipe.class);
                recipe.setIngredients(tempRecipe.getIngredients());
                recipe.setInstructions(tempRecipe.getInstructions());

                initView();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void initView() {
        try {
            Glide.with(this)
                    .load(Base64.decode(recipe.getBase64EncodedImage()))
                    .crossFade()
                    .placeholder(R.drawable.placeholder_category)
                    .into(mRecipeImage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        for (String ingredient : recipe.getIngredients()) {
            TextView tv = new TextView(this);
            tv.setText(ingredient);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 8, 4, 8);
            tv.setLayoutParams(params);
            tv.setBackground(ContextCompat.getDrawable(this, R.drawable.ingredient_rounded_background));
            tv.setTextColor(ContextCompat.getColor(this, R.color.recipe_screen_text_color));
            tv.setTypeface(Typeface.DEFAULT_BOLD);
            
            mIngredientsHolder.addView(tv);
        }

        for (String instruction : recipe.getInstructions()) {
            TextView tv = new TextView(this);
            tv.setText(instruction);
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 8, 4, 8);
            tv.setBackground(ContextCompat.getDrawable(this, R.drawable.instruction_rounded_background));
            tv.setLayoutParams(params);
            tv.setTextColor(ContextCompat.getColor(this, R.color.recipe_screen_text_color));
            tv.setTypeface(Typeface.DEFAULT_BOLD);

            mInstructionsHolder.addView(tv);
        }
    }
}
