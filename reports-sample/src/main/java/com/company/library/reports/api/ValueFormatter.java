package com.company.library.reports.api;

public interface ValueFormatter<T> {
    String format(T value);
}
