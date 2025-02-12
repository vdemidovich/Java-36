package org.example.models;

import java.util.Comparator;

public class User implements Sortable<User> {
    private final String name;
    private final String password;
    private final String email;

    private User(UserBuilder builder) {
        this.name = builder.name;
        this.password = builder.password;
        this.email = builder.email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", password=" + password + ", email=" + email + "]";
    }

    @Override
    public int compareTo(User o) {
        int result = this.name.compareTo(o.name);
        if (result == 0) {
            result = this.email.compareTo(o.email);
        }
        if (result == 0) {
            result = this.password.compareTo(o.password);
        }
        return result;
    }

    public static class UserNameComparator implements Comparator<User> {
        @Override
        public int compare(User user1, User user2) {
            return user1.getName().compareTo(user2.getName());
        }
    }

    public static class UserPasswordComparator implements Comparator<User> {
        @Override
        public int compare(User user1, User user2) {
            return user1.getPassword().compareTo(user2.getPassword());
        }
    }

    public static class UserEmailComparator implements Comparator<User> {
        @Override
        public int compare(User user1, User user2) {
            return user1.getEmail().compareTo(user2.getEmail());
        }
    }


    public static class UserBuilder {
        private String name;
        private String password;
        private String email;

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
