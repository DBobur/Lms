package uz.pdp.lms.core.config;

import java.time.LocalDateTime;

public record AppErrorRequest(String errorPath, String errorMessage, Integer errorCode, LocalDateTime now) {
}
