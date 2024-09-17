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

package com.elm.exolifemonitor.mapper;

import com.elm.exolifemonitor.dto.ResourcesDTO;
import com.elm.exolifemonitor.model.Resources;
import com.elm.exolifemonitor.model.Station;
import com.elm.exolifemonitor.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {

    @Autowired
    private StationRepository stationRepository;

    public Resources toEntity(ResourcesDTO dto) {
        Station station = stationRepository.findById(dto.getStation())
                .orElseThrow(() -> new RuntimeException("Station not found"));

        Resources resource = new Resources();
        resource.setId(dto.getId());
        resource.setType(dto.getType());
        resource.setCurrentLevel(dto.getCurrentLevel());
        resource.setCapacity(dto.getCapacity());
        resource.setStation(station);

        return resource;
    }

}
