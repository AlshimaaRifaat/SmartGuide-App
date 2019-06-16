package com.example.alshimaa.smartguide.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alshimaa.smartguide.NetworkConnection;
import com.example.alshimaa.smartguide.R;
import com.example.alshimaa.smartguide.SplashActivity;
import com.example.alshimaa.smartguide.activity.NavigationActivity;
import com.example.alshimaa.smartguide.adapter.FollowFlightsAdapter;
import com.example.alshimaa.smartguide.model.FollowFlightsData;
import com.example.alshimaa.smartguide.model.NotificationsData;
import com.example.alshimaa.smartguide.presenter.FollowFlightsPresenter;
import com.example.alshimaa.smartguide.presenter.NotificationsPresenter;
import com.example.alshimaa.smartguide.view.DetailsFollowFlightsView;
import com.example.alshimaa.smartguide.view.FollowFlightsView;
import com.example.alshimaa.smartguide.view.NotificationNumbersView;
import com.example.alshimaa.smartguide.view.NotificationsView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.example.alshimaa.smartguide.activity.NavigationActivity.toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowFlightsFragment extends Fragment implements FollowFlightsView
        ,DetailsFollowFlightsView,NotificationsView{
    @BindView(R.id.follow_flights_icon_plus) ImageView iconPlus;
   public static   ImageView iconNotification;
    @BindView(R.id.relative_sort_by) RelativeLayout sortByRelative;
     public static TextView textView;
    private Unbinder unbinder;

Toolbar toolbar;


    RecyclerView recyclerViewFollowFlights;
    FollowFlightsAdapter followFlightsAdapter;
    FollowFlightsPresenter followFlightsPresenter;

    NetworkConnection networkConnection;

    Bundle bundle;
    public  static String Mosnda,Kayd_tnfez,Moalaq,Malghia,
            Mokfl_nhaey,Mokfl_gozey,Mogdwla,Old,New,TripStatus_follow_flight;
    SharedPreferences.Editor sharedPref_status;

    NotificationsPresenter notificationsPresenter;

    /*SharedPreferences sharedPreferences_status;*/

    //List<FollowFlightsData> listAfterRemoveItems=new ArrayList<>() ;

    public FollowFlightsFragment() {
        // Required empty public constructor
    }

View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_follow_flights, container, false);
        unbinder= ButterKnife.bind(this,view);
        init();
        /*sharedPreferences_status=getContext().getSharedPreferences("status", Context.MODE_PRIVATE);
        TripStatus_follow_flight=sharedPreferences_status.getString("trip_status",null);
        Toast.makeText(getContext(), TripStatus_follow_flight, Toast.LENGTH_SHORT).show()*/;
        notificationsPresenter=new NotificationsPresenter(getContext(),this);
        notificationsPresenter.getNotificationsResult(SplashActivity.Login, "supervisors");
            // TODO: Add OneSignal initialization here
           /* OneSignal.startInit(getContext())
                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                    .unsubscribeWhenNotificationsAreDisabled(true)
                    .init();*/

        sharedPref_status=getContext().getSharedPreferences("status", Context.MODE_PRIVATE).edit();
        followFlightsPresenter=new FollowFlightsPresenter(getContext(),this);


        NavigationActivity.toggle = new ActionBarDrawerToggle(
                getActivity(), NavigationActivity.drawer, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        NavigationActivity.drawer.addDrawerListener(NavigationActivity.toggle);
        NavigationActivity.toggle.syncState();

        NavigationActivity.toggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(R.drawable.group151);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (NavigationActivity.drawer.isDrawerOpen(GravityCompat.START)) {
                    NavigationActivity.drawer.closeDrawer(GravityCompat.START);
                } else {
                    NavigationActivity.drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        networkConnection=new NetworkConnection( getContext() );


        iconPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_navigation,new
                        NewTripFragment()).addToBackStack(null).commit();

            }
        });
        sortByRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.content_navigation,new
                        SortByFragment()).addToBackStack(null).commit();
            }
        });
        bundle=this.getArguments();
        if(bundle!=null)
        {
            Mosnda=bundle.getString("mosnda");
            Kayd_tnfez=bundle.getString("kayd_tnfez");
            Moalaq=bundle.getString("moalaq");
            Malghia=bundle.getString("malghia");

            Mokfl_nhaey=bundle.getString("mokfl_nhaey");
            Mokfl_gozey=bundle.getString("mokfl_gozey");
            Mogdwla=bundle.getString("mogdwla");
            SortByStatus();

            Old=bundle.getString("old");
            New=bundle.getString("new");
            SortByDate();

        }/*else if (Old=="old" ||New =="new"){*/




            else
        {
            FollowFlights();
        }
        iconNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationFragment notificationFragment=new NotificationFragment();
                Bundle bundle=new Bundle();
                bundle.putString("module","supervisor_notification");
                notificationFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.content_navigation,
                        notificationFragment).addToBackStack(null).commit();

            }
        });
       /* Toast.makeText(getContext(),"mos "+ Mosnda, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"kayd " +Kayd_tnfez, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"moalaq "+ Moalaq, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"malgh "+ Malghia, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"nahy "+ Mokfl_nhaey, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"gozy "+ Mokfl_gozey, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(),"mogdwl "+ Mogdwla, Toast.LENGTH_SHORT).show();

        Toast.makeText(getContext(),"USER   "+ SplashActivity.Login, Toast.LENGTH_SHORT).show();
*/


            /*TabLayout.Tab tab = HomeActivity.tabLayout.getTabAt(1); // fourth tab
            View tabView = tab.getCustomView();*/


        return view;
    }

    private void SortByDate() {
        if(Old=="old") {
          //  Toast.makeText(getContext(),"o "+ Old, Toast.LENGTH_SHORT).show();

            followFlightsPresenter.getSortByDateResult(SplashActivity.Login, "ar", "old");
        }else if(New=="new") {
           // Toast.makeText(getContext(),"n "+ New, Toast.LENGTH_SHORT).show();
           // Toast.makeText(getContext(), SplashActivity.Login, Toast.LENGTH_SHORT).show();
            followFlightsPresenter.getSortByDateResult(SplashActivity.Login, "ar", "new");
        }
    }

    private void SortByStatus() {
        if(Mosnda=="1") {
            followFlightsPresenter.getSortByStatusResult(SplashActivity.Login, "ar", "1");
        }else if(Kayd_tnfez=="2") {
            followFlightsPresenter.getSortByStatusResult(SplashActivity.Login, "ar", "2");
        }else if(Moalaq=="3") {
            followFlightsPresenter.getSortByStatusResult(SplashActivity.Login, "ar", "3");
        }else if(Malghia=="4") {
            followFlightsPresenter.getSortByStatusResult(SplashActivity.Login, "ar", "4");
        }else if(Mokfl_nhaey=="5") {
            followFlightsPresenter.getSortByStatusResult(SplashActivity.Login, "ar", "5");
        }else if(Mokfl_gozey=="6") {
            followFlightsPresenter.getSortByStatusResult(SplashActivity.Login, "ar", "6");
        }else if(Mogdwla=="7") {
            followFlightsPresenter.getSortByStatusResult(SplashActivity.Login, "ar", "7");
        }
        //status
    }

    private void FollowFlights() {

        followFlightsPresenter.getFollowFlightsResult("ar", SplashActivity.Login,"1");
    }

    private void init() {

        toolbar=view.findViewById(R.id.follow_flights_tool_bar);
       // iconPlus=view.findViewById(R.id.follow_flights_icon_plus);
        recyclerViewFollowFlights=view.findViewById(R.id.follow_flights_recycler);
                iconNotification=view.findViewById(R.id.follow_flights_notification);
        textView=view.findViewById(R.id.notification_num);


    }

    @Override
    public void showFollowFlightsList(List<FollowFlightsData> followFlightsDataList) {
         /*for(int i=0;i<followFlightsDataList.size();i++)
         {

             if(!followFlightsDataList.get(i).getStatus().equals("متواقف"))
             {
                 FollowFlightsData followFlightsData=new FollowFlightsData();
                 followFlightsData.setBusName(followFlightsDataList.get(i).getBusName());
                 followFlightsData.setStatus(followFlightsDataList.get(i).getStatus());

                 listAfterRemoveItems.add(followFlightsData) ;
             }
         }*/
        followFlightsAdapter=new FollowFlightsAdapter( getContext(),followFlightsDataList );
       followFlightsAdapter.onClick(this);
        recyclerViewFollowFlights.setLayoutManager( new GridLayoutManager(getContext(),2));
        recyclerViewFollowFlights.setAdapter( followFlightsAdapter );
    }

    @Override
    public void showFollowFlightsError() {

    }

    @Override
    public void showSortByStatusList(List<FollowFlightsData> followFlightsDataList) {
        followFlightsAdapter=new FollowFlightsAdapter( getContext(),followFlightsDataList );
        followFlightsAdapter.onClick(this);
        recyclerViewFollowFlights.setLayoutManager( new GridLayoutManager(getContext(),2));
        recyclerViewFollowFlights.setAdapter( followFlightsAdapter );
    }

    @Override
    public void showSortByStatusError() {

    }

    @Override
    public void showSortByDateList(List<FollowFlightsData> followFlightsDataList) {
        followFlightsAdapter=new FollowFlightsAdapter( getContext(),followFlightsDataList );
        followFlightsAdapter.onClick(this);
        recyclerViewFollowFlights.setLayoutManager( new GridLayoutManager(getContext(),2));
        recyclerViewFollowFlights.setAdapter( followFlightsAdapter );
    }

    @Override
    public void showSortByDateError() {

    }

    @Override
    public void showDetailsFollowFlights(FollowFlightsData followFlightsData) {
        DetailsFollowFlightsFragment detailsFollowFlightsFragment=new DetailsFollowFlightsFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("follow_flight_item",followFlightsData);

   sharedPref_status.putString("trip_status", followFlightsData.getStatus());
        sharedPref_status.apply();
        DetailsFollowFlightsFragment.TripStatus = followFlightsData.getStatus();

        detailsFollowFlightsFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.content_navigation,
               detailsFollowFlightsFragment ).addToBackStack(null).commit();
    }






    @Override
    public void showNotificationList(List<NotificationsData> notificationsDataList) {
        if (notificationsDataList.size() >= 0) {
            /*TabLayout.Tab tab = HomeActivity.tabLayout.getTabAt(1); // fourth tab
            View tabView = tab.getCustomView();*/
            textView.setVisibility(View.VISIBLE);
            //Toast.makeText(getContext(), String.valueOf(notificationsDataList.size()), Toast.LENGTH_SHORT).show();
            textView.setText(notificationsDataList.size() + "");
        }
    }

    @Override
    public void showNotificationError() {

    }
}
