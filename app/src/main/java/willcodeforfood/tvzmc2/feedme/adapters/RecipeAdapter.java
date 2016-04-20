package willcodeforfood.tvzmc2.feedme.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.firebase.client.utilities.Base64;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.io.IOException;

import willcodeforfood.tvzmc2.feedme.R;
import willcodeforfood.tvzmc2.feedme.models.Recipe;

public class RecipeAdapter extends FirebaseRecyclerAdapter<Recipe, RecipeAdapter.RecipeViewHolder>{


    public RecipeAdapter(Class<Recipe> modelClass,int modelLayout, Class<RecipeViewHolder> viewHolderClass, Firebase ref ){
        super(modelClass, modelLayout,viewHolderClass,ref);
    }


    @Override
    protected void populateViewHolder(RecipeAdapter.RecipeViewHolder recipeViewHolder, Recipe recipe, int i) {
            try{
                Glide.with(recipeViewHolder.context)
                        .load(Base64.decode(recipe.getBase64EncodedImage()))
                        .crossFade()
                        .placeholder(R.drawable.placeholder_category)
                        .into(recipeViewHolder.recipeImage);
            }catch(IOException e){
                e.printStackTrace();
            }
        recipeViewHolder.recipeNameTextView.setText(recipe.getRecipeName());
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder{
        Context context;
        ImageView recipeImage;
        TextView recipeNameTextView;

        public RecipeViewHolder(View itemView){
            super(itemView);
            this.context = itemView.getContext();
            this.recipeImage = (ImageView) itemView.findViewById(R.id.recipeImage);
            this.recipeNameTextView = (TextView) itemView.findViewById(R.id.recipeNameTextView);
        }
    }
}
