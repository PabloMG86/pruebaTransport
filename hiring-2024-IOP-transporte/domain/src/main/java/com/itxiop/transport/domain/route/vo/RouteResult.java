package com.itxiop.transport.domain.route.vo;

import java.time.Duration;
import java.util.List;

import com.itxiop.transport.domain.entities.Route;

public record RouteResult(List<Route> route, Duration cost) {}
