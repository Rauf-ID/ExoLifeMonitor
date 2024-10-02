/*
 * This file is part of ExoLifeMonitor-Java.
 *
 * ExoLifeMonitor-Java is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ExoLifeMonitor-Java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ExoLifeMonitor-Java.  If not, see <https://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2024 Rauf Agaguliev
 */

package com.elm.exolifemonitor.service;

import com.elm.exolifemonitor.model.Resources;
import com.elm.exolifemonitor.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public void processResourceData(Resources resource) {
//        resource.setReceivedAt(LocalDateTime.now());
        resourceRepository.save(resource);
    }

    public void processBatchResourceData(List<Resources> resources) {
//        resources.forEach(resource -> resource.setReceivedAt(LocalDateTime.now()));
        resourceRepository.saveAll(resources);
    }

    public List<Resources> getAllResources() {
        return resourceRepository.findAll();
    }

    public List<Resources> getResourcesByStation(Long stationId) {
        return resourceRepository.findByStationId(stationId);
    }
}
