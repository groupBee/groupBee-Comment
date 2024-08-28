package groupbee.comment.service.feign;

import groupbee.comment.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "Employee 정보", url = "${FEIGN_BASE_URL}",configuration = FeignConfig.class)
public interface EmployeeFeignClient {
    @GetMapping("/api/employee/info")
    public Map<String,Object> getUserInfo();
}
