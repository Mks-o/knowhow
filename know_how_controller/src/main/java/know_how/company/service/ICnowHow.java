package know_how.company.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import know_how.company.dto.AnswerDto;
import know_how.company.dto.RequestDto;

public interface ICnowHow {

	public List<AnswerDto> getAnswer(RequestDto request);

	public AnswerDto rateAnswer(Integer answer_id);
	
	public List<AnswerDto> findAnswer(RequestDto request) throws IOException, URISyntaxException;
	
	public List<RequestDto> getLast10Request();
	public List<String> getImagesSrcByRequest(Integer request_id);
}
