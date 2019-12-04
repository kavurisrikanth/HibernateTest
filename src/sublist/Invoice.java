package sublist;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Invoice")
@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @ManyToOne
    private Customer customer;

    @NotNull
    private double amount;

    @NotNull
    private boolean status;
}
