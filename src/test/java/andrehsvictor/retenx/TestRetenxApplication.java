package andrehsvictor.retenx;

import org.springframework.boot.SpringApplication;

public class TestRetenxApplication {

	public static void main(String[] args) {
		SpringApplication.from(RetenxApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
