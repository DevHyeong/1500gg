package kr.gg.lol.common.util;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiUtils<T> {
    public static <T> ApiResult<T> success(T body){
        return ApiResult.of(HttpStatus.OK, body);
    }

    public static <T> ApiResult<T> error(HttpStatus status, T body){
        return ApiResult.of(status, body);
    }
    @Getter
    public static class ApiResult<T> {
        private HttpStatus status;
        private T body;

        private ApiResult(HttpStatus status, T  body){
            this.status = status;
            this.body = body;
        }

        public static <T> ApiResult<T> of(HttpStatus status, T body){
            return new ApiResult(status, body);
        }

    }
}
