package example.haim.shoppingappfirebase;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.Scopes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import example.haim.shoppingappfirebase.fragments.ShoppingListsFragment;
import example.haim.shoppingappfirebase.models.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final int RC_SIGN_IN = 1;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDataBase;

    private FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            mUser = firebaseAuth.getCurrentUser();
            if (mUser == null){
                //goLogin

                List<AuthUI.IdpConfig> mProviders = Arrays.asList(
                        new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).setPermissions(Arrays.asList(
                          Scopes.EMAIL, Scopes.PROFILE)).
                                build()
                        );

                Intent intent = AuthUI.getInstance().
                        createSignInIntentBuilder().
                        setLogo(R.drawable.logo).
                        setProviders(mProviders).
                        setIsSmartLockEnabled(false). //disable smart lock for debugging needs
                        build();
                startActivityForResult(intent, RC_SIGN_IN);
            }else{
                //Save the user to DataBase
//                DatabaseReference usersTables = mDataBase.getReference("Users");
//                DatabaseReference newRow = usersTables.push();
//                newRow.setValue(mUser.getDisplayName());

                //Add the user to the Database only with unique ID.
                User u = new User(mUser);
                mDataBase.getReference("Users").child(mUser.getUid()).setValue(mUser.getDisplayName());
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);

    }


    @Override
    protected void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        mAuth= FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_sign_out:
                AuthUI.getInstance().signOut(this);
                return true;
        }
        

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.container, new ShoppingListsFragment()).
                    commit();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
