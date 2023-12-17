package org.rmj.guanzongroup.gsecurity.data.repository;

import org.rmj.guanzongroup.gsecurity.data.remote.service.ApiService;

import javax.inject.Inject;

public class CheckpointRepository {

    private final ApiService apiService;

    @Inject
    public CheckpointRepository(
            ApiService apiService
    ) {
        this.apiService = apiService;
    }


}
