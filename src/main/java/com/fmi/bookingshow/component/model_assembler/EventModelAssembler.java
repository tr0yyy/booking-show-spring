package com.fmi.bookingshow.component.model_assembler;

import com.fmi.bookingshow.controller.EventController;
import com.fmi.bookingshow.dto.event.OutputEventDto;
import com.fmi.bookingshow.model.EventEntity;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EventModelAssembler implements RepresentationModelAssembler<OutputEventDto, EntityModel<OutputEventDto>> {
    @Override
    public EntityModel<OutputEventDto> toModel(OutputEventDto entity) {
        return EntityModel.of(entity,
                linkTo(methodOn(EventController.class).getEventById(entity.eventId)).withSelfRel(),
                linkTo(methodOn(EventController.class).getAllEvents()).withRel("books"));
    }
}
