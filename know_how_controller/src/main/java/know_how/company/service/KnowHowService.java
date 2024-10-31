package know_how.company.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import know_how.company.dto.*;
import know_how.company.entity.*;
import know_how.company.repositories.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KnowHowService implements ICnowHow {

	KnowHowRepository knowHowRepository;
	KnowHowRequestsRepository knowHowRequestsRepository;
	ObjectMapper objectMapper = new ObjectMapper();

	@Override
	@Transactional
	public List<AnswerDto> getAnswer(RequestDto request) {
		//System.out.println(request.getRequest());
		//request.setRequest(request.getRequest().replace(' ', '_'));
		RequestEntity req = knowHowRequestsRepository.findByRequest(request.getRequest());
		if (req == null)
			req = knowHowRequestsRepository.save(request.convertToEntity());
		List<AnswerEntity> answers = knowHowRepository.findAllByRequest_id(req.getRequest_id());
		if (answers.isEmpty())
			answers = knowHowRepository.findAllByRequest(req.getRequest());
		if (answers.isEmpty()) {
			try {
				answers = findAnswer(req.convertToDto()).stream().map(x -> x.convertToEntity()).toList();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				return null;
			}
		}
		//answers.forEach(a -> System.out.println(a));
		return answers.stream().map(x -> x.consertToDto()).toList();
	}

	@Override
	@Transactional
	public List<AnswerDto> findAnswer(RequestDto request) throws IOException {
		String link = "https://www.google.com/search?q=" + request.getRequest().replaceAll(" ", "%20");
		String img_source = "https://unsplash.com/s/photos/" + request.getRequest().replaceAll(" ", "%20");
		System.out.println("parse link:--->" + link);
		System.out.println("parse link:--->" + img_source);
		Document doc = Jsoup.connect(link).timeout(7000).get();
		Document imgDoc = Jsoup.connect(img_source).timeout(7000).get();
		doc.outputSettings().charset("ISO-8859-1");
		imgDoc.outputSettings().charset("ISO-8859-1");
		Document parsed_google_res = Jsoup.parse(doc.toString());
		Document img_document_parsed = Jsoup.parse(imgDoc.toString());
		List<String> img_src = new ArrayList<>();
		if (img_document_parsed.getElementsByClass("_VG2i") != null)
			img_src = img_document_parsed.getElementsByClass("_VG2i").select("img[src]").eachAttr("src");
		Elements page_elements = parsed_google_res.select("div.MjjYud");
		List<AnswerDto> answers = page_elements.stream().map(x -> {
			String text = x.selectFirst("div.VwiC3b span") != null ? x.selectFirst("div.VwiC3b span").ownText() : "";
			String url = x.selectFirst("cite span") != null ? x.selectFirst("cite span").ownText() : "";
			if (text != "") {
				AnswerDto answer = new AnswerDto(null, text, url, request.getRequest_id(), "no image", LocalDate.now(),
						0, true);
				return answer;
			} else return null;
		}).toList();
		List<AnswerDto> filter = answers.stream().filter(x -> x != null).toList();
		String jsonArray = objectMapper.writeValueAsString(img_src);
		for (AnswerDto answerDto : filter) answerDto.setImagessrc(jsonArray);
		List<AnswerEntity> entities = filter.stream().map(x -> x.convertToEntity()).toList();
		return knowHowRepository.saveAll(entities).stream().map(r->r.consertToDto()).toList();
		//return filter;
	}

	@Override
	@Transactional
	public AnswerDto rateAnswer(Integer answer_id) {
		if (knowHowRepository.findByAnswer_Id(answer_id) != null) {
			AnswerEntity entity = knowHowRepository.findByAnswer_Id(answer_id);
			entity.setRaiting(1 + entity.getRaiting());
			knowHowRepository.save(entity);
			return entity.consertToDto();
		}
		return null;
	}

	@Override
	public List<RequestDto> getLast10Request() {
		List<RequestEntity> requests = knowHowRequestsRepository.getLast10Request();
		return requests.stream().map(r->r.convertToDto()).toList();
	}

	@Override
	public List<String> getImagesSrcByRequest(Integer request_id) {
		List<String> images_src = knowHowRepository.findAllImagessrcByRequestId(request_id);
		return images_src;
	}
}
