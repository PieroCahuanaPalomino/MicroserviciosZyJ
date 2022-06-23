package com.tutorial.auth.service.dto;

import com.tutorial.auth.service.entity.AuthUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TokenDto {
	private String token;
	
}
