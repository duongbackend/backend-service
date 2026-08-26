package com.duong.backendservice.dto.event;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserCreatedEvent {
    private String email;
    private String name;
    private String templateName;
}
