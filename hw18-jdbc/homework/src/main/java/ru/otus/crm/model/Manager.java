package ru.otus.crm.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Manager {
    @Id
    private Long no;

    private String label;
    private String param1;

    public Manager(String label) {
        this.label = label;
    }

    public Manager(Long no, String label, String param1) {
        this.no = no;
        this.label = label;
        this.param1 = param1;
    }

    @Override
    public String toString() {
        return "Manager{" + "no=" + no + ", label='" + label + '\'' + '}';
    }
}
