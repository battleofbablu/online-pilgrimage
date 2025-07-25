package com.example.hotelmanager.service;

import com.example.hotelmanager.dto.ManagerRequest;
import com.example.hotelmanager.dto.ManagerResponse;
import com.example.hotelmanager.entity.Manager;

import java.util.List;

/**
 * Service interface for hotel manager-related operations.
 */
public interface ManagerService {

    /**
     * Registers a new hotel manager using the provided request data.
     *
     * @param request DTO containing manager + hotel info
     * @return ManagerResponse DTO with essential response details
     */
    ManagerResponse registerManager(ManagerRequest request);

    /**
     * Fetches a manager entity by their email (used in login/profile).
     *
     * @param email Email of the manager
     * @return Manager entity or null if not found
     */
    Manager getManagerByEmail(String email);

    List<Manager> getAllHotels();
}
