package com.vivacon.service;

import com.vivacon.event.handler.ActiveSessionChangingListener;

import java.util.Set;

public interface ActiveSessionManager {
    void addSession(String sessionId, String username);

    void removeSession(String sessionId);

    Set<String> getAll();

    void registerListener(ActiveSessionChangingListener listener);

    void removeListener(ActiveSessionChangingListener listener);

    void notifyListeners();
}
