package com.bymbank.emailingsystem.services;

public interface UserService {
    void notifyTellerWithCreation(Long tellerId, String password);
    void notifyAdminWithTellerCreation(Long adminId, Long tellerId);

    void notifyUserWithCreation(Long createdUserId, String password);

    void notifyTellerWithUserCreation(Long creatingUserId, Long createdUserId);
}
