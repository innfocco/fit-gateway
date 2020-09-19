package jc.gateway.entity;

import java.io.Serializable;
import java.util.Map;

import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("Behavior")
public class Behavior implements Serializable {
	private static final long serialVersionUID = -528197881867000791L;
	
	private String id;
	private String name;
	private String route;
	private int httpStatus;
	private String payloadResponse;
	private Long delay;
	private Map<String, String> headersResponse;
	
}
