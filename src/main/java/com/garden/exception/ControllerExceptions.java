package com.garden.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptions {

    @ExceptionHandler(AdminException.class)
    public ResponseEntity<?> handleAdminException(final AdminException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(ChildrenException.class)
    public ResponseEntity<?> handleChildrenException(final ChildrenException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(GuardianException.class)
    public ResponseEntity<?> handleGuardianException(final GuardianException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleJwtException(final JwtException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureException(final SignatureException s){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", s.getMessage()));
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<?> handleInternalAuthenticationServiceException(final InternalAuthenticationServiceException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("error", "Ya existe un registro con este email.")
        );
    }

}
