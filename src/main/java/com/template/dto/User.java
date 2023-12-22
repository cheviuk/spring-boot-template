package com.template.dto;

import com.template.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Long expired;
    private Boolean locked;
    private Long credentialExpired;
    private Boolean enabled;
    private Role role;

    public User(UserEntity userEntity) {
        BeanUtils.copyProperties(userEntity, this, "role");
        this.role = userEntity.getRole();
    }

    public static class UserUtils {
        public static Long getLongMillisecondsInFuture(int calendarDateTimeUnit, int inCount) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date()); // now
            calendar.add(calendarDateTimeUnit, inCount);
            return calendar.getTimeInMillis();
        }
    }
}
