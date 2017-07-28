package club.zhcs.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.nutz.lang.util.NutMap;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import club.zhcs.common.Result;

@ControllerAdvice
public class ValidationExceptionHandler {

	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result handle(MethodArgumentNotValidException exception) {
		final List<NutMap> errors = new ArrayList<NutMap>();
		Lang.each(exception.getBindingResult().getAllErrors(), new Each<ObjectError>() {

			@Override
			public void invoke(int index, ObjectError error, int length) throws ExitLoop, ContinueLoop, LoopException {
				errors.add(NutMap.NEW().addv("msg", error.getDefaultMessage()).addv("obj", error.getObjectName()).addv("arguments", error.getArguments()).addv("code",
						error.getCode()).addv("codes", error.getCodes()));
			}
		});
		return Result.fail(errors);
	}

	@ResponseBody
	@ExceptionHandler(ValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Result handle(ValidationException exception) {
		if (exception instanceof ConstraintViolationException) {

			final List<NutMap> errors = new ArrayList<NutMap>();
			Lang.each(((ConstraintViolationException) exception).getConstraintViolations(), new Each<ConstraintViolation>() {

				@Override
				public void invoke(int index, ConstraintViolation error, int length) throws ExitLoop, ContinueLoop, LoopException {
					errors.add(NutMap.NEW().addv("msg", error.getMessage()).addv("obj", error.getConstraintDescriptor()).addv("arguments", error.getExecutableParameters()));
				}
			});
			return Result.fail(errors);
		}
		return Result.fail("参数不正确");
	}
}
