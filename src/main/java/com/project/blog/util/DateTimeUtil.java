package com.project.blog.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateTimeUtil {
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	public static SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}

	private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

	public static DateTimeFormatter getDateTimeFormatter() {
		return dateTimeFormatter;
	}

	public static long getMinutesBetween(LocalDateTime startTime, LocalDateTime endTime) {
		return startTime.until(endTime, ChronoUnit.MINUTES);
	}

	public static String formatDateTime(LocalDateTime datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss");
		return datetime.format(formatter);
	}

	public static String formatDateTimeForCashRegister(LocalDateTime datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		return datetime.format(formatter);
	}

	public static String formatDateTimeForProjection(LocalDateTime datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return datetime.format(formatter);
	}

	public static LocalDateTime getDateTimeFromString(String dateTimeString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, formatter);
		return dateTime;
	}

	public static String formatDateTimeForBill(LocalDateTime datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		return datetime.format(formatter);
	}

	public static String getDateFromLocalDateTime(LocalDateTime datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		return datetime.format(formatter);
	}

	public static String getTimeFromLocalDateTime(LocalDateTime datetime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return datetime.format(formatter);
	}

	public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
		return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static long numberOfDaysInRange(LocalDateTime startTime, LocalDateTime endTime) {
		LocalDateTime start = LocalDateTime.of(startTime.getYear(), startTime.getMonthValue(),
				startTime.getDayOfMonth(), 0, 0);
		LocalDateTime end = LocalDateTime.of(endTime.getYear(), endTime.getMonthValue(), endTime.getDayOfMonth(), 0, 0);
		long daysBetween = ChronoUnit.DAYS.between(start, end);
		return (daysBetween + 1);
	}
}
