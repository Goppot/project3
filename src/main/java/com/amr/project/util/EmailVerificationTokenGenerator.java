package com.amr.project.util;

import java.util.UUID;

public class EmailVerificationTokenGenerator {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
