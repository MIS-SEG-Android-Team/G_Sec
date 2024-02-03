package org.rmj.guanzongroup.gsecurity.ui.screens.settings.createpatrol.schedule;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.guanzongroup.gsecurity.data.preferences.DataStore;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.CreateScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.param.patrolschedule.PersonnelPatrolSchedule;
import org.rmj.guanzongroup.gsecurity.data.remote.param.updatepatrolschedule.UpdatePatrolScheduleParams;
import org.rmj.guanzongroup.gsecurity.data.remote.response.personnelpatrol.PersonnelPatrolModel;
import org.rmj.guanzongroup.gsecurity.data.repository.ScheduleRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class VMSchedule extends ViewModel {

    private final DataStore dataStore;
    private final ScheduleRepository scheduleRepository;

    private final MutableLiveData<Boolean> forUpdate = new MutableLiveData<>(false);
    private final MutableLiveData<List<PersonnelPatrolSchedule>> schedules = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<CreateScheduleParams> patrolRoute = new MutableLiveData<>();
    private final MutableLiveData<PersonnelPatrolModel> patrolScheduleForUpdate = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingUpdateSchedule = new MutableLiveData<>(false);
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>("");
    private final MutableLiveData<Boolean> scheduleSaved = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> scheduleUpdated = new MutableLiveData<>(false);

    @Inject
    public VMSchedule(DataStore dataStore,
                      ScheduleRepository scheduleRepository) {
        this.dataStore = dataStore;
        this.scheduleRepository = scheduleRepository;

        forUpdate.setValue(scheduleRepository.getPatrolRouteForUpdate() != null);
    }

    public LiveData<Boolean> foUpdate() { return forUpdate; }
    public LiveData<List<PersonnelPatrolSchedule>> getSchedules() { return schedules; }
    public LiveData<String> errorMessage() { return errorMessage; }
    public LiveData<Boolean> scheduleSaved() { return scheduleSaved; }
    public LiveData<Boolean> scheduleUpdated() { return scheduleSaved; }
    public LiveData<Boolean> isLoadingUpdateSchedule() { return isLoadingUpdateSchedule; }

    public void initScheduleForUpdate() {
        patrolScheduleForUpdate.setValue(scheduleRepository.getPatrolRouteForUpdate());

        if (patrolScheduleForUpdate.getValue() != null) {
            PersonnelPatrolModel params = patrolScheduleForUpdate.getValue();
            List<PersonnelPatrolSchedule> schedules1 = params.getSSchedule();
            if (schedules1 != null) {
                schedules.setValue(schedules1);
            }
        }
    }

    public void initCreatedSchedule() {
        patrolRoute.setValue(scheduleRepository.getPatrolScheduleFromCache());

        if (patrolRoute.getValue() != null) {
            CreateScheduleParams params = patrolRoute.getValue();
            List<PersonnelPatrolSchedule> schedules1 = params.getSSchedule();
            if (schedules1 != null) {
                schedules.setValue(schedules1);
            }
        }
    }

    public void addSchedule(String time) {
        if (schedules.getValue() != null) {
            List<PersonnelPatrolSchedule> schedules1 = schedules.getValue();

            for (int x = 0; x < schedules1.size(); x++) {
                if (time.equalsIgnoreCase(schedules1.get(x).getDTimexxxx())) {
                    errorMessage.setValue("Time selected already exist in schedules.");
                    return;
                }
            }

            if (!isTimeGapValid(schedules1, time)) {
                errorMessage.setValue("New time has less than 30-minute gap.");
                return;
            }

            PersonnelPatrolSchedule schedule = new PersonnelPatrolSchedule();
            int position = schedules1.size() + 1;
            schedule.setNSchedule(String.valueOf(position));
            schedule.setDTimexxxx(time);
            schedules1.add(schedule);
            schedules1.sort(Comparator.comparing(PersonnelPatrolSchedule::getDTimexxxx));
            schedules.setValue(schedules1);
        }
    }

    public void editSchedule(int position, String time) {
        if (schedules.getValue() != null) {
            List<PersonnelPatrolSchedule> schedules1 = schedules.getValue();
            schedules1.get(position).setNSchedule(String.valueOf(position));
            schedules1.get(position).setDTimexxxx(time);
            schedules1.sort(Comparator.comparing(PersonnelPatrolSchedule::getDTimexxxx));
            schedules.setValue(schedules1);
        }
    }

    public void deleteSchedule(int position) {
        if (schedules.getValue() != null) {
            List<PersonnelPatrolSchedule> schedules1 = schedules.getValue();
            schedules1.remove(position);
            schedules.setValue(schedules1);
        }
    }

    public void dismissErrorMessage() {
        errorMessage.setValue("");
    }

    public void saveSchedule() {
        List<PersonnelPatrolSchedule> schedules1 = schedules.getValue();

        if (schedules1 == null) {
            errorMessage.setValue("Unable to proceed without any patrol schedule created.");
            return;
        }

        if (schedules1.isEmpty()) {
            errorMessage.setValue("Unable to proceed without any patrol schedule created.");
            return;
        }

        Objects.requireNonNull(patrolRoute.getValue()).setSSchedule(schedules1);

        scheduleRepository.updatePatrolScheduleToCache(patrolRoute.getValue());
        scheduleSaved.setValue(true);
    }

    @SuppressLint("CheckResult")
    public void updateSchedule() {
        isLoadingUpdateSchedule.setValue(true);
        List<PersonnelPatrolSchedule> schedules1 = schedules.getValue();

        if (schedules1 == null) {
            errorMessage.setValue("Unable to proceed without any patrol schedule created.");
            return;
        }

        if (schedules1.isEmpty()) {
            errorMessage.setValue("Unable to proceed without any patrol schedule created.");
            return;
        }

        PersonnelPatrolModel personnelPatrolSchedule = patrolScheduleForUpdate.getValue();

        if (personnelPatrolSchedule == null) {
            errorMessage.setValue("Something went wrong...");
            return;
        }

        personnelPatrolSchedule.setSSchedule(schedules1);
        scheduleRepository.updatePatrolRouteForUpdate(personnelPatrolSchedule);
        UpdatePatrolScheduleParams params = new UpdatePatrolScheduleParams();
        params.setSAdminIDx(dataStore.getUserId());
        params.setSSchedIDx(personnelPatrolSchedule.getSSchedIDx());
        params.setSSchedule(schedules1);
        scheduleRepository.updateSchedule(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> {
                            isLoadingUpdateSchedule.setValue(false);
                            if (response.getResult().equalsIgnoreCase("error")) {
                                errorMessage.setValue(response.getError().getMessage());
                                return;
                            }

                            scheduleUpdated.setValue(true);
                        },
                        error -> {
                            errorMessage.setValue(error.getMessage());
                            isLoadingUpdateSchedule.setValue(false);
                        }
                );
    }

    @SuppressLint("SimpleDateFormat")
    private static boolean isTimeGapValid(List<PersonnelPatrolSchedule> timeList, String newTime) {
        // Custom date format for parsing times
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        try {
            // Parse the new time
            Date newTimeDate = dateFormat.parse(newTime);

            if (newTimeDate == null) {
                return false;
            }

            // Iterate through existing times
            for (PersonnelPatrolSchedule existingTime : timeList) {
                Date existingTimeDate = dateFormat.parse(existingTime.getDTimexxxx());

                if (existingTimeDate == null) {
                    return false;
                }

                // Calculate the time difference in milliseconds
                long timeDifference = Math.abs(newTimeDate.getTime() - existingTimeDate.getTime());

                // Convert milliseconds to minutes
                long timeDifferenceMinutes = timeDifference / (60 * 1000);

                // Check if the time difference is less than or equal to 30 minutes
                if (timeDifferenceMinutes <= 30) {
                    return false; // New time has less than 30-minute gap
                }
            }

            return true; // New time is valid
        } catch (ParseException e) {
            e.printStackTrace();
            return false; // Error in parsing times
        }
    }
}