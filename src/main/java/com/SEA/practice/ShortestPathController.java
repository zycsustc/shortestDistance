package com.SEA.practice;

import com.SEA.practice.modules.Vertex;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class ShortestPathController {

    private final ShortestPathService shortestPathService = new ShortestPathService();

    @GetMapping("/shortestPath/{source}/{target}")
    public String getShortestPath(@PathVariable String source, @PathVariable String target) {
        LinkedList<Vertex> result = shortestPathService.getShortestPath(source, target);
        return result == null ? "NO SUCH ROUTE" : result.toString();
    }

    @GetMapping("/shortestPath/stops/{condition}/{source}/{target}/{number}")
    public String getPathsWithConditionOnStops(
            @PathVariable String condition, @PathVariable String number, @PathVariable String source, @PathVariable String target) {
        ArrayList<String> paths = shortestPathService.getPathsWithConditionOnStops(source, target, condition, Integer.parseInt(number));
        StringBuilder stringBuilder = new StringBuilder();
        if (paths == null) {
            stringBuilder.append("NO SUCH ROUTE");
        } else {
            for (String path : paths) {
                stringBuilder.append(path, 1, path.length() - 1);
                stringBuilder.append(" / ");
            }
            stringBuilder.append(" There are totally ").append(paths.size()).append(" trips.");
        }
        return stringBuilder.toString();
    }

    @GetMapping("/shortestPath/exactPath/{pathStops}")
    public String getDistanceOfExactPath(@PathVariable String pathStops) {
        return shortestPathService.getDistanceOfExactPath(new ArrayList<>(Arrays.asList(pathStops.split(""))));
    }

    @GetMapping(value = "/shortestPath/maxDistance/{source}/{target}/{maxDistance}")
    public String getPathsWithMaxDistance(@PathVariable String source, @PathVariable String target, @PathVariable String maxDistance) {
        ArrayList<String> result = shortestPathService.getPathsWithMaxDistance(source, target, Integer.parseInt(maxDistance));
        StringBuilder stringBuilder = new StringBuilder();
        if (result == null) {
            stringBuilder.append("NO SUCH ROUTE");
        } else {
            for (String path : result) {
                stringBuilder.append(path, 1, path.length() - 1);
                stringBuilder.append(" / ");
            }
            stringBuilder.append(" There are totally ").append(result.size()).append(" trips.");
        }
        return stringBuilder.toString();
    }
}
