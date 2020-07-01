package lebron;


import com.dubbo.api.TestService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @Reference
    private TestService testService;

    @GetMapping("/test")
    public String test() {
        RpcContext.getContext().setAttachment("dubboApplication", "consumer-sg");
        testService.test();
        return "sg";
    }

    @GetMapping("/test2")
    public String test2() {
        testService.test2();
        return "sg";
    }
}
