package com.hellomistri.hellomistriprovidern.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.hellomistri.hellomistriprovidern.BuildConfig;
import com.hellomistri.hellomistriprovidern.Fragments.FragSet;
import com.hellomistri.hellomistriprovidern.Fragments.PrivacyPolicy;
import com.hellomistri.hellomistriprovidern.Fragments.RefIn;
import com.hellomistri.hellomistriprovidern.Fragments.ShareApp;
import com.hellomistri.hellomistriprovidern.Fragments.TermsAndCondition;
import com.hellomistri.hellomistriprovidern.Fragments.Wallet;
import com.hellomistri.hellomistriprovidern.Fragments.HomeFragment;
import com.hellomistri.hellomistriprovidern.R;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private long pressedTime;


    ActionBarDrawerToggle drawerToggle;
    FirebaseAuth mAuth;
    Context context;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.logoutUser) {
//
//
//            return true;
//        }

        if (id == R.id.logout) {

        /*    SharedPreferences preferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove("isLogin");
            editor.commit();

            finish();R
*/
            Intent intent = new Intent(Home.this, Edit_User.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences preferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();


        String city = preferences.getString("city","");
        String proid = preferences.getString("uid","");
        String cat = preferences.getString("category_id","");


        getSupportFragmentManager().beginTransaction().add(R.id.flContent,new HomeFragment()).commit();



        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open_nav,R.string.close_nav);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if (id==R.id.logoutUser){

                    SharedPreferences preferences = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    Intent intent = new Intent(Home.this,SignIn.class);
                    startActivity(intent);
                    editor.remove("isLogin");
                    editor.clear();
                    editor.commit();

                    finish();

                }

                Fragment fragment = null;
                Class fragmentClass = null;


                switch (item.getItemId())
                {

                    case R.id.home:{

                        fragmentClass = HomeFragment.class;
                        break;
                    }

                    case R.id.Wallet:{
                        fragmentClass = Wallet.class;

                        break;
                    }
                    case R.id.setting:{
                        fragmentClass = FragSet.class;
                     //   Toast.makeText(Home.this, "Setting Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.tandc:
                    {
                        fragmentClass = TermsAndCondition.class;
                     //   Toast.makeText(Home.this, "Terms and Condition Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.priPolicy:
                    {
                        fragmentClass = PrivacyPolicy.class;
                  //      Toast.makeText(Home.this, "Privacy Policy Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.referral:{
                        fragmentClass = RefIn.class;
                     //   Toast.makeText(Home.this, "Share Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.share:{

                        fragmentClass = ShareApp.class;
                             break;
                    }
                    default:
                        fragmentClass = HomeFragment.class;
                }
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Insert the fragment by replacing any existing fragment
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();

                // Highlight the selected item has been done by NavigationView
                item.setChecked(true);
                // Set action bar title
                drawerLayout.closeDrawers();

                return false;
            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notification, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {


        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getSupportFragmentManager().popBackStack();
        }


    }


  /*  public void ShowDialog()

    {

         AlertDialog.Builder popDialog = new AlertDialog.Builder(this);

         RatingBar rating = new RatingBar(this);

        rating.setMax(6);



        popDialog.setIcon(android.R.drawable.btn_star_big_on);

        popDialog.setTitle("Are you enjoy");
        popDialog.setView(rating);
        popDialog.setPositiveButton("Rate us", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                               // Toast.makeText(context, ""+rating.getProgress(), Toast.LENGTH_SHORT).show();

                                //txtView.setText(String.valueOf(rating.getProgress()));

                                finish();
                            }

                        })

                .setNegativeButton("Remind me Later",

                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();

                            }
                        });

        popDialog.create();

        popDialog.show();

    }*/

    /*@Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle extras = intent.getExtras();
        if(extras!=null){
            if(extras.containsKey("from_notif")){

            }
        }
    }*/
}