package com.itxiop.transport.infrastructure.repository.route;

import com.itxiop.transport.domain.city.repository.CityRepositoryPort;
import com.itxiop.transport.domain.exceptions.CoreRuntimeException;
import com.itxiop.transport.domain.exceptions.DomainException;
import com.itxiop.transport.domain.entities.Route;
import com.itxiop.transport.domain.exceptions.ResourceNotFoundException;
import com.itxiop.transport.domain.route.repository.RouteRepositoryPort;
import com.itxiop.transport.domain.vo.TransportTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.DomainNameUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.management.InstanceNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class RouteRepositoryFileAdapter implements RouteRepositoryPort, InitializingBean {

  @Value("${graph.folder}")
  private String graphFolder;

  private final CityRepositoryPort cityRepositoryPort;

  private final Map<TransportTypeEnum, List<Route>> routeBook = new HashMap<>();

  @Override
  public void afterPropertiesSet() throws Exception {
    readAllGraphs(graphFolder);
  }

  private void readAllGraphs(String folderPath) throws IOException {

    log.trace("Loading graph folder: [{}]", folderPath);
    try (Stream<Path> paths = Files.walk(new ClassPathResource(folderPath).getFile().toPath())) {
      paths.filter(Files::isRegularFile)
          .forEach(file -> {
            try {
              Optional<TransportTypeEnum> candidateTransportType = Arrays.stream(TransportTypeEnum.values())
                  .filter(f -> f.name().equalsIgnoreCase(file.getFileName().toString().replace(".graph", ""))).findFirst();
              if (candidateTransportType.isPresent()) {
                readGraph(candidateTransportType.get(), file);
              }
            } catch (IOException e) {
              throw new CoreRuntimeException("Error reading file: " + file, e);
            } catch (ResourceNotFoundException e) {
                throw new CoreRuntimeException("Graph file references missing resources", e);
            }
          });
    }
  }

  private void readGraph(TransportTypeEnum transportTypeEnum, Path filePath) throws IOException, ResourceNotFoundException {

    List<Route> routeList = new ArrayList<>();
    routeBook.put(transportTypeEnum, routeList);
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        String origin = parts[0];
        String destination = parts[1];
        Duration cost = Duration.of(Long.parseLong(parts[2]), ChronoUnit.HOURS);
        routeList.add(
            Route.of(UUID.randomUUID(), cityRepositoryPort.findByCityCode(origin), cityRepositoryPort.findByCityCode(destination), transportTypeEnum, cost));
      }
    }
  }

  @Override
  public Map<TransportTypeEnum, List<Route>> findAvailableRoutes(List<TransportTypeEnum> transportTypeFilter) {
    return routeBook.entrySet().stream().filter(entry -> transportTypeFilter.contains(entry.getKey()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }


}
