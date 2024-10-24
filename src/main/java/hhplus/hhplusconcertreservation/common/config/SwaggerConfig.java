package hhplus.hhplusconcertreservation.common.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@SecurityScheme(
	type = SecuritySchemeType.APIKEY,
	name= "Authorization",
	description = "대기열 Token을 입력해주세요.",
	in = SecuritySchemeIn.HEADER)
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.components(new Components())
			.info(apiInfo())
			.security(getSecurityRequirement());  // Security 설정 적용
	}

	private List<SecurityRequirement> getSecurityRequirement() {
		List<SecurityRequirement> requirements = new ArrayList<>();
		requirements.add(new SecurityRequirement().addList("Authorization"));  // Authorization 헤더 추가
		return requirements;
	}

	private Info apiInfo() {
		return new Info()
			.title("콘서트 예약 서비스")
			.description("항해 플러스")
			.version("1.0.0");
	}
}