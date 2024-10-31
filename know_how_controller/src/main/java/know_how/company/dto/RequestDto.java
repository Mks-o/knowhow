package know_how.company.dto;

import know_how.company.entity.RequestEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
	Integer request_id;
	String request;
	
	public RequestEntity convertToEntity()
	{
		return new RequestEntity(request_id, request);
	}
}
