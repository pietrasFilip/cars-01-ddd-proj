package com.app.infrastructure.email;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailConfiguration {
    final String fromAddress;

    final String fromName;
}
