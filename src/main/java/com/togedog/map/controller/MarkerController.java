package com.togedog.map.controller;

import com.togedog.redis.MarkerService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class MarkerController {
    private final MarkerService markerService;

    public MarkerController(MarkerService markerService) {
        this.markerService = markerService;
    }

    @GetMapping("/markers")
    public ResponseEntity<List<MarkerService.Marker>> getMarkers() {
        // Redis에서 마커 키들을 가져와 해당 마커 정보를 반환
        Set<String> markerKeys = markerService.getKeysByPattern("marker:*");
        List<MarkerService.Marker> markers = markerKeys.stream()
                .map(key -> markerService.getMarker(key))
                .collect(Collectors.toList());
        return ResponseEntity.ok(markers);
    }
//레디스에 저장하도록
    //마커 저장을 위는 매칭 데이터 가져오도록
    @PostMapping("/saveMarker")
    public ResponseEntity<String> saveMarker(@RequestBody LocationRequest locationRequest, Authentication authentication) {
        double latitude = locationRequest.getLatitude();
        double longitude = locationRequest.getLongitude();
        String userEmail = authentication.getName();

        // latitude와 longitude를 문자열로 변환하여 키 구성
        String markerKey = "marker:" + userEmail + ":" + latitude + ":" + longitude;

        // 위치 정보를 저장할 때 이메일도 함께 저장
        markerService.saveMarker(markerKey, latitude, longitude, userEmail);

        return ResponseEntity.ok("Marker saved successfully");
    }

    @Getter
    @Setter
    public static class LocationRequest {
        private double latitude;
        private double longitude;

        public LocationRequest() {
        }

        public LocationRequest(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}