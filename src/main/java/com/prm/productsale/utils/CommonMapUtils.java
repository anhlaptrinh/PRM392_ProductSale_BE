package com.prm.productsale.utils;

import org.mapstruct.Named;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface CommonMapUtils {
    @Named("nullToEmpty")
    default String nullToEmpty(String value) {
        return value == null ? "" : value;
    }

    @Named("nullToZero")
    default Integer nullToZero(Integer value) {
        return value == null ? 0 : value;
    }

    @Named("nullToZeroLong")
    default Long nullToZeroLong(Long value) {
        return value == null ? 0L : value;
    }

    @Named("nullToBigDecimalZero")
    default BigDecimal nullToBigDecimalZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    @Named("dateToString")
    default String dateToString(LocalDate date) {
        return date == null ? "" : date.format(DateTimeFormatter.ISO_DATE);
    }

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String date) {
        return (date == null || date.isEmpty())
                ? null
                : LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }

    @Named("booleanToInteger")
    default Integer booleanToInteger(Boolean value) {
        return (value != null && value) ? 1 : 0;
    }

    @Named("integerToBoolean")
    default Boolean integerToBoolean(Integer value) {
        return (value != null && value == 1);
    }

    @Named("trimString")
    default String trimString(String value) {
        return value == null ? null : value.trim();
    }

    @Named("formatDateTimeToString")
    default String formatDateTimeToString(LocalDateTime dateTime) {
        return dateTime == null ? "" : dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
