package come.omega.omagedecode;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import come.omega.omagedecode.*;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.InputStream;

@SpringBootApplication
public class OmageDecodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OmageDecodeApplication.class, args);
	}
	@Autowired
	private MinIo minIo;
	@Autowired
	private MinIoItem minIoItem;
	@Autowired
	private Rabbitmq rabbitmq;

	private int num = 0;
	public void run(String... args) throws Exception{
		int limit=5;
		while(true){
			if(num<limit) {
				num=num+1;
				String message=rabbitmq.getInfo();
				new Thread() {
					@SneakyThrows
					@Override
					public void run() {
						InputStream stream = minIo.getObject(minIoItem.bucket1080, message);
						File file360p = Encode.encode(stream, message, 1);
						minIo.putObject(file360p, minIoItem.bucket360, message);
						File file720p = Encode.encode(stream, message, 2);
						minIo.putObject(file720p, minIoItem.bucket720, message);
						num=num-1;
					}
				}.start();
			}
			Thread.sleep(200);

		}
	}
}
