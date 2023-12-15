package org.rmj.guanzongroup.gsecurity.ui.screens.settings.category;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.guanzongroup.gsecurity.R;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentCategoryBinding;

public class FragmentCategory extends Fragment {

    private VMCategory mViewModel;

    private FragmentCategoryBinding binding;

    public static FragmentCategory newInstance() {
        return new FragmentCategory();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(VMCategory.class);

        return inflater.inflate(R.layout.fragment_category, container, false);
    }
}