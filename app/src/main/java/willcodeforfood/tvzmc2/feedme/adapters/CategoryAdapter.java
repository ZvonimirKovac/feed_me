package willcodeforfood.tvzmc2.feedme.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
            categoryViewHolder.itemView.setBackgroundDrawable(createBitmap(category.getBase64EncodedImage()));
            categoryViewHolder.nameView.setText(category.getCategoryName());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private BitmapDrawable createBitmap(String base64EncodedImage) throws IOException {
        byte[] decodedImage = Base64.decode(base64EncodedImage);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
        return new BitmapDrawable(null, bitmap);
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView nameView;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.nameView = (TextView) itemView.findViewById(R.id.categoryNameTextView);

            initListeners();
        }

        private void initListeners() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String name = nameView.getText().toString();
                    Toast.makeText(v.getContext(), name + "clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
