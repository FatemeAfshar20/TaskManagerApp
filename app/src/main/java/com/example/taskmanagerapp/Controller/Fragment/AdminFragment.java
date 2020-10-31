package com.example.taskmanagerapp.Controller.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapp.Adapter.AdminAdapter;
import com.example.taskmanagerapp.Controller.Activity.LoginActivity;
import com.example.taskmanagerapp.R;
import com.example.taskmanagerapp.Repository.UserDBRepository;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminFragment extends Fragment {

    public static final String FRAGMENT_BOTTOM_SHEET_FRAGMENT = "Bottom Sheet Fragment";
    private RecyclerView mRecyclerView;
    private UserDBRepository mUserRepository;
    private AdminAdapter mAdminAdapter;

    public AdminFragment() {
        // Required empty public constructor
    }

    public static AdminFragment newInstance() {
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mUserRepository= UserDBRepository.getInstance(getContext());
        Toolbar toolbar=new Toolbar(getContext());

        getActivity().setActionBar(toolbar);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_admin, container, false);
        findElem(view);
        mAdminAdapter=new AdminAdapter(mUserRepository.getList(), getContext(), new AdminAdapter.BottomSheetFragmentShow() {
            @Override
            public void getBottomSheetFrag(UUID userId) {
                BottomSheetAdminFragment
                        bottomSheetAdminFragment=
                        BottomSheetAdminFragment.newInstance(userId);

                bottomSheetAdminFragment.show(getFragmentManager(),
                        FRAGMENT_BOTTOM_SHEET_FRAGMENT);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdminAdapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_delete:
                getDialogOk();
                return true;
        }
        return false;
    }

    private void getDialogOk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).
                setMessage("Do you sure?")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mUserRepository.deleteAll();
                        updateUi();
                    }
                })
                .setNegativeButton("Cancel",null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void findElem(View view){
        mRecyclerView=view.findViewById(R.id.recycler_view_admin);
    }

    private void updateUi(){
        if(mAdminAdapter!=null) {
            mAdminAdapter.setUserList(mUserRepository.getList());
            mAdminAdapter.notifyDataSetChanged();
        }
    }
}