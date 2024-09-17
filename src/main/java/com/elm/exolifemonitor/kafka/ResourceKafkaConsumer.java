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

package com.elm.exolifemonitor.kafka;

import com.elm.exolifemonitor.dto.ResourcesDTO;
import com.elm.exolifemonitor.mapper.ResourceMapper;
import com.elm.exolifemonitor.model.Resources;
import com.elm.exolifemonitor.service.ResourceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResourceKafkaConsumer {
    private static final Logger log = LogManager.getLogger(ResourceKafkaConsumer.class);

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceMapper resourceMapper;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "resource-data-topic", groupId = "group_id", containerFactory = "kafkaListenerContainerFactory")
    public void consume(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        try {
            for (ConsumerRecord<String, String> record : records) {
                ResourcesDTO resourcesDTO = objectMapper.readValue(record.value(), ResourcesDTO.class);
                Resources resource = resourceMapper.toEntity(resourcesDTO);
                resourceService.processResourceData(resource);
            }
            ack.acknowledge();
        } catch (Exception e) {
            log.error("e: ", e);
        }
    }

}
