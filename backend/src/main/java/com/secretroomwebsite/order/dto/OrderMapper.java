    package com.secretroomwebsite.order.dto;

    import com.secretroomwebsite.order.Order;
    import com.secretroomwebsite.order.items.OrderItem;
    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;
    import org.mapstruct.factory.Mappers;
    import org.springframework.stereotype.Component;

    @Mapper(componentModel = "spring")
    public interface OrderMapper {

        OrderMapper INSTANCE = Mappers.getMapper( OrderMapper.class );



        Order OrderDTOtoOrder(OrderRequestDTO orderDTO);

    //    @Mapping(target = "order", ignore = true)
    //    OrderItem toEntity(OrderItemDTO dto);
    //
    //    // Обратные преобразования (если нужны)
    //    OrderRequestDTO toDTO(Order order);
    //    OrderItemDTO toDTO(OrderItem orderItem);
    }
