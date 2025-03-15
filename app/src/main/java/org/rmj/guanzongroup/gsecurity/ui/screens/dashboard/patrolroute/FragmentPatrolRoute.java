package org.rmj.guanzongroup.gsecurity.ui.screens.dashboard.patrolroute;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.registerReceiver;
import static androidx.core.content.ContextCompat.startForegroundService;
import static androidx.recyclerview.widget.RecyclerView.VERTICAL;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.QR_CODE_DATA;
import static org.rmj.guanzongroup.gsecurity.constants.Constants.READ_NFC_DATA_PAYLOAD;
import static org.rmj.guanzongroup.gsecurity.utils.ImageFileCreator.CreateImageUri;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import org.rmj.guanzongroup.gsecurity.BuildConfig;
import org.rmj.guanzongroup.gsecurity.data.preferences.PatrolCache;
import org.rmj.guanzongroup.gsecurity.databinding.FragmentPatrolRouteBinding;
import org.rmj.guanzongroup.gsecurity.service.TimeCheckService;
import org.rmj.guanzongroup.gsecurity.ui.activity.AuthenticationActivity;
import org.rmj.guanzongroup.gsecurity.ui.activity.QrCodeScannerActivity;
import org.rmj.guanzongroup.gsecurity.ui.activity.ReadNfcActivity;
import org.rmj.guanzongroup.gsecurity.ui.components.adapter.AdapterPatrolRoute;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogLoad;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogMessage;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogResult;
import org.rmj.guanzongroup.gsecurity.ui.components.dialog.DialogTagOption;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import timber.log.Timber;

public class FragmentPatrolRoute extends Fragment {

    @Inject
    VMPatrolRoute mViewModel;

    @Inject
    PatrolCache patrolCache;

    private DialogLoad dialogLoad;
    private FragmentPatrolRouteBinding binding;

    //private Boolean isTaggingRequestedVisit = false;
    private String QrCodeData = "";

