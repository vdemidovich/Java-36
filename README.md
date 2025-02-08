class User implements CustomClass {
    private final String name;
    private final String password;
    private final String email;

    private User(Builder builder) {
        this.name = builder.name;
        this.password = builder.password;
        this.email = builder.email;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public int compareTo(CustomClass other) {
        if (other instanceof User otherUser) {
            return this.name.compareTo(otherUser.name); // Сравнение по имени пользователя
        }
        throw new ClassCastException("Невозможно сравнить объекты разных типов");
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getPassword() {
        return password;
    }


    public static class Builder {
        private String name;
        private String password;
        private String email;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
