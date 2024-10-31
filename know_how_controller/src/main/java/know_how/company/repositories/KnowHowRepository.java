package know_how.company.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import know_how.company.entity.AnswerEntity;

public interface KnowHowRepository extends JpaRepository<AnswerEntity, Integer>{
	
	@Query(nativeQuery = true, value = "select * from answers where answer like %?1%")
	public List<AnswerEntity> findAllByRequest(String request);

	@Query(nativeQuery = true, value = "select * from answers where request_id=?1")
	public List<AnswerEntity> findAllByRequest_id(Integer id);

	@Query(nativeQuery = true, value = "select * from answers where answer_id=?1")
	public AnswerEntity findByAnswer_Id(Integer answer_id);

	@Query(nativeQuery = true, value = "select imagessrc from answers where request_id=?1")
	public List<String> findAllImagessrcByRequestId(Integer request_id);

}
