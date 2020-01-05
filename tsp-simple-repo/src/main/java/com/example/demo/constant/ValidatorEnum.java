package com.example.demo.constant;


import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import static com.example.demo.constant.VertifyDatePatternConst.DATE_PATTERN;


public enum ValidatorEnum {
	NONE(null), VALIDATE_JWT(jwt -> {
		int firstPeriod = jwt.indexOf('.');
		int lastPeriod = jwt.lastIndexOf('.');

		if (firstPeriod <= 0 || lastPeriod <= firstPeriod) {
			return false;
		}
		return true;
	}), VALIDATE_PUBLICDATE(date -> {
		 DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_PATTERN);
		try {
			format.parse(date);
		} catch (DateTimeException e) {
			return false;
		}
		return true;
	});

	private Function<String, Boolean> validator;

	ValidatorEnum(Function<String, Boolean> validator) {
		this.validator = validator;
	}

	public Function<String, Boolean> getValidator() {
		return validator;
	}
}
