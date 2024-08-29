package groupbee.comment.service.feign;

import groupbee.comment.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "employeeClient", url = "https://api.bmservice.kro.kr")
public interface EmployeeFeignClient {
    @GetMapping("/api/employee/info")
    Map<String, Object> getUserInfo(@RequestParam("id") String id);
}



