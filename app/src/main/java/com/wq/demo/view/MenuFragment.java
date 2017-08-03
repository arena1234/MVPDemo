package com.wq.demo.view;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.wq.demo.R;
import com.wq.demo.bean.Keys;
import com.wq.demo.widget.CustomToast;

public class MenuFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
    private IAppUi mAppUi;

    public MenuFragment(IAppUi appUi) {
        mAppUi = appUi;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.menu_main);
        getPreferenceScreen().findPreference(Keys.KEY_RECYCLE_VIEW).setOnPreferenceClickListener(this);
        getPreferenceScreen().findPreference(Keys.KEY_TAB_PAGE).setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        mAppUi.closeDrawers();
        switch (preference.getKey()) {
            case Keys.KEY_RECYCLE_VIEW:
                CustomToast.show(getContext(), "Recycle view is selected.");
                mAppUi.getPresenter().setMenuValue(Keys.VALUE_MENU_RECYCLER);
                break;
            case Keys.KEY_TAB_PAGE:
                CustomToast.show(getContext(), "Tab page is selected.");
                mAppUi.getPresenter().setMenuValue(Keys.VALUE_MENU_TAB);
                break;
        }
        return false;
    }
}
