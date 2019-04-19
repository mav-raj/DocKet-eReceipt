package com.solution.engineering.docket_ereceipt;

import android.content.Context;
import android.icu.util.Calendar;
import android.icu.util.RangeValueIterator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/circular_std_book.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_about);
//        LinearLayout rootActivity = (LinearLayout)findViewById(R.id.linear_about_activity);
//        View view = (View)findViewById(R.id.aboutView);
        Element adsElement = new Element();
        adsElement.setTitle("Docket e-Receipt");
        Element blankElement = new Element();
        blankElement.setTitle("");


        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.d_icon)
                .setDescription("With DocKet, it is possible to instantly authenticate the transfer of digital receipts, bind them to the user and also get into the handy Smart phone for browsing. From now on, you can opt to have the store send your receipt to your DocKet app, which keeps all your electronic receipts organized. Docket will also gives you a summary of your expenses which makes it so much easier to monitor your total spending, regardless of whether you pay in cash or which debit/credit card you use.")
                .addItem(adsElement)
                .addItem(new Element().setTitle("Version 1.0"))
                .addGroup("Connect With Us")
                .addEmail("mav.raj452@gmail.com")
                .addWebsite("www.docket-ereceipt.com")
                .addItem(createCopyright())
                .addItem(blankElement)
                .create();

        setContentView(aboutPage);

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    private Element createCopyright(){
        Element copyright = new Element();
        final String copyrightString = String.format("Copyright %d by Docket Inc", java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
        copyright.setTitle(copyrightString);
        copyright.setIconDrawable(R.mipmap.ic_launcher);
        copyright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutActivity.this, copyrightString, Toast.LENGTH_SHORT).show();
            }
        });
        return copyright;
    }
}
