package willcodeforfood.tvzmc2.feedme.activities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import willcodeforfood.tvzmc2.feedme.R;
import willcodeforfood.tvzmc2.feedme.adapters.RecipeAdapter;
import willcodeforfood.tvzmc2.feedme.fragments.FavoritesFragment;
import willcodeforfood.tvzmc2.feedme.fragments.MyRecipesFragment;
import willcodeforfood.tvzmc2.feedme.fragments.NewRecipeFragment;
import willcodeforfood.tvzmc2.feedme.fragments.RecipeFragment;
import willcodeforfood.tvzmc2.feedme.fragments.ShoppingListFragment;
import willcodeforfood.tvzmc2.feedme.models.Recipe;

public class RecipeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.inflateMenu(R.menu.menu_recipe_drawer);

        mRecipe = (Recipe) getIntent().getSerializableExtra(RecipeAdapter.SELECTED_RECIPE);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, RecipeFragment.newInstance(mRecipe), "RECIPE_FRAGMENT")
                .commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_recipe) {
            updateFragment(RecipeFragment.newInstance(mRecipe));
        }
        else if(id == R.id.nav_favorites) {
            updateFragment(new FavoritesFragment());
        }
        else if(id == R.id.nav_my_recipes) {
            updateFragment(new MyRecipesFragment());
        }
        else if(id == R.id.nav_new_recipe) {
            updateFragment(new NewRecipeFragment());
        }
        else if(id == R.id.nav_shooping_list) {
            updateFragment(new ShoppingListFragment());
        }
        else if (id == R.id.nav_share) {
            Toast.makeText(this, "Share clicked", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.nav_about) {
            Toast.makeText(this, "About clicked", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void updateFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment)
                .commit();
    }
}
