package groupbee.comment.service.feign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@org.springframework.cloud.openfeign.FeignClient(name = "employeeClient", url = "https://api.bmservice.kro.kr")
public interface FeignClient {
    @GetMapping("/api/employee/info")
    Map<String, Object> getUserInfo();
}



