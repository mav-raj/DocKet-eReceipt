package com.solution.engineering.docket_ereceipt;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CategoryActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public SectionsPagerAdapter mSectionsPagerAdapter;
    public String keyValue;
    private DatabaseReference mRef;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public ViewPager mViewPager;
    public int extrasPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/circular_std_book.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_category);

        Intent intent = getIntent();
        if(intent == null) {
            extrasPosition = 0;
        } else {
            extrasPosition = intent.getIntExtra("position",0);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //display the desired fragment
        mViewPager.setCurrentItem(extrasPosition);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
//    @Override
//    protected void onStart(){
//        super.onStart();
//        FirebaseRecyclerAdapter<DataModel, DataSaveActivity.ReceiptViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataModel, DataSaveActivity.ReceiptViewHolder>
//                (DataModel.class, R.layout.reciept_row, DataSaveActivity.ReceiptViewHolder.class, mRef){
//            @Override
//            protected void populateViewHolder(DataSaveActivity.ReceiptViewHolder viewHolder, DataModel model, int position) {
//                viewHolder.setBillNo(model.getBillNo());
//                viewHolder.setStoreName(model.getCompany());
//                viewHolder.setDate(model.getDate());
//                viewHolder.setTime(model.getTime());
//                viewHolder.setPhone(model.getPhone());
//                viewHolder.setBillTotal(model.getTotal());
//            }
//        };
//        mReceipt.setAdapter(firebaseRecyclerAdapter);
//    }
//    public static class ReceiptViewHolder extends RecyclerView.ViewHolder {
//        View mView;
//        public ReceiptViewHolder(View itemView){
//            super(itemView);
//            mView = itemView;
//        }
//        public void setBillNo(String billNo){
//            TextView BillNo = (TextView)mView.findViewById(R.id.receiptBillNo);
//            BillNo.setText(billNo);
//        }
//        public void setStoreName(String company){
//            TextView StoreName = (TextView)mView.findViewById(R.id.receiptStoreName);
//            StoreName.setText(company);
//        }
//        public void setDate(String date){
//            TextView Date = (TextView)mView.findViewById(R.id.receiptDate);
//            Date.setText(date);
//        }
//        public void setTime(String time){
//            TextView Time = (TextView)mView.findViewById(R.id.receiptTime);
//            Time.setText(time);
//        }
//        public void setPhone(String phone){
//            TextView Phone = (TextView)mView.findViewById(R.id.receiptPhone);
//            Phone.setText(phone);
//        }
//        public void setBillTotal(String total){
//            TextView BillTotal = (TextView)mView.findViewById(R.id.receiptTotal);
//            BillTotal.setText(total);
//        }
//
//    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance(position + 1);

            // returning current tabs using switch case
            switch (position){
                case 0:
                    viewClothing tab0 = new viewClothing();
                    return tab0;
                case 1:
                    viewElectronics tab1 = new viewElectronics();
                    return tab1;
                case 2:
                    viewFurniture tab2 = new viewFurniture();
                    return tab2;
                case 3:
                    viewGrocery tab3 = new viewGrocery();
                    return tab3;
                case 4:
                    viewHardware tab4 = new viewHardware();
                    return tab4;
                case 5:
                    viewMedical tab5 = new viewMedical();
                    return tab5;
                case 6:
                    viewOthers tab6 = new viewOthers();
                    return tab6;
                    default:
                        return null;
            }
        }
        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return "Clothing";
                case 1:
                    return "Electronics";
                case 2:
                    return "Furniture";
                case 3:
                    return "Grocery";
                case 4:
                    return "Hardware";
                case 5:
                    return "Medical";
                case 6:
                    return "Others";
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 7 total pages.
            return 7;
        }
    }
}
