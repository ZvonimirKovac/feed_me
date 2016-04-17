package willcodeforfood.tvzmc2.feedme.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.firebase.client.Firebase;

import willcodeforfood.tvzmc2.feedme.R;


public class CategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Firebase mFirebaseRef;
    /*private RecipeAdapter recipeAdapter;*/

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

    }


}
