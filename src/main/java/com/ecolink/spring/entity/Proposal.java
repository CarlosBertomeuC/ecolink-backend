package com.ecolink.spring.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Proposal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@ManyToOne
	@JsonIgnore
	private Challenge challenge;

	@ManyToOne
	@JsonIgnore
	private Startup startup;

	private String description;

	private String link;

	private LocalDate date;
	private Status status;

	public Proposal(Startup startup, Challenge challenge, String description, LocalDate date, Status status) {

		this.challenge = challenge;
		this.startup = startup;
		this.description = description;
		this.date = date;
		this.status = status;
	}
}
