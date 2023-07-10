package com.secretroomwebsite.product;

import com.secretroomwebsite.productSizes.Size;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "product_characteristics")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Characteristics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "size_id", referencedColumnName = "id")
//    private Size size;
//



}
