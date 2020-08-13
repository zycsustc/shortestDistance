package com.SEA.practice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@CrossOrigin
@RestController
public class ShortestPathController {

    private final ShortestPathService shortestPathService = new ShortestPathService();

    @GetMapping("/shortestPath/{source}/{target}")
    public String getShortestPath(@PathVariable String source, @PathVariable String target) {
        LinkedList<Vertex> result = shortestPathService.getShortestPath(source, target);
        return result==null ? "NO SUCH ROUTE" : result.toString();
    }

    @GetMapping("/shortestPath/stops/{condition}/{number}/{source}/{target}")
    public String getPathsWithConditionOnStops(
            @PathVariable String condition, @PathVariable String number, @PathVariable String source, @PathVariable String target) {
        ArrayList<LinkedList<Vertex>> paths = shortestPathService.getPathsWithConditionOnStops(source, target, condition, Integer.parseInt(number));
        StringBuilder stringBuilder = new StringBuilder();
        if(paths == null){
            stringBuilder.append("NO SUCH ROUTE");
        } else {
            for(LinkedList<Vertex> path: paths){
                stringBuilder.append(path.toString(), 1, path.toString().length()-1);
                stringBuilder.append("/ ");
            }
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    @GetMapping("/shortestPath/exactPath/{pathStops}")
    public String getDistanceOfExactPath(@PathVariable String pathStops) {
        return shortestPathService.getDistanceOfExactPath(Arrays.asList(pathStops.split("")));
    }

    @GetMapping("/shortestPath/exactNumberStops/{source}/{target}/{stops}")
    public String getPathsWithExactNumberOfStops(@PathVariable String source, @PathVariable String target, @PathVariable String stops) {
        Set<String> result = shortestPathService.getAllPathsWithExactStops(source, target, Integer.parseInt(stops));
        return result.isEmpty() ? "NO SUCH ROUTE" : result.toString().substring(1, result.toString().length()-1);
    }

    @GetMapping(value = "/shortestPath/maxDistance/{source}/{target}/{maxDistance}")
    public String getPathsWithMaxDistance(@PathVariable String source, @PathVariable String target, @PathVariable String maxDistance) {
        Set<String> result = shortestPathService.getPathsWithMaxDistance(source, target, Integer.parseInt(maxDistance));
        return result.isEmpty() ? "NO SUCH ROUTE" : result.toString().substring(1, result.toString().length()-1);
    }
}
