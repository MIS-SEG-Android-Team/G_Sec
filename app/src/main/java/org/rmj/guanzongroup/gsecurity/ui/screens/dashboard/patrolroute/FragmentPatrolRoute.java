package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.recyclerview.widget.RecyclerView.VERTICAL;
import static org.rmj.guanzongroup.gsecurity.data.file.ImageFileCreator.CreateImageUri;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.rmj.guanzongroup.gsecurity.databinding.FragmentPatrolRouteBinding;
import org.rmj.guanzongroup.gsecurity.ui.activity.AuthenticationActivity;
import org.rmj.guanzongroup.gsecurity.ui.activity.QrCodeScannerActivity;
import org.rmj.guanzongroup.gsecurity.ui.activity.ReadNfcActivity;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.AdapterPatrolRoute;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogTagOption;

import javax.inject.Inject;

public class FragmentPatrolRoute extends Fragment {

    @Inject
    VMItineraries mViewModel;

    private DialogLoad dialogLoad;
    private FragmentPatrolRouteBinding binding;

    private String visitedPlace;

    private final ActivityResultLauncher<Intent> intentFrontCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
       if(result.getResultCode() == RESULT_OK) {
           new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, "You visited " + visitedPlace).showDialog();
       } else if(result.getResultCode() == RESULT_CANCELED) {
           Toast.makeText(requireActivity(), "Selfie tagging cancelled.", Toast.LENGTH_SHORT).show();
       } else {
           Toast.makeText(requireActivity(), "Unknown error occurred", Toast.LENGTH_SHORT).show();
       }
    });

    private final ActivityResultLauncher<Intent> intentQrCodeScanner = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == RESULT_OK) {
            // TODO: Process the JSON Data to intent Selfie Camera...
            Intent intentTakeSelfie = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intentTakeSelfie.putExtra(MediaStore.EXTRA_OUTPUT, CreateImageUri(requireActivity()));
            intentTakeSelfie.putExtra("android.intent.extras.CAMERA_FACING", 1);
            intentFrontCamera.launch(intentTakeSelfie);
        } else if(result.getResultCode() == RESULT_CANCELED) {
            Toast.makeText(
                    requireActivity(),
                    "Scanner has been cancelled",
                    Toast.LENGTH_SHORT)
                    .show();
        } else {

        }
    });

    private final ActivityResultLauncher<Intent> intentNFCReader = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result-> {
        if(result.getResultCode() == RESULT_OK) {
            // TODO: Process the JSON Data to intent Selfie Camera...
            new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, "You visited " + visitedPlace).showDialog();
        } else if(result.getResultCode() == RESULT_CANCELED) {
            Toast.makeText(
                            requireActivity(),
                            "NFC reader has been cancelled",
                            Toast.LENGTH_SHORT)
                    .show();
        } else {

        }
    });

    public static FragmentPatrolRoute newInstance() {
        return new FragmentPatrolRoute();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMItineraries.class);
        binding = FragmentPatrolRouteBinding.inflate(getLayoutInflater());
        dialogLoad = new DialogLoad(requireActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(VERTICAL);

        mViewModel.getItineraryList().observe(getViewLifecycleOwner(), patrolRoutes -> {
            if(patrolRoutes == null) {
                return;
            }

            binding.patrolRouteList.setAdapter(new AdapterPatrolRoute(patrolRoutes, (nfcID, placeName) -> {
                new DialogTagOption(requireActivity(), placeName, new DialogTagOption.DialogTagOptionCallback() {
                    @Override
                    public void onClickNFCButton() {
                        visitedPlace = placeName;
                        Intent intent = new Intent(requireActivity(), ReadNfcActivity.class);
                        intentNFCReader.launch(intent);
                    }

                    @Override
                    public void onClickQrCodeButton() {
                        visitedPlace = placeName;
                        Intent intent = new Intent(requireActivity(), QrCodeScannerActivity.class);
                        intentQrCodeScanner.launch(intent);
                    }
                }).show();
            }));
            binding.patrolRouteList.setLayoutManager(linearLayoutManager);
        });

        binding.visitRequestBanner.setOnClickListener(view-> {
            String placeName = "Warehouse 2 Back Door";
            new DialogTagOption(requireActivity(), placeName, new DialogTagOption.DialogTagOptionCallback() {
                @Override
                public void onClickNFCButton() {
                    visitedPlace = placeName;
                    Intent intent = new Intent(requireActivity(), ReadNfcActivity.class);
                    intentNFCReader.launch(intent);
                }

                @Override
                public void onClickQrCodeButton() {
                    visitedPlace = placeName;
                    Intent intent = new Intent(requireActivity(), QrCodeScannerActivity.class);
                    intentQrCodeScanner.launch(intent);
                }
            }).show();
        });

        setupObservables();

        binding.logoutButton.setOnClickListener(view -> {
            DialogMessage dialogMessage = new DialogMessage(requireActivity());
            dialogMessage.initDialog("Logout", "Logout account?");
            dialogMessage.setNegativeButton("NO", Dialog::dismiss);
            dialogMessage.setPositiveButton("Yes", dialog -> {
                dialog.dismiss();
                mViewModel.logoutUser();
            });
            dialogMessage.show();
        });

        return binding.getRoot();
    }

    private void setupObservables() {
        // region Observables
        mViewModel.isLoggingOut().observe(getViewLifecycleOwner(), isLoggingOut -> {
            if (isLoggingOut) {
                dialogLoad.show("Signing out...");
            } else {
                dialogLoad.dismiss();
            }
        });
        mViewModel.hasLogout().observe(getViewLifecycleOwner(), hasLogOut -> {
            if (hasLogOut) {
                requireActivity().finish();
                requireActivity().startActivity(new Intent(requireActivity(), AuthenticationActivity.class));
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage == null) {
                return;
            }


            if (errorMessage.isEmpty()) {
                return;
            }

            new DialogResult(requireActivity(), DialogResult.RESULT.FAILED, errorMessage).showDialog();
        });
    }
}