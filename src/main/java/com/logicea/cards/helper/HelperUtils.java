package com.logicea.cards.helper;

import com.logicea.cards.entities.users.User;
import com.logicea.cards.exceptions.ResourceNotFoundException;
import com.logicea.cards.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.SimpleDateFormat;
import java.util.*;

public class HelperUtils {
    private final static SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ROOT);
    private final UserRepository userRepository;

    public HelperUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User currentlyLoggedInUser() throws Exception {
        String username = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        Optional<User> userOptional = userRepository.findByEmail(username);

        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("Somehow the logged in user doesn't exist");
        }

        return userOptional.get();
    }

    public static Date startDate(final Date date) {
        Calendar calendar = new GregorianCalendar((TimeZone.getDefault()));

        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.setTimeZone(TimeZone.getTimeZone("Africa/Nairobi"));
        return calendar.getTime();
    }

    public static Date endDate(final Date date) {
        Calendar calendar = new GregorianCalendar((TimeZone.getDefault()));

        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
}