package com.miage.utils;

import java.util.Optional;

public class ConvertUtils {
	
	public static <T> T convertFromOptional(Optional<T> optional) {
        return optional.orElse(null);
    }

    public static <T> Optional<T> convertToOptional(T t) {
        return Optional.of(t);
    }
}
