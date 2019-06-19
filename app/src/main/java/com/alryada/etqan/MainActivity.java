package com.alryada.etqan;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alryada.etqan.Adapters.ViewPagerAdapter;
import com.alryada.etqan.Fragments.MapFragment;
import com.alryada.etqan.Fragments.OrdersFragment;
import com.alryada.etqan.Fragments.ProfileFragment;
import com.alryada.etqan.Fragments.fragment_profile_discover;
import com.alryada.etqan.Helpers.ApplicationClass;
import com.alryada.etqan.Helpers.CommonsMethods;
import com.alryada.etqan.Model.MakeOrderRequest;
import com.alryada.etqan.Model.UserData;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alryada.etqan.Helpers.Constants.KEY_LANGUAGE_ARABIC;
import static com.alryada.etqan.Helpers.Constants.KEY_LANGUAGE_CODE;
import static com.alryada.etqan.Helpers.Constants.KEY_LANGUAGE_ENGLISH;
import static com.alryada.etqan.Helpers.Constants.PREF_KEY;
import static com.alryada.etqan.Helpers.Constants.PREF_USER_LOGGED;

public class MainActivity extends AppCompatActivity implements NavigationView
        .OnNavigationItemSelectedListener {
//        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    //region declare var
    @BindView(R.id.btnNotification)
    Button btnNotification;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager viewPager;



    private static final int REQUEST_PERMISSIONS = 20;
    private AlertDialog alertDialog;
    private DrawerLayout drawer;
    private ProgressDialog progressDialog;

    ApplicationClass applicationClass;
    MakeOrderRequest makeOrderRequest;
    UserData userData;

    private int[] tabIcons = {
            R.drawable.ic_home,
            R.drawable.ic_orders,
            R.drawable.ic_profile
    };
//endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initScreen();
    }

    void initScreen() {

        applicationClass = (ApplicationClass) getApplication();
        userData = applicationClass.getUserData();

//        CommonsMethods.setLangaugeAct(applicationClass.getLangKey(), MainActivity.this,
//                MainActivity.this,true);
        if (userData != null) {
//            btnNotification.setVisibility(View.VISIBLE);
            btnNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                    startActivity(intent);
                }
            });

        } else {
            btnLogin.setVisibility(View.VISIBLE);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
            });
        }
        initNavigationDrawer();

        setupViewPager();
    }


    private void setupViewPager() {
        SharedPreferences sharedpreferences = getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE);
        boolean isUserLogged = sharedpreferences.getBoolean(PREF_USER_LOGGED, false);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        OrdersFragment ordersFragment = new OrdersFragment();
        ordersFragment.setArguments(viewPager);
        adapter.addFragment(new MapFragment(), "");
        adapter.addFragment(ordersFragment, "TWO");
        if (!isUserLogged){
            adapter.addFragment(new fragment_profile_discover(), "THREE");
        }else{
            adapter.addFragment(new ProfileFragment(), "THREE");
        }

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color
                .app_red);
        int tabIconColorUnSel = ContextCompat.getColor(MainActivity.this, R.color
                .littel_grey);

        tabLayout.getTabAt(0).setIcon(tabIcons[0]).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]).getIcon().setColorFilter(tabIconColorUnSel, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]).getIcon().setColorFilter(tabIconColorUnSel, PorterDuff.Mode.SRC_IN);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color
                        .app_red);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color
                        .littel_grey);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        String g = "";
        if (getIntent().hasExtra("MakeOrder")){
            g = getIntent().getStringExtra("MakeOrder");
            if(g.matches("1")) {
                viewPager.setCurrentItem(1);
                tabLayout.getTabAt(1).select();
            }
        }
    }

    /**
     * initlize navatigation drawer
     */
    void initNavigationDrawer() {


        findViewById(R.id.imMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        if (userData == null) {
            Menu menu = navigationView.getMenu();
            MenuItem navLogOut = menu.findItem(R.id.menu_log_out);
            navLogOut.setTitle(getString(R.string.log_in));
        }
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView txtUserName = headerView.findViewById(R.id.txtUserName);
        TextView txtUserPhone = headerView.findViewById(R.id.txtUserPhone);
        TextView txtUserLocation = headerView.findViewById(R.id.txtUserLocation);

        if (userData != null) {
            txtUserName.setText(userData.getNameEN());
            txtUserPhone.setText(userData.getMobile());
            txtUserLocation.setText(userData.getAddressEN());
        }
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                drawer.closeDrawer(GravityCompat.START);
                tabLayout.getTabAt(2).select();

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_home) {

            tabLayout.getTabAt(0).select();

        } else if (id == R.id.menu_orders) {

            tabLayout.getTabAt(1).select();
//            Intent intent = new Intent(MainActivity.this, MakeOrderActivity.class);
//            startActivity(intent);
        } else if (id == R.id.menu_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_notifications) {
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_share_app) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Magd");
                String sAux = "\nLet me recommend you this \n\n";
                sAux = sAux + "https://play.google.com/store/apps/details?id=" + getPackageName();
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }
        } else if (id == R.id.menu_language) {
            changeLanguageDialog();
//        } else if (id == R.id.menu_wallet) {
//            Intent intent = new Intent(MainActivity.this, WalletActivity.class);
//            startActivity(intent);
        } else if (id == R.id.menu_rate_app) {
            Log.e("Rate app ", "http://play.google.com/store/apps/details?id=" + getPackageName
                    ());
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));

        } else if (id == R.id.menu_log_out) {
            CommonsMethods.clearAllSavedSharedData(MainActivity.this);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeLanguageDialog() {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_choose_language, null);

        final android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                this, R.style.Dialog);

        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setTitle(getResources().getString(R.string.change_language));

        final RadioGroup radioGroup = (RadioGroup) promptsView.findViewById(R.id.radioGrLang);
        alertDialogBuilder.setPositiveButton(R.string.txt_confirm, new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.radioBtnArabic) {
                    CommonsMethods.setLangaugeAct(KEY_LANGUAGE_ARABIC, MainActivity.this,
                            MainActivity.this, true);
                    CommonsMethods.storeDataToSharedPref(MainActivity.this,
                            KEY_LANGUAGE_ARABIC, KEY_LANGUAGE_CODE);
                } else {
                    CommonsMethods.setLangaugeAct(KEY_LANGUAGE_ENGLISH, MainActivity.this,
                            MainActivity.this, true);
                    CommonsMethods.storeDataToSharedPref(MainActivity.this,
                            KEY_LANGUAGE_ENGLISH, KEY_LANGUAGE_CODE);
                }
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color
                        .littel_grey);
                tabLayout.getTabAt(1).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);;
                tabLayout.getTabAt(2).getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);;
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.txt_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                Toast.makeText(MainActivity.this, "Cancel " + radioGroup.getCheckedRadioButtonId(), Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