    @SuppressLint("NewApi")
    private final ActivityResultLauncher<Intent> intentFrontCamera = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if(result.getResultCode() == RESULT_OK) {

           /*if (!isTaggingRequestedVisit) {
               mViewModel.tagVisitedCheckpoint(QrCodeData);
           } else {
               mViewModel.tagRequestedVisit(QrCodeData);
           }*/

            mViewModel.tagVisitedCheckpoint(QrCodeData);

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

    @SuppressLint("NewApi")
    private final ActivityResultLauncher<Intent> intentNFCReader = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result-> {
        if(result.getResultCode() == RESULT_OK) {
            Intent intentResult = result.getData();

            if (intentResult != null) {
                String payload = intentResult.getStringExtra(READ_NFC_DATA_PAYLOAD);
                Timber.tag("NFC").d("Received NFC data: %s", payload);

                /*if (!isTaggingRequestedVisit) {
                    mViewModel.tagVisitedCheckpoint(payload);
                } else {
                    mViewModel.tagRequestedVisit(payload);
                }*/

                mViewModel.tagVisitedCheckpoint(payload);

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

    @SuppressLint("NewApi")
    private final ActivityResultLauncher<String> notificationPermission =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                mViewModel.setNotificationPermissionEnabled(isGranted);
            });

    //todo: create event receiver for every clock changed
    private  final BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @SuppressLint("NewApi")
        @Override
        public void onReceive(Context context, Intent intent) {

            if (Objects.equals(intent.getAction(), Intent.ACTION_TIME_TICK)){

                Timber.tag("TimeChangeReceiver").d("CLOCK CHANGED TO %s", LocalTime.now());

                //todo: import schedules from local data
                mViewModel.getPatrolRouteSchedules();

            }
        }
    };

    public static FragmentPatrolRoute newInstance() {
        return new FragmentPatrolRoute();
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMPatrolRoute.class);
        binding = FragmentPatrolRouteBinding.inflate(getLayoutInflater());
        dialogLoad = new DialogLoad(requireActivity());

        binding.labelVersionInfo.setText(BuildConfig.VERSION_NAME);

        //todo: check draw over apps permission
        mViewModel.setDrawoverappsPermissionEnabled(Settings.canDrawOverlays(getContext()));
        mViewModel.isDrawoverappsPermissionEnabled().observe(getViewLifecycleOwner(), isGranted -> {

            if (!isGranted){

                //todo: display dialog prompt to enable draw over apps permission
                DialogMessage dialogMessage = new DialogMessage(requireActivity());
                dialogMessage.initDialog("GSecure", "Your app should display over other applications. Please enable this permission to run properly.");

                //todo: positive button to direct on settings page and close the app
                dialogMessage.setPositiveButton("Enable", dialog -> {

                    dialog.dismiss();

                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getContext().getPackageName()));

                    startActivity(intent);
                    requireActivity().finish();

                });

                //todo: negative button to close the app
                dialogMessage.setNegativeButton("Cancel", dialog -> {
                    dialog.dismiss();
                    requireActivity().finish();
                });
                dialogMessage.show();

            }

        });

        //todo: get notification permission
        boolean isNotificationPermissionGranted =
                ContextCompat.checkSelfPermission(requireActivity(),
                        Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;

        //todo: set notification permission
        mViewModel.setNotificationPermissionEnabled(isNotificationPermissionGranted);

        //todo: observe notification permission
        mViewModel.isNotificationPermissionEnabled().observe(requireActivity(), isGranted -> {

            //todo: if granted, start service
            if (isGranted) {
                Intent patrolServiceIntent = new Intent(requireActivity(), TimeCheckService.class);
                startForegroundService(requireActivity(), patrolServiceIntent);
            } else {

                //todo: display dialog prompt to enable notification permission
                DialogMessage dialogMessage = new DialogMessage(requireActivity());
                dialogMessage.initDialog("Permission", "Enable notifications permission in order to make the app run properly.");
                dialogMessage.setNegativeButton("Enable", dialog -> {
                    dialog.dismiss();
                    notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS);
                });
                dialogMessage.setPositiveButton("Cancel", dialog -> {
                    dialog.dismiss();
                    requireActivity().finish();
                });
                dialogMessage.show();
            }
        });

        /*mViewModel.getRequestedVisit().observe(getViewLifecycleOwner(), requestedVisit -> {
            if (requestedVisit == null) {
                binding.visitRequestBanner.setVisibility(View.GONE);
                return;
            }

            binding.nfcSiteDescription.setText(requestedVisit.getsDescript());
            binding.siteRemarks.setText(requestedVisit.getsRemarksx());

            Timber.tag("FragmentPatrolRoute").d(requestedVisit.getsRemarksx());

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
        });*/

        //todo: observe loading status
        mViewModel.isLoadingPatrolRoute().observe(requireActivity(), loadingPatrolRoute -> {
            if (loadingPatrolRoute) {

            } else {

                //todo: init patrol checkpoints
                mViewModel.initPatrolCheckpoints();

                //todo: initialize patrol cache data
                mViewModel.initPatrolCache();

                //todo: initialize cache schedule for observation every minute
                mViewModel.initNFCacheSchedule();

                //todo: get patrol checkpoints
                mViewModel.getPatrolCheckpoints().observe(requireActivity(), checkpoints -> {
                    if(checkpoints == null) { return; }

                    //todo: observe data set from cache
                    mViewModel.getNFCCache().observe(requireActivity(), nfcCache ->{

                        //todo: set to adapter list
                        AdapterPatrolRoute adapterPatrolRoute =
                                new AdapterPatrolRoute(checkpoints, nfcCache.getSchedule(),
                                        nfcCache.getNfccheckpoint(), mViewModel, nfcCache.getHasStarted(),
                                        nfcCache.getnDurationx(), (patrol, position) -> {

                                    //todo: check if patrol is done
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
                                            //isTaggingRequestedVisit = false;
                                            mViewModel.setCheckpoint(patrol, position);
                                            mViewModel.setRemarks(remarks);
                                            Intent intent = new Intent(requireActivity(), ReadNfcActivity.class);
                                            intentNFCReader.launch(intent);
                                        }

                                        @Override
                                        public void onClickQrCodeButton(String remarks) {
                                            //isTaggingRequestedVisit = false;
                                            mViewModel.setCheckpoint(patrol, position);
                                            mViewModel.setRemarks(remarks);
                                            Intent intent = new Intent(requireActivity(), QrCodeScannerActivity.class);
                                            intentQrCodeScanner.launch(intent);
                                        }
                                    }).show();

                                });

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
                        linearLayoutManager.setOrientation(VERTICAL);

                        binding.patrolRouteList.setLayoutManager(linearLayoutManager);
                        binding.patrolRouteList.setAdapter(adapterPatrolRoute);

                    });

                });

                if (isDeviceConnected(requireActivity())) {

                    if (isReachable()) {

                        //todo: resend tagged checkpoints, if not posting/sending
                        mViewModel.getPosted().observe(requireActivity(), new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer integer) {

                                if (integer > 0){
                                    mViewModel.postTaggedCheckpoints();

//                                    mViewModel.isPostingCheckpoint().observe(requireActivity(), new Observer<Boolean>() {
//                                        @Override
//                                        public void onChanged(Boolean aBoolean) {
//
//                                            Log.d("ISPOST", String.valueOf(aBoolean));
//
//                                            if (!aBoolean){
//                                                mViewModel.postTaggedCheckpoints();
//                                            }
//
//                                        }
//                                    });

                                }

                            }
                        });

                    }
                }

            }
        });

        //todo: init other observables
        setupObservables();

        //todo: register event receiver
        registerReceiver(requireActivity(), timeReceiver, new IntentFilter(Intent.ACTION_TIME_TICK), ContextCompat.RECEIVER_EXPORTED);

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

    @SuppressLint("NewApi")
    private void setupObservables() {

        // region Observables

        //todo: observe lastlog, to auto logout
        mViewModel.getLastLog().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                if (s != null){

                    if (!s.isEmpty()){

                        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        String logDate = s;

                        if (!currentDate.equals(logDate)){
                            mViewModel.logoutUser();
                        }
                    }
                }

            }
        });

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

    private boolean isDeviceConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    private boolean isReachable()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        trustAllCertificates();

        try
        {
            String lsAddress = "http://192.165.10.65/"; //"https://restgk.guanzongroup.com.ph"

            HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(
                    lsAddress).openConnection();
            httpUrlConnection.setRequestProperty("Connection", "close");
            httpUrlConnection.setRequestMethod("HEAD");
            httpUrlConnection.setConnectTimeout(5000);
            int responseCode = httpUrlConnection.getResponseCode();

            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (IOException | NetworkOnMainThreadException noInternetConnection){
            noInternetConnection.printStackTrace();
            return false;
        }
    }

    public void trustAllCertificates() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                            return myTrustedAnchors;
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception e) {
        }
    }
}