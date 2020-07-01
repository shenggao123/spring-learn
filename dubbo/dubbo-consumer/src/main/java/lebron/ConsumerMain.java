package lebron;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ConsumerMain {

    public static void main(String[] args) throws IOException {
        SpringApplication application = new SpringApplication(ConsumerMain.class);
        application.run(args);
//        SpringApplication.run(Main.class, args);
//        System.in.read();

//        System.out.println(URLDecoder.decode("dubbo%3A%2F%2F192.168.20.1%3A20880%2Fcom.dubbo.api.TestService%3Fanyhost%3Dtrue%26application%3Dsentinel-dubbo%26bean.name%3DServiceBean%3Acom.dubbo.api.TestService%26deprecated%3Dfalse%26dubbo%3D2.0.2%26dynamic%3Dtrue%26generic%3Dfalse%26interface%3Dcom.dubbo.api.TestService%26methods%3Dtest%26owner%3Dsg%26pid%3D16808%26register%3Dtrue%26release%3D2.7.2%26side%3Dprovider%26timestamp%3D1593537431219"));
    }
}
