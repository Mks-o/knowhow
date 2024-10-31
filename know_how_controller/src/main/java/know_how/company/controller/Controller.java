package know_how.company.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import know_how.company.dto.*;
import know_how.company.service.KnowHowService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequiredArgsConstructor
@Validated
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Controller {

	KnowHowService knowHowService;

	@PostMapping("/send_request")
	@CrossOrigin()
	public List<AnswerDto> getAnswer(@RequestBody RequestDto request) {
		return knowHowService.getAnswer(request);
	}

	@PostMapping("/rate_answer/{answer_id}")
	@CrossOrigin
	public AnswerDto setAnswerRaiting(@PathVariable Integer answer_id) {
		return knowHowService.rateAnswer(answer_id);
	}

	@GetMapping("/get_top_ten_requests")
	@CrossOrigin
	public List<RequestDto> getTop10Requests() {
		return knowHowService.getLast10Request();
	}

	@GetMapping("/get_request_srcs/{answer_id}")
	@CrossOrigin
	public List<String> getImagesSrcByRequest(@PathVariable Integer answer_id) {
		return knowHowService.getImagesSrcByRequest(answer_id);
	}
}
