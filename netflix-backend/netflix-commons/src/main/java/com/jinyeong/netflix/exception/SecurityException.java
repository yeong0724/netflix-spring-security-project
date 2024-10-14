package com.jinyeong.netflix.exception;

public class SecurityException extends NetflixException {

  public SecurityException(ErrorCode errorCode) {
    super(errorCode);
  }

  public static class PasswordEncryptionException extends SecurityException {
    public PasswordEncryptionException() {
      super(ErrorCode.PASSWORD_ENCRYPTION_FAILED);
    }
  }
}
