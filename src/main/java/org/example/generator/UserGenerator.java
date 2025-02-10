package org.example.generator;

import org.example.models.User;

public class UserGenerator extends DataGenerator<User> {
    @Override
    public User generate() {
        String name = "User" + generateUniqueNumber();
        String password = generateRandomString(8);
        String email = generateRandomString(5) + "@" + generateRandomString(3) + "." + generateMainDomain();
        return new User.UserBuilder().setName(name).setPassword(password).setEmail(email).build();
    }

    private String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }

    private String generateMainDomain() {
        String[] domains = {"com", "net", "org", "ru", "edu", "by"};
        int index = (int) (Math.random() * domains.length);
        return domains[index];
    }
}
