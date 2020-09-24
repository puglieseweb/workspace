package io.confluent.developer.spring.avro;

import com.puglieseweb.core.event.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class KafkaController {
  private final Producer producer;

  @Autowired
  KafkaController(Producer producer) {
    this.producer = producer;
  }

  @PostMapping(value = "/publish")
  public void sendMessageToKafkaTopic(@RequestParam("name") String name, @RequestParam("age") Integer age) {

    User user = User.newBuilder().setName(name).setAge(age).build();
    this.producer.sendMessage(user);
  }
}