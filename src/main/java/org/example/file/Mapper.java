package org.example.file;

public interface Mapper<T> {
    T map(String line);
}
