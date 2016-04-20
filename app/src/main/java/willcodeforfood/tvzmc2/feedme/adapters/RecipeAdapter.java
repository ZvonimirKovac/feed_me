package willcodeforfood.tvzmc2.feedme.adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.firebase.client.utilities.Base64;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import willcodeforfood.tvzmc2.feedme.R;
import willcodeforfood.tvzmc2.feedme.activities.RecipeActivity;
import willcodeforfood.tvzmc2.feedme.models.Recipe;

public class RecipeAdapter extends FirebaseRecyclerAdapter<Recipe, RecipeAdapter.RecipeViewHolder> {
    public static final String SELECTED_RECIPE = "willcodeforfood.tvzmc2.feedme.adapters.SELECTED_RECIPE";

    private static List<Recipe> recipes;
    private String category;

    public RecipeAdapter(String category, Class<Recipe> modelClass, int modelLayout, Class<RecipeViewHolder> viewHolderClass, Firebase ref ){
        super(modelClass, modelLayout, viewHolderClass, ref);
        recipes = new ArrayList<>();
        this.category = category;
    }

    @Override
    protected void populateViewHolder(RecipeAdapter.RecipeViewHolder recipeViewHolder, Recipe recipe, int i) {
            try {
                Glide.with(recipeViewHolder.context)
                        .load(Base64.decode(recipe.getBase64EncodedImage()))
                        .crossFade()
                        .placeholder(R.drawable.placeholder_category)
                        .into(recipeViewHolder.recipeImage);
            } catch(IOException e) {
                e.printStackTrace();
            }
        recipeViewHolder.recipeNameTextView.setText(recipe.getRecipeName());
        recipeViewHolder.position = i;
        recipe.setCategory(category);
        recipes.add(recipe);
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder{
        Context context;
        CardView cardView;
        ImageView recipeImage;
        TextView recipeNameTextView;
        int position;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            this.cardView = (CardView) itemView.findViewById(R.id.cardView);
            this.recipeImage = (ImageView) itemView.findViewById(R.id.recipeImage);
            this.recipeNameTextView = (TextView) itemView.findViewById(R.id.recipeNameTextView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, RecipeActivity.class);
                    Recipe recipe = recipes.get(position);
                    intent.putExtra(SELECTED_RECIPE, recipe);
                    context.startActivity(intent);
                }
            });
        }
    }
}
