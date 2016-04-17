package willcodeforfood.tvzmc2.feedme.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.Firebase;
import com.firebase.client.utilities.Base64;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.io.IOException;

import willcodeforfood.tvzmc2.feedme.R;
import willcodeforfood.tvzmc2.feedme.models.Category;

public class CategoryAdapter extends FirebaseRecyclerAdapter<Category, CategoryAdapter.CategoryViewHolder> {

    public CategoryAdapter(Class<Category> modelClass, int modelLayout, Class<CategoryViewHolder> viewHolderClass, Firebase ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
    }

    @Override
    protected void populateViewHolder(CategoryViewHolder categoryViewHolder, Category category, int i) {
        try {
            Glide.with(categoryViewHolder.context)
                    .load(Base64.decode(category.getBase64EncodedImage()))
                    .crossFade()
                    .placeholder(R.drawable.placeholder_category)
                    .into(categoryViewHolder.categoryImage);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        categoryViewHolder.nameView.setText(category.getCategoryName());
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        Context context;
        ImageView categoryImage;
        TextView nameView;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            this.categoryImage = (ImageView) itemView.findViewById(R.id.categoryImage);
            this.nameView = (TextView) itemView.findViewById(R.id.categoryNameTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Clicked " + nameView.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
