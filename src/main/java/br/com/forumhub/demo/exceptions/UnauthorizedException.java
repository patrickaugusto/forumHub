package br.com.forumhub.demo.exceptions;

public class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
}
