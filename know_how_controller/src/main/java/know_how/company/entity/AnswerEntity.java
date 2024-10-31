package know_how.company.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import know_how.company.dto.AnswerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="answers")
public class AnswerEntity {
	@Id
	@Column(unique = true)
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	Integer answer_id;
	@Column(length = 2600)
	String answer;
	String answer_url;
	Integer request_id;
	@Column(length = 2600)
	String imagessrc;
	LocalDate cretedDate;
	Integer raiting;
	Boolean available;
	
	public AnswerDto consertToDto() {
		return new AnswerDto(answer_id, answer,answer_url, request_id, imagessrc, cretedDate, raiting, available);
	}
}
