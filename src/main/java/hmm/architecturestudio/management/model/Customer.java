package hmm.architecturestudio.management.model;

import javax.persistence.*;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String cif;
    private String taxResidence;
    private String phone;
    private String population;
    private String email;


}
