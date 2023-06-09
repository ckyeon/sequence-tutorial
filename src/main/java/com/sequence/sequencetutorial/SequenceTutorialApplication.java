package com.sequence.sequencetutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SequenceTutorialApplication {

  public static void main(String[] args) {
    SpringApplication.run(SequenceTutorialApplication.class, args);
  }

}
