package com.example.auth.controller;

import com.example.auth.entity.User;
import com.example.auth.feign.BookingClient;
import com.example.auth.feign.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/manager")
@RequiredArgsConstructor
public class ManagerProxyController {

    private final BookingClient bookingClient;
    private final UserClient userClient;

    @GetMapping("/{managerId}/booked-users")
    public ResponseEntity<List<User>> getBookedUsers(@PathVariable String managerId) {
        List<String> emails = bookingClient.getUserEmailsByManagerId(managerId);
        List<User> users = emails.stream()
                .map(email -> {
                    Map<String, String> payload = Map.of("email", email);
                    return userClient.getUserByEmail(payload);
                })
                .toList();
        return ResponseEntity.ok(users);
    }
}
