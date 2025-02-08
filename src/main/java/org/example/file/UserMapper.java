package org.example.file;

import org.example.models.User;

public class UserMapper implements Mapper<User> {
    @Override
    public User map(String line) {
        String[] parts = line.split(",");
        return new User.UserBuilder()
            .setName(parts[0].trim())
            .setPassword(parts[1].trim())
            .setEmail(parts[2].trim())
            .build();
    }
}
