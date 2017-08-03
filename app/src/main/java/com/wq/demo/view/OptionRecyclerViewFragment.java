package com.wq.demo.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import com.wq.demo.R;
import com.wq.demo.bean.Keys;

public class OptionRecyclerViewFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private ListPreference mPrefRecycle;
    private IAppUi mAppUi;

    public OptionRecyclerViewFragment(IAppUi appUi) {
        mAppUi = appUi;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.options_recycle_view);
        mPrefRecycle = (ListPreference) getPreferenceScreen().findPreference(Keys.KEY_OPTION_RECYCLE);
        mPrefRecycle.setSummary(mPrefRecycle.getEntry());
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case Keys.KEY_OPTION_RECYCLE:
                mAppUi.replaceContent(mAppUi.getPresenter().getContentValue());
                break;
        }
    }
}