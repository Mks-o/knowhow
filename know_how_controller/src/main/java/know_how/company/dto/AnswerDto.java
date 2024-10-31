package know_how.company.dto;
import java.time.LocalDate;

import know_how.company.entity.AnswerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor

public class AnswerDto {
	Integer answer_id;
	String answer;
	String answer_url;
	Integer request_id;
	String imagessrc;
	LocalDate cretedDate;
	Integer raiting;
	Boolean available;
	public AnswerEntity convertToEntity() {
		return new AnswerEntity(answer_id, answer,answer_url, request_id, imagessrc, cretedDate, raiting, available);
	}
}
