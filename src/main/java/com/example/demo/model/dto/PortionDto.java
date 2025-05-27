package com.example.demo.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortionDto {
	@NotNull
    private Integer sid;
	@NotNull
    private Integer fid;
	@NotNull
	private Integer pid;
	
	@NotBlank
	@Size(max = 40)
    private String pname;
}
