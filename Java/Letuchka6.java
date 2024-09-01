-------------------------------------------------- pom.xml (mqtt-publisher) --------------------------------------------------
  <?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>mqtt-publisher</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.eclipse.paho</groupId>
            <artifactId>org.eclipse.paho.client.mqttv3</artifactId>
            <version>1.2.5</version>
        </dependency>
    </dependencies>
</project>


-------------------------------------------------- MqttPublisher.java --------------------------------------------------
    package com.example;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Scanner;

public class MqttPublisher {
    public static void main(String[] args) {
        String broker = "tcp://test.mosquitto.org:1883";
        String topic = "iu9/trofimenko";
        String clientId = "JavaPublisher";

        try {
            MqttClient client = new MqttClient(broker, clientId);
            client.connect();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Введите коэффициенты a, b и c через пробел: ");
                String input = scanner.nextLine();
                MqttMessage message = new MqttMessage(input.getBytes());
                client.publish(topic, message);
                System.out.println("Сообщение опубликовано.");
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}


-------------------------------------------------- pom.xml (mqtt-subscriber) --------------------------------------------------
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>mqtt-subscriber</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.eclipse.paho</groupId>
            <artifactId>org.eclipse.paho.client.mqttv3</artifactId>
            <version>1.2.5</version>
        </dependency>
    </dependencies>
</project>


-------------------------------------------------- MqttSubscriber.java --------------------------------------------------
package com.example;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttSubscriber {
    public static void main(String[] args) {
        String broker = "tcp://test.mosquitto.org:1883";
        String topic = "iu9/trofimenko";
        String clientId = "JavaSubscriber";

        try {
            MqttClient client = new MqttClient(broker, clientId);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Соединение потеряно: " + cause.getMessage());
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());
                    System.out.println("Получено сообщение: " + payload);

                    String[] coefficients = payload.split(" ");
                    if (coefficients.length == 3) {
                        try {
                            double a = Double.parseDouble(coefficients[0]);
                            double b = Double.parseDouble(coefficients[1]);
                            double c = Double.parseDouble(coefficients[2]);
                            solveQuadraticEquation(a, b, c);
                        } catch (NumberFormatException e) {
                            System.out.println("Ошибка в формате коэффициентов.");
                        }
                    } else {
                        System.out.println("Неверное количество коэффициентов.");
                    }
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            });

            client.connect();
            client.subscribe(topic);
            System.out.println("Подписка на топик: " + topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private static void solveQuadraticEquation(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c;
        if (discriminant > 0) {
            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            System.out.println("Корни уравнения: " + root1 + " и " + root2);
        } else if (discriminant == 0) {
            double root = -b / (2 * a);
            System.out.println("Единственный корень: " + root);
        } else {
            System.out.println("Корней нет.");
        }
    }
}
