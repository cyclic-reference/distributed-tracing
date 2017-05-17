package io.acari.service.zulu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
class RestEndpoint {

    private DiscoveryClient discoStu;
    private MessagingSource messagingSource;

    @Autowired
    public RestEndpoint(DiscoveryClient discoStu, MessagingSource messagingSource) {
        this.discoStu = discoStu;
        this.messagingSource = messagingSource;
    }

    @RequestMapping("/")
    public ResponseEntity<String> get() {
        List<String> services = discoStu.getServices();
        messagingSource.sendMessage("Zulu Stream Sending " + services + " @ " + Instant.now());
        return ResponseEntity.ok(services.toString());
    }
}