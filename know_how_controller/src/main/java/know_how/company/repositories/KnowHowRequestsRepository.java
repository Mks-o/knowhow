package know_how.company.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import know_how.company.entity.RequestEntity;

public interface KnowHowRequestsRepository extends JpaRepository<RequestEntity, Integer>{

	@Query(nativeQuery = true,value = "select * from requests where request=?1 limit 1")
	RequestEntity findByRequest(String request);

	@Query("select r from RequestEntity r,AnswerEntity a where a.request_id=r.request_id ORDER BY a.raiting DESC limit 10")
	List<RequestEntity> getLast10Request();
}
