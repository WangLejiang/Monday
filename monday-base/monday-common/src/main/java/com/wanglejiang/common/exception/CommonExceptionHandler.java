package com.wanglejiang.common.exception;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanglejiang.common.result.R;
import com.wanglejiang.common.result.ResultStatusEnum;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Optional;


@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @Resource
    private Environment environment;

    /**
     * @Valid抛出的异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<R> exceptionHandler(MethodArgumentNotValidException e) {
        R r = constructExceptionByCode(e, ResultStatusEnum.INVALID_PARAM);
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<R> handleFeignException(FeignException e) {
        R r = constructExceptionByCode(e, ResultStatusEnum.FEIGN_EXCEPTION);
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<R> handleException(Exception e) {
        R r = constructExceptionByCode(e, ResultStatusEnum.INVALID_PARAM);
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<R> handleBusinessException(Exception e) {
        R r = constructExceptionByCode(e, ResultStatusEnum.BUSINESS_EXCEPTION);
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<R> handleSystemException(Exception e) {
        R r = constructExceptionByCode(e, ResultStatusEnum.SYSTEM_EXCEPTION);
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RepeatRequestException.class)
    public ResponseEntity<R> handleRepeatRequestException(Exception e) {
        R r = constructExceptionByCode(e, ResultStatusEnum.REPEAT_REQUEST_EXCEPTION);
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<R> handleAuthenticationException(Exception e) {
        R r = constructExceptionByCode(e, ResultStatusEnum.AUTHENTICATION_EXCEPTION);
        return new ResponseEntity<>(r, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthRequiredException.class)
    public ResponseEntity<R> handleAuthRequiredException(Exception e) {
        R r = constructExceptionByCode(e, ResultStatusEnum.AUTH_REQUIRED_EXCEPTION);
        return new ResponseEntity<>(r, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<R> handleMultipartException(Exception e) {
        log.error("multipart exception", e);
        R r = R.error(ResultStatusEnum.FILE_EXEEDS_SIZE_EXCEPTION.getCode(), "文件大小超出" + environment.getProperty("spring.servlet.multipart.max-file-size"));
        return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
    }

    protected R constructExceptionByCode(Exception e, ResultStatusEnum statusEnum) {
        /**
         * 针对@Valid，我们需要手动处理一下
         */
        String stackTrace = getStackTrace(e);
        log.error(stackTrace);
        String message = null != e.getMessage() ? e.getMessage() : "";
        if (e instanceof MethodArgumentNotValidException) {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            StringBuilder sb = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                sb.append(fieldError.getDefaultMessage()).append(";");
            }
            sb.deleteCharAt(sb.lastIndexOf(";"));
            message = sb.toString();
        }

        if (e instanceof FeignException) {
            int errorCode = statusEnum.getCode();
            FeignException feignException = (FeignException) e;
            Optional<ByteBuffer> optionByteBuffer = feignException.responseBody();
            if (optionByteBuffer.isPresent()) {
                try {
                    String feignExceptionMessage = getString(optionByteBuffer.get());
                    HashMap<String, Object> map = (new ObjectMapper()).readValue(feignExceptionMessage, HashMap.class);
                    message = (String) map.get("message");
                    errorCode = (int) map.get("code");
                } catch (Exception ex) {
                    log.error("解析远程调用时异常：{}", ex.getMessage());
                }
            } else {
                message = "远程调用异常";
            }
            return R.error(errorCode, message).put(stackTrace);
        }
        if (StringUtils.isBlank(message)) {
            message = e.getClass().getName();
        }
        return R.error(statusEnum.getCode(), statusEnum.getMessage() + ":" + message).put(stackTrace);
    }

    /**
     * ByteBuffer 转换 String
     *
     * @param buffer
     * @return
     */
    public static String getString(ByteBuffer buffer) {
        return StandardCharsets.UTF_8.decode(buffer).toString();
    }

    private String getStackTrace(Throwable t) {
        if (null == t) {
            return "";
        }
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            t.printStackTrace(pw);
            return sw.toString();
        }
    }
}
