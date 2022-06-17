package com.tencoding.blog.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class RoleId implements Serializable {
	
	@Column(length = 255)
	private String title;
	
	@Column(length = 30)
	private String actorName;
}
