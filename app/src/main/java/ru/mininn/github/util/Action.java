package ru.mininn.github.util;

public interface Action<T> {
    void execute(T arg);
}
