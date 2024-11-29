package uz.pro.usm.security;

import java.time.LocalDateTime;

public record AppErrorRequest(String errorPath, String errorMessage, Integer errorCode, LocalDateTime now) {
}
