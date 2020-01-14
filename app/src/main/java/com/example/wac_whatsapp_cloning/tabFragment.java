package com.example.wac_whatsapp_cloning;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class tabFragment extends FragmentPagerAdapter {
    public tabFragment(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return new ft_user_chat();

            case 1:
                return new user_camera();

            case 2:
                return new user_profile();

                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Chats";

            case 1:
                return "Camera";

            case 2:
                return "Profile";

                default:
                    return null;
        }
    }
}
