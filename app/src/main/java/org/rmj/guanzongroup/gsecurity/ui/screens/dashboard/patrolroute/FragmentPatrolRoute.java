package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.recyclerview.widget.RecyclerView.VERTICAL;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.QR_CODE_DATA;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.READ_NFC_DATA_PAYLOAD;
import static org.rmj.guanzongroup.gsecurity.utils.ImageFileCreator.CreateImageUri;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
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

import timber.log.Timber;

public class FragmentPatrolRoute extends Fragment {

    @Inject
    VMPatrolRoute mViewModel;

    private DialogLoad dialogLoad;
    private FragmentPatrolRouteBinding binding;

    private Boolean isTaggingRequestedVisit = false;
    private String QrCodeData = "";

    private final ActivityResultLauncher<Intent> intentFrontCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
       if(result.getResultCode() == RESULT_OK) {
           if (!isTaggingRequestedVisit) {
               mViewModel.tagVisitedCheckpoint(QrCodeData);
           } else {
               mViewModel.tagRequestedVisit(QrCodeData);
           }
       } else if(result.getResultCode() == RESULT_CANCELED) {
           Toast.makeText(requireActivity(), "Selfie tagging cancelled.", Toast.LENGTH_SHORT).show();
       } else {
           Toast.makeText(requireActivity(), "Unknown error occurred", Toast.LENGTH_SHORT).show();
       }
    });

    private final ActivityResultLauncher<Intent> intentQrCodeScanner = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == RESULT_OK) {
            Intent intentResult = result.getData();
            if (intentResult != null) {
                String payload = intentResult.getStringExtra(QR_CODE_DATA);
                Timber.tag("QrCode").d("Received QrCode data: %s", payload);
                QrCodeData = payload;
            }

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
            Intent intentResult = result.getData();

            if (intentResult != null) {
                String payload = intentResult.getStringExtra(READ_NFC_DATA_PAYLOAD);
                Timber.tag("NFC").d("Received NFC data: %s", payload);

                if (!isTaggingRequestedVisit) {
                    mViewModel.tagVisitedCheckpoint(payload);
                } else {
                    mViewModel.tagRequestedVisit(payload);
                }
            }
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

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMPatrolRoute.class);
        binding = FragmentPatrolRouteBinding.inflate(getLayoutInflater());
        dialogLoad = new DialogLoad(requireActivity());


        mViewModel.getRequestedVisit().observe(getViewLifecycleOwner(), requestedVisit -> {
            if (requestedVisit == null) {
                binding.visitRequestBanner.setVisibility(View.GONE);
                return;
            }

            binding.nfcSiteDescription.setText(requestedVisit.getSDescript());
            binding.siteRemarks.setText(requestedVisit.getSRemark1());
            binding.visitRequestBanner.setVisibility(View.VISIBLE);
            binding.visitRequestBanner.setOnClickListener( view -> {
                new DialogTagOption(requireActivity(), requestedVisit.getSDescript(), new DialogTagOption.DialogTagOptionCallback() {
                    @Override
                    public void onClickNFCButton(String remarks) {
                        isTaggingRequestedVisit = true;
                        mViewModel.setRequestedVisit(requestedVisit);
                        Intent intent = new Intent(requireActivity(), ReadNfcActivity.class);
                        intentNFCReader.launch(intent);
                    }

                    @Override
                    public void onClickQrCodeButton(String remarks) {
                        isTaggingRequestedVisit = true;
                        Intent intent = new Intent(requireActivity(), QrCodeScannerActivity.class);
                        intentQrCodeScanner.launch(intent);
                    }
                }).show();
            });
        });

        mViewModel.isLoadingPatrolRoute().observe(getViewLifecycleOwner(), loadingPatrolRoute -> {
            if (loadingPatrolRoute) {

            } else {
                mViewModel.initPatrolCheckpoints();
            }
        });

        mViewModel.getPatrolCheckpoints().observe(getViewLifecycleOwner(), checkpoints -> {
            if(checkpoints == null) { return; }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
            linearLayoutManager.setOrientation(VERTICAL);
            AdapterPatrolRoute adapterPatrolRoute = new AdapterPatrolRoute(checkpoints, (patrol, position) -> {
                if (patrol.isVisited()) {
                    new DialogResult(requireActivity(), DialogResult.RESULT.FAILED, "You already tagged this checkpoint as visited.", dialog -> {
                        dialog.dismiss();
                        mViewModel.clearMessage();
                    }).showDialog();
                    return;
                }

                new DialogTagOption(requireActivity(), patrol.getsDescript(), new DialogTagOption.DialogTagOptionCallback() {
                    @Override
                    public void onClickNFCButton(String remarks) {
                        isTaggingRequestedVisit = false;
                        mViewModel.setCheckpoint(patrol, position);
                        mViewModel.setRemarks(remarks);
                        Intent intent = new Intent(requireActivity(), ReadNfcActivity.class);
                        intentNFCReader.launch(intent);
                    }

                    @Override
                    public void onClickQrCodeButton(String remarks) {
                        isTaggingRequestedVisit = false;
                        mViewModel.setCheckpoint(patrol, position);
                        mViewModel.setRemarks(remarks);
                        Intent intent = new Intent(requireActivity(), QrCodeScannerActivity.class);
                        intentQrCodeScanner.launch(intent);
                    }
                }).show();
            });

            binding.patrolRouteList.setLayoutManager(linearLayoutManager);
            binding.patrolRouteList.setAdapter(adapterPatrolRoute);
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

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                DialogMessage dialogMessage = new DialogMessage(requireActivity());
                dialogMessage.initDialog("GSecure", "Exit GSecure?");
                dialogMessage.setNegativeButton("Yes", dialog -> {
                    dialog.dismiss();
                    requireActivity().finish();
                });
                dialogMessage.setPositiveButton("No", Dialog::dismiss);
                dialogMessage.show();
            }
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
            if (errorMessage == null) { return; }
            if (errorMessage.isEmpty()) { return; }

            new DialogResult(requireActivity(), DialogResult.RESULT.FAILED, errorMessage, Dialog::dismiss).showDialog();
        });

        mViewModel.successfullyTagged().observe(getViewLifecycleOwner(), message -> {
            if (!message.isEmpty()) {
                new DialogResult(requireActivity(), DialogResult.RESULT.SUCCESS, message, dialog -> {
                    dialog.dismiss();
                    mViewModel.clearMessage();
                }).showDialog();
            }
        });
    }
}