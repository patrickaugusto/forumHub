package br.com.forumhub.demo.exceptions;

public class RespostaNotFoundException extends RuntimeException {

    public RespostaNotFoundException(String message) {
        super(message);
    }
}
